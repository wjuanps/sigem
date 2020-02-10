/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.dao;

import br.com.jsampaio.sigem.model.bo.Paginacao;
import br.com.jsampaio.sigem.model.vo.Pessoa;
import br.com.jsampaio.sigem.util.HibernateUtil;
import br.com.jsampaio.sigem.model.vo.Solicitante;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Janilson
 */
public class SolicitanteDAO extends GenericDAO<Solicitante> implements Serializable {
 
    /**
     * 
     * @param pesquisa
     * @param paginacao
     * @param ativo
     * @param tipo
     * @return 
     */
    public List<Solicitante> getList(String pesquisa, Paginacao paginacao, String ativo, Pessoa.Tipo tipo) {
        Criteria criteria = criarCriteria(pesquisa, ativo, tipo);
        
        paginacao.setTotalResultados(getQuantidadeDeRregistros(pesquisa, ativo, tipo));
        
        criteria.setFirstResult(paginacao.inicio());
        criteria.setMaxResults(paginacao.getItensPorPagina());
        
        return criteria.list();
    }
    
    /**
     * 
     * @param pesquisa
     * @return 
     */
    private int getQuantidadeDeRregistros(String pesquisa, String ativo, Pessoa.Tipo tipo) {
        Criteria criteria = criarCriteria(pesquisa, ativo, tipo);
        criteria.setProjection(Projections.rowCount());
        return ((Number) criteria.uniqueResult()).intValue();
    }
    
    /**
     * 
     * @param pesquisa
     * @return 
     */
    private Criteria criarCriteria(String pesquisa, String ativo, Pessoa.Tipo tipo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria criteria = session.createCriteria(Solicitante.class)
                    .createAlias("unidadeCurso", "uc")
                    .add(Restrictions.or(
                            Restrictions.ilike("nome", pesquisa, MatchMode.ANYWHERE),
                            Restrictions.ilike("uc.unidadeCurso", pesquisa, MatchMode.ANYWHERE)
                    ));
            
            if (ativo != null && !ativo.isEmpty()) {
                criteria.add(Restrictions.eq("ativo", ativo.equals("Ativos")));
            }
            
            if (tipo != null && !tipo.equals(Pessoa.Tipo.Todos)) {
                criteria.add(Restrictions.eq("tipo", tipo));
            }
            
            return criteria;
            
        } catch (RuntimeException r) {
            throw r;
        }
    }
    
    /**
     * 
     * @param matricula
     * @return 
     */
    public Solicitante getSolicitante(Long matricula) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (Solicitante) session.createCriteria(Solicitante.class)
                    .add(Restrictions.eq("matricula", matricula))
                    .uniqueResult();
        } catch (RuntimeException r) {
            throw r;
        } finally {
            session.close();
        }
    }
}