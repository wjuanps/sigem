/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.dao;

import br.com.jsampaio.sigem.model.bo.Paginacao;
import br.com.jsampaio.sigem.util.HibernateUtil;
import br.com.jsampaio.sigem.model.vo.Equipamento;
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
public class EquipamentoDAO extends GenericDAO<Equipamento> implements Serializable {

    /**
     *
     * @param pesquisa
     * @param filtro
     * @param paginacao
     * @param ativo
     * @return
     */
    public List<Equipamento> getList(String pesquisa, String filtro, Paginacao paginacao, String ativo) {
        Criteria criteria = criarCriteria(pesquisa, filtro, ativo);

        paginacao.setTotalResultados(getQuantidadeDeRegistros(pesquisa, filtro, ativo));

        criteria.setFirstResult(paginacao.inicio());
        criteria.setMaxResults(paginacao.getItensPorPagina());

        return criteria.list();
    }

    /**
     *
     * @param pesquisa
     * @param filtro
     * @param ativo
     * @return
     */
    private Criteria criarCriteria(String pesquisa, String filtro, String ativo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria criteria = session.createCriteria(Equipamento.class)
                    .add(Restrictions.ilike("status", filtro, MatchMode.ANYWHERE))
                    .add(Restrictions.or(
                        Restrictions.ilike("tipo", pesquisa, MatchMode.ANYWHERE),
                        Restrictions.ilike("descricao", pesquisa, MatchMode.ANYWHERE)
                    ));

            if (ativo != null && !ativo.isEmpty()) {
                criteria.add(Restrictions.eq("ativo", ativo.equals("Ativos")));
            } else if (ativo == null) {
                criteria.add(Restrictions.eq("ativo", true));
            }

            return criteria;

        } catch (RuntimeException r) {
            throw r;
        }
    }

    /**
     *
     * @param pesquisa
     * @param filtro
     * @param ativo
     * @return
     */
    private int getQuantidadeDeRegistros(String pesquisa, String filtro, String ativo) {
        Criteria criteria = criarCriteria(pesquisa, filtro, ativo);
        criteria.setProjection(Projections.rowCount());
        return ((Number) criteria.uniqueResult()).intValue();
    }

    /**
     *
     * @param numTombamento
     * @return
     */
    public Equipamento getEquipamentoPeloNumTombamento(Long numTombamento) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (Equipamento) session.createCriteria(Equipamento.class)
                    .add(Restrictions.eq("numeroTombamento", numTombamento))
                    .uniqueResult();
        } catch (RuntimeException r) {
            throw r;
        } finally {
            session.close();
        }
    }

}
