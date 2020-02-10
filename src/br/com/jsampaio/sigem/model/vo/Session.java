/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.vo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Janilson
 */
public final class Session implements Serializable {
    
    private static Usuario usuario;
    private static Date dataAcesso;

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        Session.usuario = usuario;
        Session.dataAcesso = Calendar.getInstance().getTime();
    }

    public static Date getDataAcesso() {
        return dataAcesso;
    }

    public static void setDataAcesso(Date dataAcesso) {
        Session.dataAcesso = dataAcesso;
    }
}
