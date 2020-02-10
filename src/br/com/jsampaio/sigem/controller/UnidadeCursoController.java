/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.main.Messenger;
import br.com.jsampaio.sigem.model.bo.UnidadeCursoBO;
import br.com.jsampaio.sigem.model.vo.MessageType;
import br.com.jsampaio.sigem.model.vo.UnidadeCurso;
import br.com.jsampaio.sigem.util.Log;
import br.com.jsampaio.sigem.view.TelaCadastrarUnidadeCurso;
import br.com.jsampaio.sigem.view.TelaSelecionarUnidadeCurso;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Janilson
 */
public class UnidadeCursoController {

    private final TelaSelecionarUnidadeCurso telaSelecionarUnidadeCurso;
    private final TelaCadastrarUnidadeCurso telaCadastrarUnidadeCurso;
    private UnidadeCurso unidadeCurso;
    private Long codigo = null;
    private Long codigoUnidade = null;
    private String descricao = null;

    private final PaginacaoController paginacaoController;

    public UnidadeCursoController() {
        this.telaSelecionarUnidadeCurso = new TelaSelecionarUnidadeCurso(null, true);
        this.telaCadastrarUnidadeCurso = new TelaCadastrarUnidadeCurso(null, true);
        this.paginacaoController = new PaginacaoController();

        this.telaSelecionarUnidadeCurso.getJpPaginacao().add(this.paginacaoController.getTelaPaginacao(), 0);
        this.paginacaoController.setObject(this, UnidadeCursoController.class, "carregarUnidadeCurso");

        final Listener listener = this.new Listener();
        this.telaSelecionarUnidadeCurso.getTxtPesquisa().addKeyListener(listener);
        this.telaSelecionarUnidadeCurso.getTblUnidadeCurso().addMouseListener(listener.getMouseListener());

        this.telaCadastrarUnidadeCurso.getBtnSalvar().addActionListener(listener);
        this.telaCadastrarUnidadeCurso.getBtnCancelar().addActionListener(listener);
        this.telaCadastrarUnidadeCurso.getBtnExcluir().addActionListener(listener);
        this.telaCadastrarUnidadeCurso.getBtnPesquisar().addActionListener(listener);
        this.telaCadastrarUnidadeCurso.getTxtUnidadeCurso().addActionListener(listener);

        this.listenerTabelaUnidadeCurso();
    }

    private final class Listener extends KeyAdapter implements ActionListener {

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getSource() == telaSelecionarUnidadeCurso.getTxtPesquisa()) {

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    selecionarUnidadeCurso();
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
                    return;
                }

