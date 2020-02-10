/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.bo;

import br.com.jsampaio.sigem.model.dao.UsuarioDAO;
import br.com.jsampaio.sigem.model.vo.Usuario;
import br.com.jsampaio.sigem.util.Log;
import java.util.List;

/**
 *
 * @author Janilson
 */
public class UsuarioBO {

    /**
     * 
     * @param pesquisa
     * @param paginacao
     * @param ativo
     * @return 
     */
    public List<Usuario> getUsuarios(String pesquisa, Paginacao paginacao, String ativo) {
        try {
            final UsuarioDAO udao = new UsuarioDAO();
            return udao.getUsuarios(pesquisa, paginacao, ativo);
        } catch (RuntimeException r) {
            Log.saveLog(r, UsuarioBO.class);
        }
        return null;
    }
    
    /**
     * 
     * @param matricula
     * @return 
     */
    public Usuario getUsuario(Long matricula) {
        try {
            final UsuarioDAO udao = new UsuarioDAO();
            return udao.getUsuario(matricula);
        } catch (RuntimeException r) {
            Log.saveLog(r, UsuarioBO.class);
        }
        return null;
    }
    
    /**
     * 
     * @param usuario 
     */
    public void atualizarUsuario(Usuario usuario) {
        try {
            final UsuarioDAO udao = new UsuarioDAO();
            udao.atualizar(usuario);
        } catch (RuntimeException r) {
            Log.saveLog(r, UsuarioBO.class);
        }
    }
}
