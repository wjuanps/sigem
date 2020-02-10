/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.dao;

import br.com.jsampaio.sigem.model.bo.Paginacao;
import br.com.jsampaio.sigem.util.HibernateUtil;
import br.com.jsampaio.sigem.model.vo.Usuario;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Janilson
 */
public class UsuarioDAO extends GenericDAO<Usuario> implements Serializable {

    /**
     * 
     * @param usuario
     * @return 
     */
    public Usuario autenticar(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            final StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM usuario WHERE ")
                    .append("BINARY senha = ? AND BINARY login = ? ")
                    .append("AND is_ativo = 1");

            final Query query = session.createSQLQuery(sql.toString())
                    .addEntity(Usuario.class)
                    .setString(0, usuario.getSenha())
                    .setString(1, usuario.getLogin());

            Usuario usuarioAutenticado = (Usuario) query.uniqueResult();

            if (usuarioAutenticado != null) {
                return usuarioAutenticado;
            }
        } catch (RuntimeException r) {
            throw r;
        } finally {
            session.close();
        }
        return null;
    }

    /**
     *
     * @param search
     * @param paginacao
     * @param ativo
     * @return
     */
    public List<Usuario> getUsuarios(String search, Paginacao paginacao, String ativo) {
        Criteria criteria = criarCriteria(search, ativo);

        paginacao.setTotalResultados(getQuantidadeDeRregistros(search, ativo));

        criteria.setFirstResult(paginacao.inicio());
        criteria.setMaxResults(paginacao.getItensPorPagina());

        return criteria.list();
    }

    /**
     * 
     * @param search
     * @param ativo
     * @return 
     */
    private Criteria criarCriteria(String search, String ativo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            final Criteria criteria = session.createCriteria(Usuario.class)
                    .createAlias("unidadeCurso", "uc")
                    .add(Restrictions.or(
                            Restrictions.ilike("nome", search, MatchMode.ANYWHERE),
                            Restrictions.ilike("uc.unidadeCurso", search, MatchMode.ANYWHERE)
                    ));

            if (ativo != null && !ativo.isEmpty()) {
                criteria.add(Restrictions.eq("ativo", ativo.equals("Ativos")));
            }

            return criteria;

        } catch (RuntimeException r) {
            throw r;
        }
    }

    /**
     *
     * @param search
     * @return
     */
    private int getQuantidadeDeRregistros(String search, String ativo) {
        Criteria criteria = criarCriteria(search, ativo);
        criteria.setProjection(Projections.rowCount());
        return ((Number) criteria.uniqueResult()).intValue();
    }

    /**
     *
     * @param search
     * @return
     */
    public Usuario getUsuario(Object search) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Long matricula = 0L;
        try {
            matricula = Long.valueOf(search.toString());
        } catch (NumberFormatException n) {
        }

        try {
            return (Usuario) session.createCriteria(Usuario.class)
                    .add(Restrictions.or(
                            Restrictions.ilike("login", search.toString(), MatchMode.ANYWHERE),
                            Restrictions.eq("matricula", matricula)
                    ))
                    .uniqueResult();
        } catch (RuntimeException r) {
            throw r;
        } finally {
            session.close();
        }
    }
}
