package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.main.Messenger;
import br.com.jsampaio.sigem.view.MessageView;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;

import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;

/**
 *
 * @author Juan Soares
 *
 */
public class ListenerMessage extends MouseAdapter {

    private final MessageView messageView;
    
    private int option = 0;
    
    /**
     *
     * @param messageView
     */
    public ListenerMessage(MessageView messageView) {
        this.messageView = messageView;
        messageView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                messageView.setVisible(false);
            }
        });

        this.addAction(KeyEvent.VK_ESCAPE, "escAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageView.dispose();
                messageView.setVisible(false);
            }
        });
        
        this.addAction(KeyEvent.VK_ENTER, "enterAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                option = Messenger.YES_OPTION;
                messageView.dispose();
                messageView.setVisible(false);
            }
        });

        messageView.getLbCancelar().addMouseMotionListener(this);
        messageView.getLbNao().addMouseMotionListener(this);
        messageView.getLbCancelar().addMouseListener(this);
        messageView.getLbOk().addMouseMotionListener(this);
        messageView.getLbNao().addMouseListener(this);
        messageView.getLbOk().addMouseListener(this);
    }

    /**
     * KeyEvent.VK_ESCAPE
     * @param keyCode
     * @param key 
     */
    private void addAction(int keyCode, String key, AbstractAction action) {
        InputMap inputMap = messageView.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(keyCode, 0), key);
        ActionMap actionMap = messageView.getRootPane().getActionMap();
        actionMap.put(key, action);
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        changeCollor(e, true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        changeCollor(e, false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        final JLabel source = (JLabel) e.getSource();
        this.option = (source.getText().equals("Sim")) ? Messenger.YES_OPTION : Messenger.NO_CANCEL_OPTION;
        this.messageView.dispose();
        this.messageView.setVisible(false);
        changeCollor(e, false);
    }

    /**
     *
     * @param entrar
     */
    private void changeCollor(ComponentEvent e, boolean in) {
        final JLabel source = (JLabel) e.getSource();
        if (in) {
            source.setForeground(new Color(62, 57, 57));
            source.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                    new Color(62, 57, 57)));
        } else {
            source.setForeground(new Color(0));
            source.setBorder(null);
        }
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }
    
}
