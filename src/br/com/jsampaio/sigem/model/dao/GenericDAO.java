/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.dao;

import br.com.jsampaio.sigem.util.HibernateUtil;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Sophia
 * @param <T>
 */
public abstract class GenericDAO<T> {

    private final Class<T> classe;

    public GenericDAO() {
        this.classe = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * 
     * @return 
     */
    public static GenericDAO getInstance() {
        return new GenericDAO() {};
    }
    
    /**
     *
     * @param t
     * @return
     */
    public T salvar(T t) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            T object = (T) session.merge(t);
            transaction.commit();
            return object;
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
     * @param t
     */
    public void atualizar(T t) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.update(t);
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
     * @param codigo
     * @return
     */
    public T pesquisar(Long codigo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria criteria = session.createCriteria(this.classe);
            criteria.add(Restrictions.idEq(codigo));
            T t = (T) criteria.uniqueResult();
            return t;
        } catch (RuntimeException r) {
            throw r;
        } finally {
            session.close();
        }
    }
    
    /**
     *
     * @return
     */
    public List<T> getList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria criteria = session.createCriteria(this.classe);
            List<T> lista = criteria.list();
            return lista;
        } catch (RuntimeException r) {
            throw r;
        } finally {
            session.close();
        }
    }
    
    /**
     * 
     * @param t 
     * @return  
     */
    public boolean delete(T t) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(t);
            transaction.commit();
            return true;
        } catch (RuntimeException r) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw r;
        } finally {
            session.close();
        }
    }
}