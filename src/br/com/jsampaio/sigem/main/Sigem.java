package br.com.jsampaio.sigem.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.jsampaio.sigem.controller.InicioController;
import br.com.jsampaio.sigem.model.vo.MessageType;
import br.com.jsampaio.sigem.util.Log;
import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Janilson
 */
public class Sigem {

    /**
     * 
     */
    private static final int CLOSE_SYSTEM = 0;
    
    static {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            Log.saveLog(ex, Sigem.class);
        }
    }

    private static ServerSocket serverSocket;

    static {
        try {
            serverSocket = new ServerSocket(4757);
            serverSocket.setReuseAddress(true);
        } catch (IOException ex) {
            Messenger.showMessage("O sistema já está aberto nessa máquina", MessageType.ERROR);
            Log.saveLog(ex, Sigem.class);
            System.exit(CLOSE_SYSTEM);
        }
    }

    public static void closeSystem() {
        if (Messenger.showMessage("Você realmente deseja sair do sistema?",
                MessageType.QUESTION) == Messenger.YES_OPTION) {
            System.exit(CLOSE_SYSTEM);
        }
    }

    public static void main(String[] args) {
        try {
            InicioController.main(args);
        } catch (ExceptionInInitializerError erro) {
            Messenger.showMessage("Não foi possível conectar ao banco de dados", MessageType.ERROR);
            Log.saveLog(erro, Sigem.class);
            System.exit(CLOSE_SYSTEM);
        } catch (Exception e) {
            Messenger.showMessage("Erro ao inicializar o sistema", MessageType.ERROR);
            Log.saveLog(e, Sigem.class);
            System.exit(CLOSE_SYSTEM);
        }
    }
}
