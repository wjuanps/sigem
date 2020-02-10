package br.com.jsampaio.sigem.model.dao;

import br.com.jsampaio.sigem.model.bo.Paginacao;
import br.com.jsampaio.sigem.util.HibernateUtil;
import br.com.jsampaio.sigem.model.vo.Material;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Janilson
 */
public class MaterialDAO extends GenericDAO<Material> implements Serializable {

    /**
     *
     * @param descricao
     * @param paginacao
     * @param ativo
     * @return
     */
    public List<Material> getList(String descricao, Paginacao paginacao, String ativo) {
        Criteria criteria = criarCriteria(descricao, ativo);

        if (paginacao == null) {
            return criteria.list();
        }

        paginacao.setTotalResultados(getQuantidadeDeRegistros(descricao, ativo));

        criteria.setFirstResult(paginacao.inicio());
        criteria.setMaxResults(paginacao.getItensPorPagina());

        return criteria.list();
    }

    /**
     *
     * @param descricao
     * @param ativo
     * @return
     */
    public Criteria criarCriteria(String descricao, String ativo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria criteria = session.createCriteria(Material.class)
                    .add(Restrictions.ilike("descricao", descricao, MatchMode.ANYWHERE))
                    .addOrder(Order.asc("descricao"));

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
     * @param descricao
     * @param ativo
     * @return
     */
    public int getQuantidadeDeRegistros(String descricao, String ativo) {
        Criteria criteria = criarCriteria(descricao, ativo);
        criteria.setProjection(Projections.rowCount());
        return ((Number) criteria.uniqueResult()).intValue();
    }
    
    /**
     * 
     * @return 
     */
    public List<Material> listEstoqueMinimo() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createSQLQuery("SELECT * FROM material WHERE ((material.quantidade * 100) / material.quantidade_maxima) <= 20")
                    .addEntity(Material.class).list();
        } catch (RuntimeException r) {
            throw r;
        } finally {
            session.close();
        }
    }
}
