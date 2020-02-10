/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Janilson
 */
@Entity
@Table(name = "requisicao_has_materiais")
public class RequisicaoMateriais implements Serializable {
    
    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Requisicao requisicao;
    
    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Material material;
    
    @Column(name = "data_hora_entrega", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataEntrega;
    
    @Column(name = "quantidade_requisitada")
    private int quantidadeRequisitada;
    
    @Column(name = "quantidade_entregue")
    private int quantidadeEntregue;

    public RequisicaoMateriais() {
    }

    public RequisicaoMateriais(Requisicao requisicao, Material material) {
        this.requisicao = requisicao;
        this.material = material;
    }

    @Override
    public String toString() {
        return (
            String.format("%s - %d", this.material, this.quantidadeEntregue)
        );
    }
    
    public Requisicao getRequisicao() {
        return requisicao;
    }

    public void setRequisicao(Requisicao requisicao) {
        this.requisicao = requisicao;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public int getQuantidadeRequisitada() {
        return quantidadeRequisitada;
    }

    public void setQuantidadeRequisitada(int quantidadeRequisitada) {
        this.quantidadeRequisitada = quantidadeRequisitada;
    }

    public int getQuantidadeEntregue() {
        return quantidadeEntregue;
    }

    public void setQuantidadeEntregue(int quantidadeEntregue) {
        this.quantidadeEntregue = quantidadeEntregue;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.requisicao);
        hash = 79 * hash + Objects.hashCode(this.material);
        hash = 79 * hash + Objects.hashCode(this.dataEntrega);
        hash = 79 * hash + this.quantidadeRequisitada;
        hash = 79 * hash + this.quantidadeEntregue;
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
        final RequisicaoMateriais other = (RequisicaoMateriais) obj;
        if (this.quantidadeRequisitada != other.quantidadeRequisitada) {
            return false;
        }
        if (this.quantidadeEntregue != other.quantidadeEntregue) {
            return false;
        }
        if (!Objects.equals(this.requisicao, other.requisicao)) {
            return false;
        }
        if (!Objects.equals(this.material, other.material)) {
            return false;
        }
        return Objects.equals(this.dataEntrega, other.dataEntrega);
    }
    
}
