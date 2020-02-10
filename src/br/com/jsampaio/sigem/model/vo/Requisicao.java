/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Janilson
 */
@Entity
@Table(name = "requisicao")
public class Requisicao extends AbstractDomain implements Serializable {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Solicitante solicitante;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Solicitante recebedor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Usuario usuario;
    
    @OneToMany(mappedBy = "requisicao", fetch = FetchType.LAZY)
    private final List<RequisicaoMateriais> requisicaoMateriais = new ArrayList<>();
    
    /**
     * 
     * @param material 
     * @param qtdRequisitada 
     * @param qtdRecebida 
     */
    public void addMaterial(Material material, int qtdRequisitada, int qtdRecebida) {
        final RequisicaoMateriais rm = new RequisicaoMateriais(this, material);
        if (!this.getRequisicaoMateriais().contains(rm)) {
            rm.setQuantidadeRequisitada(qtdRequisitada);
            rm.setQuantidadeEntregue(qtdRecebida);
            rm.setDataEntrega(Calendar.getInstance().getTime());
            this.getRequisicaoMateriais().add(rm);
            material.getMateriais().add(rm);
        }
    }

    public List<RequisicaoMateriais> getRequisicaoMateriais() {
        return requisicaoMateriais;
    }

    public Solicitante getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Solicitante solicitante) {
        this.solicitante = solicitante;
    }

    public Solicitante getRecebedor() {
        return recebedor;
    }

    public void setRecebedor(Solicitante recebedor) {
        this.recebedor = recebedor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}