/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.main.Messenger;
import br.com.jsampaio.sigem.model.bo.GeradorRelatorioJasper;
import br.com.jsampaio.sigem.model.bo.RequisicaoBO;
import br.com.jsampaio.sigem.model.vo.Material;
import br.com.jsampaio.sigem.model.vo.MessageType;
import br.com.jsampaio.sigem.model.vo.Relatorio;
import br.com.jsampaio.sigem.model.vo.Requisicao;
import br.com.jsampaio.sigem.model.vo.RequisicaoMateriais;
import br.com.jsampaio.sigem.model.vo.Session;
import br.com.jsampaio.sigem.model.vo.Solicitante;
import br.com.jsampaio.sigem.util.Arquivo;
import br.com.jsampaio.sigem.view.TelaAviso;
import br.com.jsampaio.sigem.view.TelaLiberarMaterial;
import br.com.jsampaio.sigem.view.TelaSelecionarMaterial;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;

/**
 *
 * @author Janilson
 */
public class LiberarMaterialController {

    private final TelaLiberarMaterial telaLiberarMaterial;
    private final TelaSelecionarMaterial telaSelecionarMaterial;
    private final SelecionarPessoaController selecionarPessoaController;
    private final TelaAviso telaAviso;
    
    private final PaginacaoController paginacaoController;

    private Requisicao requisicao;

