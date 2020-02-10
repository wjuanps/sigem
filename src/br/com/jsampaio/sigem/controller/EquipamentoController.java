/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.main.Messenger;
import br.com.jsampaio.sigem.model.bo.EquipamentoBO;
import br.com.jsampaio.sigem.model.vo.Equipamento;
import br.com.jsampaio.sigem.model.vo.MessageType;
import br.com.jsampaio.sigem.util.Log;
import br.com.jsampaio.sigem.view.TelaCadastrarEquipamento;
import br.com.jsampaio.sigem.view.TelaSelecionarEquipamento;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Janilson
 */
public class EquipamentoController {

    private final TelaCadastrarEquipamento telaCadastrarEquipamento;
    private final TelaSelecionarEquipamento telaSelecionarEquipamento;
    private final EquipamentoBO equipamentoBO;

    private Long codigo = null;

    public EquipamentoController() {
        this.telaCadastrarEquipamento = new TelaCadastrarEquipamento();
        this.telaSelecionarEquipamento = new TelaSelecionarEquipamento(null, true);

        final EquipamentoListener equipamentoListener = new EquipamentoListener();
        this.telaCadastrarEquipamento.getBtnCadastrarEquip().addActionListener(equipamentoListener);
        this.telaCadastrarEquipamento.getBtnLimpar().addActionListener(equipamentoListener);

        this.equipamentoBO = new EquipamentoBO();
    }

    private class EquipamentoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == telaCadastrarEquipamento.getBtnCadastrarEquip()) {
                try {
                    final Equipamento equipamento = new Equipamento();
                    equipamento.setNumeroTombamento(Long.valueOf(telaCadastrarEquipamento.getTxtNumTombamento().getText()));
                    equipamento.setDescricao(telaCadastrarEquipamento.getTxtDescricao().getText());
                    equipamento.setStatus(telaCadastrarEquipamento.getJcbSituacao().getSelectedItem().toString());
                    equipamento.setTipo(telaCadastrarEquipamento.getTxtTipoEquip().getText());
                    equipamento.setAtivo(true);

                    if (codigo != null) {
                        if (equipamento.getStatus().equals("Emprestado")) {
                            equipamento.setStatus("Disponível");
                        }
                        equipamento.setCodigo(codigo);
                    }

                    final String msg = equipamentoBO.cadastrarEquipamento(equipamento);
                    if (msg.isEmpty()) {
                        Messenger.showMessage("Cadastro salvo com sucesso!!", MessageType.INFORMATION);
                        limparCampos();
                        return;
                    }

                    Messenger.showMessage(msg, MessageType.ERROR);
                } catch (NumberFormatException n) {
                    Messenger.showMessage("Número de tombamento inválido!!", MessageType.ERROR);
                    Log.saveLog(n, EquipamentoController.class);
                }
            } else if (e.getSource() == telaCadastrarEquipamento.getBtnLimpar()) {
                limparCampos();
            }
        }
    }

    public void limparCampos() {
        telaCadastrarEquipamento.getTxtNumTombamento().setEnabled(true);
        telaCadastrarEquipamento.getTxtNumTombamento().setText(null);
        telaCadastrarEquipamento.getTxtDescricao().setText(null);
        telaCadastrarEquipamento.getTxtTipoEquip().setText(null);
        telaCadastrarEquipamento.getJcbSituacao().setSelectedIndex(0);
        telaCadastrarEquipamento.getJcbSituacao().setEnabled(true);

        codigo = null;
    }
    
    public TelaCadastrarEquipamento getTelaCadastrarEquipamento() {
        return telaCadastrarEquipamento;
    }

    public TelaSelecionarEquipamento getTelaSelecionarEquipamento() {
        return telaSelecionarEquipamento;
    }
    
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    
}
