/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.main.Messenger;
import br.com.jsampaio.sigem.model.bo.Autenticacao;
import br.com.jsampaio.sigem.model.vo.MessageType;
import br.com.jsampaio.sigem.model.vo.Session;
import br.com.jsampaio.sigem.model.vo.Usuario;
import br.com.jsampaio.sigem.view.TelaAtualizarSenha;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Janilson
 */
public class AtualizarSenhaController {
    
    private final TelaAtualizarSenha telaAtualizarSenha;
    
    public AtualizarSenhaController() {
        this.telaAtualizarSenha = new TelaAtualizarSenha(null, true);
        
        final AtualizarSenhaListener atualizarSenhaListener = this.new AtualizarSenhaListener();
        this.telaAtualizarSenha.getBtnSalvar().addActionListener(atualizarSenhaListener);
        this.telaAtualizarSenha.getBtnCancelar().addActionListener(atualizarSenhaListener);
        this.telaAtualizarSenha.getTxtNovaSenha().addActionListener(atualizarSenhaListener);
        this.telaAtualizarSenha.getTxtSenhaAntiga().addActionListener(atualizarSenhaListener);
    }
    
    private class AtualizarSenhaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == telaAtualizarSenha.getBtnCancelar()) {
                telaAtualizarSenha.dispose();
                telaAtualizarSenha.setVisible(false);
                limparCampos();
                return;
            }
            
            if (e.getSource() == telaAtualizarSenha.getTxtSenhaAntiga()) {
                telaAtualizarSenha.getTxtNovaSenha().requestFocus();
                return;
            }
            
            if (e.getSource() == telaAtualizarSenha.getBtnSalvar()
                    || e.getSource() == telaAtualizarSenha.getTxtNovaSenha()) {
                final Autenticacao autenticacao = new Autenticacao();
                
                final String senhaAntiga = String.valueOf(telaAtualizarSenha.getTxtSenhaAntiga().getPassword());
                final String novaSenha = String.valueOf(telaAtualizarSenha.getTxtNovaSenha().getPassword());
                final String msg = autenticacao.validarCampos(Session.getUsuario(), senhaAntiga, novaSenha);
                if (msg.isEmpty()) {
                    final Usuario usuarioAtualzado = autenticacao.confirmarTrocaSenha(Session.getUsuario(), novaSenha);
                    if (usuarioAtualzado != null) {
                        Messenger.showMessage("Cadastro salvo com sucesso", MessageType.INFORMATION);
                        Session.setUsuario(usuarioAtualzado);
                        telaAtualizarSenha.dispose();
                        telaAtualizarSenha.setVisible(false);
                        limparCampos();
                    }
                } else {
                    Messenger.showMessage(msg, MessageType.ERROR);
                }
            }
        }
    }

    public void limparCampos() {
        this.telaAtualizarSenha.getTxtNovaSenha().setText(null);
        this.telaAtualizarSenha.getTxtSenhaAntiga().setText(null);
    }
    
    public TelaAtualizarSenha getTelaAtualizarSenha() {
        return telaAtualizarSenha;
    }
    
}
