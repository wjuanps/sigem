/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.bo;

import br.com.jsampaio.sigem.model.dao.UsuarioDAO;
import br.com.jsampaio.sigem.model.vo.Usuario;
import br.com.jsampaio.sigem.util.Log;
import br.com.jsampaio.sigem.util.SigemUtil;

/**
 *
 * @author Janilson
 */
public class Autenticacao {

    private final UsuarioDAO udao = new UsuarioDAO();

    public Usuario autenticar(Usuario usuario) {
        try {
            if (usuario == null) {
                return null;
            }

            if (usuario.getLogin().isEmpty() || usuario.getSenha().isEmpty()
                    || usuario.getLogin() == null || usuario.getSenha() == null) {
                return null;
            }

            final Usuario usuarioAutenticado = udao.autenticar(usuario);
            if (usuarioAutenticado != null) {
                return usuarioAutenticado;
            }
        } catch (RuntimeException r) {
            Log.saveLog(r, Autenticacao.class);
            return null;
        }
        return null;
    }

    /**
     *
     * @param usuario
     * @param novaSenha
     * @return
     */
    public Usuario confirmarTrocaSenha(Usuario usuario, String novaSenha) {
        try {
            usuario.setSenha(SigemUtil.criptografar(novaSenha));
            final UsuarioDAO udao = new UsuarioDAO();
            usuario = udao.salvar(usuario);
            if (usuario != null) {
                return usuario;
            }
        } catch (RuntimeException r) {
            Log.saveLog(r, Autenticacao.class);
        }
        return null;
    }

    /**
     *
     * @param usuario
     * @param senhaAntiga
     * @param novaSenha
     * @return
     */
    public String validarCampos(Usuario usuario, String senhaAntiga, String novaSenha) {
        try {
            if (usuario == null) {
                return "Usuário Inválido";
            }

            if (senhaAntiga == null || senhaAntiga.isEmpty()) {
                return "Informe a Senha antiga";
            }

            if (novaSenha == null || novaSenha.isEmpty()) {
                return "Informe a nova Senha";
            }

            if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
                return "Usuário inválido";
            }

            senhaAntiga = SigemUtil.criptografar(senhaAntiga);
            if (!senhaAntiga.equals(usuario.getSenha())) {
                return "Senha Antiga está incorreta";
            }
        } catch (RuntimeException r) {
            Log.saveLog(r, Autenticacao.class);
            return r.getMessage();
        }
        return "";
    }
}
