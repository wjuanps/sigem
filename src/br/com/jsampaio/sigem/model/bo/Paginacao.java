/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.bo;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Janilson
 */
public final class Paginacao {
     
    /**
     * Define a quantidade de itens que irá aparecer em cada página.
     */
    private int itensPorPagina;
    /**
     * Armazena a quantidade de registros que a consulta retornou
     */
    private int totalResultados;
    /**
     * Indica a pagina atual
     */
    private int pagina = 0;
    
    /**
     * Indica se sera mostrada a proxima pagina
     * 
     * @see #setPagina() 
     * @see #avancarPagina() 
     */
    private boolean proximo  = false;
    /**
     * indica se sera mostrada a pagina anterior
     * 
     * @see #setPagina() 
     * @see #voltarPagina() 
     */
    private boolean anterior = false;
    
    /**
     * Verifica se o pagina selecionada e a ultima
     * 
     * @see #habilitarDesabilitarBotao(javax.swing.JButton, javax.swing.JButton) 
     */
    private boolean ULTIMA_PAGINA;
    /**
     * Verifica se nao ha paginas para a consulta
     * 
     * @see #habilitarDesabilitarBotao(javax.swing.JButton, javax.swing.JButton) 
     */
    private boolean SEM_PAGINAS;
    /**
     * Verifica se e a primeira pagina da consulta
     * 
     * @see #habilitarDesabilitarBotao(javax.swing.JButton, javax.swing.JButton) 
     */
    private boolean PRIMEIRA_PAGINA;
    /**
     * Verifica se é alguma pagina intermediaria da consulta
     * 
     * @see #habilitarDesabilitarBotao(javax.swing.JButton, javax.swing.JButton) 
     */
    private boolean PAGINAS_INTERMEDIARIAS;
    /**
     * Verifica se a consulta retornou apenas uma pagina
     * 
     * @see #habilitarDesabilitarBotao(javax.swing.JButton, javax.swing.JButton) 
     */
    private boolean UMA_PAGINA;

    /**
     * 
     * Retorna a quantidade de tuplas retornadas na consulta
     * 
     * @return o numero de tuplas
     */
    public int getTotalResultados() {
        return totalResultados;
    }

    /**
     * 
     * Define a quantidade de registros retornados do banco de dados
     * 
     * @param totalResultados
     * @see PessoaDAO#get(java.sql.Connection, java.lang.String)
     * @see #setPagina()
     */
    public void setTotalResultados(int totalResultados) {
        this.totalResultados = totalResultados;
        setPagina();
    }
    
    /**
     * 
     * Retorna a quantidade de paginas de acordo com os registros
     * 
     * @return a quantidade de paginas
     */
    public int numPaginas() {
        return (
            (int) (Math.ceil(totalResultados / (double) itensPorPagina))
        );
    }
    
    /**
     *
     * Indica o inicio para a busca no banco de dados
     * 
     * <pre>
     *      SELECT * FROM `aluno` LIMIT Paginacao.inicio(), Paginacao.getItensPorPagina()
     * </pre>
     * 
     * @return 
     */
    public int inicio() {
        if (pagina > 0) {
            return (
                (pagina * itensPorPagina) - itensPorPagina
            );
        } else {
            return 0;
        }
    }
    
    /**
     * Define a pagina atual
     */
    private void setPagina() {
        if (!isProximo() && !isAnterior()) {
            pagina = (totalResultados > 0) ? 1 : 0;
        } else if (isProximo()) {
            if (pagina < numPaginas()) {
                ++pagina;
                proximo = false;
            }
        } else if (isAnterior()) {
            if (pagina > 1) {
                --pagina;
                anterior = false;
            }
        }
    }
    
    /**
     * 
     * @param inicio
     * @param totalPaginas
     * @param total
     */
    public void printResultados(JLabel inicio, JLabel totalPaginas, JLabel total) {
        inicio.setText(String.valueOf(getPagina()));
        totalPaginas.setText(String.valueOf(numPaginas()));
        total.setText(String.valueOf(getTotalResultados()));
    }
    
    /**
     * 
     * @param anterior
     * @param proximo
     */
    public void habilitarDesabilitarBotao(JButton anterior, JButton proximo) {
        ULTIMA_PAGINA          = (numPaginas() == pagina);
        SEM_PAGINAS            = (numPaginas() == 0);
        PRIMEIRA_PAGINA        = (pagina == 1);
        PAGINAS_INTERMEDIARIAS = (!ULTIMA_PAGINA & !SEM_PAGINAS);
        UMA_PAGINA             = (totalResultados <= itensPorPagina);
        
        if (SEM_PAGINAS) {
            anterior.setEnabled(false);
            proximo.setEnabled(false);
        } else if (ULTIMA_PAGINA) {
            proximo.setEnabled(false);
            anterior.setEnabled(true);
            
            if (UMA_PAGINA)
                anterior.setEnabled(false);
        } else if (PAGINAS_INTERMEDIARIAS) {
            anterior.setEnabled(true);
            proximo.setEnabled(true);
            
            if (PRIMEIRA_PAGINA)
                anterior.setEnabled(false);            
        }
    }
    
    /**
     *
     * @return 
     */
    public int getPagina() {
        return pagina;
    }
    
    /**
     * 
     */
    public void avancarPagina() {
        proximo = true;
    }
    
    /**
     *
     */
    public void voltarPagina() {
        anterior = true;
    }

   /**
    * 
    * @return 
    */
    public boolean isProximo() {
        return proximo;
    }

    /**
     * 
     * @return 
     */
    public boolean isAnterior() {
        return anterior;
    }

    public int getItensPorPagina() {
        return itensPorPagina;
    }

    public void setItensPorPagina(int itensPorPagina) {
        this.itensPorPagina = itensPorPagina;
    }
}