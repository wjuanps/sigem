/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.view;

import br.com.jsampaio.sigem.util.Configs;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 *
 * @author AdaL
 */
public class TelaInicial extends javax.swing.JFrame {
    
    /**
     * Creates new form Inicio
     */
    public TelaInicial() {
        initComponents();
        super.setIconImage(GraphicsConstants.SYSTEM_ICON);
        
        super.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.btnVoltarInicio.setVisible(false);
        this.btnPainelDeControle.setVisible(false);
        this.jSeparator.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        root = new javax.swing.JPanel();
        jpInicio = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbDataAcesso = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnCadastrarPessoa = new javax.swing.JButton();
        btnCadastrarMaterial = new javax.swing.JButton();
        btnCadastrarEquipamento = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnRequisitarMaterial = new javax.swing.JButton();
        btnEmprestarEquipamento = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnPainelDeControle = new javax.swing.JButton();
        jSeparator = new javax.swing.JToolBar.Separator();
        btnVoltarInicio = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jmiCadastrarPessoa = new javax.swing.JMenuItem();
        jmiAtualizarPessoa = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jmiCadastrarEquipamento = new javax.swing.JMenuItem();
        jmiAtualizarEquipamento = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jmiCadastrarMaterial = new javax.swing.JMenuItem();
        jmiAtualizarMaterial = new javax.swing.JMenuItem();
        jmiCadastrarUnidadeCurso = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jmiRequisitarMaterial = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jmiEmprestimoEquipamento = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jmiDevolucaoEquipamento = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jmiRelatorioMaterial = new javax.swing.JMenuItem();
        jmiRelatorioEquipamento = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jmiRelatorioRequisicoes = new javax.swing.JMenuItem();
        jmiRelatorioEmprestimo = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jmiRelatorioEstoqueMinimo = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jmiAtualizarDadosUsuario = new javax.swing.JMenuItem();
        jmiTrocarSenha = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(Configs.ConfigTela.getConfig("Abreviado").concat(" - ").concat(Configs.ConfigTela.getConfig("Completo")));
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(1000, 700));
        setName("Inicio"); // NOI18N
        setState(6);

        root.setBackground(GraphicsConstants.COR_FUNDO);
        root.setLayout(new java.awt.CardLayout());

        jpInicio.setBackground(GraphicsConstants.COR_FUNDO);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/Logo1.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lbDataAcesso.setForeground(new java.awt.Color(153, 153, 153));
        lbDataAcesso.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbDataAcesso.setText("Acesso em");

