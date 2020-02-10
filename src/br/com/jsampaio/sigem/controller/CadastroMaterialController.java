/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.main.Messenger;
import br.com.jsampaio.sigem.model.bo.MaterialBO;
import br.com.jsampaio.sigem.model.vo.Material;
import br.com.jsampaio.sigem.model.vo.MessageType;
import br.com.jsampaio.sigem.view.TelaCadastroMaterial;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author Janilson
 */
public class CadastroMaterialController {
    
    private final TelaCadastroMaterial telaCadastroMaterial;

    public CadastroMaterialController() {
        this.telaCadastroMaterial = new TelaCadastroMaterial();
        
        final MaterialListener materialListener = this.new MaterialListener();
        this.telaCadastroMaterial.getBtnCadastrar().addActionListener(materialListener);
        this.telaCadastroMaterial.getBtnLimpar().addActionListener(materialListener);
    }

    private final class MaterialListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btnTemp = (JButton) e.getSource();
            if (btnTemp.equals(telaCadastroMaterial.getBtnCadastrar())) {
                final MaterialBO mbo = new MaterialBO();
                final Material material = telaCadastroMaterial.getMaterial();
                final String mensagem = mbo.salvarMaterial(material);
                if (mensagem.isEmpty()) {
                    Messenger.showMessage("Cadastro salvo com sucesso", MessageType.INFORMATION);
                    telaCadastroMaterial.limparCampos();
                } else {
                    Messenger.showMessage(mensagem, MessageType.ERROR);
                }
                return;
            }
            
            if (btnTemp.equals(telaCadastroMaterial.getBtnLimpar())) {
                telaCadastroMaterial.limparCampos();
            }
        }
    }
    
    public TelaCadastroMaterial getTelaCadastroMaterial() {
        return telaCadastroMaterial;
    }
    
}
