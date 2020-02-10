/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.dao;

import br.com.jsampaio.sigem.model.vo.Pessoa;
import br.com.jsampaio.sigem.model.vo.Usuario;
import br.com.jsampaio.sigem.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Janilson
 */
public class AdministradorDAO {

    /**
     * 
     * @return 
     */
    public Usuario getAdministrador() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (Usuario) session.createCriteria(Usuario.class)
                    .add(Restrictions.and(
                            Restrictions.eq("tipo", Pessoa.Tipo.Administrador),
                            Restrictions.eq("login", "Admin")
                    ))
                    .uniqueResult();
        } catch (RuntimeException r) {
            throw r;
        } finally {
            session.close();
        }
    }
}