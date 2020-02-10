/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.bo;

import br.com.jsampaio.sigem.model.dao.EmprestimoDAO;
import br.com.jsampaio.sigem.model.vo.Emprestimo;
import br.com.jsampaio.sigem.model.vo.EmprestimoEquipamento;
import br.com.jsampaio.sigem.model.vo.FiltroRelatorio;
import br.com.jsampaio.sigem.util.Log;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Janilson
 */
public class RelatorioEmprestimo {

    /**
     *
     * @param filtro
     * @param paginacao
     * @return
     */
    public List<EmprestimoEquipamento> getEquipamentosEmprestados(FiltroRelatorio filtro, Paginacao paginacao) {
        try {
            final EmprestimoDAO edao = new EmprestimoDAO();
            final List<EmprestimoEquipamento> emprestimos = edao.getEquipamentosEmprestados(filtro, paginacao);
            if (emprestimos != null && !emprestimos.isEmpty()) {
                return emprestimos;
            }
        } catch (RuntimeException r) {
            Log.saveLog(r, RelatorioEmprestimo.class);
        }
        return null;
    }

    /**
     *
     * @param filtro
     * @param paginacao
     * @return
     */
    public Set<Emprestimo> getEmprestimos(FiltroRelatorio filtro, Paginacao paginacao) {
        try {
            final EmprestimoDAO edao = new EmprestimoDAO();
            final Set<Emprestimo> emprestimos = edao.getEmprestimos(filtro, paginacao);
            if (emprestimos != null && !emprestimos.isEmpty()) {
                return emprestimos;
            }
        } catch (RuntimeException r) {
            Log.saveLog(r, RelatorioEmprestimo.class);
        }
        return null;
    }
}