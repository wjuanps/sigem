/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.main.Messenger;
import br.com.jsampaio.sigem.main.Sigem;
import br.com.jsampaio.sigem.model.bo.GeradorRelatorioJasper;
import br.com.jsampaio.sigem.model.bo.RequisicaoBO;
import br.com.jsampaio.sigem.model.vo.Equipamento;
import br.com.jsampaio.sigem.model.vo.Material;
import br.com.jsampaio.sigem.model.vo.MessageType;
import br.com.jsampaio.sigem.model.vo.Pessoa;
import br.com.jsampaio.sigem.model.vo.Relatorio;
import br.com.jsampaio.sigem.model.vo.Session;
import br.com.jsampaio.sigem.util.Arquivo;
import br.com.jsampaio.sigem.util.Configs;
import br.com.jsampaio.sigem.util.Directory;
import br.com.jsampaio.sigem.util.HibernateUtil;
import br.com.jsampaio.sigem.view.TelaAviso;
import br.com.jsampaio.sigem.view.TelaInicial;
import br.com.jsampaio.sigem.view.TelaSplash;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.JLabel;

/**
 *
 * @author Janilson
 */
public class InicioController implements ActionListener {

    private final TelaInicial telaInicial;
    private final SelecionarPessoaController selecionarPessoaController;
    private final SelecionarEquipamentoController selecionarEquipamentoController;
    private final UnidadeCursoController unidadeCursoController;
    private final AdministradorController administradorController;
    private final TelaAviso telaAviso;
    private EmprestimoController emprestimoController;
    private LiberarMaterialController liberarMaterialController;
    private EquipamentoController equipamentoController;
    private PessoaController pessoaController;
    private CadastroMaterialController cadastroMaterialController;
    private AtualizacaoMaterialController atualizacaoMaterialController;
    private RelatorioRequisicaoController relatorioRequisicaoController;
    private RelatorioEmprestimoController relatorioEmprestimoController;
    private RelatorioMaterialController relatorioMaterialController;
    private RelatorioEquipamentoController relatorioEquipamentoController;
    private DevolucaoEmprestimoController devolucaoEmprestimoController;
    private AtualizarSenhaController atualizarSenhaController;

    private String telaAtual = "telaInicio";

