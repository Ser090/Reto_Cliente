package business;

import exception.ServerNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilidades.Message;
import utilidades.MessageType;
import utilidades.Signable;
import utilidades.User;

/**
 * La clase {@code Client} representa un cliente que se conecta a un servidor
 * para realizar operaciones de inicio de sesión y registro de usuarios.
 * Implementa la interfaz {@code Signable} para proporcionar métodos de
 * autenticación de usuarios.
 *
 * Esta clase sigue el patrón Singleton para garantizar que solo haya una
 * instancia de cliente en la aplicación, y maneja la conexión, el envío y la
 * recepción de mensajes a través de sockets.
 *
 * @author Sergio
 */
public class Client implements Signable {

    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private Socket socket; // Socket para la conexión con el servidor
    private ObjectOutputStream outputStream; // Flujo de salida para enviar objetos
    private ObjectInputStream inputStream; // Flujo de entrada para recibir objetos

    private String serverIP; // Dirección IP del servidor
    private Integer serverPort; // Puerto del servidor
    private ResourceBundle bundle; // Carga de configuración

    // Instancia única del cliente
    private static Client instance;

    /**
     * Constructor que inicializa la dirección IP y el puerto del servidor
     * utilizando los valores configurados en un archivo de propiedades.
     *
     * @throws ServerNotFoundException Si no se encuentra el archivo de
     * propiedades o si los valores de la dirección IP o puerto son inválidos.
     *
     * @throws NumberFormatException Si el valor del puerto en el archivo de
     * propiedades no es un número válido.
     */
    public Client() {
        try {
            bundle = ResourceBundle.getBundle("business.conecction");
            this.serverIP = bundle.getString("serverIP");
            this.serverPort = Integer.parseInt(bundle.getString("serverPort"));
        } catch (MissingResourceException | NumberFormatException event) {
            LOGGER.log(Level.SEVERE, "Error al cargar la configuración: {0}", event.getMessage());
            throw new ServerNotFoundException(); // Lanza excepción personalizada si falla
        }
    }

    /**
     * Obtiene la instancia única del cliente. Si no existe, crea una nueva
     * instancia.
     *
     * @return La instancia de {@code Client}.
     */
    public static Client getInstance() {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

    /**
     * Establece una conexión con el servidor utilizando la dirección IP y el
     * puerto especificados. Crea los flujos de entrada y salida necesarios para
     * la comunicación con el servidor.
     *
     * @throws ServerNotFoundException si ocurre un error de conexión.
     */
    public void connect() {
        try {
            if (socket == null || socket.isClosed()) {
                socket = new Socket(serverIP, serverPort);
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());
                LOGGER.log(Level.INFO, "Conectado al servidor en {0}:{1}", new Object[]{serverIP, serverPort});
            }
        } catch (IOException event) {
            LOGGER.log(Level.SEVERE, "Error al abrir la conexión: {0}", event.getMessage());
            throw new ServerNotFoundException(); // Lanza excepción personalizada si falla
        }
    }

    /**
     * Cierra la conexión con el servidor, liberando los recursos asociados al
     * socket.
     */
    public void closeConnection() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                LOGGER.info("Conexión cerrada.");
            }
        } catch (IOException event) {
            LOGGER.log(Level.SEVERE, "Error al cerrar la conexión: {0}", event.getMessage());
        }
    }

    /**
     * Realiza una solicitud de registro para el usuario proporcionado.
     * Establece la conexión con el servidor, envía la solicitud y recibe la
     * respuesta.
     *
     * @param user El objeto {@code User} que contiene la información del
     * usuario a registrar.
     * @return Un objeto {@code Message} que representa la respuesta del
     * servidor.
     */
    @Override
    public Message signUp(User user) {
        Message response = null;
        try {
            connect(); // Conectar al servidor
            Message signUpRequest = new Message(MessageType.SIGN_UP_REQUEST, user); // Crear solicitud
            sendMessage(signUpRequest); // Enviar solicitud
            response = receiveMessage(); // Recibir respuesta
            return response; // Retornar respuesta
        } catch (ServerNotFoundException event) {
            return event.CreateMessage(); // Crear mensaje de error
        }
    }

    /**
     * Realiza una solicitud de inicio de sesión para el usuario proporcionado.
     * Establece la conexión con el servidor, envía la solicitud y recibe la
     * respuesta.
     *
     * @param user El objeto {@code User} que contiene la información del
     * usuario que intenta iniciar sesión.
     * @return Un objeto {@code Message} que representa la respuesta del
     * servidor.
     */
    @Override
    public Message signIn(User user) {
        Message response = null;
        try {
            connect(); // Conectar al servidor
            Message signInRequest = new Message(MessageType.SIGN_IN_REQUEST, user); // Crear solicitud
            sendMessage(signInRequest); // Enviar solicitud
            response = receiveMessage(); // Recibir respuesta
            return response; // Retornar respuesta
        } catch (ServerNotFoundException event) {
            return event.CreateMessage(); // Crear mensaje de error
        }
    }

    // Métodos privados
    /**
     * Envía un mensaje al servidor a través del flujo de salida.
     *
     * @param message El objeto {@code Message} que se enviará.
     */
    private void sendMessage(Message message) {
        try {
            outputStream.writeObject(message);
            outputStream.flush();
            LOGGER.log(Level.INFO, "Mensaje enviado al servidor: {0}", message);
        } catch (IOException event) {
            LOGGER.log(Level.SEVERE, "Error al enviar mensaje al servidor: {0}", event.getMessage());
        }
    }

    /**
     * Recibe un mensaje del servidor a través del flujo de entrada.
     *
     * @return Un objeto {@code Message} recibido del servidor.
     */
    private Message receiveMessage() {
        try {
            Message message = (Message) inputStream.readObject();
            LOGGER.log(Level.INFO, "Mensaje recibido del servidor: {0}", message);
            return message; // Retornar mensaje recibido
        } catch (IOException | ClassNotFoundException event) {
            LOGGER.log(Level.SEVERE, "Error al recibir mensaje del servidor: {0}", event.getMessage());
            return null; // Retornar null si hay un error
        }
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean verifyEmail(String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
