/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.dao;

import br.com.jsampaio.sigem.model.bo.Paginacao;
import br.com.jsampaio.sigem.util.HibernateUtil;
import br.com.jsampaio.sigem.model.vo.Emprestimo;
import br.com.jsampaio.sigem.model.vo.EmprestimoEquipamento;
import br.com.jsampaio.sigem.model.vo.Equipamento;
import br.com.jsampaio.sigem.model.vo.FiltroRelatorio;
import br.com.jsampaio.sigem.model.vo.Solicitante;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Janilson
 */
public class EmprestimoDAO extends GenericDAO<Emprestimo> implements Serializable {

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-MM 00:00:00");

    /**
     *
     * @param emprestimo
     */
    public void realizarEmprestimo(Emprestimo emprestimo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(emprestimo);

            emprestimo.getEquipamentos().forEach((eq) -> {
                session.save(eq);
            });

            transaction.commit();
        } catch (RuntimeException r) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw r;
        }
    }

    /**
     *
     * @param filtro
     * @param paginacao
     * @return
     */
    public List<EmprestimoEquipamento> getEquipamentosEmprestados(FiltroRelatorio filtro, Paginacao paginacao) {
        Criteria criteria = criarCriteria(filtro, false);

        paginacao.setTotalResultados(getQuantidadeDeRegistros(filtro, false));

        criteria.setMaxResults(paginacao.getItensPorPagina());
        criteria.setFirstResult(paginacao.inicio());

        return criteria.list();
    }

    /**
     *
     * @param filtro
     * @param paginacao
     * @return
     */
    public Set<Emprestimo> getEmprestimos(FiltroRelatorio filtro, Paginacao paginacao) {
        Criteria criteria = criarCriteria(filtro, true);

        paginacao.setTotalResultados(getQuantidadeDeRegistros(filtro, true));

        criteria.setMaxResults(paginacao.getItensPorPagina());
        criteria.setFirstResult(paginacao.inicio());

        return new HashSet<>(criteria.list());
    }

    /**
     *
     * @param criteria
     * @param filtro
     * @return
     */
    private FiltroRelatorio getFiltro(Criteria criteria, FiltroRelatorio filtro) {
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

        if (filtro.getRequisitante() != null) {
            criteria.add(Restrictions.eq("sol.matricula", filtro.getRequisitante()));
        }

        if (filtro.getEquipamento() != null) {
            criteria.add(Restrictions.eq("eq.numeroTombamento", filtro.getEquipamento()));
        }

        if (filtro.getNomeSolicitante() == null) {
            filtro.setNomeSolicitante("");
        }

        return filtro;
    }

    /**
     *
     * @param filtro
     * @return
     */
    private int getQuantidadeDeRegistros(FiltroRelatorio filtro, boolean emprestimo) {
        Criteria criteria = criarCriteria(filtro, emprestimo);
        
        if (emprestimo) {
            criteria.setProjection(Projections.countDistinct("numeroProtocolo"));
        } else {
            criteria.setProjection(Projections.rowCount());
        }
        
        return ((Number) criteria.uniqueResult()).intValue();
    }

    /**
     *
     * @param filtro
     * @return
     */
    private Criteria criarCriteria(FiltroRelatorio filtro, boolean emprestimo) {
        return ((emprestimo) ? criarCriteriaEmprestimos(filtro) : criarCriteriaEquipamentosEmprestados(filtro));
    }

    /**
     *
     * @return
     */
    private Criteria criarCriteriaEquipamentosEmprestados(FiltroRelatorio filtro) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria criteria = session.createCriteria(EmprestimoEquipamento.class)
                    .createAlias("equipamento", "eq")
                    .createAlias("emprestimo", "em")
                    .createAlias("em.solicitante", "sol")
                    .createAlias("sol.unidadeCurso", "uc");

            filtro = getFiltro(criteria, filtro);

            criteria.add(Restrictions.between("dataHoraEntrega", filtro.getDataInicio(), filtro.getDataFinal()));
            criteria.addOrder(Order.asc("em.numeroProtocolo"));

            return criteria;

        } catch (RuntimeException r) {
            throw r;
        }
    }

    /**
     *
     * @return
     */
    private Criteria criarCriteriaEmprestimos(FiltroRelatorio filtro) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria criteria = session.createCriteria(Emprestimo.class)
                    .createAlias("equipamentos", "eqs")
                    .createAlias("eqs.equipamento", "eq")
                    .createAlias("solicitante", "sol")
                    .createAlias("sol.unidadeCurso", "uc");

            filtro = getFiltro(criteria, filtro);

            criteria.add(Restrictions.between("eqs.dataHoraEntrega", filtro.getDataInicio(), filtro.getDataFinal()));
            criteria.add(Restrictions.ilike("sol.nome", filtro.getNomeSolicitante(), MatchMode.ANYWHERE));
            criteria.addOrder(Order.asc("numeroProtocolo"));
            
            return criteria;

        } catch (RuntimeException r) {
            throw r;
        }
    }

    /**
     *
     * @param equipamento
     * @return
     */
    public int getQuantidadeEmprestimos(Equipamento equipamento) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return ((Number) session.createCriteria(Emprestimo.class)
                    .createAlias("equipamentos", "eqs")
                    .createAlias("eqs.equipamento", "eq")
                    .add(Restrictions.eq("eq.numeroTombamento", equipamento.getNumeroTombamento()))
                    .setProjection(Projections.count("eq.codigo"))
                    .uniqueResult()).intValue();
        } catch (RuntimeException r) {
            throw r;
        } finally {
            session.close();
        }
    }

    /**
     *
     * @param numProtocolo
     * @return
     */
    public Emprestimo getEmprestimo(Long numProtocolo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return ((Emprestimo) session.createCriteria(Emprestimo.class)
                    .add(Restrictions.eq("numeroProtocolo", numProtocolo))
                    .uniqueResult());
        } catch (NullPointerException n) {
            return null;
        } catch (RuntimeException r) {
            throw r;
        } finally {
            session.close();
        }
    }

    /**
     *
     * @param solicitante
     * @return
     */
    public Set<Emprestimo> getEmprestimoAtrasado(Solicitante solicitante) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria criteria = session.createCriteria(Emprestimo.class)
                    .createAlias("equipamentos", "eqs")
                    .createAlias("solicitante", "solicitante")
                    .add(Restrictions.and(
                            Restrictions.eq("solicitante.codigo", solicitante.getCodigo()),
                            Restrictions.eq("eqs.devolvido", false),
                            Restrictions.lt("eqs.dataHoraDevolucaoPrevista", Calendar.getInstance().getTime())
                    ));

            return new HashSet<>(criteria.list());
        } catch (RuntimeException r) {
            throw r;
        } finally {
            session.close();
        }
    }

    /**
     *
     * @param numProtocolo
     * @return
     */
    public boolean hasNumProtocolo(Long numProtocolo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createCriteria(Emprestimo.class)
                    .add(Restrictions.eq("numeroProtocolo", numProtocolo))
                    .uniqueResult() != null;
        } catch (NullPointerException n) {
            return false;
        } catch (RuntimeException r) {
            throw r;
        } finally {
            session.close();
        }
    }
}
