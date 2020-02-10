/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.bo;

import br.com.jsampaio.sigem.model.dao.AdministradorDAO;
import br.com.jsampaio.sigem.model.dao.UnidadeCursoDAO;
import br.com.jsampaio.sigem.model.dao.UsuarioDAO;
import br.com.jsampaio.sigem.model.vo.Pessoa;
import br.com.jsampaio.sigem.model.vo.Usuario;
import br.com.jsampaio.sigem.util.Log;
import br.com.jsampaio.sigem.util.SigemUtil;
import java.util.Calendar;

/**
 *
 * @author Janilson
 */
public class AdministradorBO {
    
    /**
     * 
     * @return 
     */
    public boolean cadastrarAdministrador() {
        try {
            Usuario usuario = new Usuario();
            usuario.setNome("Administrador");
            usuario.setMatricula(Calendar.getInstance().getTimeInMillis());
            usuario.setEmail("administrador@sigem.com");
            usuario.setTelefone("(91) 9 0000-0000");
            usuario.setTipo(Pessoa.Tipo.Administrador);
            usuario.setUnidadeCurso(new UnidadeCursoDAO().getUnidadeCurso(0L));
            usuario.setAtivo(true);
            usuario.setLogin("Admin");
            usuario.setSenha(SigemUtil.criptografar("admin"));
            
            UsuarioDAO udao = new UsuarioDAO();
            usuario = udao.salvar(usuario);
            
            return (usuario != null);
            
        } catch (RuntimeException e) {
            Log.saveLog(e, AdministradorBO.class);
        }
        return false;
    }
    
    /**
     * 
     * @return 
     */
    public boolean existeAdministrador() {
        try {
            final AdministradorDAO adao = new AdministradorDAO();
            final Usuario administrador = adao.getAdministrador();
            return (administrador != null);
        } catch (RuntimeException e) {
            Log.saveLog(e, AdministradorBO.class);
        }
        return false;
    }
}