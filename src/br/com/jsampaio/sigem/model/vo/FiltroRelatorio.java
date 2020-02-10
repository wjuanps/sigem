/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.vo;

import java.util.Date;

/**
 *
 * @author Janilson
 */
public class FiltroRelatorio {
    
    private Date dataInicio;
    private Date dataFinal;
    
    private Long unidadeCurso;
    private Long requisitante;
    private Long recebedor;
    private Long equipamento;
    
    private String nomeSolicitante;

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Long getUnidadeCurso() {
        return unidadeCurso;
    }

    public void setUnidadeCurso(Long unidadeCurso) {
        this.unidadeCurso = unidadeCurso;
    }

    public Long getRequisitante() {
        return requisitante;
    }

    public void setRequisitante(Long requisitante) {
        this.requisitante = requisitante;
    }

    public Long getRecebedor() {
        return recebedor;
    }

    public void setRecebedor(Long recebedor) {
        this.recebedor = recebedor;
    }

    public String getNomeSolicitante() {
        return nomeSolicitante;
    }

    public void setNomeSolicitante(String nomeSolicitante) {
        this.nomeSolicitante = nomeSolicitante;
    }

    public Long getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Long equipamento) {
        this.equipamento = equipamento;
    }
    
}