        javax.swing.GroupLayout jpInicioLayout = new javax.swing.GroupLayout(jpInicio);
        jpInicio.setLayout(jpInicioLayout);
        jpInicioLayout.setHorizontalGroup(
            jpInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpInicioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbDataAcesso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1010, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpInicioLayout.setVerticalGroup(
            jpInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpInicioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbDataAcesso)
                .addContainerGap())
        );

        root.add(jpInicio, "telaInicio");

        jPanel1.setBackground(GraphicsConstants.COR_FUNDO);

        jToolBar1.setBackground(GraphicsConstants.COR_FUNDO);
        jToolBar1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 204), new java.awt.Color(255, 255, 255), new java.awt.Color(204, 204, 204), new java.awt.Color(255, 255, 255)));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnCadastrarPessoa.setBackground(GraphicsConstants.COR_FUNDO);
        btnCadastrarPessoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/cadastrar_pessoa.png"))); // NOI18N
        btnCadastrarPessoa.setToolTipText(Configs.ConfigTela.getConfig("ToolTipCadastroPessoa"));
        btnCadastrarPessoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadastrarPessoa.setFocusable(false);
        btnCadastrarPessoa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCadastrarPessoa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnCadastrarPessoa);

        btnCadastrarMaterial.setBackground(GraphicsConstants.COR_FUNDO);
        btnCadastrarMaterial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/cadastro_material.png"))); // NOI18N
        btnCadastrarMaterial.setToolTipText(Configs.ConfigTela.getConfig("ToolTipCadastroMaterial"));
        btnCadastrarMaterial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadastrarMaterial.setFocusable(false);
        btnCadastrarMaterial.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCadastrarMaterial.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnCadastrarMaterial);

        btnCadastrarEquipamento.setBackground(GraphicsConstants.COR_FUNDO);
        btnCadastrarEquipamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/cadastrar_equipamento.png"))); // NOI18N
        btnCadastrarEquipamento.setToolTipText(Configs.ConfigTela.getConfig("ToolTipCadastroEquipamento"));
        btnCadastrarEquipamento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadastrarEquipamento.setFocusable(false);
        btnCadastrarEquipamento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCadastrarEquipamento.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnCadastrarEquipamento);
        jToolBar1.add(jSeparator3);

        btnRequisitarMaterial.setBackground(GraphicsConstants.COR_FUNDO);
        btnRequisitarMaterial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/liberar_material2.png"))); // NOI18N
        btnRequisitarMaterial.setToolTipText(Configs.ConfigTela.getConfig("ToolTipRequisicao"));
        btnRequisitarMaterial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRequisitarMaterial.setFocusable(false);
        btnRequisitarMaterial.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRequisitarMaterial.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnRequisitarMaterial);

        btnEmprestarEquipamento.setBackground(GraphicsConstants.COR_FUNDO);
        btnEmprestarEquipamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/Empresta.png"))); // NOI18N
        btnEmprestarEquipamento.setToolTipText(Configs.ConfigTela.getConfig("ToolTipEmprestimo"));
        btnEmprestarEquipamento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEmprestarEquipamento.setFocusable(false);
        btnEmprestarEquipamento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEmprestarEquipamento.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnEmprestarEquipamento);
        jToolBar1.add(jSeparator2);

        btnPainelDeControle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/settings.png"))); // NOI18N
        btnPainelDeControle.setMnemonic('A');
        btnPainelDeControle.setToolTipText(Configs.ConfigTela.getConfig("ToolTipPainelControle"));
        btnPainelDeControle.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPainelDeControle.setFocusable(false);
        btnPainelDeControle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPainelDeControle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnPainelDeControle);
        jToolBar1.add(jSeparator);

        btnVoltarInicio.setBackground(GraphicsConstants.COR_FUNDO);
        btnVoltarInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/cancelar.png"))); // NOI18N
        btnVoltarInicio.setToolTipText(Configs.ConfigTela.getConfig("ToolTipVoltar"));
        btnVoltarInicio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVoltarInicio.setFocusable(false);
        btnVoltarInicio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVoltarInicio.setPreferredSize(new java.awt.Dimension(67, 57));
        btnVoltarInicio.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnVoltarInicio);

        btnSair.setBackground(GraphicsConstants.COR_FUNDO);
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/sair2.png"))); // NOI18N
        btnSair.setToolTipText(Configs.ConfigTela.getConfig("ToolTipSair"));
        btnSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSair.setFocusable(false);
        btnSair.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSair.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSair);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jMenu1.setText(Configs.ConfigTela.getConfig("MenuCadastros"));

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/pessoa20.png"))); // NOI18N
        jMenu6.setText(Configs.ConfigTela.getConfig("SubPessoa"));

        jmiCadastrarPessoa.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jmiCadastrarPessoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/pessoa20.png"))); // NOI18N
        jmiCadastrarPessoa.setText("Cadastrar");
        jMenu6.add(jmiCadastrarPessoa);

        jmiAtualizarPessoa.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jmiAtualizarPessoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/pessoa20.png"))); // NOI18N
        jmiAtualizarPessoa.setText("Atualizar");
        jMenu6.add(jmiAtualizarPessoa);

        jMenu1.add(jMenu6);

        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/equipamento20.png"))); // NOI18N
        jMenu7.setText(Configs.ConfigTela.getConfig("SubEquipamento"));

        jmiCadastrarEquipamento.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jmiCadastrarEquipamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/equipamento20.png"))); // NOI18N
        jmiCadastrarEquipamento.setText("Cadastrar");
        jMenu7.add(jmiCadastrarEquipamento);

        jmiAtualizarEquipamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/equipamento20.png"))); // NOI18N
        jmiAtualizarEquipamento.setText("Atualizar");
        jMenu7.add(jmiAtualizarEquipamento);

        jMenu1.add(jMenu7);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/cadastro_material20.png"))); // NOI18N
        jMenu5.setText(Configs.ConfigTela.getConfig("SubMaterial"));

        jmiCadastrarMaterial.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        jmiCadastrarMaterial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/icon-controle-de-estoqu.png"))); // NOI18N
        jmiCadastrarMaterial.setText("Cadastrar");
        jMenu5.add(jmiCadastrarMaterial);

        jmiAtualizarMaterial.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jmiAtualizarMaterial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/atualizar.png"))); // NOI18N
        jmiAtualizarMaterial.setText("Atualizar");
        jMenu5.add(jmiAtualizarMaterial);

        jMenu1.add(jMenu5);

        jmiCadastrarUnidadeCurso.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        jmiCadastrarUnidadeCurso.setText(Configs.ConfigTela.getConfig("ItemUnidade/Curso"));
        jMenu1.add(jmiCadastrarUnidadeCurso);

        jMenuBar1.add(jMenu1);

        jMenu2.setText(Configs.ConfigTela.getConfig("MenuRequisições"));

        jmiRequisitarMaterial.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jmiRequisitarMaterial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/cadastro_material20.png"))); // NOI18N
        jmiRequisitarMaterial.setText(Configs.ConfigTela.getConfig("ItemMaterial"));
        jMenu2.add(jmiRequisitarMaterial);

        jMenuBar1.add(jMenu2);

        jMenu3.setText(Configs.ConfigTela.getConfig("MenuEmpréstimo"));

        jmiEmprestimoEquipamento.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jmiEmprestimoEquipamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/equipamento20.png"))); // NOI18N
        jmiEmprestimoEquipamento.setText(Configs.ConfigTela.getConfig("ItemEquipamento"));
        jMenu3.add(jmiEmprestimoEquipamento);
        jMenu3.add(jSeparator4);

        jmiDevolucaoEquipamento.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jmiDevolucaoEquipamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/equipamento20.png"))); // NOI18N
        jmiDevolucaoEquipamento.setText(Configs.ConfigTela.getConfig("ItemDevolucaoEquipamento"));
        jMenu3.add(jmiDevolucaoEquipamento);

        jMenuBar1.add(jMenu3);

        jMenu4.setText(Configs.ConfigTela.getConfig("MenuRelatórios"));

        jmiRelatorioMaterial.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jmiRelatorioMaterial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/relatorio_material.png"))); // NOI18N
        jmiRelatorioMaterial.setText(Configs.ConfigTela.getConfig("ItemRelatorioMateriais"));
        jMenu4.add(jmiRelatorioMaterial);

        jmiRelatorioEquipamento.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jmiRelatorioEquipamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sigem/Image/relatorio_equipamentos.jpg"))); // NOI18N
        jmiRelatorioEquipamento.setText(Configs.ConfigTela.getConfig("ItemRelatorioEquipamentos"));
        jMenu4.add(jmiRelatorioEquipamento);
        jMenu4.add(jSeparator1);

        jmiRelatorioRequisicoes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jmiRelatorioRequisicoes.setText(Configs.ConfigTela.getConfig("ItemRelatorioRequisições"));
        jMenu4.add(jmiRelatorioRequisicoes);

        jmiRelatorioEmprestimo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jmiRelatorioEmprestimo.setText(Configs.ConfigTela.getConfig("ItemRelatorioEmprestimos"));
        jMenu4.add(jmiRelatorioEmprestimo);
        jMenu4.add(jSeparator5);

        jmiRelatorioEstoqueMinimo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        jmiRelatorioEstoqueMinimo.setText("Relatório Estoque Mínimo");
        jMenu4.add(jmiRelatorioEstoqueMinimo);

        jMenuBar1.add(jMenu4);

        jMenu8.setText(Configs.ConfigTela.getConfig("MenuUsuários"));

        jmiAtualizarDadosUsuario.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        jmiAtualizarDadosUsuario.setText(Configs.ConfigTela.getConfig("ItemUsuarioAtualizarDados"));
        jMenu8.add(jmiAtualizarDadosUsuario);

        jmiTrocarSenha.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        jmiTrocarSenha.setText(Configs.ConfigTela.getConfig("ItemUsuarioTrocarSenha"));
        jMenu8.add(jmiTrocarSenha);

        jMenuBar1.add(jMenu8);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(root, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(root, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public JPanel getRoot() {
        return root;
    }

    public JMenuItem getJmiEmprestimoEquipamento() {
        return jmiEmprestimoEquipamento;
    }

    public JMenuItem getJmiRequisitarMaterial() {
        return jmiRequisitarMaterial;
    }

    public JMenuItem getJmiCadastrarEquipamento() {
        return jmiCadastrarEquipamento;
    }

    public JMenuItem getJmiCadastrarMaterial() {
        return jmiCadastrarMaterial;
    }

    public JMenuItem getJmiCadastrarPessoa() {
        return jmiCadastrarPessoa;
    }

    public JButton getBtnSair() {
        return btnSair;
    }

    public JButton getBtnVoltarInicio() {
        return btnVoltarInicio;
    }

    public JLabel getLbDataAcesso() {
        return lbDataAcesso;
    }

    public JButton getBtnCadastrarEquipamento() {
        return btnCadastrarEquipamento;
    }

    public void setBtnCadastrarEquipamento(JButton btnCadastrarEquipamento) {
        this.btnCadastrarEquipamento = btnCadastrarEquipamento;
    }

    public JButton getBtnCadastrarMaterial() {
        return btnCadastrarMaterial;
    }

    public void setBtnCadastrarMaterial(JButton btnCadastrarMaterial) {
        this.btnCadastrarMaterial = btnCadastrarMaterial;
    }

    public JButton getBtnCadastrarPessoa() {
        return btnCadastrarPessoa;
    }

    public void setBtnCadastrarPessoa(JButton btnCadastrarPessoa) {
        this.btnCadastrarPessoa = btnCadastrarPessoa;
    }

    public JButton getBtnEmprestarEquipamento() {
        return btnEmprestarEquipamento;
    }

    public void setBtnEmprestarEquipamento(JButton btnEmprestarEquipamento) {
        this.btnEmprestarEquipamento = btnEmprestarEquipamento;
    }

    public JButton getBtnRequisitarMaterial() {
        return btnRequisitarMaterial;
    }

    public void setBtnRequisitarMaterial(JButton btnRequisitarMaterial) {
        this.btnRequisitarMaterial = btnRequisitarMaterial;
    }

    public JMenuItem getJmiRelatorioEquipamento() {
        return jmiRelatorioEquipamento;
    }

    public JMenuItem getJmiRelatorioMaterial() {
        return jmiRelatorioMaterial;
    }

    public JMenuItem getJmiRelatorioRequisicoes() {
        return jmiRelatorioRequisicoes;
    }

    public JMenuItem getJmiRelatorioEmprestimo() {
        return jmiRelatorioEmprestimo;
    }

    public JMenuItem getJmiDevolucaoEquipamento() {
        return jmiDevolucaoEquipamento;
    }

    public JMenuItem getJmiAtualizarMaterial() {
        return jmiAtualizarMaterial;
    }

    public JMenuItem getJmiCadastrarUnidadeCurso() {
        return jmiCadastrarUnidadeCurso;
    }

    public JMenuItem getJmiAtualizarPessoa() {
        return jmiAtualizarPessoa;
    }

    public JMenuItem getJmiAtualizarEquipamento() {
        return jmiAtualizarEquipamento;
    }

    public JMenuItem getJmiAtualizarDadosUsuario() {
        return jmiAtualizarDadosUsuario;
    }

    public JMenuItem getJmiTrocarSenha() {
        return jmiTrocarSenha;
    }

    public JButton getBtnPainelDeControle() {
        return btnPainelDeControle;
    }

    public JToolBar.Separator getjSeparator() {
        return jSeparator;
    }

    public JMenuItem getJmiRelatorioEstoqueMinimo() {
        return jmiRelatorioEstoqueMinimo;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrarEquipamento;
    private javax.swing.JButton btnCadastrarMaterial;
    private javax.swing.JButton btnCadastrarPessoa;
    private javax.swing.JButton btnEmprestarEquipamento;
    private javax.swing.JButton btnPainelDeControle;
    private javax.swing.JButton btnRequisitarMaterial;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnVoltarInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar.Separator jSeparator;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem jmiAtualizarDadosUsuario;
    private javax.swing.JMenuItem jmiAtualizarEquipamento;
    private javax.swing.JMenuItem jmiAtualizarMaterial;
    private javax.swing.JMenuItem jmiAtualizarPessoa;
    private javax.swing.JMenuItem jmiCadastrarEquipamento;
    private javax.swing.JMenuItem jmiCadastrarMaterial;
    private javax.swing.JMenuItem jmiCadastrarPessoa;
    private javax.swing.JMenuItem jmiCadastrarUnidadeCurso;
    private javax.swing.JMenuItem jmiDevolucaoEquipamento;
    private javax.swing.JMenuItem jmiEmprestimoEquipamento;
    private javax.swing.JMenuItem jmiRelatorioEmprestimo;
    private javax.swing.JMenuItem jmiRelatorioEquipamento;
    private javax.swing.JMenuItem jmiRelatorioEstoqueMinimo;
    private javax.swing.JMenuItem jmiRelatorioMaterial;
    private javax.swing.JMenuItem jmiRelatorioRequisicoes;
    private javax.swing.JMenuItem jmiRequisitarMaterial;
    private javax.swing.JMenuItem jmiTrocarSenha;
    private javax.swing.JPanel jpInicio;
    private javax.swing.JLabel lbDataAcesso;
    private javax.swing.JPanel root;
    // End of variables declaration//GEN-END:variables

}
