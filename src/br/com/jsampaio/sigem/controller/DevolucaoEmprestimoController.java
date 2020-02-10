/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.main.Messenger;
import br.com.jsampaio.sigem.model.bo.EmprestimoBO;
import br.com.jsampaio.sigem.model.bo.GeradorRelatorioJasper;
import br.com.jsampaio.sigem.model.bo.RelatorioEmprestimo;
import br.com.jsampaio.sigem.model.vo.Emprestimo;
import br.com.jsampaio.sigem.model.vo.FiltroRelatorio;
import br.com.jsampaio.sigem.model.vo.MessageType;
import br.com.jsampaio.sigem.model.vo.Relatorio;
import br.com.jsampaio.sigem.model.vo.Session;
import br.com.jsampaio.sigem.util.Arquivo;
import br.com.jsampaio.sigem.view.TelaDevolucaoEmprestimo;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Janilson
 */
public class DevolucaoEmprestimoController {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private final TelaDevolucaoEmprestimo telaDevolucaoEmprestimo;
    private final PaginacaoController paginacaoController;

    private Emprestimo emprestimo;

    public DevolucaoEmprestimoController() {
        this.telaDevolucaoEmprestimo = new TelaDevolucaoEmprestimo();
        this.paginacaoController = new PaginacaoController();
        
        this.telaDevolucaoEmprestimo.getJpPaginacao().add(this.paginacaoController.getTelaPaginacao(), 0);
        this.paginacaoController.setObject(this, DevolucaoEmprestimoController.class, "recarregarTabela");

        final Listener listener = this.new Listener();
        this.telaDevolucaoEmprestimo.getTxtPesquisa().addKeyListener(listener);
        this.telaDevolucaoEmprestimo.getBtnVisualizar().addActionListener(listener);
        this.telaDevolucaoEmprestimo.getBtnLimparFiltros().addActionListener(listener);
        this.telaDevolucaoEmprestimo.getJmiPopUpVisualizar().addActionListener(listener);
        this.telaDevolucaoEmprestimo.getBtnConfirmarDevolucao().addActionListener(listener);
        this.telaDevolucaoEmprestimo.getJmiPopUpDevolucaoEmprestimo().addActionListener(listener);

        this.listenerTabela();
    }

    private class Listener extends KeyAdapter implements ActionListener {

        public Listener() {
            Component[] ComponentsDataInicial
                    = telaDevolucaoEmprestimo.getTxtDataInicial()
                            .getJCalendar().getDayChooser().getDayPanel().getComponents();
            addMouseListener(ComponentsDataInicial);

            Component[] ComponentsDataFinal
                    = telaDevolucaoEmprestimo.getTxtDataFinal()
                            .getJCalendar().getDayChooser().getDayPanel().getComponents();
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
        public void keyReleased(KeyEvent e) {
            if (e.getSource() == telaDevolucaoEmprestimo.getTxtPesquisa()) {

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    visualizar();
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
                    return;
                }

                recarregarTabela();
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == telaDevolucaoEmprestimo.getJmiPopUpVisualizar()
                    || e.getSource() == telaDevolucaoEmprestimo.getBtnVisualizar()) {
                visualizar();
                return;
            }

            if (e.getSource() == telaDevolucaoEmprestimo.getJmiPopUpDevolucaoEmprestimo()
                    || e.getSource() == telaDevolucaoEmprestimo.getBtnConfirmarDevolucao()) {
                confirmarDevolucao();
                return;
            }

            if (e.getSource() == telaDevolucaoEmprestimo.getBtnLimparFiltros()) {
                limparCampos();
                recarregarTabela();
            }
        }
    }

    /**
     * 
     */
    private void confirmarDevolucao() {
        boolean devolvido;
        final JTable table = this.telaDevolucaoEmprestimo.getTblDevolucaoEmprestimo();
        if (table.getSelectedRowCount() == 1) {
            final Long numProtocolo = Long.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString());
            final EmprestimoBO ebo = new EmprestimoBO();
            this.emprestimo = ebo.getEmprestimo(numProtocolo);
        } else {
            Messenger.showMessage("Selecione um emprestimo", MessageType.ERROR);
            return;
        }

