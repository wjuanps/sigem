/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.main.Messenger;
import br.com.jsampaio.sigem.model.bo.EmprestimoBO;
import br.com.jsampaio.sigem.model.bo.PessoaBO;
import br.com.jsampaio.sigem.model.vo.MessageType;
import br.com.jsampaio.sigem.model.vo.Solicitante;
import br.com.jsampaio.sigem.util.Log;
import br.com.jsampaio.sigem.view.TelaSelecionarPessoa;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Janilson
 */
public class SelecionarPessoaController {

    private final TelaSelecionarPessoa telaSelecionarSolicitante;
    private final PaginacaoController paginacaoController;
    private Solicitante solicitante;

    public SelecionarPessoaController() {
        this.telaSelecionarSolicitante = new TelaSelecionarPessoa(null, true);
        this.paginacaoController = new PaginacaoController();
        
        this.telaSelecionarSolicitante.getJpPaginacao().add(paginacaoController.getTelaPaginacao(), 0);
        this.paginacaoController.setObject(this, SelecionarPessoaController.class, "carregarPessoas");

        this.telaSelecionarSolicitante.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                telaSelecionarSolicitante.getTxtPesquisa().setText("");
                carregarPessoas();
            }
        });
        
        final Listener listener = this.new Listener();
        this.telaSelecionarSolicitante.getTxtPesquisa().addKeyListener(listener);
        
        this.listenerTabelaSolicitante();
    }

    private final class Listener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getSource() == telaSelecionarSolicitante.getTxtPesquisa()) {

                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
                    return;
                }
                
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    selecionarPessoa();
                    return;
                }
                
                final JTable tblPessoa = telaSelecionarSolicitante.getTblRequisitante();
                carregarPessoas();

                if (tblPessoa.getModel().getRowCount() > 0) {
                    tblPessoa.setRowSelectionInterval(0, 0);
                }
            }
        }
    }

    /**
     * 
     */
    private void listenerTabelaSolicitante() {
        final JTable tblSolicitante = telaSelecionarSolicitante.getTblRequisitante();
        GenericListener.addListener(tblSolicitante, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selecionarPessoa();
            }
        });

        tblSolicitante.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    selecionarPessoa();
                }
            }
        });
        
        GenericListener.tableListener(tblSolicitante, telaSelecionarSolicitante.getTxtPesquisa());
    }

    /**
     * 
     */
    public void carregarPessoas() {
        try {
            final PessoaBO pbo = new PessoaBO();

            while (((DefaultTableModel) this.telaSelecionarSolicitante.getTblRequisitante().getModel()).getRowCount() > 0) {
                ((DefaultTableModel) this.telaSelecionarSolicitante.getTblRequisitante().getModel()).removeRow(0);
            }

            final List<Solicitante> solicitantes = pbo.carregarSolicitantes(this.telaSelecionarSolicitante.getTxtPesquisa().getText(), paginacaoController.getPaginacao());
            int linha = 0;
            for (Solicitante pessoa : solicitantes) {
                ((DefaultTableModel) this.telaSelecionarSolicitante.getTblRequisitante().getModel()).addRow(new String[]{});

                this.telaSelecionarSolicitante.getTblRequisitante().setValueAt(pessoa.getMatricula(), linha, 0);
                this.telaSelecionarSolicitante.getTblRequisitante().setValueAt(pessoa.getNome(), linha, 1);
                this.telaSelecionarSolicitante.getTblRequisitante().setValueAt(pessoa.getUnidadeCurso().getUnidadeCurso(), linha, 2);

                linha++;
            }
            this.telaSelecionarSolicitante.getTblRequisitante().validate();
            paginacaoController.printResults();
        } catch (NullPointerException n) {
            Messenger.showMessage("Não foi possivel carregar as pessoas", MessageType.ERROR);
            Log.saveLog(n, SelecionarPessoaController.class);
        }
    }

    /**
     * 
     */
    public void selecionarPessoa() {

        if (telaSelecionarSolicitante.getTblRequisitante().getSelectedRowCount() != 1) {
            Messenger.showMessage("Selecione uma pessoa", MessageType.ERROR);
            return;
        }

        final int linha = telaSelecionarSolicitante.getTblRequisitante().getSelectedRow();
        final Long matricula = Long.valueOf(telaSelecionarSolicitante.getTblRequisitante().getValueAt(linha, 0).toString());
        final EmprestimoBO ebo = new EmprestimoBO();

        this.solicitante = ebo.getPessoa(matricula);

        if (solicitante != null) {
            telaSelecionarSolicitante.setVisible(false);
            telaSelecionarSolicitante.dispose();

            telaSelecionarSolicitante.getTxtPesquisa().setText("");
            carregarPessoas();
        } else {
            Messenger.showMessage("Erro ao selecionar a pessoa", MessageType.ERROR);
        }
    }

    public Solicitante getPessoa() {
        return solicitante;
    }

    public void setPessoa(Solicitante solicitante) {
        this.solicitante = solicitante;
    }

    public TelaSelecionarPessoa getTelaSelecionarPessoa() {
        return telaSelecionarSolicitante;
    }

    public PaginacaoController getPaginacaoController() {
        return paginacaoController;
    }
}