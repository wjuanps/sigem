/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.main.Messenger;
import br.com.jsampaio.sigem.model.bo.EmprestimoBO;
import br.com.jsampaio.sigem.model.bo.GeradorRelatorioJasper;
import br.com.jsampaio.sigem.model.dao.SolicitanteDAO;
import br.com.jsampaio.sigem.model.vo.Emprestimo;
import br.com.jsampaio.sigem.model.vo.EmprestimoEquipamento;
import br.com.jsampaio.sigem.model.vo.Equipamento;
import br.com.jsampaio.sigem.model.vo.Equipamentos;
import br.com.jsampaio.sigem.model.vo.MessageType;
import br.com.jsampaio.sigem.model.vo.Relatorio;
import br.com.jsampaio.sigem.model.vo.Session;
import br.com.jsampaio.sigem.model.vo.Solicitante;
import br.com.jsampaio.sigem.util.Arquivo;
import br.com.jsampaio.sigem.util.Log;
import br.com.jsampaio.sigem.view.TelaAviso;
import br.com.jsampaio.sigem.view.TelaEmprestimo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Janilson
 */
public class EmprestimoController {

    private final TelaEmprestimo telaEmprestimo;
    private final SelecionarPessoaController selecionarPessoaController;
    private final SelecionarEquipamentoController selecionarEquipamentoController;
    private final TelaAviso telaAviso;

    final EmprestimoListener emprestimoListener;

    private final List<Equipamento> equipamentosSelecionados = new ArrayList<>();

