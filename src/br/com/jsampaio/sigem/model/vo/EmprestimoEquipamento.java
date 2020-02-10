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
@Table(name = "emprestimo_has_equipamento")
public class EmprestimoEquipamento implements Serializable {

    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Emprestimo emprestimo;

    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Equipamento equipamento;

    @Column(name = "data_hora_entrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraEntrega;

    @Column(name = "data_hora_devolucao_prevista")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraDevolucaoPrevista;

    @Column(name = "data_hora_devolucao_efetiva")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraDevolucaoEfetiva;
    
    @Column(name = "devolvido", nullable = false)
    private Boolean devolvido;
    
    public EmprestimoEquipamento() {
    }

    public EmprestimoEquipamento(Emprestimo emprestimo, Equipamento equipamento) {
        this.emprestimo = emprestimo;
        this.equipamento = equipamento;
        this.devolvido = false;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public Date getDataHoraEntrega() {
        return dataHoraEntrega;
    }

    public void setDataHoraEntrega(Date dataHoraEntrega) {
        this.dataHoraEntrega = dataHoraEntrega;
    }

    public Date getDataHoraDevolucaoPrevista() {
        return dataHoraDevolucaoPrevista;
    }

    public void setDataHoraDevolucaoPrevista(Date dataHoraDevolucaoPrevista) {
        this.dataHoraDevolucaoPrevista = dataHoraDevolucaoPrevista;
    }

    public Date getDataHoraDevolucaoEfetiva() {
        return dataHoraDevolucaoEfetiva;
    }

    public void setDataHoraDevolucaoEfetiva(Date dataHoraDevolucaoEfetiva) {
        this.dataHoraDevolucaoEfetiva = dataHoraDevolucaoEfetiva;
    }

    public Boolean isDevolvido() {
        return devolvido;
    }

    public void setDevolvido(Boolean devolvido) {
        this.devolvido = devolvido;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.emprestimo);
        hash = 71 * hash + Objects.hashCode(this.equipamento);
        hash = 71 * hash + Objects.hashCode(this.dataHoraEntrega);
        hash = 71 * hash + Objects.hashCode(this.dataHoraDevolucaoPrevista);
        hash = 71 * hash + Objects.hashCode(this.dataHoraDevolucaoEfetiva);
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
        final EmprestimoEquipamento other = (EmprestimoEquipamento) obj;
        if (!Objects.equals(this.emprestimo, other.emprestimo)) {
            return false;
        }
        if (!Objects.equals(this.equipamento, other.equipamento)) {
            return false;
        }
        if (!Objects.equals(this.dataHoraEntrega, other.dataHoraEntrega)) {
            return false;
        }
        if (!Objects.equals(this.dataHoraDevolucaoPrevista, other.dataHoraDevolucaoPrevista)) {
            return false;
        }
        return Objects.equals(this.dataHoraDevolucaoEfetiva, other.dataHoraDevolucaoEfetiva);
    }
    
}
