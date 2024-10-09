/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;
import java.util.logging.Logger;
import utilidades.Message;
import utilidades.MessageType;
import utilidades.Signable;
import utilidades.User;

/**
 *
 * @author Sergio
 */
public class Client implements Signable {

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private String serverIP;
    private Integer serverPort;

    public Client(String serverIP, Integer serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public void connect() {
        try {
            socket = new Socket(serverIP, serverPort);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            Logger.getLogger("Conectado al servidor en " + serverIP + ":" + serverPort);
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (socket != null) {
                socket.close();
                Logger.getLogger("Conexion Cerrada.");
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        connect();
        Message signUpRequest = new Message(MessageType.SIGN_UP_REQUEST, user);
        sendMessage(signUpRequest);
        Message response = receiveMessage();
        closeConnection();
        return response;
    }

    @Override
    public Message signIn(User user) {
        connect();
        Message signInRequest = new Message(MessageType.SIGN_IN_REQUEST, user);
        sendMessage(signInRequest);
        Message response = receiveMessage();
        closeConnection();
        return response;
    }

    @Override
    public Message getCountries() {
        connect();
        Object object = Optional.ofNullable(this); //Permite que no sea nulo solo vacio
        Message signInRequest = new Message(MessageType.SIGN_IN_REQUEST, object);
        sendMessage(signInRequest);
        Message response = receiveMessage();
        closeConnection();
        return response;
    }

    //METODOS PRIVADOS
    private void sendMessage(Message message) {
        try {
            outputStream.writeObject(message);
            outputStream.flush();
            Logger.getLogger("Mensaje enviado al servidor: " + message);
        } catch (IOException e) {
            Logger.getLogger("Error al enviar mensaje al servidor: " + e.getMessage());
        }
    }

    private Message receiveMessage() {
        try {
            Message message = (Message) inputStream.readObject();
            Logger.getLogger("Mensaje recibido del servidor: " + message);
            return message;
        } catch (IOException | ClassNotFoundException e) {
            Logger.getLogger("Error al recibir mensaje del servidor: " + e.getMessage());
            return null;
        }
    }
}
