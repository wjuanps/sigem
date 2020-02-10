/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 *
 * @author Janilson
 */
public abstract class GenericListener {
    
    /**
     * 
     * @param component
     * @param action 
     */
    public static void addListener(JComponent component, AbstractAction action) {
        final String enterAction = "enterAction";
        component.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), enterAction);
        component.getActionMap().put(enterAction, action);
    }
    
    /**
     * 
     * @param table
     * @param textField 
     */
    public static void tableListener(JTable table, JTextField textField) {
        textField.requestFocus();
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    
                    final int keyCode = e.getKeyCode();
                    
                    if (keyCode != KeyEvent.VK_DOWN && keyCode != KeyEvent.VK_UP) {
                        return;
                    }
                    
                    if (table.getModel().getRowCount() > 0 && table.getSelectedRowCount() == 1) {
                        int row = table.getSelectedRow();
                        row = (keyCode == KeyEvent.VK_UP) ? row - 1 : row + 1;
                        
                        if (row == -1) {
                            row = table.getRowCount() - 1;
                        }
                    
                        table.setRowSelectionInterval(row, row);
                    } else {
                        table.setRowSelectionInterval(0, 0);
                    }
                } catch (IllegalArgumentException iae) {
                    table.setRowSelectionInterval(0, 0);
                }
            }
        });
    }
}