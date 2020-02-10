/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Janilson
 */
@Entity
@Table(name = "equipamento")
public class Equipamento extends AbstractDomain implements Serializable {
    
    @Column(name = "num_tombamento", nullable = false, unique = true)
    private Long numeroTombamento;
    
    @Column(name = "tipo", length = 50, nullable = false)
    private String tipo;
    
    @Column(name = "descricao", length = 100, nullable = false)
    private String descricao;
    
    @Column(name = "status", length = 15, nullable = false)
    private String status;

    @OneToMany(mappedBy = "equipamento", fetch = FetchType.LAZY)
    private final List<EmprestimoEquipamento> equipamentos = new ArrayList<>();

    public Long getNumeroTombamento() {
        return numeroTombamento;
    }

    public void setNumeroTombamento(Long numeroTombamento) {
        this.numeroTombamento = numeroTombamento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<EmprestimoEquipamento> getEquipamentos() {
        return equipamentos;
    }

    @Override
    public String toString() {
        return (String.format("%d - %s", this.numeroTombamento, this.tipo));
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.numeroTombamento);
        hash = 67 * hash + Objects.hashCode(this.tipo);
        hash = 67 * hash + Objects.hashCode(this.descricao);
        hash = 67 * hash + Objects.hashCode(this.status);
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
        final Equipamento other = (Equipamento) obj;
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        return Objects.equals(this.numeroTombamento, other.numeroTombamento);
    }
    
}
