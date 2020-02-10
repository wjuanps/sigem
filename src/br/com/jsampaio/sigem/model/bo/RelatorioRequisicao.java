/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.bo;

import br.com.jsampaio.sigem.model.dao.RequisicaoDAO;
import br.com.jsampaio.sigem.model.vo.FiltroRelatorio;
import br.com.jsampaio.sigem.model.vo.Requisicao;
import br.com.jsampaio.sigem.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Janilson
 */
public class RelatorioRequisicao {

    public Set<Requisicao> getRequisicoes(FiltroRelatorio filtro, Paginacao paginacao) {
        try {
            RequisicaoDAO rdao = new RequisicaoDAO();
            Set<Requisicao> requisicaos;
            if (paginacao == null) {
                requisicaos = rdao.getRequisicaos(filtro);
            } else {
                requisicaos = rdao.getRequisicaos(filtro, paginacao);
            }
            
            if (!requisicaos.isEmpty()) {
                return requisicaos;
            }
        } catch (RuntimeException r) {
            Log.saveLog(r, RelatorioRequisicao.class);
        }

        return null;
    }

    /**
     * 
     * @param requisicaos
     * @return 
     */
    public Map<String, Double> getDataSet(Set<Requisicao> requisicaos) {
        try {
            final Map<String, Double> dados = new HashMap<>();
            requisicaos.forEach((requisicao) -> {
                requisicao.getRequisicaoMateriais().forEach((rm) -> {
                    final String unidadeCurso = requisicao.getSolicitante().getUnidadeCurso().getUnidadeCurso();
                    if (dados.containsKey(unidadeCurso)) {
                        double oldValue = (double) dados.get(unidadeCurso);
                        dados.replace(unidadeCurso, oldValue + (double) rm.getQuantidadeEntregue());
                    } else {
                        dados.put(unidadeCurso, (double) rm.getQuantidadeEntregue());
                    }
                });
            });
            
            if (!dados.isEmpty()) {
                return dados;
            }
            
        } catch (RuntimeException r) {
            Log.saveLog(r, RelatorioRequisicao.class);
        }
        return null;
    }
}
