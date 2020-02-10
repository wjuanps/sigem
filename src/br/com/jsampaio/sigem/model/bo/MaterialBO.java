/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.bo;

import br.com.jsampaio.sigem.model.dao.MaterialDAO;
import br.com.jsampaio.sigem.model.vo.Material;
import br.com.jsampaio.sigem.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Janilson
 */
public class MaterialBO {

    /**
     *
     * @param material
     * @return
     */
    public String salvarMaterial(Material material) {

        try {
            if (material == null) {
                return "Material inválido";
            }

            if (material.getDescricao() == null || material.getDescricao().isEmpty()) {
                return "Informe a descrição do material";
            }

            if (material.getQuantidade() == null
                    || material.getQuantidade() <= 0
                    || !(material.getQuantidade() instanceof Number)) {
                return "Informe a quantidade";
            }

            if (material.getUnidade() == null || material.getUnidade().isEmpty()) {
                return "Informe a unidade";
            }

            final MaterialDAO mdao = new MaterialDAO();
            material = mdao.salvar(material);

            if (material != null) {
                return "";
            }
        } catch (RuntimeException r) {
            Log.saveLog(r, MaterialBO.class);
            return "Erro a cadastrar o material";
        }
        return "Erro a cadastrar o material";
    }

    /**
     *
     * @param descricao
     * @param paginacao
     * @return
     */
    public final List<Material> getListaMateriais(String descricao, Paginacao paginacao) {
        return getListaMateriais(descricao, paginacao, null);
    }
    
    /**
     * 
     * @param descricao
     * @param paginacao
     * @param ativo
     * @return 
     */
    public final List<Material> getListaMateriais(String descricao, Paginacao paginacao, String ativo) {
        try {
            final MaterialDAO mdao = new MaterialDAO();
            final List<Material> materials = mdao.getList(descricao, paginacao, ativo);

            if (!materials.isEmpty()) {
                return materials;
            }
        } catch (RuntimeException r) {
            Log.saveLog(r, MaterialBO.class);
        }
        return new ArrayList<>();
    }

    /**
     *
     * @param codigo
     * @return
     */
    public Material getMaterial(Long codigo) {
        try {
            final MaterialDAO mdao = new MaterialDAO();
            final Material material = mdao.pesquisar(codigo);
            if (material != null) {
                return material;
            }
        } catch (RuntimeException r) {
            Log.saveLog(r, MaterialBO.class);
        }
        return null;
    }
    
    /**
     * 
     * @param material 
     */
    public void atualizarMaterial(Material material) {
        try {
            final MaterialDAO mdao = new MaterialDAO();
            mdao.atualizar(material);
        } catch (RuntimeException r) {
            Log.saveLog(r, MaterialBO.class);
        }
    }
}