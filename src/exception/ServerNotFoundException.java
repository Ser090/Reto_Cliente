package exception;

import utilidades.Message;
import utilidades.MessageType;

/**
 * La clase {@code ServerNotFoundException} es una excepción personalizada que
 * se lanza cuando no se puede establecer una conexión con el servidor. Extiende
 * {@code RuntimeException}, lo que significa que es una excepción no
 * verificada.
 *
 * Esta excepción incluye un método para crear un mensaje que se puede enviar al
 * cliente, informándole sobre el error relacionado con el servidor.
 *
 * @author Urko
 */
public class ServerNotFoundException extends RuntimeException {

    /**
     * Crea un mensaje que indica un error relacionado con el servidor.
     *
     * @return Un objeto {@code Message} que contiene el tipo de error
     * {@code SERVER_ERROR} y un contenido nulo.
     */
    public Message CreateMessage() {
        return new Message(MessageType.SERVER_ERROR, null); // Retorna mensaje de error del servidor
    }
}
