/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.main.Messenger;
import br.com.jsampaio.sigem.model.bo.GeradorRelatorioJasper;
import br.com.jsampaio.sigem.model.bo.Grafico;
import br.com.jsampaio.sigem.model.bo.MaterialBO;
import br.com.jsampaio.sigem.model.vo.Material;
import br.com.jsampaio.sigem.model.vo.MessageType;
import br.com.jsampaio.sigem.model.vo.Relatorio;
import br.com.jsampaio.sigem.model.vo.Session;
import br.com.jsampaio.sigem.util.Arquivo;
import br.com.jsampaio.sigem.view.TelaGrafico;
import br.com.jsampaio.sigem.view.TelaRelatorioMaterial;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author Janilson
 */
public final class RelatorioMaterialController {

    private TelaGrafico telaGrafico;

    private final TelaRelatorioMaterial telaRelatorioMaterial;
    private final PaginacaoController paginacaoController;

    public RelatorioMaterialController() {
        this.telaRelatorioMaterial = new TelaRelatorioMaterial();
        this.paginacaoController = new PaginacaoController();

        this.telaRelatorioMaterial.getJpPaginacao().add(this.paginacaoController.getTelaPaginacao(), 0);
        this.paginacaoController.setObject(this, RelatorioMaterialController.class, "carregarMaterial");

        final MaterialListener materialListener = this.new MaterialListener();
        this.telaRelatorioMaterial.getBtnGerarRelatorio().addActionListener(materialListener);
        this.telaRelatorioMaterial.getBtnGerarGrafico().addActionListener(materialListener);
        this.telaRelatorioMaterial.getBtnLimparFiltros().addActionListener(materialListener);

        this.telaRelatorioMaterial.getTxtPesquisa().addKeyListener(materialListener);

        GenericListener.tableListener(this.telaRelatorioMaterial.getTblMaterial(), this.telaRelatorioMaterial.getTxtPesquisa());
        this.carregarMaterial();
    }

    private class MaterialListener extends KeyAdapter implements ActionListener {

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getSource() == telaRelatorioMaterial.getTxtPesquisa()) {
                carregarMaterial();
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == telaRelatorioMaterial.getBtnGerarRelatorio()) {
                final Relatorio relatorio = getRelatorio();
                GeradorRelatorioJasper.gerarJasper(relatorio);
                return;
            }

            if (e.getSource() == telaRelatorioMaterial.getBtnGerarGrafico()) {
                gerarGrafico();
                return;
            }

            if (e.getSource() == telaRelatorioMaterial.getBtnLimparFiltros()) {
                telaRelatorioMaterial.getTxtPesquisa().setText(null);
                telaRelatorioMaterial.getTxtPesquisa().requestFocus();
                carregarMaterial();
            }
        }
    }

    /**
     * 
     */
    public void carregarMaterial() {
        final JTable table = this.telaRelatorioMaterial.getTblMaterial();
        final String pesquisa = this.telaRelatorioMaterial.getTxtPesquisa().getText();
        GenericTable.carregarTabelaMateriais(table, pesquisa, paginacaoController.getPaginacao());

        this.telaRelatorioMaterial.getBtnGerarRelatorio().setEnabled(table.getRowCount() > 0);
        this.telaRelatorioMaterial.getBtnGerarGrafico().setEnabled(table.getRowCount() > 0);
        this.paginacaoController.printResults();
    }

    /**
     *
     * @return
     */
    private Map<String, Double> getDataSet(List<Material> materials) {
        if (materials != null && !materials.isEmpty()) {
            final Map<String, Double> map = new HashMap<>();
            materials.forEach((material) -> {
                map.put(material.getDescricao(), (double) material.getQuantidade());
            });
            return map;
        }
        return null;
    }

    /**
     * 
     */
    private void gerarGrafico() {
        if (this.telaGrafico == null) {
            this.telaGrafico = new TelaGrafico(null, true);
        }

        final MaterialBO mbo = new MaterialBO();
        final String pesquisa = this.telaRelatorioMaterial.getTxtPesquisa().getText().trim();
        final List<Material> materials = mbo.getListaMateriais(pesquisa, null);
        if (materials != null && !materials.isEmpty()) {
            final Map<String, Double> dados = getDataSet(materials);

            JPanel grafico = null;
            if (dados != null && !dados.isEmpty()) {
                grafico = Grafico.gerarGraficoPizza(dados, "Quantidade de Materiais em Estoque");
            }

            if (grafico != null) {
                grafico.setBounds(new Rectangle(900, 550));
                this.telaGrafico.getJpGrafico().add(grafico);
                this.telaGrafico.setVisible(true);
                this.telaGrafico.getJpGrafico().removeAll();
            } else {
                Messenger.showMessage("Ocorreu um erro ao carregar o gráfico", MessageType.ERROR);
            }
        }
    }

    /**
     *
     * @return
     */
    private Relatorio getRelatorio() {
        final String descricao = this.telaRelatorioMaterial.getTxtPesquisa().getText().trim();
        final Relatorio relatorio = new Relatorio();
        relatorio.addParametro("DESCRICAO", "%".concat(descricao).concat("%"));
        relatorio.addParametro("USUARIO", Session.getUsuario().getNome());
        relatorio.addParametro("ATIVO", 0L);

        relatorio.setFile(Arquivo.getRelatorio("relatorio_materiais"));
        relatorio.setTitulo("Relatório Material");

        return relatorio;
    }

    public TelaRelatorioMaterial getTelaRelatorioMaterial() {
        return telaRelatorioMaterial;
    }
}
