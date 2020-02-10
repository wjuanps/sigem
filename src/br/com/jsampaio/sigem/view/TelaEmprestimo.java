/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.view;

import br.com.jsampaio.sigem.model.vo.Equipamento;
import br.com.jsampaio.sigem.util.Configs;
import com.toedter.calendar.JDateChooser;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

/**
 *
 * @author Janilson
 */
public class TelaEmprestimo extends javax.swing.JPanel {

    /**
     * Creates new form TelaEmprestimo
     */
    public TelaEmprestimo() {
        initComponents();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        this.txtDataPrevistaEntrega.setDate(calendar.getTime());
        this.txtDataPrevistaEntrega.getCalendarButton().setVisible(false);
        ((JTextField) this.txtDataPrevistaEntrega.getDateEditor().getUiComponent()).setEditable(false);
        ((JTextField) this.txtDataPrevistaEntrega.getDateEditor().getUiComponent()).setFont(GraphicsConstants.FONT_INPUT);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnPesquisarSolicitante = new javax.swing.JButton();
        txtNome = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtMatricula = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jlEquipamentos = new javax.swing.JList<>();
        btnSelecionarEquipamentos = new javax.swing.JButton();
        btnRemoverEquipamento = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnEmprestar = new javax.swing.JButton();
        lbDataAcesso = new javax.swing.JLabel();
        btnLimparCampos = new javax.swing.JButton();
        txtDataPrevistaEntrega = new com.toedter.calendar.JDateChooser();

        setBackground(GraphicsConstants.COR_FUNDO);

        jLabel1.setFont(GraphicsConstants.FONT_TITULO);
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(Configs.ConfigTela.getConfig("lbTituloEmprestimo"));

        btnPesquisarSolicitante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/lupa2.png"))); // NOI18N
        btnPesquisarSolicitante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        txtNome.setEditable(false);
        txtNome.setFont(GraphicsConstants.FONT_INPUT);
        txtNome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel7.setFont(GraphicsConstants.FONT_LABEL);
        jLabel7.setText(Configs.ConfigTela.getConfig("lbNomeSolicitante"));

        jLabel9.setFont(GraphicsConstants.FONT_LABEL);
        jLabel9.setText(Configs.ConfigTela.getConfig("lbMatriculaSolicitante"));

        txtMatricula.setEditable(false);
        txtMatricula.setFont(GraphicsConstants.FONT_INPUT);

        jlEquipamentos.setFont(GraphicsConstants.FONT_INPUT);
        jlEquipamentos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jlEquipamentos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane2.setViewportView(jlEquipamentos);

        btnSelecionarEquipamentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/lupa2.png"))); // NOI18N
        btnSelecionarEquipamentos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnRemoverEquipamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/ios7-trash-mini.png"))); // NOI18N
        btnRemoverEquipamento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel8.setFont(GraphicsConstants.FONT_LABEL);
        jLabel8.setText(Configs.ConfigTela.getConfig("lbEquipamentosSolicitados"));

        jLabel11.setFont(GraphicsConstants.FONT_LABEL);
        jLabel11.setText(Configs.ConfigTela.getConfig("lbDataSolicitacao"));

        btnEmprestar.setFont(GraphicsConstants.FONT_BOTAO);
        btnEmprestar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/liberar.png"))); // NOI18N
        btnEmprestar.setText(Configs.ConfigTela.getConfig("btnEmprestar"));
        btnEmprestar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEmprestar.setMargin(new java.awt.Insets(2, 2, 2, 2));

        lbDataAcesso.setForeground(new java.awt.Color(153, 153, 153));
        lbDataAcesso.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbDataAcesso.setText("Acesso em");

        btnLimparCampos.setFont(GraphicsConstants.FONT_BOTAO);
        btnLimparCampos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/master-clean2.png"))); // NOI18N
        btnLimparCampos.setText(Configs.ConfigTela.getConfig("btnLimparEmprestimo"));
        btnLimparCampos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        txtDataPrevistaEntrega.setDateFormatString("dd/MM/yyyy HH:mm:ss");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbDataAcesso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 140, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(btnEmprestar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnLimparCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnSelecionarEquipamentos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnRemoverEquipamento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtMatricula, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtNome))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnPesquisarSolicitante, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtDataPrevistaEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 140, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisarSolicitante, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSelecionarEquipamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRemoverEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDataPrevistaEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEmprestar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimparCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbDataAcesso)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    public JButton getBtnPesquisarSolicitante() {
        return btnPesquisarSolicitante;
    }

    public JTextField getTxtNome() {
        return txtNome;
    }

    public JTextField getTxtMatricula() {
        return txtMatricula;
    }

    public JButton getBtnSelecionarEquipamentos() {
        return btnSelecionarEquipamentos;
    }
    
    public JList<Equipamento> getJlEquipamentos() {
        return jlEquipamentos;
    }

    public JButton getBtnRemoverEquipamento() {
        return btnRemoverEquipamento;
    }

    public JButton getBtnEmprestar() {
        return btnEmprestar;
    }

    public JDateChooser getTxtDataPrevistaEntrega() {
        return txtDataPrevistaEntrega;
    }
    
    public JButton getBtnLimparCampos() {
        return btnLimparCampos;
    }

    public JLabel getLbDataAcesso() {
        return lbDataAcesso;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEmprestar;
    private javax.swing.JButton btnLimparCampos;
    private javax.swing.JButton btnPesquisarSolicitante;
    private javax.swing.JButton btnRemoverEquipamento;
    private javax.swing.JButton btnSelecionarEquipamentos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<Equipamento> jlEquipamentos;
    private javax.swing.JLabel lbDataAcesso;
    private com.toedter.calendar.JDateChooser txtDataPrevistaEntrega;
    private javax.swing.JTextField txtMatricula;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}