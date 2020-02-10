/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.dao;

import br.com.jsampaio.sigem.model.bo.Paginacao;
import br.com.jsampaio.sigem.util.HibernateUtil;
import br.com.jsampaio.sigem.model.vo.UnidadeCurso;
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
public class UnidadeCursoDAO extends GenericDAO<UnidadeCurso> {
    
    /**
     * 
     * @param unidadeCurso
     * @param paginacao
     * @param ativo
     * @return 
     */
    public List<UnidadeCurso> getList(String unidadeCurso, Paginacao paginacao, String ativo) {
        Criteria criteria = criarCriteria(unidadeCurso, ativo);
        
        paginacao.setTotalResultados(getQuantidadeDeRegistros(unidadeCurso, ativo));
        
        criteria.setFirstResult(paginacao.inicio());
        criteria.setMaxResults(paginacao.getItensPorPagina());
        
        return criteria.list();
    }
    
    /**
     * 
     * @param unidadeCurso
     * @param ativo
     * @return 
     */
    private Criteria criarCriteria(String unidadeCurso, String ativo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria criteria = session.createCriteria(UnidadeCurso.class)
                    .add(Restrictions.ilike("unidadeCurso", unidadeCurso, MatchMode.ANYWHERE))
                    .add(Restrictions.gt("codigoUnidade", 0L));
            
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
     * @param unidadeCurso
     * @param ativo
     * @return 
     */
    private int getQuantidadeDeRegistros(String unidadeCurso, String ativo) {
        Criteria criteria = criarCriteria(unidadeCurso, ativo);
        criteria.setProjection(Projections.rowCount());
        return ((Number) criteria.uniqueResult()).intValue();
    }
    
    /**
     * 
     * @param codigo
     * @return 
     */
    public boolean hasCodigo(Long codigo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria criteria = session.createCriteria(UnidadeCurso.class)
                    .add(Restrictions.eq("codigoUnidade", codigo));
            
            return criteria.uniqueResult() != null;
            
        } catch (RuntimeException r) {
            throw r;
        } finally {
            session.close();
        }
    }
    
    /**
     * 
     * @param unidadeCurso
     * @return 
     */
    public boolean hasUnidadeCurso(final String unidadeCurso) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria criteria = session.createCriteria(UnidadeCurso.class)
                    .add(Restrictions.eq("unidadeCurso", unidadeCurso))
                    .add(Restrictions.gt("codigoUnidade", 0L));
            
            return criteria.uniqueResult() != null;
            
        } catch (RuntimeException r) {
            throw r;
        } finally {
            session.close();
        }
    }
    
    /**
     * 
     * @param codigoUnidadeCurso
     * @return 
     */
    public UnidadeCurso getUnidadeCurso(final Long codigoUnidadeCurso) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria criteria = session.createCriteria(UnidadeCurso.class)
                    .add(Restrictions.eq("codigoUnidade", codigoUnidadeCurso));
            
            UnidadeCurso unidadeCurso = (UnidadeCurso) criteria.uniqueResult();
            
            if (unidadeCurso == null) {
                unidadeCurso = new UnidadeCurso();
                unidadeCurso.setAtivo(true);
                unidadeCurso.setCodigoUnidade(0L);
                unidadeCurso.setUnidadeCurso("Administrador");
                
                super.salvar(unidadeCurso);
                return getUnidadeCurso(unidadeCurso.getCodigoUnidade());
            }
            
            return unidadeCurso;
            
        } catch (RuntimeException r) {
            throw r;
        } finally {
            session.close();
        }
    }
}
