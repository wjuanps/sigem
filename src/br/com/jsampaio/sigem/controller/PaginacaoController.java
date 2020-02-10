/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.model.bo.Paginacao;
import br.com.jsampaio.sigem.util.Log;
import br.com.jsampaio.sigem.view.TelaPaginacao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Janilson
 */
public class PaginacaoController implements ActionListener, ItemListener {

    private final TelaPaginacao telaPaginacao;
    private final Paginacao paginacao;
    private Object object;
    private Method method;

    public PaginacaoController() {
        this.telaPaginacao = new TelaPaginacao();
        this.telaPaginacao.setBounds(0, 0, 529, 42);

        this.paginacao = new Paginacao();

        this.paginacao.setItensPorPagina( (Integer) this.telaPaginacao.getJcbQuantidadeItensPorPagina().getSelectedItem());

        this.telaPaginacao.getBtnAnterior().addActionListener(this);
        this.telaPaginacao.getBtnProximo().addActionListener(this);
        this.telaPaginacao.getJcbQuantidadeItensPorPagina().addItemListener(this);
    }

    public void setObject(Object object, Class classe, String metodo) {
        try {
            this.object = object;
            this.method = classe.getMethod(metodo);
        } catch (NoSuchMethodException | SecurityException ex) {
            Log.saveLog(ex, PaginacaoController.class);
        }
    }

    private void invocarMetodo() {
        try {
            method.invoke(object);
        } catch (SecurityException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            Log.saveLog(ex, PaginacaoController.class);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            final JButton btnTemp = (JButton) e.getSource();
            if (btnTemp.equals(this.telaPaginacao.getBtnAnterior())) {
                paginacao.voltarPagina();
                this.invocarMetodo();
                printResults();
                return;
            }

            if (btnTemp.equals(this.telaPaginacao.getBtnProximo())) {
                paginacao.avancarPagina();
                this.invocarMetodo();
                this.printResults();
            }
        } catch (Exception ex) {
            Log.saveLog(ex, PaginacaoController.class);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        try {
            if (e.getSource() == this.telaPaginacao.getJcbQuantidadeItensPorPagina()) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    final int itensPorPagina = Integer.valueOf(telaPaginacao.getJcbQuantidadeItensPorPagina().getSelectedItem().toString());
                    paginacao.setItensPorPagina(itensPorPagina);
                    this.invocarMetodo();
                    this.printResults();
                }
            }
        } catch (NumberFormatException nfe) {
            Log.saveLog(nfe, PaginacaoController.class);
        } catch (Exception ex) {
            Log.saveLog(ex, PaginacaoController.class);
        }
    }

    public void printResults() {

        final int itensPorPagina = (Integer) this.telaPaginacao.getJcbQuantidadeItensPorPagina().getSelectedItem();
        paginacao.setItensPorPagina(itensPorPagina);

        final JLabel inicio = this.telaPaginacao.getJlbInicio();
        final JLabel totalPaginas = this.telaPaginacao.getJlbTotalPaginas();
        final JLabel totalRegistros = this.telaPaginacao.getJlbTotalRegistros();

        final JButton anterior = this.telaPaginacao.getBtnAnterior();
        final JButton proximo = this.telaPaginacao.getBtnProximo();

        paginacao.printResultados(inicio, totalPaginas, totalRegistros);
        paginacao.habilitarDesabilitarBotao(anterior, proximo);
    }

    public TelaPaginacao getTelaPaginacao() {
        return telaPaginacao;
    }

    public Paginacao getPaginacao() {
        return paginacao;
    }
}
