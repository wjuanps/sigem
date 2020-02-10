/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Janilson
 */
@Entity
@Table(name = "solicitante")
public class Solicitante extends Pessoa implements Serializable {
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solicitante", fetch = FetchType.LAZY)
    private final List<Emprestimo> emprestimos = new ArrayList<>();

    @Override
    public String toString() {
        return (String.format("%d - %s", getMatricula(), getNome()));
    }
    
    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }
    
}
