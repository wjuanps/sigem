/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.main.Messenger;
import br.com.jsampaio.sigem.model.bo.MaterialBO;
import br.com.jsampaio.sigem.model.vo.Material;
import br.com.jsampaio.sigem.model.vo.MessageType;
import br.com.jsampaio.sigem.view.TelaAtualizarMaterial;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JTable;

/**
 *
 * @author Janilson
 */
public class AtualizacaoMaterialController {

    private final TelaAtualizarMaterial telaAtualizarMaterial;
    private final PaginacaoController paginacaoController;

    public AtualizacaoMaterialController() {
        this.telaAtualizarMaterial = new TelaAtualizarMaterial();
        this.paginacaoController = new PaginacaoController();

        this.telaAtualizarMaterial.getJpPaginacao().add(this.paginacaoController.getTelaPaginacao(), 0);
        this.paginacaoController.setObject(this, AtualizacaoMaterialController.class, "carregarMateriais");

        final MaterialListener materialListener = this.new MaterialListener();
        this.telaAtualizarMaterial.getTxtPesquisa().addKeyListener(materialListener);

        this.listenerTabelaMaterial();
        this.telaAtualizarMaterial.getTxtPesquisa().requestFocus();
    }

    private final class MaterialListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getSource() == telaAtualizarMaterial.getTxtPesquisa()) {

                if (e.getKeyCode() == KeyEvent.VK_DOWN 
                        || e.getKeyCode() == KeyEvent.VK_UP
                        || e.getKeyCode() == KeyEvent.VK_ENTER) {
                    return;
                }

                carregarMateriais();
                final JTable table = telaAtualizarMaterial.getTblMaterial();
                if (table.getModel().getRowCount() > 0) {
                    table.setRowSelectionInterval(0, 0);
                }
            }
        }
    }

    /**
     *
     */
    public void carregarMateriais() {
        final JTable table = this.telaAtualizarMaterial.getTblMaterial();
        final String pesquisa = this.telaAtualizarMaterial.getTxtPesquisa().getText().trim();
        GenericTable.carregarTabelaMateriais(table, pesquisa, this.paginacaoController.getPaginacao());
        this.paginacaoController.printResults();
    }

    /**
     *
     */
    private void listenerTabelaMaterial() {
        final JTable table = this.telaAtualizarMaterial.getTblMaterial();
        GenericListener.addListener(table, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarMaterial();
            }
        });

        GenericListener.tableListener(table, this.telaAtualizarMaterial.getTxtPesquisa());
    }

    /**
     *
     */
    private void atualizarMaterial() {
        final Material material = this.telaAtualizarMaterial.getMaterial();
        if (material != null) {
            final MaterialBO mbo = new MaterialBO();
            mbo.atualizarMaterial(material);
            Messenger.showMessage("Material salvo com sucesso", MessageType.INFORMATION);
            this.telaAtualizarMaterial.getTxtPesquisa().setText(null);
            carregarMateriais();
            this.telaAtualizarMaterial.getTxtPesquisa().requestFocus();
        }
    }

    public TelaAtualizarMaterial getTelaAtualizarMaterial() {
        return telaAtualizarMaterial;
    }
}