    public EmprestimoController(SelecionarPessoaController selecionarPessoaController,
            SelecionarEquipamentoController selecionarEquipamentoController, TelaAviso telaAviso) {
        this.telaEmprestimo = new TelaEmprestimo();
        this.selecionarPessoaController = selecionarPessoaController;
        this.selecionarEquipamentoController = selecionarEquipamentoController;
        this.telaAviso = telaAviso;

        this.emprestimoListener = this.new EmprestimoListener();
        this.telaEmprestimo.getBtnPesquisarSolicitante().addActionListener(emprestimoListener);
        this.telaEmprestimo.getBtnSelecionarEquipamentos().addActionListener(emprestimoListener);
        this.telaEmprestimo.getBtnRemoverEquipamento().addActionListener(emprestimoListener);
        this.telaEmprestimo.getBtnEmprestar().addActionListener(emprestimoListener);
        this.telaEmprestimo.getBtnLimparCampos().addActionListener(emprestimoListener);

        this.telaEmprestimo.getTxtNome().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selecionarPessoa();
            }
        });
    }

    private class EmprestimoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == telaEmprestimo.getBtnPesquisarSolicitante()) {
                selecionarPessoa();
                return;
            }

            if (e.getSource() == telaEmprestimo.getBtnSelecionarEquipamentos()) {
                selecionarEquipamentoController.carregarEquipamentos();
                selecionarEquipamentoController.getTelaSelecionarEquipamento().setVisible(true);

                final Equipamento equipamento = selecionarEquipamentoController.getEquipamento();

                if (equipamento != null) {
                    if (!equipamento.getStatus().equals(Equipamentos.STATUS[0])) {
                        Messenger.showMessage("Este equipamento não está disponível para ser emprestado", MessageType.ERROR);
                        selecionarEquipamentoController.setEquipamento(null);
                        return;
                    }

                    if (!equipamentosSelecionados.contains(equipamento)) {
                        equipamentosSelecionados.add(equipamento);
                    }
                }

                selecionarEquipamentoController.setEquipamento(null);
                carregarListaEquipamentos();
                return;
            }

            if (e.getSource() == telaEmprestimo.getBtnRemoverEquipamento()) {
                if (telaEmprestimo.getJlEquipamentos().getSelectedIndex() >= 0) {
                    int response = Messenger.showMessage("Voce tem certeza que deseja excluir esse item", MessageType.QUESTION);

                    if (response == Messenger.YES_OPTION) {
                        final int index = telaEmprestimo.getJlEquipamentos().getSelectedIndex();

                        equipamentosSelecionados.remove(index);

                        carregarListaEquipamentos();

                        if (equipamentosSelecionados.isEmpty()) {
                            telaEmprestimo.getJlEquipamentos().setListData(new Equipamento[]{});
                        }
                    }
                }
                return;
            }

            if (e.getSource() == telaEmprestimo.getBtnLimparCampos()) {
                limparCampos();
                return;
            }

            if (e.getSource() == telaEmprestimo.getBtnEmprestar()) {
                confirmarEmprestimo();
            }
        }
    }

    private void selecionarPessoa() {
        selecionarPessoaController.carregarPessoas();
        selecionarPessoaController.getTelaSelecionarPessoa().setVisible(true);

        final Solicitante solicitante = selecionarPessoaController.getPessoa();

        if (solicitante != null) {
            final EmprestimoBO ebo = new EmprestimoBO();
            final Set<Emprestimo> emprestimos = ebo.getEmprestimo(solicitante);
            if (emprestimos != null && !emprestimos.isEmpty()) {

                final StringBuilder mensagem = new StringBuilder();
                mensagem.append("Esse solicitante possui empréstimo(s) pendente(s)\n\n");
                emprestimos.stream().map((emprestimo) -> {
                    return emprestimo;
                }).map((emprestimo) -> {
                    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    mensagem.append("Protocolo:\t").append(emprestimo.getNumeroProtocolo())
                            .append("\nSolicitante:\t").append(emprestimo.getSolicitante().getNome())
                            .append("\nEmpréstimo:\t").append(dateFormat.format(emprestimo.getEquipamentos().get(0).getDataHoraEntrega()))
                            .append("\nDevolução:\t").append(dateFormat.format(emprestimo.getEquipamentos().get(0).getDataHoraDevolucaoPrevista()))
                            .append("\n\n\t\t=== Materiais ===\n")
                            .append("Item\t\tTombamento\t\tDescrição\n");
                    return emprestimo;
                }).map((emprestimo) -> {
                    int i = 0;
                    for (EmprestimoEquipamento equipamento : emprestimo.getEquipamentos()) {
                        mensagem.append(String.valueOf(++i)).append("\t\t")
                                .append(equipamento.getEquipamento().getNumeroTombamento()).append("\t\t")
                                .append(equipamento.getEquipamento().getDescricao()).append("\n");
                    }
                    return emprestimo;
                }).forEachOrdered((_item) -> {
                    if (emprestimos.size() > 1) {
                        mensagem.append("\n\n----------------------------------------------------\n\n");
                    }
                });

                this.telaAviso.mostrarMensagem(mensagem, "Emprestimo Pendente", false);
                selecionarPessoaController.setPessoa(null);
                return;
            }
            telaEmprestimo.getTxtNome().setText(solicitante.getNome());
            telaEmprestimo.getTxtMatricula().setText(String.valueOf(solicitante.getMatricula()));
        }

        selecionarPessoaController.setPessoa(null);
    }

    private void carregarListaEquipamentos() {
        if (!this.equipamentosSelecionados.isEmpty()) {
            telaEmprestimo.getJlEquipamentos().removeAll();
            telaEmprestimo.getJlEquipamentos().repaint();

            telaEmprestimo.getJlEquipamentos().setListData(equipamentosSelecionados.toArray(new Equipamento[equipamentosSelecionados.size()]));

            this.selecionarEquipamentoController.getTelaSelecionarEquipamento().getTxtPesquisa().setText("");
            this.selecionarEquipamentoController.carregarEquipamentos();
        }
    }

    private void confirmarEmprestimo() {
        Long matricula = null;
        try {
            matricula = Long.valueOf(this.telaEmprestimo.getTxtMatricula().getText());
        } catch (NumberFormatException n) {
            Log.saveLog(n, EmprestimoController.class);
        }

        final Solicitante solicitante = new SolicitanteDAO().getSolicitante(matricula);
        EmprestimoBO ebo = new EmprestimoBO();

        final Date dataPrevistaEntrega = this.telaEmprestimo.getTxtDataPrevistaEntrega().getDate();

        final Emprestimo emprestimo = ebo.confirmarEmprestimo(solicitante, equipamentosSelecionados, dataPrevistaEntrega);
        if (emprestimo != null) {
            final Relatorio relatorio = new Relatorio();
            relatorio.setFile(Arquivo.getRelatorio("resumo_emprestimo"));
            relatorio.addParametro("NUM_PROTOCOLO", emprestimo.getNumeroProtocolo());
            relatorio.addParametro("USUARIO", Session.getUsuario().getNome());
            relatorio.setTitulo("Resumo Empréstimo");

            GeradorRelatorioJasper.gerarJasper(relatorio);
            this.limparCampos();
        }
    }

    public void limparCampos() {
        this.telaEmprestimo.getTxtNome().setText(null);
        this.telaEmprestimo.getTxtMatricula().setText(null);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        this.telaEmprestimo.getTxtDataPrevistaEntrega().setDate(calendar.getTime());

        this.equipamentosSelecionados.removeAll(equipamentosSelecionados);
        this.telaEmprestimo.getJlEquipamentos().setListData(new Equipamento[]{});
    }

    public TelaEmprestimo getTelaEmprestimo() {
        return telaEmprestimo;
    }
}
