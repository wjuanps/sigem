/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.main.Messenger;
import br.com.jsampaio.sigem.main.Sigem;
import br.com.jsampaio.sigem.model.bo.Autenticacao;
import br.com.jsampaio.sigem.model.bo.RequisicaoBO;
import br.com.jsampaio.sigem.model.vo.Material;
import br.com.jsampaio.sigem.model.vo.MessageType;
import br.com.jsampaio.sigem.model.vo.Pessoa;
import br.com.jsampaio.sigem.model.vo.Session;
import br.com.jsampaio.sigem.model.vo.Usuario;
import br.com.jsampaio.sigem.util.SigemUtil;
import br.com.jsampaio.sigem.view.TelaAviso;
import br.com.jsampaio.sigem.view.TelaInicial;
import br.com.jsampaio.sigem.view.TelaLogin;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import javax.swing.JTextField;

/**
 *
 * @author Janilson
 */
public class LoginController {

    private final TelaLogin telaLogin;
    private TelaInicial telaInicial;
    private TelaAviso telaAviso;

    public LoginController() {
        this.telaLogin = new TelaLogin(telaInicial, true);

        final Listener listener = this.new Listener();
        this.telaLogin.getBtnSair().addActionListener(listener);
        this.telaLogin.getBtnEntrar().addActionListener(listener);
        this.telaLogin.getTxtSenha().addActionListener(listener);
        this.telaLogin.getTxtLogin().addActionListener(listener);
        this.telaLogin.getTxtSenha().addFocusListener(listener);
        this.telaLogin.getTxtLogin().addFocusListener(listener);
    }

    private class Listener implements ActionListener, FocusListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == telaLogin.getBtnSair()) {
                Sigem.closeSystem();
                return;
            }

            if (e.getSource() == telaLogin.getBtnEntrar()
                    || e.getSource() == telaLogin.getTxtSenha()) {
                Usuario usuario = new Usuario();
                usuario.setLogin(telaLogin.getTxtLogin().getText());
                usuario.setSenha(SigemUtil.criptografar(String.valueOf(telaLogin.getTxtSenha().getPassword())));

                final Autenticacao autenticacao = new Autenticacao();
                usuario = autenticacao.autenticar(usuario);
                if (usuario != null) {

                    Session.setUsuario(usuario);
                    telaInicial.getLbDataAcesso().setText(
                            String.format("%s - %2$tA, %2$td de %2$tB de %2$tY %2$tI:%2$tM %2$tp",
                                    Session.getUsuario().getNome(), Session.getDataAcesso()));

                    if (usuario.getTipo().equals(Pessoa.Tipo.Administrador)) {
                        telaInicial.getBtnPainelDeControle().setVisible(true);
                        telaInicial.getjSeparator().setVisible(true);
                    }

                    telaLogin.dispose();
                    telaLogin.setVisible(false);
                    
                    final RequisicaoBO rbo = new RequisicaoBO();
                    final List<Material> materials = rbo.listEstoqueMinimo();
                    if (materials != null && !materials.isEmpty()) {
                        final StringBuilder mensagem = new StringBuilder("Aviso de materiais com o estoque mínimo\n\n");
                        materials.forEach((material) -> {
                            mensagem.append("Descrição:\t").append(material.getDescricao())
                                    .append("\nQuantidade Mínima:\t").append(rbo.getQuantidadeMinima(material))
                                    .append("\nQuantidade em Estoque:\t").append(material.getQuantidade()).append("\n");

                            if (materials.size() > 1) {
                                mensagem.append("------------------------------------------\n");
                            }
                        });
                        
                        telaAviso.mostrarMensagem(mensagem, "Materiais no Estoque Mínimo", true);
                    }
                    
                } else {
                    Messenger.showMessage("Login ou Senha inválido", MessageType.ERROR);
                    telaLogin.getTxtLogin().requestFocus();
                }
                return;
            }

            if (e.getSource() == telaLogin.getTxtLogin()) {
                telaLogin.getTxtSenha().requestFocus();
            }
        }

        @Override
        public void focusGained(FocusEvent e) {
            final JTextField txtLogin = telaLogin.getTxtLogin();
            final JTextField txtSenha = telaLogin.getTxtSenha();
            if (e.getSource() == txtLogin) {
                selecionarCampo(txtLogin, Color.WHITE);
                return;
            }

            if (e.getSource() == txtSenha) {
                selecionarCampo(txtSenha, Color.WHITE);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            final JTextField txtLogin = telaLogin.getTxtLogin();
            final JTextField txtSenha = telaLogin.getTxtSenha();
            if (e.getSource() == txtLogin) {
                selecionarCampo(txtLogin, new Color(204, 204, 204));
                return;
            }

            if (e.getSource() == txtSenha) {
                selecionarCampo(txtSenha, new Color(204, 204, 204));
            }
        }

        private void selecionarCampo(JTextField textField, Color color) {
            textField.setBackground(color);
            final int inicio = 0;
            final int end = textField.getText().length();
            textField.setSelectionStart(inicio);
            textField.setSelectionEnd(end);
        }
    }

    /**
     *
     * @param inicio
     * @param telaAviso
     */
    public void mostrarTelaInicial(TelaInicial inicio, TelaAviso telaAviso) {
        this.telaInicial = inicio;
        this.telaAviso = telaAviso;
        this.telaInicial.setVisible(true);
        this.telaLogin.setVisible(true);
    }
}
