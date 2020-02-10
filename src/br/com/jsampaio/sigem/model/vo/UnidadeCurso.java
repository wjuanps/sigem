/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.vo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Janilson
 */
@Entity
@Table(name = "unidade_curso")
public class UnidadeCurso extends AbstractDomain implements Serializable {
    
    @Column(name = "codigo_unidade", unique = true, nullable = true)
    private Long codigoUnidade;
    
    @Column(name = "unidade_curso", length = 70, nullable = false)
    private String unidadeCurso;
    
    public String getUnidadeCurso() {
        return unidadeCurso;
    }

    public void setUnidadeCurso(String unidadeCurso) {
        this.unidadeCurso = unidadeCurso;
    }

    public Long getCodigoUnidade() {
        return codigoUnidade;
    }

    public void setCodigoUnidade(Long codigoUnidade) {
        this.codigoUnidade = codigoUnidade;
    }
    
    @Override
    public String toString() {
        return String.format("%d - %s", getCodigo(), this.unidadeCurso);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.unidadeCurso);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UnidadeCurso other = (UnidadeCurso) obj;
        return Objects.equals(this.unidadeCurso, other.unidadeCurso);
    }
    
}
