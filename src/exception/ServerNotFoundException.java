/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

import static utilities.AlertUtilities.*;
import javafx.scene.control.Alert;
import utilidades.Message;
import utilidades.MessageType;

/**
 *
 * @author 2dam
 */
public class ServerNotFoundException extends RuntimeException {

    public Message CreateMessage() {
        return new Message(MessageType.SERVER_ERROR, null);
    }

}
