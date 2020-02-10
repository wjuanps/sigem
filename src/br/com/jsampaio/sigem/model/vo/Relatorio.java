/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Janilson
 */
public class Relatorio {
    
    private List<List<Object>> objects;
    private String[] header;
    private String file;
    private String titulo;
    
    private final Map<String, Object> parametros = new HashMap<>();
    
    private String aba;

    public void addParametro(String string, Object object) {
        this.parametros.put(string, object);
    }
    
    public List<List<Object>> getObjects() {
        return objects;
    }

    public void setObjects(List<List<Object>> objects) {
        this.objects = objects;
    }

    public String[] getHeader() {
        return header;
    }

    public void setHeader(String[] header) {
        this.header = header;
    }

    public String getAba() {
        return aba;
    }

    public void setAba(String aba) {
        this.aba = aba;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Map<String, Object> getParametros() {
        return parametros;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
}
