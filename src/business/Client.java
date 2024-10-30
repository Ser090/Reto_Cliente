/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import exception.ServerNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import utilidades.Message;
import utilidades.MessageType;
import utilidades.Signable;
import utilidades.User;
import view.ApplicationClientSignUpController;

/**
 *
 * @author Sergio
 */
public class Client implements Signable {

    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private String serverIP;
    private Integer serverPort;
    private ResourceBundle bundle = ResourceBundle.getBundle("business.conecction");

    public Client() {
        this.serverIP = bundle.getString("serverIP");
        this.serverPort = Integer.parseInt(bundle.getString("serverPort"));
    }

    private static Client instance;

    public static Client getInstance() {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

    public void connect() {
        try {
            socket = new Socket(serverIP, serverPort);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            LOGGER.info("Conectado al servidor en " + serverIP + ":" + serverPort);
        } catch (IOException e) {
            LOGGER.severe("Al abrir conexión. " + e.getMessage());
            throw new ServerNotFoundException();
        }
    }

    public void closeConnection() {
        try {
            if (socket != null) {
                socket.close();
                LOGGER.info("Conexion Cerrada.");
            }
        } catch (IOException e) {
            LOGGER.severe("Al cerrar conexión. " + e.getMessage());
        }
    }

    /**
     * Habra que mirar si podemos reducirlo a un solo metodo con dos parametros
     *
     * @param user
     * @return
     */
    @Override
    public Message signUp(User user) {
        Message response = null;
        try {
            connect();
            Message signUpRequest = new Message(MessageType.SIGN_UP_REQUEST, user);
            sendMessage(signUpRequest);
            response = receiveMessage();
            closeConnection();
            return response;
        } catch (ServerNotFoundException e) {

            return e.CreateMessage();
        }

    }

    @Override
    public Message signIn(User user) {
        Message response = null;
        try {
            connect();
            Message signInRequest = new Message(MessageType.SIGN_IN_REQUEST, user);
            sendMessage(signInRequest);
            response = receiveMessage();
            closeConnection();
            return response;
        } catch (ServerNotFoundException e) {
            return e.CreateMessage();
        }
    }

    //METODOS PRIVADOS
    private void sendMessage(Message message) {
        try {
            outputStream.writeObject(message);
            outputStream.flush();
            LOGGER.info("Mensaje enviado al servidor: " + message);
        } catch (IOException e) {
            LOGGER.severe("Al enviar mensaje al servidor: " + e.getMessage());
        }
    }

    private Message receiveMessage() {
        try {
            Message message = (Message) inputStream.readObject();
            LOGGER.info("Mensaje recibido del servidor: " + message);
            return message;
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.severe("Al recibir mensaje del servidor: " + e.getMessage());
            return null;
        }
    }
}
