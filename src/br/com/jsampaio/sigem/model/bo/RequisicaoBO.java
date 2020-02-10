/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.model.bo;

import br.com.jsampaio.sigem.model.dao.MaterialDAO;
import br.com.jsampaio.sigem.model.dao.RequisicaoDAO;
import br.com.jsampaio.sigem.model.dao.SolicitanteDAO;
import br.com.jsampaio.sigem.model.vo.Material;
import br.com.jsampaio.sigem.model.vo.Requisicao;
import br.com.jsampaio.sigem.model.vo.Session;
import br.com.jsampaio.sigem.model.vo.Solicitante;
import br.com.jsampaio.sigem.util.Log;
import java.util.List;

/**
 *
 * @author Janilson
 */
public class RequisicaoBO {

    public final List<Solicitante> getListaSolicitantes(String nome, Paginacao paginacao) {
        try {
            final SolicitanteDAO sdao = new SolicitanteDAO();
            final List<Solicitante> solicitantes = sdao.getList(nome, paginacao, null, null);
            if (!solicitantes.isEmpty()) {
                return solicitantes;
            }
        } catch (RuntimeException r) {
            Log.saveLog(r, RequisicaoBO.class);
        }

        return null;
    }

    /**
     *
     * @param codigo
     * @return
     */
    public Material getMaterial(Long codigo) {
        try {
            final MaterialDAO mdao = new MaterialDAO();
            return mdao.pesquisar(codigo);
        } catch (RuntimeException r) {
            Log.saveLog(r, RequisicaoBO.class);
        }
        return null;
    }

    /**
     *
     * @param codigoMaterial
     * @param qtdRequisitada
     * @param qtdEntregue
     * @param requisicao
     * @return
     */
    public String getMaterial(Long codigoMaterial, int qtdRequisitada, int qtdEntregue, Requisicao requisicao) {
        try {
            final Material material = this.getMaterial(codigoMaterial);

            if (material != null) {

                if (qtdRequisitada <= 0) {
                    return "Informe a quantidade requisitada";
                }

                if (qtdEntregue <= 0) {
                    return "Informe a quantidade entregue";
                }

                if (qtdEntregue > qtdRequisitada) {
                    return "A quantidade entregue não pode ser maior que a quantidade requisitada";
                }

                if (material.getQuantidade() <= 0
                        || material.getQuantidade() < qtdEntregue) {
                    return "Quantidade insuficiente no estoque";
                }

                requisicao.addMaterial(material, qtdRequisitada, qtdEntregue);
                return "";
            }
        } catch (RuntimeException r) {
            Log.saveLog(r, RequisicaoBO.class);
            return "Erro ao adicionar o material";
        }
        return "Erro ao adicionar o material";
    }

    /**
     *
     * @param requisicao
     * @param matriculaSolicitante
     * @param matriculaRecebedor
     * @return
     */
    public String liberarMaterial(Requisicao requisicao, Long matriculaSolicitante, Long matriculaRecebedor) {
        try {
            if (matriculaSolicitante == null) {
                return "Informe o Requisitante";
            }

            if (matriculaRecebedor == null) {
                return "Informe o Recebedor";
            }

            final SolicitanteDAO sdao = new SolicitanteDAO();
            final Solicitante solicitante = sdao.getSolicitante(matriculaSolicitante);

            if (solicitante == null) {
                return "Requisitante não encontrado";
            }

            final Solicitante recebedor = sdao.getSolicitante(matriculaRecebedor);

            if (recebedor == null) {
                return "Recebedor não encontrado";
            }

            if (requisicao.getRequisicaoMateriais() == null || requisicao.getRequisicaoMateriais().isEmpty()) {
                return "Selecione ao menos um material";
            }

            RequisicaoDAO rdao = new RequisicaoDAO();

            requisicao.setSolicitante(solicitante);
            requisicao.setRecebedor(recebedor);
            requisicao.setUsuario(Session.getUsuario());

            rdao.salvarRequisicao(requisicao);
            return "";

        } catch (RuntimeException r) {
            Log.saveLog(r, RequisicaoBO.class);
            return "Erro ao liberar o material";
        }
    }

    /**
     *
     * @param material
     * @return
     */
    public boolean isEstoqueMinimo(Material material) {
        try {
            final long porcentagemEmEstoque = ((material.getQuantidade() * 100L) / material.getQuantidadeMaxima());
            return (porcentagemEmEstoque <= 20);
        } catch (RuntimeException r) {
            Log.saveLog(r, RequisicaoBO.class);
        }
        return false;
    }
    
    /**
     * 
     * @param material
     * @return 
     */
    public Long getQuantidadeMinima(Material material) {
        try {
            return ((material.getQuantidadeMaxima()* 20L) / 100L);
        } catch (RuntimeException r) {
            Log.saveLog(r, RequisicaoBO.class);
        }
        return 0L;
    }
    
    /**
     * 
     * @return 
     */
    public List<Material> listEstoqueMinimo() {
        try {
            final MaterialDAO mdao = new MaterialDAO();
            final List<Material> materials = mdao.listEstoqueMinimo();
            if (materials != null && !materials.isEmpty()) {
                return materials;
            }
        } catch (RuntimeException r) {
            Log.saveLog(r, RequisicaoBO.class);
        }
        return null;
    }
}