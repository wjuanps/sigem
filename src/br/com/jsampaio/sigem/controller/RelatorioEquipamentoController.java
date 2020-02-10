/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.model.bo.GeradorRelatorioJasper;
import br.com.jsampaio.sigem.model.vo.Relatorio;
import br.com.jsampaio.sigem.model.vo.Session;
import br.com.jsampaio.sigem.util.Arquivo;
import br.com.jsampaio.sigem.view.TelaRelatorioEquipamento;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTable;

/**
 *
 * @author Janilson
 */
public class RelatorioEquipamentoController {

    private final TelaRelatorioEquipamento telaRelatorioEquipamento;
    private final PaginacaoController paginacaoController;

    public RelatorioEquipamentoController() {
        this.telaRelatorioEquipamento = new TelaRelatorioEquipamento();
        this.paginacaoController = new PaginacaoController();

        this.telaRelatorioEquipamento.getJpPaginacao().add(paginacaoController.getTelaPaginacao(), 0);
        this.paginacaoController.setObject(this, RelatorioEquipamentoController.class, "carregarTabela");

        final RelatorioEquipamentoListener relatorioEquipamentoListener = this.new RelatorioEquipamentoListener();
        this.telaRelatorioEquipamento.getBtnGerarGrafico().addActionListener(relatorioEquipamentoListener);
        this.telaRelatorioEquipamento.getBtnLimparCampos().addActionListener(relatorioEquipamentoListener);
        this.telaRelatorioEquipamento.getBtnGerarRelatorio().addActionListener(relatorioEquipamentoListener);
        this.telaRelatorioEquipamento.getTxtPesquisa().addKeyListener(relatorioEquipamentoListener);
        this.telaRelatorioEquipamento.getJcbFiltro().addItemListener(relatorioEquipamentoListener);
    }

    /**
     * 
     */
    public final void carregarTabela() {
        final JTable table = this.telaRelatorioEquipamento.getTblEquipamento();
        final String pesquisa = this.telaRelatorioEquipamento.getTxtPesquisa().getText().trim();
        final String status = this.telaRelatorioEquipamento.getJcbFiltro().getSelectedItem().toString();

        GenericTable.carregarTabelaEquipamentos(table, pesquisa, status, this.paginacaoController.getPaginacao());
        this.telaRelatorioEquipamento.getBtnGerarRelatorio().setEnabled(table.getRowCount() > 0);
        this.telaRelatorioEquipamento.getBtnGerarGrafico().setEnabled(table.getRowCount() > 0);
        this.paginacaoController.printResults();
    }

    private class RelatorioEquipamentoListener extends KeyAdapter implements ActionListener, ItemListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == telaRelatorioEquipamento.getBtnGerarRelatorio()) {
                final Relatorio relatorio = getRelatorio();
                GeradorRelatorioJasper.gerarJasper(relatorio);
                return;
            }
            
            if (e.getSource() == telaRelatorioEquipamento.getBtnGerarGrafico()) {
                
                return;
            }
            
            if (e.getSource() == telaRelatorioEquipamento.getBtnLimparCampos()) {
                limparCampos();
            }
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getSource() == telaRelatorioEquipamento.getJcbFiltro()) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    carregarTabela();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getSource() == telaRelatorioEquipamento.getTxtPesquisa()) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
                    return;
                }

                carregarTabela();

                final JTable tblEquipamento = telaRelatorioEquipamento.getTblEquipamento();
                if (tblEquipamento.getModel().getRowCount() > 0) {
                    tblEquipamento.setRowSelectionInterval(0, 0);
                }
            }
        }
    }

    /**
     *
     * @return
     */
    private Relatorio getRelatorio() {
        final String descricao = this.telaRelatorioEquipamento.getTxtPesquisa().getText().trim();
        final String situacao = this.telaRelatorioEquipamento.getJcbFiltro().getSelectedItem().toString().trim();
        final Relatorio relatorio = new Relatorio();
        relatorio.addParametro("DESCRICAO", "%".concat(descricao).concat("%"));
        relatorio.addParametro("SITUACAO", "%".concat(situacao).concat("%"));
        relatorio.addParametro("USUARIO", Session.getUsuario().getNome());
        relatorio.addParametro("ATIVO", 0L);

        relatorio.setFile(Arquivo.getRelatorio("relatorio_equipamentos"));
        relatorio.setTitulo("Relatório Equipamento");
        return relatorio;
    }

    public void limparCampos() {
        this.telaRelatorioEquipamento.getTxtPesquisa().setText(null);
        this.telaRelatorioEquipamento.getJcbFiltro().setSelectedIndex(0);
        
        this.carregarTabela();
    }
    
    public TelaRelatorioEquipamento getTelaRelatorioEquipamento() {
        return telaRelatorioEquipamento;
    }
}
