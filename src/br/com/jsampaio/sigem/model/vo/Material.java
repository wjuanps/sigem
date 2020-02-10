/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Janilson
 */
@Entity
@Table(name = "material")
public class Material extends AbstractDomain implements Serializable {
    
    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;
    
    @Column(name = "quantidade", nullable = false)
    private Long quantidade;
    
    @Column(name = "quantidade_maxima", nullable = false)
    private Long quantidadeMaxima;
    
    @Column(name = "unidade", nullable = false, length = 20)
    private String unidade;
    
    @Column(name = "valor", nullable = true, scale = 2,precision = 6)
    private BigDecimal valor;
    
    @OneToMany(mappedBy = "material")
    private final List<RequisicaoMateriais> materiais = new ArrayList<>();

    @Override
    public String toString() {
        return (
            String.format("%d - %s - %s", getCodigo(), descricao, unidade)
        );
    }
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public List<RequisicaoMateriais> getMateriais() {
        return materiais;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Long getQuantidadeMaxima() {
        return quantidadeMaxima;
    }

    public void setQuantidadeMaxima(Long quantidadeMaxima) {
        this.quantidadeMaxima = quantidadeMaxima;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.descricao);
        hash = 73 * hash + Objects.hashCode(this.quantidade);
        hash = 73 * hash + Objects.hashCode(this.quantidadeMaxima);
        hash = 73 * hash + Objects.hashCode(this.unidade);
        hash = 73 * hash + Objects.hashCode(this.valor);
        hash = 73 * hash + Objects.hashCode(this.materiais);
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
        final Material other = (Material) obj;
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.unidade, other.unidade)) {
            return false;
        }
        if (!Objects.equals(this.quantidade, other.quantidade)) {
            return false;
        }
        if (!Objects.equals(this.quantidadeMaxima, other.quantidadeMaxima)) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        return Objects.equals(this.materiais, other.materiais);
    }
}