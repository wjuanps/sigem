/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.main.Messenger;
import br.com.jsampaio.sigem.model.bo.GeradorRelatorioJasper;
import br.com.jsampaio.sigem.model.bo.Grafico;
import br.com.jsampaio.sigem.model.bo.RelatorioRequisicao;
import br.com.jsampaio.sigem.model.vo.FiltroRelatorio;
import br.com.jsampaio.sigem.model.vo.MessageType;
import br.com.jsampaio.sigem.model.vo.Relatorio;
import br.com.jsampaio.sigem.model.vo.Requisicao;
import br.com.jsampaio.sigem.model.vo.RequisicaoMateriais;
import br.com.jsampaio.sigem.model.vo.Session;
import br.com.jsampaio.sigem.model.vo.Solicitante;
import br.com.jsampaio.sigem.model.vo.UnidadeCurso;
import br.com.jsampaio.sigem.util.Arquivo;
import br.com.jsampaio.sigem.util.Log;
import br.com.jsampaio.sigem.view.TelaGrafico;
import br.com.jsampaio.sigem.view.TelaRelatorioRequisicao;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Janilson
 */
public final class RelatorioRequisicaoController {

    private TelaGrafico telaGrafico;

    private final TelaRelatorioRequisicao telaRelatorioRequisicao;
    private final SelecionarPessoaController selecionarPessoaController;
    private final UnidadeCursoController unidadeCursoController;
    private final PaginacaoController paginacaoController;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public RelatorioRequisicaoController(SelecionarPessoaController spc, UnidadeCursoController ucc) {
        this.telaRelatorioRequisicao = new TelaRelatorioRequisicao();
        this.selecionarPessoaController = spc;
        this.unidadeCursoController = ucc;
        this.paginacaoController = new PaginacaoController();

        this.telaRelatorioRequisicao.getJpPaginacao().add(this.paginacaoController.getTelaPaginacao(), 0);
        this.paginacaoController.setObject(this, RelatorioRequisicaoController.class, "recarregarTabela");

        final Listener listener = this.new Listener();
        this.telaRelatorioRequisicao.getBtnUnidade().addActionListener(listener);
        this.telaRelatorioRequisicao.getBtnRequisitante().addActionListener(listener);
        this.telaRelatorioRequisicao.getBtnRecebedor().addActionListener(listener);

        this.telaRelatorioRequisicao.getTxtUnidade().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                selecionarUnidade();
            }
        });
        this.telaRelatorioRequisicao.getTxtRequisitante().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                selecionarRequisitante();
            }
        });
        this.telaRelatorioRequisicao.getTxtRecebedor().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                selecionarRecebedor();
            }
        });

        this.telaRelatorioRequisicao.getBtnLimpar().addActionListener(listener);

        this.telaRelatorioRequisicao.getBtnGerarRelatorio().addActionListener(listener);
        this.telaRelatorioRequisicao.getBtnGerarGrafico().addActionListener(listener);

        recarregarTabela();
    }

    private final class Listener implements ActionListener {

        public Listener() {
            Component[] ComponentsDataInicial = telaRelatorioRequisicao.getTxtDataInicial().getJCalendar().getDayChooser().getDayPanel().getComponents();
            addMouseListener(ComponentsDataInicial);

            Component[] ComponentsDataFinal = telaRelatorioRequisicao.getTxtDataFinal().getJCalendar().getDayChooser().getDayPanel().getComponents();
            addMouseListener(ComponentsDataFinal);
        }

        private void addMouseListener(Component[] components) {
            for (Component component : components) {
                if (component instanceof JButton) {
                    ((JButton) component).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    ((JButton) component).addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            recarregarTabela();
                        }
                    });
                }
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == telaRelatorioRequisicao.getBtnUnidade()
                    || e.getSource() == telaRelatorioRequisicao.getTxtUnidade()) {
                selecionarUnidade();
                return;
            }

            if (e.getSource() == telaRelatorioRequisicao.getBtnRequisitante()
                    || e.getSource() == telaRelatorioRequisicao.getTxtRequisitante()) {
                selecionarRequisitante();
                return;
            }

            if (e.getSource() == telaRelatorioRequisicao.getBtnRecebedor()
                    || e.getSource() == telaRelatorioRequisicao.getTxtRecebedor()) {
                selecionarRecebedor();
                return;
            }

            if (e.getSource() == telaRelatorioRequisicao.getBtnGerarRelatorio()) {
                Relatorio relatorio = getRelatorio();
                GeradorRelatorioJasper.gerarJasper(relatorio);
                return;
            }

            if (e.getSource() == telaRelatorioRequisicao.getBtnLimpar()) {
                telaRelatorioRequisicao.limparCampos();
                recarregarTabela();
                return;
            }

            if (e.getSource() == telaRelatorioRequisicao.getBtnGerarGrafico()) {
                gerarGrafico();
            }
        }
    }

    /**
     *
     */
    public void recarregarTabela() {
        final JTable table = this.telaRelatorioRequisicao.getTblRelatorio();
        final DefaultTableModel model = (DefaultTableModel) table.getModel();

        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }

        final FiltroRelatorio filtro = this.getFiltro();
        final RelatorioRequisicao rr = new RelatorioRequisicao();
        final Set<Requisicao> requisicaos = rr.getRequisicoes(filtro, paginacaoController.getPaginacao());

        if (requisicaos != null && !requisicaos.isEmpty()) {
            int linha = 0;
            for (Requisicao requisicao : requisicaos) {
                for (RequisicaoMateriais rm : requisicao.getRequisicaoMateriais()) {

                    model.addRow(new String[0]);

                    table.setValueAt(requisicao.getCodigo(), linha, 0);
                    table.setValueAt(requisicao.getSolicitante().getUnidadeCurso().getUnidadeCurso(), linha, 1);
                    table.setValueAt(requisicao.getSolicitante().getNome(), linha, 2);
                    table.setValueAt(requisicao.getRecebedor().getNome(), linha, 3);
                    table.setValueAt(rm.getMaterial().getDescricao(), linha, 4);
                    table.setValueAt(rm.getMaterial().getUnidade(), linha, 5);
                    table.setValueAt(rm.getQuantidadeRequisitada(), linha, 6);
                    table.setValueAt(rm.getQuantidadeEntregue(), linha, 7);
                    table.setValueAt(simpleDateFormat.format(rm.getDataEntrega()), linha, 8);

                    linha++;
                }
            }
        }

        table.repaint();
        table.validate();

        this.paginacaoController.printResults();
        this.telaRelatorioRequisicao.getBtnGerarRelatorio().setEnabled(table.getRowCount() > 0);
        this.telaRelatorioRequisicao.getBtnGerarGrafico().setEnabled(table.getRowCount() > 0);
    }

    /**
     *
     * @return
     */
    private FiltroRelatorio getFiltro() {
        final FiltroRelatorio filtro = new FiltroRelatorio();
        filtro.setDataInicio(this.telaRelatorioRequisicao.getTxtDataInicial().getDate());
        filtro.setDataFinal(this.telaRelatorioRequisicao.getTxtDataFinal().getDate());

        Long unidadeCurso;
        try {
            unidadeCurso = Long.valueOf(this.telaRelatorioRequisicao.getTxtUnidade().getText().split("-")[0].trim());
            filtro.setUnidadeCurso(unidadeCurso);
        } catch (NumberFormatException n) {
            Log.saveLog(n, RelatorioRequisicaoController.class);
        }

        Long requisitante;
        try {
            requisitante = Long.valueOf(this.telaRelatorioRequisicao.getTxtRequisitante().getText().split("-")[0].trim());
            filtro.setRequisitante(requisitante);
        } catch (NumberFormatException n) {
            Log.saveLog(n, RelatorioRequisicaoController.class);
        }

        Long recebedor;
        try {
            recebedor = Long.valueOf(this.telaRelatorioRequisicao.getTxtRecebedor().getText().split("-")[0].trim());
            filtro.setRecebedor(recebedor);
        } catch (NumberFormatException n) {
            Log.saveLog(n, RelatorioRequisicaoController.class);
        }
        return filtro;
    }

    /**
     *
     * @return
     */
    private Relatorio getRelatorio() {

        String requisitante = "%%";
        String recebedor = "%%";
        String unidadeCurso = "%%";

        try {
            requisitante = this.telaRelatorioRequisicao.getTxtRequisitante().getText().split("-")[1].trim();
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException a) {
            Log.saveLog(a, RelatorioRequisicaoController.class);
        }

        try {
            recebedor = this.telaRelatorioRequisicao.getTxtRecebedor().getText().split("-")[1].trim();
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException a) {
            Log.saveLog(a, RelatorioRequisicaoController.class);
        }

        try {
            unidadeCurso = this.telaRelatorioRequisicao.getTxtUnidade().getText().split("-")[1].trim();
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException a) {
            Log.saveLog(a, RelatorioRequisicaoController.class);
        }

        Date dataInicial = this.telaRelatorioRequisicao.getTxtDataInicial().getDate();
        Date dataFinal = this.telaRelatorioRequisicao.getTxtDataFinal().getDate();

        if (dataInicial == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(1970, 1, 1);
            dataInicial = calendar.getTime();
        }

        if (dataFinal == null) {
            dataFinal = Calendar.getInstance().getTime();
        }

        if (dataInicial.compareTo(dataFinal) > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(1970, 1, 1);
            dataInicial = calendar.getTime();
        }

        final Relatorio relatorio = new Relatorio();
        relatorio.addParametro("REQUISITANTE", "%".concat(requisitante).concat("%"));
        relatorio.addParametro("RECEBEDOR", "%".concat(recebedor).concat("%"));
        relatorio.addParametro("UNIDADE_CURSO", "%".concat(unidadeCurso).concat("%"));
        relatorio.addParametro("DATA_INICIAL", dataInicial);
        relatorio.addParametro("DATA_FINAL", dataFinal);
        relatorio.addParametro("USUARIO", Session.getUsuario().getNome());

        relatorio.setFile(Arquivo.getRelatorio("relatorio_requisicao_material"));
        relatorio.setTitulo("Relatório Requisição de Materiais");

        return relatorio;
    }

    /**
     *
     */
    private void gerarGrafico() {
        if (this.telaGrafico == null) {
            this.telaGrafico = new TelaGrafico(null, true);
        }
        final FiltroRelatorio filtro = getFiltro();
        final RelatorioRequisicao rr = new RelatorioRequisicao();
        final Set<Requisicao> requisicaos = rr.getRequisicoes(filtro, null);
        if (requisicaos != null && !requisicaos.isEmpty()) {
            final RelatorioRequisicao rq = new RelatorioRequisicao();
            final Map<String, Double> dados = rq.getDataSet(requisicaos);

            JPanel grafico = null;
            if (dados != null && !dados.isEmpty()) {
                grafico = Grafico.gerarGraficoPizza(dados, "Materiais retirados por Unidade/Curso");
            }

            if (grafico != null) {
                grafico.setBounds(new Rectangle(900, 550));
                this.telaGrafico.getJpGrafico().add(grafico);
                this.telaGrafico.setVisible(true);
                this.telaGrafico.getJpGrafico().removeAll();
            } else {
                Messenger.showMessage("Ocorreu um erro ao carregar o gráfico", MessageType.ERROR);
            }
        } else {
            Messenger.showMessage("Não existem dados para serem mostrados", MessageType.ERROR);
        }
    }

    /**
     *
     */
    private void selecionarUnidade() {
        unidadeCursoController.carregarUnidadeCurso();
        unidadeCursoController.getTelaSelecionarUnidadeCurso().setVisible(true);

        final UnidadeCurso unidadeCurso = unidadeCursoController.getUnidadeCurso();
        if (unidadeCurso != null) {
            telaRelatorioRequisicao.getTxtUnidade().setText(unidadeCurso.toString());
        }

        recarregarTabela();
        unidadeCursoController.setUnidadeCurso(null);
    }

    /**
     *
     */
    private void selecionarRequisitante() {
        selecionarPessoaController.carregarPessoas();
        selecionarPessoaController.getTelaSelecionarPessoa().setVisible(true);

        final Solicitante solicitante = selecionarPessoaController.getPessoa();
        if (solicitante != null) {
            telaRelatorioRequisicao.getTxtRequisitante().setText(solicitante.toString());
        }

        recarregarTabela();
        selecionarPessoaController.setPessoa(null);
    }

    /**
     *
     */
    private void selecionarRecebedor() {
        selecionarPessoaController.carregarPessoas();
        selecionarPessoaController.getTelaSelecionarPessoa().setVisible(true);

        final Solicitante solicitante = selecionarPessoaController.getPessoa();
        if (solicitante != null) {
            telaRelatorioRequisicao.getTxtRecebedor().setText(solicitante.toString());
        }

        recarregarTabela();
        selecionarPessoaController.setPessoa(null);
    }

    public TelaRelatorioRequisicao getTelaRelatorioMaterial() {
        return telaRelatorioRequisicao;
    }

    public PaginacaoController getPaginacaoController() {
        return paginacaoController;
    }

}