    public InicioController() {
        this.telaInicial = new TelaInicial();
        this.selecionarPessoaController = new SelecionarPessoaController();
        this.selecionarEquipamentoController = new SelecionarEquipamentoController();
        this.unidadeCursoController = new UnidadeCursoController();
        this.administradorController = new AdministradorController();
        this.telaAviso = new TelaAviso(null, true);

        this.telaInicial.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Sigem.closeSystem();
            }
        });

        this.telaInicial.getJmiEmprestimoEquipamento().addActionListener(this);
        this.telaInicial.getJmiRequisitarMaterial().addActionListener(this);
        this.telaInicial.getJmiCadastrarEquipamento().addActionListener(this);
        this.telaInicial.getJmiCadastrarPessoa().addActionListener(this);
        this.telaInicial.getJmiCadastrarMaterial().addActionListener(this);
        this.telaInicial.getJmiAtualizarMaterial().addActionListener(this);
        this.telaInicial.getBtnSair().addActionListener(this);

        this.telaInicial.getBtnRequisitarMaterial().addActionListener(this);
        this.telaInicial.getBtnEmprestarEquipamento().addActionListener(this);
        this.telaInicial.getBtnCadastrarPessoa().addActionListener(this);
        this.telaInicial.getJmiAtualizarPessoa().addActionListener(this);
        this.telaInicial.getBtnCadastrarMaterial().addActionListener(this);
        this.telaInicial.getBtnCadastrarEquipamento().addActionListener(this);
        this.telaInicial.getJmiAtualizarEquipamento().addActionListener(this);
        this.telaInicial.getBtnVoltarInicio().addActionListener(this);

        this.telaInicial.getJmiRelatorioMaterial().addActionListener(this);
        this.telaInicial.getJmiRelatorioEquipamento().addActionListener(this);
        this.telaInicial.getJmiRelatorioRequisicoes().addActionListener(this);
        this.telaInicial.getJmiRelatorioEmprestimo().addActionListener(this);
        this.telaInicial.getJmiDevolucaoEquipamento().addActionListener(this);
        this.telaInicial.getJmiCadastrarUnidadeCurso().addActionListener(this);
        this.telaInicial.getJmiTrocarSenha().addActionListener(this);
        this.telaInicial.getJmiAtualizarDadosUsuario().addActionListener(this);
        this.telaInicial.getJmiRelatorioEstoqueMinimo().addActionListener(this);

        this.telaInicial.getBtnPainelDeControle().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        this.telaInicial.getBtnVoltarInicio().setVisible(true);

        if (e.getSource() == this.telaInicial.getJmiEmprestimoEquipamento()
                || e.getSource() == this.telaInicial.getBtnEmprestarEquipamento()) {
            if (this.emprestimoController == null) {
                this.emprestimoController = new EmprestimoController(selecionarPessoaController, selecionarEquipamentoController, telaAviso);
                this.telaInicial.getRoot().add(this.emprestimoController.getTelaEmprestimo(), "telaEmprestimo");
                this.setDataAcessoText(this.emprestimoController.getTelaEmprestimo().getLbDataAcesso());
            }
            this.emprestimoController.limparCampos();
            this.mudarTela("telaEmprestimo");
            return;
        }

        if (e.getSource() == this.telaInicial.getJmiRequisitarMaterial()
                || e.getSource() == this.telaInicial.getBtnRequisitarMaterial()) {
            if (this.liberarMaterialController == null) {
                this.liberarMaterialController = new LiberarMaterialController(selecionarPessoaController, telaAviso);
                this.telaInicial.getRoot().add(this.liberarMaterialController.getTelaLiberarMaterial(), "telaLiberarMaterial");
                this.setDataAcessoText(this.liberarMaterialController.getTelaLiberarMaterial().getLbAcesso());
            }
            this.liberarMaterialController.iniciarNovaRequisicao();
            this.mudarTela("telaLiberarMaterial");
            return;
        }

        if (e.getSource() == this.telaInicial.getJmiCadastrarEquipamento()
                || e.getSource() == this.telaInicial.getBtnCadastrarEquipamento()) {
            mostrarTelaCadastroEquipamento();
            return;
        }

        if (e.getSource() == this.telaInicial.getJmiAtualizarEquipamento()) {
            this.selecionarEquipamentoController.carregarEquipamentos();
            this.selecionarEquipamentoController.getTelaSelecionarEquipamento().setVisible(true);

            final Equipamento equipamento = this.selecionarEquipamentoController.getEquipamento();
            if (equipamento != null) {
                this.mostrarTelaCadastroEquipamento();
                this.equipamentoController.setCodigo(equipamento.getCodigo());

                if (equipamento.getStatus().equals("Emprestado")) {
                    this.equipamentoController.getTelaCadastrarEquipamento().getJcbSituacao().setEnabled(false);
                }

                this.equipamentoController.getTelaCadastrarEquipamento().preencherCamposAtualizacao(equipamento);
            } else {
                if (this.telaAtual.equals("telaInicio")) {
                    this.telaInicial.getBtnVoltarInicio().setVisible(false);
                }
            }

            this.selecionarEquipamentoController.setEquipamento(null);
            return;
        }

        if (e.getSource() == this.telaInicial.getJmiCadastrarPessoa()
                || e.getSource() == this.telaInicial.getBtnCadastrarPessoa()) {
            this.mostrarTelaCadastroPessoa();
            return;
        }

        if (e.getSource() == this.telaInicial.getJmiAtualizarPessoa()) {
            this.selecionarPessoaController.carregarPessoas();
            this.selecionarPessoaController.getTelaSelecionarPessoa().setVisible(true);

            final Pessoa pessoa = this.selecionarPessoaController.getPessoa();
            if (pessoa != null) {
                this.mostrarTelaCadastroPessoa();
                this.pessoaController.setCodigo(pessoa.getCodigo());
                this.pessoaController.getTelaCadastroPessoa().preencherCamposAtualizacao(pessoa);
            } else {
                if (this.telaAtual.equals("telaInicio")) {
                    this.telaInicial.getBtnVoltarInicio().setVisible(false);
                }
            }

            this.selecionarPessoaController.setPessoa(null);
            return;
        }

        if (e.getSource() == this.telaInicial.getJmiCadastrarMaterial()
                || e.getSource() == this.telaInicial.getBtnCadastrarMaterial()) {
            if (this.cadastroMaterialController == null) {
                this.cadastroMaterialController = new CadastroMaterialController();
                this.telaInicial.getRoot().add(this.cadastroMaterialController.getTelaCadastroMaterial(), "telaCadastroMaterial");
                this.setDataAcessoText(this.cadastroMaterialController.getTelaCadastroMaterial().getLbDataAcesso());
            }
            this.cadastroMaterialController.getTelaCadastroMaterial().limparCampos();
            this.mudarTela("telaCadastroMaterial");
            return;
        }

        if (e.getSource() == this.telaInicial.getJmiAtualizarMaterial()) {
            if (this.atualizacaoMaterialController == null) {
                this.atualizacaoMaterialController = new AtualizacaoMaterialController();
                this.telaInicial.getRoot().add(this.atualizacaoMaterialController.getTelaAtualizarMaterial(), "telaAtualizarMaterial");
                this.setDataAcessoText(this.atualizacaoMaterialController.getTelaAtualizarMaterial().getLbDataAcesso());
            }
            this.atualizacaoMaterialController.carregarMateriais();
            this.mudarTela("telaAtualizarMaterial");
            return;
        }

        if (e.getSource() == this.telaInicial.getJmiRelatorioRequisicoes()) {
            if (this.relatorioRequisicaoController == null) {
                this.relatorioRequisicaoController = new RelatorioRequisicaoController(selecionarPessoaController, unidadeCursoController);
                this.telaInicial.getRoot().add(this.relatorioRequisicaoController.getTelaRelatorioMaterial(), "telaRelatorioRequisicao");
                this.setDataAcessoText(this.relatorioRequisicaoController.getTelaRelatorioMaterial().getLbDataAcesso());
            }
            this.relatorioRequisicaoController.getTelaRelatorioMaterial().limparCampos();
            this.relatorioRequisicaoController.recarregarTabela();
            this.mudarTela("telaRelatorioRequisicao");
            return;
        }

        if (e.getSource() == this.telaInicial.getJmiRelatorioEquipamento()) {
            if (this.relatorioEquipamentoController == null) {
                this.relatorioEquipamentoController = new RelatorioEquipamentoController();
                this.telaInicial.getRoot().add(this.relatorioEquipamentoController.getTelaRelatorioEquipamento(), "telaRelatorioEquipamento");
                this.setDataAcessoText(this.relatorioEquipamentoController.getTelaRelatorioEquipamento().getLbDataAcesso());
            }
            this.relatorioEquipamentoController.limparCampos();
            this.mudarTela("telaRelatorioEquipamento");
            return;
        }

        if (e.getSource() == this.telaInicial.getJmiRelatorioEmprestimo()) {
            if (this.relatorioEmprestimoController == null) {
                this.relatorioEmprestimoController = new RelatorioEmprestimoController(selecionarPessoaController);
                this.telaInicial.getRoot().add(this.relatorioEmprestimoController.getTelaRelatorioEmprestimo(), "telaRelatorioEmprestimo");
                this.setDataAcessoText(this.relatorioEmprestimoController.getTelaRelatorioEmprestimo().getLbDataAcesso());
            }
            this.relatorioEmprestimoController.limparCampos();
            this.relatorioEmprestimoController.recarregarTabela();
            this.mudarTela("telaRelatorioEmprestimo");
            return;
        }

        if (e.getSource() == this.telaInicial.getJmiDevolucaoEquipamento()) {
            if (this.devolucaoEmprestimoController == null) {
                this.devolucaoEmprestimoController = new DevolucaoEmprestimoController();
                this.telaInicial.getRoot().add(this.devolucaoEmprestimoController.getTelaDevolucaoEmprestimo(), "telaDevolucaoEquipamento");
                this.setDataAcessoText(this.devolucaoEmprestimoController.getTelaDevolucaoEmprestimo().getLbDataAcesso());
            }
            this.devolucaoEmprestimoController.limparCampos();
            this.devolucaoEmprestimoController.recarregarTabela();
            this.mudarTela("telaDevolucaoEquipamento");
            return;
        }

        if (e.getSource() == this.telaInicial.getBtnPainelDeControle()) {
            if (this.administradorController.getTelaAdministrador() == null) {
                this.administradorController.setTelaAdministrador();
                this.telaInicial.getRoot().add(this.administradorController.getTelaAdministrador(), "telaAdministrador");
                this.setDataAcessoText(this.administradorController.getTelaAdministrador().getLbDataAcesso());
            }
            this.administradorController.limparCampos();
            this.mudarTela("telaAdministrador");
            return;
        }

        if (e.getSource() == this.telaInicial.getJmiCadastrarUnidadeCurso()) {
            if (this.telaAtual.equals("telaInicio")) {
                this.telaInicial.getBtnVoltarInicio().setVisible(false);
            }
            this.unidadeCursoController.limparCampos();
            this.unidadeCursoController.getTelaCadastrarUnidadeCurso().setVisible(true);
            return;
        }

        if (e.getSource() == this.telaInicial.getBtnVoltarInicio()) {
            this.mudarTela("telaInicio");
            this.telaInicial.getBtnVoltarInicio().setVisible(false);
            return;
        }

        if (e.getSource() == this.telaInicial.getJmiRelatorioMaterial()) {
            if (this.relatorioMaterialController == null) {
                this.relatorioMaterialController = new RelatorioMaterialController();
                this.telaInicial.getRoot().add(this.relatorioMaterialController.getTelaRelatorioMaterial(), "telaRelatorioMaterial");
                this.setDataAcessoText(this.relatorioMaterialController.getTelaRelatorioMaterial().getLbDataAcesso());
            }
            this.relatorioMaterialController.carregarMaterial();
            this.mudarTela("telaRelatorioMaterial");
            return;
        }

        if (e.getSource() == this.telaInicial.getBtnSair()) {
            if (this.telaAtual.equals("telaInicio")) {
                this.telaInicial.getBtnVoltarInicio().setVisible(false);
            }
            Sigem.closeSystem();
            return;
        }

        if (e.getSource() == telaInicial.getJmiTrocarSenha()) {
            if (atualizarSenhaController == null) {
                atualizarSenhaController = new AtualizarSenhaController();
            }
            if (this.telaAtual.equals("telaInicio")) {
                this.telaInicial.getBtnVoltarInicio().setVisible(false);
            }
            atualizarSenhaController.getTelaAtualizarSenha().setVisible(true);

            return;
        }

        if (e.getSource() == telaInicial.getJmiRelatorioEstoqueMinimo()) {
            
            final RequisicaoBO rbo = new RequisicaoBO();
            final List<Material> materials = rbo.listEstoqueMinimo();
            if (materials == null || materials.isEmpty()) {
                Messenger.showMessage("Não existem materiais no estoque mínimo", MessageType.INFORMATION);
                return;
            }
            
            final Relatorio relatorio = new Relatorio();
            relatorio.setFile(Arquivo.getRelatorio("relatorio_estoque_minimo"));
            relatorio.addParametro("USUARIO", Session.getUsuario().getNome());
            relatorio.setTitulo("Relatório Estóque Mínimo");

            GeradorRelatorioJasper.gerarJasper(relatorio);
        }
    }

    public void mostrarTelaCadastroPessoa() {
        if (this.pessoaController == null) {
            this.pessoaController = new PessoaController(unidadeCursoController);
            this.telaInicial.getRoot().add(this.pessoaController.getTelaCadastroPessoa(), "telaCadastrarPessoa");
            this.setDataAcessoText(this.pessoaController.getTelaCadastroPessoa().getLbDataAcesso());
        }
        this.pessoaController.setCodigo(null);
        this.pessoaController.getTelaCadastroPessoa().limparCampos();
        this.mudarTela("telaCadastrarPessoa");
    }

    public void mostrarTelaCadastroEquipamento() {
        if (this.equipamentoController == null) {
            this.equipamentoController = new EquipamentoController();
            this.telaInicial.getRoot().add(this.equipamentoController.getTelaCadastrarEquipamento(), "telaCadastrarEquipamento");
            this.setDataAcessoText(this.equipamentoController.getTelaCadastrarEquipamento().getLbDataAcesso());
        }
        this.equipamentoController.limparCampos();
        this.mudarTela("telaCadastrarEquipamento");
    }

    private void mudarTela(String nomeTela) {
        this.telaAtual = nomeTela;
        CardLayout cl = (CardLayout) this.telaInicial.getRoot().getLayout();
        cl.show(this.telaInicial.getRoot(), nomeTela);
    }

    private void setDataAcessoText(JLabel label) {
        label.setText(String.format("%s - %2$tA, %2$td de %2$tB de %2$tY %2$tI:%2$tM %2$tp",
                Session.getUsuario().getNome(), Session.getDataAcesso()));
    }

    public TelaInicial getTelaInicial() {
        return telaInicial;
    }

    public TelaAviso getTelaAviso() {
        return telaAviso;
    }

    public AdministradorController getAdministradorController() {
        return administradorController;
    }

    public static void main(String[] args) {

        final TelaSplash telaSplash = new TelaSplash();
        telaSplash.iniciarThread();

        HibernateUtil.getSessionFactory().openSession();
        Directory.criarDiretorios();
        Arquivo.copiarArquivos(Paths.get(Arquivo.SYSTEM_REPORTS), Arquivo.APP_DATA_REPORTS);
        Arquivo.copiarArquivos(Paths.get(Arquivo.SYSTEM_CONFIG), Arquivo.APP_DATA_CONFIG);
        Arquivo.carregarRelatorios();
        Configs.loadConfigs();

        telaSplash.finalizarThread();

        final InicioController inicioController = new InicioController();
        final AdministradorController administradorController = inicioController.getAdministradorController();
        administradorController.cadastrarAdministrador();

        final LoginController loginController = new LoginController();
        loginController.mostrarTelaInicial(inicioController.getTelaInicial(), inicioController.getTelaAviso());
    }
}
