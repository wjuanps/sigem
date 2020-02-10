/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.main.Messenger;
import br.com.jsampaio.sigem.model.bo.AdministradorBO;
import br.com.jsampaio.sigem.model.bo.EquipamentoBO;
import br.com.jsampaio.sigem.model.bo.GeradorRelatorioJasper;
import br.com.jsampaio.sigem.model.bo.MaterialBO;
import br.com.jsampaio.sigem.model.bo.Paginacao;
import br.com.jsampaio.sigem.model.bo.PessoaBO;
import br.com.jsampaio.sigem.model.bo.UnidadeCursoBO;
import br.com.jsampaio.sigem.model.bo.UsuarioBO;
import br.com.jsampaio.sigem.model.vo.Equipamento;
import br.com.jsampaio.sigem.model.vo.Material;
import br.com.jsampaio.sigem.model.vo.MessageType;
import br.com.jsampaio.sigem.model.vo.Pessoa;
import br.com.jsampaio.sigem.model.vo.Relatorio;
import br.com.jsampaio.sigem.model.vo.Session;
import br.com.jsampaio.sigem.model.vo.Solicitante;
import br.com.jsampaio.sigem.model.vo.UnidadeCurso;
import br.com.jsampaio.sigem.model.vo.Usuario;
import br.com.jsampaio.sigem.util.Arquivo;
import br.com.jsampaio.sigem.util.Log;
import br.com.jsampaio.sigem.view.TelaAdministrador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;

/**
 *
 * @author Janilson
 */
public class AdministradorController {

    private final JComboBox<String> combo = new JComboBox<>();

    private TelaAdministrador telaAdministrador;
    private PaginacaoController paginacaoController;

    private String telaAtual = "";

    public AdministradorController() {
    }

