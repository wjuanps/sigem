/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.main.Messenger;
import br.com.jsampaio.sigem.model.bo.PessoaBO;
import br.com.jsampaio.sigem.model.vo.MessageType;
import br.com.jsampaio.sigem.model.vo.Pessoa;
import br.com.jsampaio.sigem.model.vo.UnidadeCurso;
import br.com.jsampaio.sigem.view.TelaCadastroPessoa;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 *
 * @author Janilson
 */
public class PessoaController {

    private final TelaCadastroPessoa telaCadastroPessoa;
    private final UnidadeCursoController unidadeCursoController;
    final PessoaListener pessoaListener;
    
    private Long codigo = null;

    public PessoaController(UnidadeCursoController unidadeCursoController) {
        this.telaCadastroPessoa = new TelaCadastroPessoa();
        this.unidadeCursoController = unidadeCursoController;

        this.pessoaListener = this.new PessoaListener();
        this.telaCadastroPessoa.getJcbTipo().addItemListener(pessoaListener);
        this.telaCadastroPessoa.getBtnLimapar().addActionListener(pessoaListener);
        this.telaCadastroPessoa.getBtnCadastrar().addActionListener(pessoaListener);
        this.telaCadastroPessoa.getTxtUnidadeCurso().addActionListener(pessoaListener);
        this.telaCadastroPessoa.getBtnPesquisarUnidadeCurso().addActionListener(pessoaListener);

    }

    private final class PessoaListener extends KeyAdapter implements ActionListener, ItemListener {

        public PessoaListener() {
            telaCadastroPessoa.getTxtUnidadeCurso().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    selecionarUnidadeCurso();
                }
            });
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final JButton btnTemp = (JButton) e.getSource();
            if (btnTemp.equals(telaCadastroPessoa.getBtnCadastrar())) {

                final String login = telaCadastroPessoa.getTxtLogin().getText();
                final String senha = new String(telaCadastroPessoa.getTxtSenha().getPassword());
                final Pessoa pessoa = telaCadastroPessoa.getPessoa();
                final PessoaBO sbo = new PessoaBO();
                
                if (codigo != null) {
                    pessoa.setCodigo(codigo);
                }
                
                final String mensagem = sbo.cadastrarPessoa(pessoa, login, senha);
                if (mensagem.isEmpty()) {
                    Messenger.showMessage("Cadastro salvo com sucesso", MessageType.INFORMATION);
                    telaCadastroPessoa.getJcbTipo().setSelectedIndex(0);
                    telaCadastroPessoa.limparCampos();
                } else {
                    Messenger.showMessage(mensagem, MessageType.ERROR);
                }
                
                codigo = null;
                return;
            }

            if (e.getSource() == telaCadastroPessoa.getBtnPesquisarUnidadeCurso()
                    || e.getSource() == telaCadastroPessoa.getTxtUnidadeCurso()) {

                selecionarUnidadeCurso();
                return;
            }

            if (btnTemp.equals(telaCadastroPessoa.getBtnLimapar())) {
                telaCadastroPessoa.limparCampos();
                codigo = null;
            }
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getSource() == telaCadastroPessoa.getJcbTipo()) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    telaCadastroPessoa.getJpUsuario().setVisible(telaCadastroPessoa.getJcbTipo().getSelectedItem().equals(Pessoa.Tipo.Usuario));
                }
            }
        }

        private void selecionarUnidadeCurso() {
            unidadeCursoController.carregarUnidadeCurso();
            unidadeCursoController.getTelaSelecionarUnidadeCurso().setVisible(true);

            final UnidadeCurso unidadeCurso = unidadeCursoController.getUnidadeCurso();

            if (unidadeCurso != null) {
                telaCadastroPessoa.getTxtUnidadeCurso().setText(unidadeCurso.toString());
            }

            unidadeCursoController.setUnidadeCurso(null);
        }
    }

    public TelaCadastroPessoa getTelaCadastroPessoa() {
        return telaCadastroPessoa;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    
}
