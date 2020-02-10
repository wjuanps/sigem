/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.bo;

import br.com.jsampaio.sigem.model.dao.EquipamentoDAO;
import br.com.jsampaio.sigem.model.vo.Equipamento;
import br.com.jsampaio.sigem.util.Log;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author Janilson
 */
public final class EquipamentoBO {

    /**
     *
     * @param equipamento
     * @return
     */
    public String cadastrarEquipamento(Equipamento equipamento) {

        if (equipamento == null) {
            return "Falha ao cadastrar equipamento";
        }

        if (equipamento.getTipo() == null || equipamento.getTipo().isEmpty()) {
            return "Informe o tipo do equipamento";
        }

        if (equipamento.getDescricao() == null || equipamento.getDescricao().isEmpty()) {
            return "Informe a descrição do equipamento";
        }

        if (equipamento.getNumeroTombamento() == null) {
            return "Número de tombamento inválido";
        }

        if (equipamento.getStatus() == null || equipamento.getStatus().isEmpty()) {
            return "Falha ao salvar o equipamento";
        }

        try {
            final EquipamentoDAO edao = new EquipamentoDAO();
            edao.salvar(equipamento);
        } catch (ConstraintViolationException c) {
            Log.saveLog(c, EquipamentoBO.class);
            return "Já existe um equipamento com esse número de Tombamento";
        } catch (RuntimeException r) {
            Log.saveLog(r, EquipamentoBO.class);
            return "Erro ao salvar o equipamento";
        }

        return "";
    }

    /**
     * 
     * @param pesquisa
     * @param filtro
     * @param paginacao
     * @return 
     */
    public List<Equipamento> carregarEquipamentos(String pesquisa, String filtro, Paginacao paginacao) {
        return carregarEquipamentos(pesquisa, filtro, paginacao, null);
    }
    
    /**
     *
     * @param pesquisa
     * @param filtro
     * @param paginacao
     * @param ativo
     * @return
     */
    public List<Equipamento> carregarEquipamentos(String pesquisa, String filtro, Paginacao paginacao, String ativo) {
        try {
            final EquipamentoDAO edao = new EquipamentoDAO();
            filtro = (filtro.equals("Filtro")) ? "" : filtro;
            final List<Equipamento> equipamentos = edao.getList(pesquisa, filtro, paginacao, ativo);

            if (!equipamentos.isEmpty()) {
                return equipamentos;
            }

        } catch (RuntimeException r) {
            Log.saveLog(r, EquipamentoBO.class);
        }
        return new ArrayList<>();
    }

    /**
     *
     * @param numTombamento
     * @return
     */
    public Equipamento getEquipamento(Long numTombamento) {
        try {
            final EquipamentoDAO edao = new EquipamentoDAO();
            return edao.getEquipamentoPeloNumTombamento(numTombamento);
        } catch (RuntimeException r) {
            Log.saveLog(r, EquipamentoBO.class);
        }
        return null;
    }
    
    /**
     * 
     * @param equipamento 
     */
    public void atualizarEquipamento(Equipamento equipamento) {
        try {
            final EquipamentoDAO edao = new EquipamentoDAO();
            edao.atualizar(equipamento);
        } catch (RuntimeException r) {
            Log.saveLog(r, EquipamentoBO.class);
        }
    }
}