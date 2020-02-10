/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import br.com.jsampaio.sigem.controller.InicioController;
import java.io.IOException;
import java.net.ServerSocket;
import javax.swing.JOptionPane;

/**
 *
 * @author Janilson
 */
public class RunSigem {

    static {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RunSigem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    private static ServerSocket serverSocket;
    static {
        try {
            serverSocket = new ServerSocket(4757);
            serverSocket.setReuseAddress(true);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "O sistema já está aberto nessa máquina");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        try {
            InicioController.main(args);
        } catch (ExceptionInInitializerError erro) {
            JOptionPane.showMessageDialog(null, "Não foi possível conectar ao banco de dados", "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inicializar o sistema");
            System.exit(0);
        }
    }
}
