/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author Janilson
 */
public interface GraphicsConstants {
    
    /**
     * Define a o icone do sistema
     */
    Image SYSTEM_ICON = Toolkit.getDefaultToolkit().getImage(GraphicsConstants.class.getResource("/sigem/Image/icone.png"));
    
    /**
     * Define um padrão de cor para o fundo das janelas.
     */
    Color COR_FUNDO = new Color(239, 239, 239);
    
    /**
     * Define uma fonte padrão para os títulos
     */
    Font FONT_TITULO = new Font("Tahoma", Font.PLAIN, 28);
    
    /**
     * Define uma fonte padrão para os rótulos
     */
    Font FONT_LABEL = new Font("Tahoma", Font.PLAIN, 18);
    
    /**
     * Define uma fonte padrão para os campos de entrada de dados
     */
    Font FONT_INPUT = new Font("Tahoma", Font.PLAIN, 18);
    
    /**
     * Define uma padrão para os botões
     */
    Font FONT_BOTAO = new Font("Tahoma", Font.PLAIN, 18);
    
}
