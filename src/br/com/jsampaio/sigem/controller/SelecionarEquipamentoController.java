/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.main.Messenger;
import br.com.jsampaio.sigem.model.bo.EquipamentoBO;
import br.com.jsampaio.sigem.model.vo.Equipamento;
import br.com.jsampaio.sigem.model.vo.MessageType;
import br.com.jsampaio.sigem.util.Log;
import br.com.jsampaio.sigem.view.TelaSelecionarEquipamento;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.JTable;

/**
 *
 * @author Janilson
 */
public class SelecionarEquipamentoController extends KeyAdapter implements ItemListener {

    private final TelaSelecionarEquipamento telaSelecionarEquipamento;
    private final PaginacaoController paginacaoController;
    private Equipamento equipamento;

    public SelecionarEquipamentoController() {
        this.telaSelecionarEquipamento = new TelaSelecionarEquipamento(null, true);
        this.paginacaoController = new PaginacaoController();
        
        this.telaSelecionarEquipamento.getJpPaginacao().add(this.paginacaoController.getTelaPaginacao(), 0);
        this.paginacaoController.setObject(this, SelecionarEquipamentoController.class, "carregarEquipamentos");

        this.telaSelecionarEquipamento.getTxtPesquisa().addKeyListener(this);
        this.telaSelecionarEquipamento.getJcbFiltro().addItemListener(this);

        this.listenerTabelaEquipamentos();
    }

    public TelaSelecionarEquipamento getTelaSelecionarEquipamento() {
        return telaSelecionarEquipamento;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == telaSelecionarEquipamento.getTxtPesquisa()) {

            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                selecionarEquipamento();
                return;
            }

            if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
                return;
            }
            
            carregarEquipamentos();

            final JTable tblEquipamento = telaSelecionarEquipamento.getTblEquipamento();
            if (tblEquipamento.getModel().getRowCount() > 0) {
                tblEquipamento.setRowSelectionInterval(0, 0);
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == telaSelecionarEquipamento.getJcbFiltro()) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                carregarEquipamentos();
            }
        }
    }

    private void selecionarEquipamento() {
        final JTable tblEquipamento = this.telaSelecionarEquipamento.getTblEquipamento();
        if (tblEquipamento.getSelectedRowCount() != 1) {
            Messenger.showMessage("Selecione um equipamento", MessageType.ERROR);
            return;
        }

        final int linha = tblEquipamento.getSelectedRow();
        final Long numTombamento = Long.valueOf(tblEquipamento.getValueAt(linha, 0).toString());

        final EquipamentoBO ebo = new EquipamentoBO();
        this.equipamento = ebo.getEquipamento(numTombamento);

        if (this.equipamento != null) {
            this.telaSelecionarEquipamento.setVisible(false);
            this.telaSelecionarEquipamento.dispose();
            this.telaSelecionarEquipamento.getJcbFiltro().setSelectedIndex(0);
            this.telaSelecionarEquipamento.getTxtPesquisa().setText("");
            carregarEquipamentos();
        } else {
            Messenger.showMessage("Erro ao selecionar o equipamento", MessageType.ERROR);
        }
    }

    public void carregarEquipamentos() {
        try {
            final JTable tblEquipamento = this.telaSelecionarEquipamento.getTblEquipamento();
            final String pesquisa = this.telaSelecionarEquipamento.getTxtPesquisa().getText().trim();
            final String status = this.telaSelecionarEquipamento.getJcbFiltro().getSelectedItem().toString().trim();
            GenericTable.carregarTabelaEquipamentos(tblEquipamento, pesquisa, status, this.paginacaoController.getPaginacao());
            this.paginacaoController.printResults();
        } catch (NullPointerException n) {
            Messenger.showMessage("Não foi possivel carregar equipamentos", MessageType.ERROR);
            Log.saveLog(n, SelecionarEquipamentoController.class);
        }
    }

    private void listenerTabelaEquipamentos() {
        final JTable tblEquipamento = this.telaSelecionarEquipamento.getTblEquipamento();
        GenericListener.addListener(tblEquipamento, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selecionarEquipamento();
            }
        });

        tblEquipamento.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    selecionarEquipamento();
                }
            }
        });
        
        GenericListener.tableListener(tblEquipamento, this.telaSelecionarEquipamento.getTxtPesquisa());
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }
}