    private void initListener() {
        final AdministradorListener listener = this.new AdministradorListener();
        this.telaAdministrador.getBtnCursos().addActionListener(listener);
        this.telaAdministrador.getBtnEquipamentos().addActionListener(listener);
        this.telaAdministrador.getBtnMateriais().addActionListener(listener);
        this.telaAdministrador.getBtnPessoas().addActionListener(listener);
        this.telaAdministrador.getBtnUsuarios().addActionListener(listener);
        this.telaAdministrador.getBtnLimparFiltros().addActionListener(listener);
        this.telaAdministrador.getBtnImprimir().addActionListener(listener);

        this.telaAdministrador.getTxtPesquisa().addKeyListener(listener);
        this.telaAdministrador.getJcbAtivo().addItemListener(listener);
        this.telaAdministrador.getJcbTipo().addItemListener(listener);
        this.telaAdministrador.getJcbSituacao().addItemListener(listener);

        this.telaAdministrador.getTblAdministrador().addFocusListener(listener);
        this.telaAdministrador.getTblAdministrador().addKeyListener(listener);
        this.telaAdministrador.getTblAdministrador().addMouseListener(listener);

        GenericListener.tableListener(this.telaAdministrador.getTblAdministrador(), this.telaAdministrador.getTxtPesquisa());

        GenericListener.addListener(this.telaAdministrador.getTblAdministrador(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarDados(false);
            }
        });
    }

    private class AdministradorListener extends KeyAdapter implements ActionListener, ItemListener, FocusListener, MouseListener {

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN
                    || e.getKeyCode() == KeyEvent.VK_UP
                    || e.getKeyCode() == KeyEvent.VK_ENTER) {
                toggleBotoes();
                return;
            }

            if (e.getSource() == telaAdministrador.getTxtPesquisa()) {
                if (telaAtual != null && !telaAtual.isEmpty()) {
                    carregarRegistros();
                }
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            final JButton source = (JButton) e.getSource();

            if (source == telaAdministrador.getBtnImprimir()) {
                gerarRelatorio();
                return;
            }

            if (source == telaAdministrador.getBtnLimparFiltros()) {
                telaAdministrador.limparFiltros();
                carregarRegistros();
                return;
            }

            if (!telaAtual.equals(source.getName())) {
                habilitarControles();
                telaAtual = source.getName();
                telaAdministrador.getJcbSituacao().setVisible(false);
                telaAdministrador.getJcbTipo().setVisible(false);
                carregarRegistros(telaAtual, true);
                source.setEnabled(false);
            }
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getSource() != combo && telaAtual != null && !telaAtual.isEmpty()) {
                carregarRegistros();
            }
        }

        @Override
        public void focusGained(FocusEvent e) {
            if (e.getSource() == telaAdministrador.getTblAdministrador()) {
                toggleBotoes();
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getSource() == telaAdministrador.getTblAdministrador()) {
                toggleBotoes();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    /**
     *
     */
    public void carregarRegistros() {
        carregarRegistros(telaAtual, false);
    }

    /**
     *
     * @param tela
     * @param criarTabela
     */
    private void carregarRegistros(String tela, boolean criarTabela) {
        if (tela != null && !tela.isEmpty()) {
            final JTable table = this.telaAdministrador.getTblAdministrador();
            final Paginacao paginacao = this.paginacaoController.getPaginacao();
            final String pesquisa = this.telaAdministrador.getTxtPesquisa().getText().trim();
            final String ativo = this.telaAdministrador.getJcbAtivo().getSelectedItem().toString().trim();

            List<List<String>> dados = null;

            switch (tela) {
                case "Usuários":
                    dados = this.carregarUsuarios(table, pesquisa, ativo, criarTabela, paginacao);
                    break;
                case "Pessoas":
                    this.telaAdministrador.getJcbTipo().setVisible(true);
                    dados = this.carregarPessoas(table, pesquisa, ativo, criarTabela, paginacao);
                    break;
                case "Equipamentos":
                    this.telaAdministrador.getJcbSituacao().setVisible(true);
                    final String filtro = this.telaAdministrador.getJcbSituacao().getSelectedItem().toString().trim();
                    dados = this.carregarEquipamentos(table, pesquisa, filtro, ativo, criarTabela, paginacao);
                    break;
                case "Materiais":
                    dados = this.carregarMateriais(table, pesquisa, ativo, criarTabela, paginacao);
                    break;
                case "Unidade/Curso":
                    dados = this.carregarUnidadesCursos(table, pesquisa, ativo, criarTabela, paginacao);
                    break;
                default:
                    break;
            }

            GenericTable.addCombo(this.combo, table, new String[]{"Ativo", "Desativado"});
            GenericTable.carregarTabelaAdministrador(table, dados);
            table.repaint();
            table.validate();

            this.paginacaoController.printResults();
            this.telaAdministrador.getBtnImprimir().setEnabled(table.getRowCount() > 0);
        }
    }

    /**
     *
     */
    private void toggleBotoes() {
        if (this.telaAtual != null && !this.telaAtual.isEmpty()) {
            try {
                final JTable table = this.telaAdministrador.getTblAdministrador();
                if (table.getSelectedRowCount() == 1) {
                    final int row = table.getSelectedRow();
                    final int col = table.getColumn("Status").getModelIndex();
                    final String status = table.getValueAt(row, col).toString();

                    boolean ativo = (status.equals("Ativo"));

                }
            } catch (ArrayIndexOutOfBoundsException a) {
                Log.saveLog(a, AdministradorController.class);
            }
        }
    }

    /**
     *
     * @param status
     */
    private void atualizarDados(boolean ativarDesativar) {
        if (this.telaAtual != null && !this.telaAtual.isEmpty()) {
            final JTable table = this.telaAdministrador.getTblAdministrador();
            final Long codigo = Long.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString());

            if (Messenger.showMessage("Você realmente deseja realizar essa operação?",
                    MessageType.QUESTION) == Messenger.YES_OPTION) {
                if (table.getSelectedRowCount() == 1) {

                    final List<String> colunas = new ArrayList();
                    try {
                        if (!ativarDesativar) {
                            for (int i = 1; i < 7; i++) {
                                colunas.add(table.getValueAt(table.getSelectedRow(), i).toString());
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException a) {
                        Log.saveLog(a, AdministradorController.class);
                    }

                    final boolean status = colunas.get(colunas.size() - 1).equals("Ativo");

                    switch (this.telaAtual) {
                        case "Usuários":
                            this.atualizarUsuario(status, codigo, colunas);
                            break;
                        case "Pessoas":
                            this.atualizarPessoa(status, codigo, colunas);
                            break;
                        case "Equipamentos":
                            this.atualizarEquipamento(status, codigo, colunas);
                            break;
                        case "Materiais":
                            this.atualizarMaterial(status, codigo, colunas);
                            break;
                        case "Unidade/Curso":
                            this.atualizarUnidadeCurso(status, codigo, colunas);
                            break;
                        default:
                            break;
                    }
                }
            }

        }

        this.carregarRegistros();
    }

    /**
     *
     * @param table
     * @param pesquisa
     * @param ativo
     * @param criarTabela
     */
    private List<List<String>> carregarUsuarios(JTable table, String pesquisa, String ativo, boolean criarTabela, Paginacao paginacao) {
        final UsuarioBO ubo = new UsuarioBO();

        if (criarTabela) {
            final String colunas[] = {"Matricula", "Nome", "Email", "Telefone", "Unidade/Curso", "Status"};
            final boolean edit[] = {false, true, true, true, false, true};
            table.setModel(GenericTable.getModel(colunas, edit));
        }

        final List<List<String>> dados = new ArrayList<>();
        final List<Usuario> usuarios = ubo.getUsuarios(pesquisa, paginacao, ativo);

        usuarios.stream().map((usuario) -> {
            final List<String> dado = new ArrayList<>();
            dado.add(String.valueOf(usuario.getMatricula()));
            dado.add(usuario.getNome());
            dado.add(usuario.getEmail());
            dado.add(usuario.getTelefone());
            dado.add(usuario.getUnidadeCurso().getUnidadeCurso());
            dado.add((usuario.isAtivo()) ? "Ativo" : "Desativado");
            return dado;
        }).forEachOrdered((dado) -> {
            dados.add(dado);
        });

        return dados;
    }

    /**
     *
     * @param ativarDesativar
     * @param status
     * @param codigo
     * @param colunas
     */
    private void atualizarUsuario(boolean status, Long codigo, List<String> colunas) {
        final UsuarioBO ubo = new UsuarioBO();
        final Usuario usuario = ubo.getUsuario(codigo);

        if (!status && usuario.getTipo().equals(Pessoa.Tipo.Administrador)) {
            Messenger.showMessage("Você não pode desativar o Administrador", MessageType.INFORMATION);
            return;
        }

        usuario.setNome(colunas.get(0));
        usuario.setEmail(colunas.get(1));
        usuario.setTelefone(colunas.get(2));
        usuario.setAtivo(status);

        ubo.atualizarUsuario(usuario);
    }

    /**
     *
     * @param table
     * @param pesquisa
     * @param ativo
     * @return
     */
    private List<List<String>> carregarPessoas(JTable table, String pesquisa, String ativo, boolean criarTabela, Paginacao paginacao) {
        final PessoaBO pbo = new PessoaBO();

        if (criarTabela) {
            final String colunas[] = {"Matricula", "Nome", "Email", "Telefone", "Unidade/Curso", "Tipo", "Status"};
            final boolean edit[] = {false, true, true, true, false, false, true};
            table.setModel(GenericTable.getModel(colunas, edit));
        }

        final Pessoa.Tipo tipo = (Pessoa.Tipo) this.telaAdministrador.getJcbTipo().getSelectedItem();
        final List<List<String>> dados = new ArrayList<>();
        final List<Solicitante> pessoas = pbo.carregarSolicitantes(pesquisa, paginacao, ativo, tipo);

        pessoas.stream().map((pessoa) -> {
            final List<String> dado = new ArrayList<>();
            dado.add(String.valueOf(pessoa.getMatricula()));
            dado.add(pessoa.getNome());
            dado.add(pessoa.getEmail());
            dado.add(pessoa.getTelefone());
            dado.add(pessoa.getUnidadeCurso().getUnidadeCurso());
            dado.add(pessoa.getTipo().toString());
            dado.add((pessoa.isAtivo()) ? "Ativo" : "Desativado");
            return dado;
        }).forEachOrdered((dado) -> {
            dados.add(dado);
        });

        return dados;
    }

    /**
     *
     * @param ativarDesativar
     * @param status
     * @param codigo
     * @param colunas
     */
    private void atualizarPessoa(boolean status, Long codigo, List<String> colunas) {
        final PessoaBO pbo = new PessoaBO();
        final Solicitante solicitante = pbo.getPessoa(codigo);

        solicitante.setNome(colunas.get(0));
        solicitante.setEmail(colunas.get(1));
        solicitante.setTelefone(colunas.get(2));
        solicitante.setAtivo(status);

        pbo.atualizarPessoa(solicitante);
    }

    /**
     *
     * @param table
     * @param pesquisa
     * @param ativo
     * @param criarTabela
     */
    private List<List<String>> carregarEquipamentos(JTable table, String pesquisa, String filtro, String ativo, boolean criarTabela, Paginacao paginacao) {
        if (criarTabela) {
            final String colunas[] = {"Num. Tombameto", "Descrição", "Tipo", "Situação", "Status"};
            final boolean edit[] = {false, true, true, false, true};
            table.setModel(GenericTable.getModel(colunas, edit));
        }

        final EquipamentoBO ebo = new EquipamentoBO();
        final List<List<String>> dados = new ArrayList<>();
        List<Equipamento> equipamentos = ebo.carregarEquipamentos(pesquisa, filtro, paginacao, ativo);

        equipamentos.stream().map((equipamento) -> {
            final List<String> dado = new ArrayList<>();
            dado.add(String.valueOf(equipamento.getNumeroTombamento()));
            dado.add(equipamento.getDescricao());
            dado.add(equipamento.getTipo());
            dado.add(equipamento.getStatus());
            dado.add((equipamento.isAtivo()) ? "Ativo" : "Desativado");
            return dado;
        }).forEachOrdered((dado) -> {
            dados.add(dado);
        });

        return dados;
    }

    /**
     *
     * @param ativarDesativar
     * @param status
     * @param codigo
     * @param colunas
     */
    private void atualizarEquipamento(boolean status, Long codigo, List<String> colunas) {
        final EquipamentoBO ebo = new EquipamentoBO();
        final Equipamento equipamento = ebo.getEquipamento(codigo);

        equipamento.setDescricao(colunas.get(0));
        equipamento.setTipo(colunas.get(1));
        equipamento.setAtivo(status);

        ebo.atualizarEquipamento(equipamento);
    }

    /**
     *
     * @param table
     * @param pesquisa
     * @return
     */
    private List<List<String>> carregarMateriais(JTable table, String pesquisa, String ativo, boolean criarTabela, Paginacao paginacao) {
        if (criarTabela) {
            final String colunas[] = {"Código", "Descrição", "Quantidade", "Qtd. Máxima", "Un.", "Valor", "Status"};
            final boolean edit[] = {false, true, true, true, true, false, true};
            table.setModel(GenericTable.getModel(colunas, edit));
        }

        final MaterialBO mbo = new MaterialBO();
        final List<List<String>> dados = new ArrayList<>();
        List<Material> materials = mbo.getListaMateriais(pesquisa, paginacao, ativo);

        materials.stream().map((material) -> {
            final List<String> dado = new ArrayList<>();
            dado.add(String.valueOf(material.getCodigo()));
            dado.add(material.getDescricao());
            dado.add(String.valueOf(material.getQuantidade()));
            dado.add(String.valueOf(material.getQuantidadeMaxima()));
            dado.add(material.getUnidade());
            dado.add(String.valueOf(material.getValor()));
            dado.add((material.isAtivo()) ? "Ativo" : "Desativado");
            return dado;
        }).forEachOrdered((dado) -> {
            dados.add(dado);
        });

        return dados;
    }

    /**
     *
     * @param ativarDesativar
     * @param status
     * @param codigo
     * @param colunas
     */
    private void atualizarMaterial(boolean status, Long codigo, List<String> colunas) {
        final MaterialBO mbo = new MaterialBO();
        final Material material = mbo.getMaterial(codigo);

        material.setDescricao(colunas.get(0));
        material.setQuantidade(Long.valueOf(colunas.get(1)));
        material.setQuantidadeMaxima(Long.valueOf(colunas.get(2)));
        material.setUnidade(colunas.get(3));
        material.setAtivo(status);

        mbo.atualizarMaterial(material);
    }

    /**
     *
     * @param table
     * @param pesquisa
     * @param ativo
     * @param criarTabela
     * @return
     */
    private List<List<String>> carregarUnidadesCursos(JTable table, String pesquisa, String ativo, boolean criarTabela, Paginacao paginacao) {
        if (criarTabela) {
            final String colunas[] = {"Código", "Descrição", "Status"};
            final boolean edit[] = {false, true, true};
            table.setModel(GenericTable.getModel(colunas, edit));
        }

        final UnidadeCursoBO ucbo = new UnidadeCursoBO();
        final List<List<String>> dados = new ArrayList<>();
        List<UnidadeCurso> unidadeCursos = ucbo.getUnidadesCursos(pesquisa, paginacao, ativo);

        unidadeCursos.stream().map((unidadeCurso) -> {
            final List<String> dado = new ArrayList<>();
            dado.add(String.valueOf(unidadeCurso.getCodigo()));
            dado.add(unidadeCurso.getUnidadeCurso());
            dado.add((unidadeCurso.isAtivo()) ? "Ativo" : "Desativado");
            return dado;
        }).forEachOrdered((dado) -> {
            dados.add(dado);
        });

        return dados;
    }

    /**
     *
     * @param ativarDesativar
     * @param status
     * @param codigo
     * @param colunas
     */
    private void atualizarUnidadeCurso(boolean status, Long codigo, List<String> colunas) {
        final UnidadeCursoBO ucbo = new UnidadeCursoBO();
        final UnidadeCurso uc = ucbo.getUnidadeCurso(codigo);

        uc.setUnidadeCurso(colunas.get(0));
        uc.setAtivo(status);

        ucbo.atualizarUnidadeCurso(uc);
    }

    /**
     *
     */
    public void cadastrarAdministrador() {
        final AdministradorBO abo = new AdministradorBO();
        if (!abo.existeAdministrador()) {
            if (!abo.cadastrarAdministrador()) {
                Messenger.showMessage("Erro ao inicializar o sistema\n\nAdmin", MessageType.ERROR);
                System.exit(0);
            }
        }
    }

    /**
     *
     */
    public void limparCampos() {
        this.telaAdministrador.limparFiltros();
        this.telaAtual = "";

        telaAdministrador.getJcbSituacao().setVisible(false);
        telaAdministrador.getJcbTipo().setVisible(false);

        this.paginacaoController.getPaginacao().setTotalResultados(0);
        this.paginacaoController.printResults();

        final JTable table = this.telaAdministrador.getTblAdministrador();
        table.setModel(GenericTable.getModel(new String[]{"Painel de Controle"}, new boolean[]{false}));
        GenericTable.carregarTabelaAdministrador(table, null);
        this.habilitarControles();
    }

    /**
     *
     */
    public void gerarRelatorio() {
        if (this.telaAtual != null && !this.telaAtual.isEmpty()) {

            String file = telaAtual.toLowerCase().replace('á', 'a').replace('/', '_');
            file = Arquivo.getRelatorio("relatorio_".concat(file));

            final String strAtivo = telaAdministrador.getJcbAtivo().getSelectedItem().toString();
            final Long ativo = ((strAtivo.isEmpty()) ? 2L : (strAtivo.equals("Ativos")) ? 0L : 1L);
            final String descricao = telaAdministrador.getTxtPesquisa().getText().trim();

            final Relatorio relatorio = new Relatorio();
            relatorio.setTitulo("Relatório ".concat(this.telaAtual));
            relatorio.addParametro("DESCRICAO", "%".concat(descricao).concat("%"));
            relatorio.addParametro("USUARIO", Session.getUsuario().getNome());
            relatorio.addParametro("ATIVO", ativo);
            relatorio.setFile(file);

            if (this.telaAtual.equals("Pessoas")) {
                String tipo = this.telaAdministrador.getJcbTipo().getSelectedItem().toString().trim();
                tipo = (tipo.equals("Todos")) ? "" : tipo;
                relatorio.addParametro("TIPO", "%".concat(tipo).concat("%"));
            } else if (this.telaAtual.equals("Equipamentos")) {
                String situacao = this.telaAdministrador.getJcbSituacao().getSelectedItem().toString().trim();
                relatorio.addParametro("SITUACAO", "%".concat(situacao).concat("%"));
            }

            GeradorRelatorioJasper.gerarJasper(relatorio);
        }
    }

    private void habilitarControles() {
        this.telaAdministrador.getBtnUsuarios().setEnabled(true);
        this.telaAdministrador.getBtnPessoas().setEnabled(true);
        this.telaAdministrador.getBtnEquipamentos().setEnabled(true);
        this.telaAdministrador.getBtnMateriais().setEnabled(true);
        this.telaAdministrador.getBtnCursos().setEnabled(true);
    }

    public TelaAdministrador getTelaAdministrador() {
        return telaAdministrador;
    }

    public void setTelaAdministrador() {
        this.telaAdministrador = new TelaAdministrador();
        this.paginacaoController = new PaginacaoController();

        this.telaAdministrador.getJpPaginacao().add(this.paginacaoController.getTelaPaginacao(), 0);
        this.paginacaoController.setObject(this, AdministradorController.class, "carregarRegistros");

        this.paginacaoController.printResults();

        initListener();
    }
}
