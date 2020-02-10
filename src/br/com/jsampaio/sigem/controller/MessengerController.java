package br.com.jsampaio.sigem.controller;

import br.com.jsampaio.sigem.view.MessageView;

/**
 *
 * @author Juan Soares
 *
 */
public final class MessengerController {

    private final MessageView messageView;
    private final ListenerMessage listenerMessage;

    public MessengerController() {
        this.messageView = new MessageView(null, true);
        this.listenerMessage = new ListenerMessage(this.messageView);
    }

    public MessageView getViewMesseger() {
        return messageView;
    }
    
    
    public int getOption() {
        return this.listenerMessage.getOption();
    }
    
    public void setOption(int option) {
        this.listenerMessage.setOption(option);
    }
}
