/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.model.bo.GeradorRelatorioJasper;
import br.com.jsampaio.sigem.model.bo.RelatorioEmprestimo;
import br.com.jsampaio.sigem.model.vo.EmprestimoEquipamento;
import br.com.jsampaio.sigem.model.vo.Equipamento;
import br.com.jsampaio.sigem.model.vo.FiltroRelatorio;
import br.com.jsampaio.sigem.model.vo.Relatorio;
import br.com.jsampaio.sigem.model.vo.Session;
import br.com.jsampaio.sigem.model.vo.Solicitante;
import br.com.jsampaio.sigem.util.Arquivo;
import br.com.jsampaio.sigem.util.Log;
import br.com.jsampaio.sigem.view.TelaRelatorioEmprestimo;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Janilson
 */
public class RelatorioEmprestimoController {

    private final TelaRelatorioEmprestimo telaRelatorioEmprestimo;
    private final SelecionarPessoaController selecionarPessoaController;
    private final SelecionarEquipamentoController selecionarEquipamentoController;
    private final PaginacaoController paginacaoController;

    public RelatorioEmprestimoController(SelecionarPessoaController selecionarPessoaController) {
        this.telaRelatorioEmprestimo = new TelaRelatorioEmprestimo();
        this.selecionarPessoaController = selecionarPessoaController;
        this.selecionarEquipamentoController = new SelecionarEquipamentoController();
        this.paginacaoController = new PaginacaoController();

        this.telaRelatorioEmprestimo.getJpPaginacao().add(this.paginacaoController.getTelaPaginacao(), 0);
        this.paginacaoController.setObject(this, RelatorioEmprestimoController.class, "recarregarTabela");

        final Listener listener = this.new Listener();
        this.telaRelatorioEmprestimo.getBtnFiltrarSolicitante().addActionListener(listener);
        this.telaRelatorioEmprestimo.getBtnLimparCampos().addActionListener(listener);
        this.telaRelatorioEmprestimo.getBtnFiltrarEquipamento().addActionListener(listener);
        this.telaRelatorioEmprestimo.getBtnGerarRelatorio().addActionListener(listener);
        this.telaRelatorioEmprestimo.getBtnGerarGrafico().addActionListener(listener);

        this.telaRelatorioEmprestimo.getTxtFiltrarSolicitante().addMouseListener(listener);
        this.telaRelatorioEmprestimo.getTxtFiltrarEquipamento().addMouseListener(listener);
    }

    private final class Listener extends MouseAdapter implements ActionListener {

        public Listener() {
            Component[] ComponentsDataInicial = telaRelatorioEmprestimo.getTxtDataInicial().getJCalendar().getDayChooser().getDayPanel().getComponents();
            addMouseListener(ComponentsDataInicial);

            Component[] ComponentsDataFinal = telaRelatorioEmprestimo.getTxtDataFinal().getJCalendar().getDayChooser().getDayPanel().getComponents();
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
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == telaRelatorioEmprestimo.getTxtFiltrarEquipamento()) {
                selecionarEquipamento();
                return;
            }
            
            if (e.getSource() == telaRelatorioEmprestimo.getTxtFiltrarSolicitante()) {
                selecionarSolicitante();
            }
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == telaRelatorioEmprestimo.getBtnFiltrarSolicitante()) {
                selecionarSolicitante();
                return;
            }

            if (e.getSource() == telaRelatorioEmprestimo.getBtnFiltrarEquipamento()) {
                selecionarEquipamento();
                return;
            }
            
            if (e.getSource() == telaRelatorioEmprestimo.getBtnGerarRelatorio()) {
                final Relatorio relatorio = getRelatorio();
                GeradorRelatorioJasper.gerarJasper(relatorio);
                return;
            }

