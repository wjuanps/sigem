/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name = "emprestimo")
public class Emprestimo extends AbstractDomain implements Serializable {
    
    @Column(name = "num_protocolo", nullable = false, unique = true)
    private Long numeroProtocolo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Solicitante solicitante;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Usuario usuario;
    
    @OneToMany(mappedBy = "emprestimo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<EmprestimoEquipamento> equipamentos = new ArrayList<>();
    
    /**
     * 
     * @param equipamento 
     * @param devolucaoPrevista 
     */
    public void addEquipamento(Equipamento equipamento, Date devolucaoPrevista) {
        final EmprestimoEquipamento ee = new EmprestimoEquipamento(this, equipamento);
        ee.setDataHoraEntrega(Calendar.getInstance().getTime());
        ee.setDataHoraDevolucaoPrevista(devolucaoPrevista);
        this.getEquipamentos().add(ee);
        equipamento.getEquipamentos().add(ee);
    }

    public List<EmprestimoEquipamento> getEquipamentos() {
        return equipamentos;
    }

    public Long getNumeroProtocolo() {
        return numeroProtocolo;
    }

    public void setNumeroProtocolo(Long numeroProtocolo) {
        this.numeroProtocolo = numeroProtocolo;
    }

    public Solicitante getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Solicitante solicitante) {
        this.solicitante = solicitante;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.numeroProtocolo);
        hash = 53 * hash + Objects.hashCode(this.solicitante);
        hash = 53 * hash + Objects.hashCode(this.usuario);
        hash = 53 * hash + Objects.hashCode(this.equipamentos);
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
        final Emprestimo other = (Emprestimo) obj;
        if (!Objects.equals(this.numeroProtocolo, other.numeroProtocolo)) {
            return false;
        }
        if (!Objects.equals(this.solicitante, other.solicitante)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        return Objects.equals(this.equipamentos, other.equipamentos);
    }
    
}
