/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.model.bo.EquipamentoBO;
import br.com.jsampaio.sigem.model.bo.MaterialBO;
import br.com.jsampaio.sigem.model.bo.Paginacao;
import br.com.jsampaio.sigem.model.vo.Equipamento;
import br.com.jsampaio.sigem.model.vo.Material;
import br.com.jsampaio.sigem.util.Log;
import java.awt.Font;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Janilson
 */
public abstract class GenericTable {

    /**
     *
     * @param table
     * @param pesquisa
     * @param paginacao
     * @return
     */
    public static List<Material> carregarTabelaMateriais(JTable table, String pesquisa, Paginacao paginacao) {
        List<Material> materials = null;
        try {
            final MaterialBO mbo = new MaterialBO();

            while (((DefaultTableModel) table.getModel()).getRowCount() > 0) {
                ((DefaultTableModel) table.getModel()).removeRow(0);
            }

            materials = mbo.getListaMateriais(pesquisa, paginacao);
            int linha = 0;
            for (Material material : materials) {
                ((DefaultTableModel) table.getModel()).addRow(new String[]{});

                table.setValueAt(material.getCodigo(), linha, 0);
                table.setValueAt(material.getDescricao(), linha, 1);
                table.setValueAt(material.getUnidade(), linha, 2);
                table.setValueAt(material.getQuantidade(), linha, 3);
                table.setValueAt(material.getQuantidadeMaxima(), linha, 4);
                table.setValueAt(material.getValor(), linha, 5);

                linha++;
            }
            table.repaint();
            table.validate();
        } catch (NullPointerException n) {
            Log.saveLog(n, GenericTable.class);
        }
        return materials;
    }

    /**
     *
     * @param table
     * @param pesquisa
     * @param status
     * @param paginacao
     */
    public static void carregarTabelaEquipamentos(JTable table, String pesquisa, String status, Paginacao paginacao) {
        while (table.getModel().getRowCount() > 0) {
            ((DefaultTableModel) table.getModel()).removeRow(0);
        }

        final EquipamentoBO ebo = new EquipamentoBO();
        final List<Equipamento> equipamentos = ebo.carregarEquipamentos(pesquisa, status, paginacao);
        int linha = 0;
        for (Equipamento equipamento : equipamentos) {
            ((DefaultTableModel) table.getModel()).addRow(new String[]{});

            table.setValueAt(equipamento.getNumeroTombamento(), linha, 0);
            table.setValueAt(equipamento.getTipo(), linha, 1);
            table.setValueAt(equipamento.getDescricao(), linha, 2);
            table.setValueAt(equipamento.getStatus(), linha, 3);

            linha++;
        }

        table.repaint();
        table.validate();
    }

    /**
     *
     * @param table
     * @param dados
     */
    public static void carregarTabelaAdministrador(JTable table, List<List<String>> dados) {
        while (table.getModel().getRowCount() > 0) {
            ((DefaultTableModel) table.getModel()).removeRow(0);
        }

        if (dados != null && !dados.isEmpty()) {
            int linha = 0;
            for (List<String> dado : dados) {
                ((DefaultTableModel) table.getModel()).addRow(new String[]{});

                for (int i = 0; i < dado.size(); i++) {
                    table.setValueAt(dado.get(i), linha, i);
                }
                linha++;
            }
        }

        table.repaint();
        table.revalidate();
    }

    /**
     *
     * @param colunas
     * @param edit
     * @return
     */
    public static DefaultTableModel getModel(String[] colunas, boolean[] edit) {
        return new javax.swing.table.DefaultTableModel(
                new Object[][]{}, colunas
        ) {
            boolean[] canEdit = edit;

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
    }

    /**
     *
     * @param combo
     * @param table
     * @param itens
     */
    public static void addCombo(final JComboBox<String> combo, final JTable table, String... itens) {
        combo.removeAllItems();
        combo.setFont(new Font("Times New Roman", 0, 13));
        for (String item : itens) {
            combo.addItem(item);
        }
        table.getColumn("Status").setCellEditor(new DefaultCellEditor(combo));
    }
}