            if (e.getSource() == telaRelatorioEmprestimo.getBtnLimparCampos()) {
                limparCampos();
                recarregarTabela();
            }
        }
    }

    /**
     *
     */
    private void selecionarSolicitante() {
        selecionarPessoaController.carregarPessoas();
        selecionarPessoaController.getTelaSelecionarPessoa().setVisible(true);

        final Solicitante solicitante = selecionarPessoaController.getPessoa();
        if (solicitante != null) {
            telaRelatorioEmprestimo.getTxtFiltrarSolicitante().setText(solicitante.toString());
        }

        this.recarregarTabela();
        selecionarPessoaController.setPessoa(null);
    }

    /**
     *
     */
    private void selecionarEquipamento() {
        this.selecionarEquipamentoController.carregarEquipamentos();
        this.selecionarEquipamentoController.getTelaSelecionarEquipamento().setVisible(true);

        final Equipamento equipamento = this.selecionarEquipamentoController.getEquipamento();
        if (equipamento != null) {
            this.telaRelatorioEmprestimo.getTxtFiltrarEquipamento().setText(equipamento.toString());
        }

        this.recarregarTabela();
        this.selecionarEquipamentoController.setEquipamento(null);
    }

    /**
     *
     */
    public void recarregarTabela() {
        final JTable table = this.telaRelatorioEmprestimo.getTblRelatorioEmprestimo();
        final DefaultTableModel model = (DefaultTableModel) table.getModel();

        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }

        final FiltroRelatorio filtro = new FiltroRelatorio();
        filtro.setDataInicio(this.telaRelatorioEmprestimo.getTxtDataInicial().getDate());
        filtro.setDataFinal(this.telaRelatorioEmprestimo.getTxtDataFinal().getDate());

        Long requisitante;
        try {
            requisitante = Long.valueOf(this.telaRelatorioEmprestimo.getTxtFiltrarSolicitante().getText().split("-")[0].trim());
            filtro.setRequisitante(requisitante);
        } catch (NumberFormatException n) {
            Log.saveLog(n, RelatorioEmprestimoController.class);
        }

        Long tombamento;
        try {
            tombamento = Long.valueOf(this.telaRelatorioEmprestimo.getTxtFiltrarEquipamento().getText().split("-")[0].trim());
            filtro.setEquipamento(tombamento);
        } catch (NumberFormatException n) {
            Log.saveLog(n, RelatorioEmprestimoController.class);
        }

        final RelatorioEmprestimo re = new RelatorioEmprestimo();
        final List<EmprestimoEquipamento> emprestimos = re.getEquipamentosEmprestados(filtro, this.paginacaoController.getPaginacao());

        final SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        int linha = 0;
        if (emprestimos != null && !emprestimos.isEmpty()) {
            for (EmprestimoEquipamento emprestimo : emprestimos) {
                model.addRow(new String[0]);

                table.setValueAt(emprestimo.getEmprestimo().getNumeroProtocolo(), linha, 0);
                table.setValueAt(emprestimo.getEquipamento().getNumeroTombamento(), linha, 1);
                table.setValueAt(emprestimo.getEquipamento().getTipo(), linha, 2);
                table.setValueAt(emprestimo.getEquipamento().getDescricao(), linha, 3);
                table.setValueAt(emprestimo.getEmprestimo().getSolicitante().getNome(), linha, 4);
                table.setValueAt(emprestimo.getEmprestimo().getSolicitante().getUnidadeCurso().getUnidadeCurso(), linha, 5);
                table.setValueAt(format.format(emprestimo.getDataHoraEntrega()), linha, 6);

                linha++;
            }
        }

        table.repaint();
        table.validate();
        this.paginacaoController.printResults();
        this.telaRelatorioEmprestimo.getBtnGerarGrafico().setEnabled(table.getRowCount() > 0);
        this.telaRelatorioEmprestimo.getBtnGerarRelatorio().setEnabled(table.getRowCount() > 0);
    }

    /**
     *
     * @return
     */
    private Relatorio getRelatorio() {

        Long numTombamento = null;
        Long solicitante = null;

        try {
            numTombamento = Long.valueOf(this.telaRelatorioEmprestimo.getTxtFiltrarEquipamento().getText().split("-")[0].trim());
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException a) {
            Log.saveLog(a, RelatorioEmprestimoController.class);
        }

        try {
            solicitante = Long.valueOf(this.telaRelatorioEmprestimo.getTxtFiltrarSolicitante().getText().split("-")[0].trim());
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException a) {
            Log.saveLog(a, RelatorioEmprestimoController.class);
        }

        Date dataInicial = this.telaRelatorioEmprestimo.getTxtDataInicial().getDate();
        Date dataFinal = this.telaRelatorioEmprestimo.getTxtDataFinal().getDate();

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
        relatorio.addParametro("NUM_TOMBAMENTO", numTombamento);
        relatorio.addParametro("SOLICITANTE", solicitante);
        relatorio.addParametro("DATA_INICIAL", dataInicial);
        relatorio.addParametro("DATA_FINAL", dataFinal);
        relatorio.addParametro("USUARIO", Session.getUsuario().getNome());

        relatorio.setFile(Arquivo.getRelatorio("relatorio_emprestimo"));
        relatorio.setTitulo("Relatório Empréstimo");

        return relatorio;
    }

    public void limparCampos() {
        this.telaRelatorioEmprestimo.getTxtDataInicial().setDate(null);
        this.telaRelatorioEmprestimo.getTxtDataFinal().setDate(null);
        this.telaRelatorioEmprestimo.getTxtFiltrarSolicitante().setText(null);
        this.telaRelatorioEmprestimo.getTxtFiltrarEquipamento().setText(null);

        this.paginacaoController.printResults();
    }

    public TelaRelatorioEmprestimo getTelaRelatorioEmprestimo() {
        return telaRelatorioEmprestimo;
    }
}
