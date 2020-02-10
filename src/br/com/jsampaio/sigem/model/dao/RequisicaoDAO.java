/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.dao;

import br.com.jsampaio.sigem.model.bo.Paginacao;
import br.com.jsampaio.sigem.util.HibernateUtil;
import br.com.jsampaio.sigem.model.vo.FiltroRelatorio;
import br.com.jsampaio.sigem.model.vo.Requisicao;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

/**
 *
 * @author Janilson
 */
public class RequisicaoDAO extends GenericDAO<Requisicao> implements Serializable {

    public void salvarRequisicao(Requisicao requisicao) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(requisicao);

            requisicao.getRequisicaoMateriais().forEach((r) -> {
                session.save(r);
            });

            transaction.commit();
        } catch (RuntimeException r) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw r;
        } finally {
            session.close();
        }
    }

    /**
     *
     * @param filtro
     * @param paginacao
     * @return
     */
    public Set<Requisicao> getRequisicaos(FiltroRelatorio filtro, Paginacao paginacao) {
        Criteria criteria = criarCriteria(filtro);

        paginacao.setTotalResultados(getQuantidadeDeRegistros(filtro));

        criteria.setFirstResult(paginacao.inicio());
        criteria.setMaxResults(paginacao.getItensPorPagina());

        Set<Requisicao> requisicaos = new HashSet<>(criteria.list());
        return requisicaos;
    }
    
    /**
     * 
     * @param filtro
     * @return 
     */
    public Set<Requisicao> getRequisicaos(FiltroRelatorio filtro) {
        Criteria criteria = criarCriteria(filtro);
        Set<Requisicao> requisicaos = new HashSet<>(criteria.list());
        return requisicaos;
    }

    /**
     *
     * @param filtro
     * @return
     */
    private Criteria criarCriteria(FiltroRelatorio filtro) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria criteria = session.createCriteria(Requisicao.class)
                    .createAlias("requisicaoMateriais", "rm", JoinType.LEFT_OUTER_JOIN)
                    .createAlias("solicitante", "sol", JoinType.LEFT_OUTER_JOIN)
                    .createAlias("recebedor", "rec", JoinType.LEFT_OUTER_JOIN)
                    .createAlias("sol.unidadeCurso", "uc", JoinType.LEFT_OUTER_JOIN);

            if (filtro.getDataInicio() == null) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(1970, 1, 1);
                filtro.setDataInicio(calendar.getTime());
            }
            
            if (filtro.getDataFinal() == null) {
                filtro.setDataFinal(Calendar.getInstance().getTime());
            } 

            if (filtro.getDataInicio().compareTo(filtro.getDataFinal()) > 0) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(1970, 1, 1);
                filtro.setDataInicio(calendar.getTime());
            }
                
            criteria.add(Restrictions.between("rm.dataEntrega", filtro.getDataInicio(), filtro.getDataFinal()));

            if (filtro.getUnidadeCurso() != null) {
                criteria.add(Restrictions.eq("uc.codigo", filtro.getUnidadeCurso()));
            }

            if (filtro.getRequisitante() != null) {
                criteria.add(Restrictions.eq("sol.matricula", filtro.getRequisitante()));
            }

            if (filtro.getRecebedor() != null) {
                criteria.add(Restrictions.eq("rec.matricula", filtro.getRecebedor()));
            }

            return criteria;
        } catch (RuntimeException r) {
            throw r;
        }
    }

    private int getQuantidadeDeRegistros(FiltroRelatorio filtro) {
        Criteria criteria = criarCriteria(filtro);
        criteria.setProjection(Projections.rowCount());
        return ((Number) criteria.uniqueResult()).intValue();
    }
}