    public LiberarMaterialController(SelecionarPessoaController selecionarPessoaController, TelaAviso telaAviso) {
        this.telaLiberarMaterial = new TelaLiberarMaterial();
        this.selecionarPessoaController = selecionarPessoaController;
        this.telaSelecionarMaterial = new TelaSelecionarMaterial(null, true);
        this.paginacaoController = new PaginacaoController();
        this.telaAviso = telaAviso;
        this.requisicao = new Requisicao();
        
        this.telaSelecionarMaterial.getJpPaginacao().add(this.paginacaoController.getTelaPaginacao(), 0);
        this.paginacaoController.setObject(this, LiberarMaterialController.class, "carregarMateriais");

        final LiberarMaterialListener liberarMaterialListener = this.new LiberarMaterialListener();
        this.telaLiberarMaterial.getBtnPesquisarRequisitante().addActionListener(liberarMaterialListener);
        this.telaLiberarMaterial.getBtnSelecionarMaterial().addActionListener(liberarMaterialListener);
        this.telaLiberarMaterial.getBtnRemoverMaterial().addActionListener(liberarMaterialListener);
        this.telaLiberarMaterial.getBtnLiberarMaterial().addActionListener(liberarMaterialListener);
        this.telaLiberarMaterial.getBtnLimpar().addActionListener(liberarMaterialListener);
        this.telaLiberarMaterial.getBtnSelecionarRecebedor().addActionListener(liberarMaterialListener);

        this.telaSelecionarMaterial.getTxtPesquisa().addKeyListener(liberarMaterialListener);
        this.telaSelecionarMaterial.getBtnConfirmar().addActionListener(liberarMaterialListener);
        this.telaSelecionarMaterial.getBtnFechar().addActionListener(liberarMaterialListener);
        
        this.telaLiberarMaterial.getTxtRequisitante().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selecionarRequisititante();
            }
        });
        
        this.telaLiberarMaterial.getTxtRecebedor().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selecionarRecebedor();
            }
        });
        
        GenericListener.tableListener(telaSelecionarMaterial.getTblMaterial(), telaSelecionarMaterial.getTxtPesquisa());
    }

    private class LiberarMaterialListener extends KeyAdapter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == telaLiberarMaterial.getBtnPesquisarRequisitante()) {
                selecionarRequisititante();
                return;
            }

            if (e.getSource() == telaLiberarMaterial.getBtnSelecionarRecebedor()) {
                selecionarRecebedor();
                return;
            }

            if (e.getSource() == telaLiberarMaterial.getBtnSelecionarMaterial()) {
                carregarMateriais();
                telaSelecionarMaterial.setVisible(true);
                return;
            }

            if (e.getSource() == telaSelecionarMaterial.getBtnConfirmar()) {
                selecionarMaterial();

                telaSelecionarMaterial.getTxtPesquisa().requestFocus();
                return;
            }

            if (e.getSource() == telaLiberarMaterial.getBtnRemoverMaterial()) {
                if (telaLiberarMaterial.getJlMateriais().getSelectedIndex() >= 0) {
                    int response = Messenger.showMessage("Voce tem certeza que deseja excluir esse item", MessageType.QUESTION);
                    if (response == Messenger.YES_OPTION) {
                        final int index = telaLiberarMaterial.getJlMateriais().getSelectedIndex();
                        requisicao.getRequisicaoMateriais().remove(index);
                        carregarListaMateriais();

                        if (requisicao.getRequisicaoMateriais().isEmpty()) {
                            telaLiberarMaterial.getJlMateriais().setListData(new RequisicaoMateriais[]{});
                        }
                    }
                }
                return;
            }

            if (e.getSource() == telaLiberarMaterial.getBtnLiberarMaterial()) {
                Long matriculaSolicitante;
                try {
                    matriculaSolicitante = Long.valueOf(telaLiberarMaterial.getTxtRequisitante().getText().split("\\s")[0].trim());
                } catch (NumberFormatException n) {
                    Messenger.showMessage("Informe o Solicitante", MessageType.ERROR);
                    return;
                }

                Long matriculaRecebedor;
                try {
                    matriculaRecebedor = Long.valueOf(telaLiberarMaterial.getTxtRecebedor().getText().split("\\s")[0].trim());
                } catch (NumberFormatException n) {
                    Messenger.showMessage("Informe o Recebedor", MessageType.ERROR);
                    return;
                }
                
                final RequisicaoBO rbo = new RequisicaoBO();
                final String mensagem = rbo.liberarMaterial(requisicao, matriculaSolicitante, matriculaRecebedor);
                if (mensagem.isEmpty()) {
                    final Relatorio relatorio = new Relatorio();
                    relatorio.setFile(Arquivo.getRelatorio("resumo_retirada_material"));
                    relatorio.addParametro("NUM_REQUISICAO", requisicao.getCodigo());
                    relatorio.addParametro("USUARIO", Session.getUsuario().getNome());
                    relatorio.setTitulo("Resumo Requisição");
                    
                    GeradorRelatorioJasper.gerarJasper(relatorio);
                    iniciarNovaRequisicao();
                } else {
                    Messenger.showMessage(mensagem, MessageType.INFORMATION);
                }
                return;
            }

            if (e.getSource() == telaLiberarMaterial.getBtnLimpar()) {
                iniciarNovaRequisicao();
                return;
            }

            if (e.getSource() == telaSelecionarMaterial.getBtnFechar()) {
                telaSelecionarMaterial.getTxtPesquisa().setText("");
                telaSelecionarMaterial.getJspQtdEntregue().setValue(0);
                telaSelecionarMaterial.getJspQtdRequisitada().setValue(0);

                telaSelecionarMaterial.setVisible(false);
                telaSelecionarMaterial.dispose();
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            
            if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
                return;
            }
            
            if (e.getSource() == telaSelecionarMaterial.getTxtPesquisa()) {
                carregarMateriais();
                final JTable tblMaterial = telaSelecionarMaterial.getTblMaterial();
                if (tblMaterial.getModel().getRowCount() > 0) {
                    tblMaterial.setRowSelectionInterval(0, 0);
                }
            }
        }
    }
    
    public void carregarMateriais() {
        final JTable table = telaSelecionarMaterial.getTblMaterial();
        final String pesquisa = telaSelecionarMaterial.getTxtPesquisa().getText().trim();
        GenericTable.carregarTabelaMateriais(table, pesquisa, this.paginacaoController.getPaginacao());
        this.paginacaoController.printResults();
    }

    /**
     *
     */
    private void selecionarMaterial() {
        if (this.telaSelecionarMaterial.getTblMaterial().getSelectedRowCount() != 1) {
            Messenger.showMessage("Selecione um material", MessageType.ERROR);
            return;
        }

        final int linha = this.telaSelecionarMaterial.getTblMaterial().getSelectedRow();
        final Long codigoMaterial = Long.valueOf(this.telaSelecionarMaterial.getTblMaterial().getValueAt(linha, 0).toString());

        final RequisicaoBO requisicaoBO = new RequisicaoBO();
        final Material material = requisicaoBO.getMaterial(codigoMaterial);
        
        if (requisicaoBO.isEstoqueMinimo(material)) {
            final StringBuilder aviso = new StringBuilder("\n")
                    .append(material.getDescricao()).append(" atingiu o estoque mínimo.\nPor favor solicitar a compra\n\n")
                    .append("Quantidade mínima:\t").append(requisicaoBO.getQuantidadeMinima(material))
                    .append("\nQuantidade em estoque:\t").append(material.getQuantidade());
            telaAviso.mostrarMensagem(aviso, "Estoque Mínimo", false);
        }
        
        final int qtdRequisitada = ((Number) this.telaSelecionarMaterial.getJspQtdRequisitada().getValue()).intValue();
        final int qtdEntregue = ((Number) this.telaSelecionarMaterial.getJspQtdEntregue().getValue()).intValue();

        final String mensagem = requisicaoBO.getMaterial(codigoMaterial, qtdRequisitada, qtdEntregue, requisicao);

        if (mensagem.isEmpty()) {
            this.carregarListaMateriais();
        } else {
            Messenger.showMessage(mensagem, MessageType.ERROR);
        }
    }

    /**
     *
     */
    private void carregarListaMateriais() {
        if (!this.requisicao.getRequisicaoMateriais().isEmpty()) {
            this.telaLiberarMaterial.getJlMateriais().removeAll();
            this.telaLiberarMaterial.getJlMateriais().repaint();

            this.telaLiberarMaterial.getJlMateriais().setListData(this.requisicao.getRequisicaoMateriais()
                    .toArray(new RequisicaoMateriais[requisicao.getRequisicaoMateriais().size()]));

            this.telaSelecionarMaterial.getTxtPesquisa().setText("");
            this.telaSelecionarMaterial.getJspQtdRequisitada().setValue(0);
            this.telaSelecionarMaterial.getJspQtdEntregue().setValue(0);
            carregarMateriais();
        }
    }

    public void iniciarNovaRequisicao() {
        this.telaLiberarMaterial.getTxtRequisitante().setText(null);
        this.telaLiberarMaterial.getTxtUnidade().setText(null);
        this.telaLiberarMaterial.getTxtRecebedor().setText(null);
        this.telaLiberarMaterial.getJlMateriais().setListData(new RequisicaoMateriais[]{});

        this.telaSelecionarMaterial.getTxtPesquisa().setText("");
        this.telaSelecionarMaterial.getJspQtdEntregue().setValue(0);
        this.telaSelecionarMaterial.getJspQtdRequisitada().setValue(0);
        this.carregarMateriais();

        this.requisicao = new Requisicao();
    }
    
    private void selecionarRequisititante() {
        selecionarPessoaController.carregarPessoas();
        selecionarPessoaController.getTelaSelecionarPessoa().setVisible(true);

        final Solicitante solicitante = selecionarPessoaController.getPessoa();

        if (solicitante != null) {
            telaLiberarMaterial.getTxtRequisitante().setText(String.format("%d - %s", solicitante.getMatricula(), solicitante.getNome()));
            telaLiberarMaterial.getTxtUnidade().setText(solicitante.getUnidadeCurso().getUnidadeCurso());
        }

        selecionarPessoaController.setPessoa(null);
    }
    
    private void selecionarRecebedor() {
        selecionarPessoaController.carregarPessoas();
        selecionarPessoaController.getTelaSelecionarPessoa().setVisible(true);

        final Solicitante solicitante = selecionarPessoaController.getPessoa();

        if (solicitante != null) {
            final String recebedor = String.format("%d - %s - %s", solicitante.getMatricula(),
                    solicitante.getNome(), solicitante.getUnidadeCurso().getUnidadeCurso());
            telaLiberarMaterial.getTxtRecebedor().setText(recebedor);
        }

        selecionarPessoaController.setPessoa(null);
    }

    public TelaLiberarMaterial getTelaLiberarMaterial() {
        return telaLiberarMaterial;
    }

    public Requisicao getRequisicao() {
        return requisicao;
    }

    public void setRequisicao(Requisicao requisicao) {
        this.requisicao = requisicao;
    }

}
