/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.bo;

import br.com.jsampaio.sigem.model.dao.UnidadeCursoDAO;
import br.com.jsampaio.sigem.model.vo.UnidadeCurso;
import br.com.jsampaio.sigem.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Janilson
 */
public class UnidadeCursoBO {
    
    public String salvarUnidadeCurso(UnidadeCurso unidadeCurso) {
        try {
            if (unidadeCurso == null) {
                return "Valor inválido";
            }
            
            if (unidadeCurso.getUnidadeCurso() == null || unidadeCurso.getUnidadeCurso().isEmpty()) {
                return "Informe a Unidade/Curso";
            }
            
            UnidadeCursoDAO ucdao = new UnidadeCursoDAO();
            
            if (ucdao.hasUnidadeCurso(unidadeCurso.getUnidadeCurso())) {
                return unidadeCurso.getUnidadeCurso().concat(" já está cadastrada");
            }
            
            Long codigo = 0L;
            do {
                codigo = gerarCodigoUnidadeCurso();
            } while (ucdao.hasCodigo(codigo));
            
            unidadeCurso.setCodigoUnidade(codigo);
            
            unidadeCurso = ucdao.salvar(unidadeCurso);
            if (unidadeCurso != null) {
                return "";
            }
            
        } catch (RuntimeException r) {
            Log.saveLog(r, UnidadeCursoBO.class);
        }
        return "Erro ao salvar a Unidade/Curso";
    }
    
    /**
     * 
     * @param unidadeCurso
     * @param paginacao
     * @param ativo
     * @return 
     */
    public List<UnidadeCurso> getUnidadesCursos(String unidadeCurso, Paginacao paginacao, String ativo) {
        try {
            final UnidadeCursoDAO ucdao = new UnidadeCursoDAO();
            final List<UnidadeCurso> unidadeCursos = ucdao.getList(unidadeCurso, paginacao, ativo);
            if (!unidadeCursos.isEmpty()) {
                return unidadeCursos;
            }
        } catch (RuntimeException r) {
            Log.saveLog(r, UnidadeCursoBO.class);
        }
        return new ArrayList<>();
    }
    
    /**
     * 
     * @param unidadeCurso
     * @param paginacao
     * @return 
     */
    public List<UnidadeCurso> getUnidadesCursos(String unidadeCurso, Paginacao paginacao) {
        return getUnidadesCursos(unidadeCurso, paginacao, null);
    }
    
    /**
     * 
     * @param codigo
     * @return 
     */
    public UnidadeCurso getUnidadeCurso(Long codigo) {
        try {
            final UnidadeCursoDAO ucdao = new UnidadeCursoDAO();
            final UnidadeCurso unidadeCurso = ucdao.pesquisar(codigo);
            if (unidadeCurso != null) {
                return unidadeCurso;
            }
        } catch (RuntimeException r) {
            Log.saveLog(r, UnidadeCursoBO.class);
        }
        return null;
    }
    
    /**
     * 
     * @return 
     */
    private Long gerarCodigoUnidadeCurso() {
        Random random = new Random();
        String gen = "";
        for (int i = 0; i < 6; i++) {
            gen = gen.concat(String.valueOf(random.nextInt(10)));
        }
        return Long.valueOf(gen);
    }
    
    /**
     * 
     * @param unidadeCurso
     * @return 
     */
    public boolean atualizarUnidadeCurso(UnidadeCurso unidadeCurso) {
        if (unidadeCurso != null) {
            if (unidadeCurso.getCodigo() != null 
                    && (unidadeCurso.getUnidadeCurso() != null 
                    && !unidadeCurso.getUnidadeCurso().isEmpty())) {
                final UnidadeCursoDAO ucdao = new UnidadeCursoDAO();
                ucdao.atualizar(unidadeCurso);
                return true;
            }
        }
        return false;
    }
}