        if (this.emprestimo != null) {

            devolvido = emprestimo.getEquipamentos().get(0).isDevolvido();
            if (devolvido) {
                Messenger.showMessage("Esse emprestimo já foi dado baixa", MessageType.INFORMATION);
                return;
            }

            int response = Messenger.showMessage("Você tem certeza que deseja confirmar essa operação?", MessageType.QUESTION);
            if (response == Messenger.YES_OPTION) {

                emprestimo.getEquipamentos().stream().map((equipamento) -> {
                    equipamento.setDevolvido(true);
                    return equipamento;
                }).forEachOrdered((equipamento) -> {
                    equipamento.setDataHoraDevolucaoEfetiva(Calendar.getInstance().getTime());
                });

                final EmprestimoBO ebo = new EmprestimoBO();
                String msg = ebo.confirmarDevolução(emprestimo);
                Messenger.showMessage((msg.isEmpty()) ? "Devolvido com sucesso" : msg, MessageType.INFORMATION);

                limparCampos();
                recarregarTabela();
            }
        }
    }

    /**
     * 
     */
    private void visualizar() {
        final JTable table = this.telaDevolucaoEmprestimo.getTblDevolucaoEmprestimo();
        if (table.getSelectedRowCount() == 1) {
            final Long numProtocolo = Long.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString());
            final Relatorio relatorio = new Relatorio();
            relatorio.setFile(Arquivo.getRelatorio("resumo_emprestimo"));
            relatorio.addParametro("NUM_PROTOCOLO", numProtocolo);
            relatorio.addParametro("USUARIO", Session.getUsuario().getNome());
            relatorio.setTitulo("Resumo Empréstimo");

            GeradorRelatorioJasper.gerarJasper(relatorio);
        }
    }

    /**
     * 
     */
    public void recarregarTabela() {
        final JTable table = this.telaDevolucaoEmprestimo.getTblDevolucaoEmprestimo();
        final DefaultTableModel model = (DefaultTableModel) table.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }

        final RelatorioEmprestimo re = new RelatorioEmprestimo();
        final FiltroRelatorio filtro = new FiltroRelatorio();

        final String nomeSolicitante = this.telaDevolucaoEmprestimo.getTxtPesquisa().getText();
        filtro.setNomeSolicitante(nomeSolicitante);
        filtro.setDataInicio(this.telaDevolucaoEmprestimo.getTxtDataInicial().getDate());
        filtro.setDataFinal(this.telaDevolucaoEmprestimo.getTxtDataFinal().getDate());

        final Set<Emprestimo> emprestimos = re.getEmprestimos(filtro, this.paginacaoController.getPaginacao());
        int linha = 0;
        if (emprestimos != null && !emprestimos.isEmpty()) {
            for (Emprestimo emprestimo : emprestimos) {
                model.addRow(new String[0]);

                table.setValueAt(emprestimo.getNumeroProtocolo(), linha, 0);
                table.setValueAt(emprestimo.getSolicitante().getNome(), linha, 1);
                table.setValueAt(dateFormat.format(emprestimo.getEquipamentos().get(0).getDataHoraEntrega()), linha, 2);
                table.setValueAt(dateFormat.format(emprestimo.getEquipamentos().get(0).getDataHoraDevolucaoPrevista()), linha, 3);

                final Date dataDevolucao = emprestimo.getEquipamentos().get(0).getDataHoraDevolucaoEfetiva();
                if (dataDevolucao != null) {
                    table.setValueAt(dateFormat.format(dataDevolucao), linha, 4);
                } else {
                    table.setValueAt("", linha, 4);
                }

                String status;
                boolean atrasado = (Calendar.getInstance().getTime().compareTo(emprestimo.getEquipamentos().get(0).getDataHoraDevolucaoPrevista()) > 0);
                boolean devolvido = emprestimo.getEquipamentos().get(0).isDevolvido();

                if (atrasado && !devolvido) {
                    status = "Atrasado";
                } else {
                    status = (devolvido) ? "Devolvido" : "Pendente";
                }

                table.setValueAt(status, linha, 5);

                linha++;
            }

            table.repaint();
            table.validate();

            table.setRowSelectionInterval(0, 0);
            this.paginacaoController.printResults();
        }
    }

    /**
     * 
     */
    private void listenerTabela() {
        final JTable table = this.telaDevolucaoEmprestimo.getTblDevolucaoEmprestimo();
        GenericListener.addListener(table, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizar();
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    visualizar();
                }
            }
        });

        GenericListener.tableListener(table, this.telaDevolucaoEmprestimo.getTxtPesquisa());
    }

    public void limparCampos() {
        telaDevolucaoEmprestimo.getTxtPesquisa().setText(null);
        this.telaDevolucaoEmprestimo.getTxtDataInicial().setDate(null);
        this.telaDevolucaoEmprestimo.getTxtDataFinal().setDate(null);
        emprestimo = null;
        
        this.paginacaoController.printResults();
    }

    public TelaDevolucaoEmprestimo getTelaDevolucaoEmprestimo() {
        return telaDevolucaoEmprestimo;
    }
}