                carregarUnidadeCurso();
            }
        }

        public MouseListener getMouseListener() {
            return new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getSource() == telaSelecionarUnidadeCurso.getTblUnidadeCurso()) {
                        if (e.getClickCount() == 2) {
                            selecionarUnidadeCurso();
                        }
                    }
                }
            };
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == telaCadastrarUnidadeCurso.getBtnCancelar()) {
                telaCadastrarUnidadeCurso.dispose();
                telaCadastrarUnidadeCurso.setVisible(false);
                limparCampos();
                return;
            }

            if (e.getSource() == telaCadastrarUnidadeCurso.getBtnSalvar()
                    || e.getSource() == telaCadastrarUnidadeCurso.getTxtUnidadeCurso()) {
                final UnidadeCurso unidadeCurso = new UnidadeCurso();
                final String uc = telaCadastrarUnidadeCurso.getTxtUnidadeCurso().getText().trim();
                unidadeCurso.setUnidadeCurso(uc);
                unidadeCurso.setAtivo(true);

                if (codigo != null) {
                    unidadeCurso.setCodigo(codigo);
                }

                UnidadeCursoBO ucbo = new UnidadeCursoBO();
                final String msg = ucbo.salvarUnidadeCurso(unidadeCurso);
                if (msg.isEmpty()) {
                    Messenger.showMessage("Cadastro salvo com sucesso", MessageType.INFORMATION);
                    limparCampos();
                } else {
                    Messenger.showMessage(msg, MessageType.ERROR);
                }

                telaCadastrarUnidadeCurso.getTxtUnidadeCurso().requestFocus();
                return;
            }

            if (e.getSource() == telaCadastrarUnidadeCurso.getBtnPesquisar()) {
                carregarUnidadeCurso();
                telaSelecionarUnidadeCurso.setVisible(true);

                final UnidadeCurso unidadeCurso = getUnidadeCurso();

                if (unidadeCurso != null) {
                    codigo = unidadeCurso.getCodigo();
                    descricao = unidadeCurso.getUnidadeCurso();
                    codigoUnidade = unidadeCurso.getCodigoUnidade();
                    telaCadastrarUnidadeCurso.getTxtUnidadeCurso().setText(unidadeCurso.getUnidadeCurso());
                    telaCadastrarUnidadeCurso.getBtnExcluir().setEnabled(true);
                } else {
                    limparCampos();
                }
                setUnidadeCurso(null);
                return;
            }

            if (e.getSource() == telaCadastrarUnidadeCurso.getBtnExcluir()) {
                if (codigo != null) {
                    if (Messenger.showMessage("Você tem certeza que deseja excluir " + descricao,
                            MessageType.QUESTION) == Messenger.YES_OPTION) {
                        final UnidadeCursoBO ucbo = new UnidadeCursoBO();
                        final UnidadeCurso unidadeCurso = new UnidadeCurso();
                        unidadeCurso.setCodigo(codigo);
                        unidadeCurso.setCodigoUnidade(codigoUnidade);
                        unidadeCurso.setUnidadeCurso(descricao);
                        unidadeCurso.setAtivo(false);
                        Messenger.showMessage((ucbo.atualizarUnidadeCurso(unidadeCurso))
                                ? "Operação realizada com sucesso"
                                : "Falha ao realizar a operação", MessageType.INFORMATION);
                    }
                    limparCampos();
                }
            }
        }
    }

    /**
     *
     */
    public void selecionarUnidadeCurso() {
        final JTable table = this.telaSelecionarUnidadeCurso.getTblUnidadeCurso();
        if (table.getSelectedRowCount() != 1) {
            Messenger.showMessage("Selecione uma Unidade/Curso", MessageType.ERROR);
            return;
        }

        final UnidadeCursoBO ucbo = new UnidadeCursoBO();
        final Long codigo = Long.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString());
        this.unidadeCurso = ucbo.getUnidadeCurso(codigo);

        if (unidadeCurso != null) {
            this.telaSelecionarUnidadeCurso.setVisible(false);
            this.telaSelecionarUnidadeCurso.dispose();
            this.telaSelecionarUnidadeCurso.getTxtPesquisa().setText("");
        }
    }

    public void carregarUnidadeCurso() {
        try {
            telaCadastrarUnidadeCurso.getBtnExcluir().setEnabled(false);
            final JTable tbl = this.telaSelecionarUnidadeCurso.getTblUnidadeCurso();
            final DefaultTableModel model = (DefaultTableModel) tbl.getModel();
            while (model.getRowCount() > 0) {
                model.removeRow(0);
            }

            final UnidadeCursoBO ucbo = new UnidadeCursoBO();
            final String nomeUnidadeCurso = telaSelecionarUnidadeCurso.getTxtPesquisa().getText().trim();
            final List<UnidadeCurso> unidadeCursos = ucbo.getUnidadesCursos(nomeUnidadeCurso, this.paginacaoController.getPaginacao());
            int linha = 0;
            for (UnidadeCurso unidadeCurso : unidadeCursos) {
                model.addRow(new String[1]);

                tbl.setValueAt(unidadeCurso.getCodigo(), linha, 0);
                tbl.setValueAt(unidadeCurso.getUnidadeCurso(), linha, 1);

                linha++;
            }
            tbl.validate();
            tbl.repaint();

            this.paginacaoController.printResults();
            if (!unidadeCursos.isEmpty()) {
                tbl.setRowSelectionInterval(0, 0);
            }

        } catch (Exception r) {
            Log.saveLog(r, UnidadeCursoController.class);
        }
    }

    /**
     *
     */
    private void listenerTabelaUnidadeCurso() {
        final JTable table = this.telaSelecionarUnidadeCurso.getTblUnidadeCurso();
        GenericListener.addListener(table, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selecionarUnidadeCurso();
            }
        });

        GenericListener.tableListener(table, this.telaSelecionarUnidadeCurso.getTxtPesquisa());
    }

    public void limparCampos() {
        this.telaCadastrarUnidadeCurso.getTxtUnidadeCurso().setText(null);
        this.telaCadastrarUnidadeCurso.getTxtUnidadeCurso().requestFocus();
        this.telaCadastrarUnidadeCurso.getBtnExcluir().setEnabled(false);
        this.codigo = null;
        this.descricao = null;
        this.codigoUnidade = null;
    }

    public TelaSelecionarUnidadeCurso getTelaSelecionarUnidadeCurso() {
        return telaSelecionarUnidadeCurso;
    }

    public TelaCadastrarUnidadeCurso getTelaCadastrarUnidadeCurso() {
        return telaCadastrarUnidadeCurso;
    }

    public UnidadeCurso getUnidadeCurso() {
        return unidadeCurso;
    }

    public void setUnidadeCurso(UnidadeCurso unidadeCurso) {
        this.unidadeCurso = unidadeCurso;
    }

    public PaginacaoController getPaginacaoController() {
        return paginacaoController;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

}
