/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.vo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Janilson
 */
@MappedSuperclass
public class Pessoa extends AbstractDomain implements Serializable {
    
    public enum Tipo {Todos, Docente, Funcionario, Aluno, Usuario, Administrador}
    
    @Column(name = "matricula", length = 12, nullable = false, unique = true)
    private Long matricula;
    
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;
    
    @Column(name = "telefone", length = 20, nullable = false)
    private String telefone;
    
    @Column(name = "email", length = 100, nullable = false)
    private String email;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private UnidadeCurso unidadeCurso;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 25)
    private Tipo tipo;

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public UnidadeCurso getUnidadeCurso() {
        return unidadeCurso;
    }

    public void setUnidadeCurso(UnidadeCurso unidadeCurso) {
        this.unidadeCurso = unidadeCurso;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.matricula);
        hash = 43 * hash + Objects.hashCode(this.nome);
        hash = 43 * hash + Objects.hashCode(this.telefone);
        hash = 43 * hash + Objects.hashCode(this.email);
        hash = 43 * hash + Objects.hashCode(this.tipo);
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
        final Pessoa other = (Pessoa) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.telefone, other.telefone)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        return Objects.equals(this.matricula, other.matricula);
    }
    
}
