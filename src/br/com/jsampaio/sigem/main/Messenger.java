package br.com.jsampaio.sigem.main;

import br.com.jsampaio.sigem.controller.MessengerController;
import br.com.jsampaio.sigem.model.vo.MessageType;

/**
 *
 * @author Juan Soares
 *
 */
public class Messenger {

    /**
     * 
     */
    public static final int YES_OPTION = 1;
    
    /**
     * 
     */
    public static final int NO_CANCEL_OPTION = -1;
    
    /**
     *
     */
    private static MessengerController controller;

    /**
     *
     * @param message
     */
    private static void showMessageInformation(String message) {
        controller.getViewMesseger().getLbCancelar().setVisible(false);
        controller.getViewMesseger().getLbNao().setVisible(false);
        controller.getViewMesseger().getLbOk().setVisible(true);
        controller.getViewMesseger().getTxtMensagem().setText(message);
        controller.getViewMesseger().setVisible(true);
    }

    /**
     *
     * @param message
     */
    private static void showMessageError(String message) {
        controller.getViewMesseger().getLbCancelar().setVisible(false);
        controller.getViewMesseger().getLbNao().setVisible(false);
        controller.getViewMesseger().getLbOk().setVisible(true);
        controller.getViewMesseger().getTxtMensagem().setText(message);
        controller.getViewMesseger().setVisible(true);
    }

    private static int showMessageQuestion(String message) {
        controller.getViewMesseger().getLbCancelar().setVisible(true);
        controller.getViewMesseger().getLbNao().setVisible(true);
        controller.getViewMesseger().getLbOk().setVisible(true);
        controller.getViewMesseger().getTxtMensagem().setText(message);
        controller.getViewMesseger().setVisible(true);
        
        return controller.getOption();
    }
    
    /**
     *
     * @param message
     * @param type
     * @return 
     */
    public static int showMessage(String message, MessageType type) {

        int option = Messenger.NO_CANCEL_OPTION;
        
        if (controller == null) {
            controller = new MessengerController();
        }

        controller.getViewMesseger().getLbOk().setText("Ok");
        controller.setOption(option);
        
        if (message != null && !message.isEmpty()) {
            switch (type) {
                case INFORMATION:
                    showMessageInformation(message);
                    break;

                case ERROR:
                    showMessageError(message);
                    break;

                case QUESTION:
                    controller.getViewMesseger().getLbOk().setText("Sim");
                    option = showMessageQuestion(message);
                    break;
                    
                default:
                    break;
            }
        }
        
        return option;
    }
}
