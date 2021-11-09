package uy.com.banca.exception;

import org.springframework.http.HttpStatus;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manejo de excepciones genérico
 */
public abstract class GenericHTTPException extends RuntimeException {

    private static Logger log = Logger.getLogger(GenericHTTPException.class.getName());

    /**
     * codigo de excepcion utilizado
     */
    protected HttpStatus httpStatus;

    /**
     * nivel de log que se quiere utilizar
     */
    protected Level logLevel;

    /**
     * error para mostrarle al cliente
     */
    protected String clientMessage;


    /**
     * @param httpStatus codigo http
     * @param level      nivel de log
     */
    public GenericHTTPException(HttpStatus httpStatus, Level level) {
        super();
        this.httpStatus = httpStatus;
        this.logLevel = level;
        String message = "status=" + httpStatus.value() + " reason_phrase=\"" + httpStatus.getReasonPhrase() + "\"" + new Date();
        this.clientMessage = message;
        log.log(logLevel, message);
    }

    /**
     * @param httpStatus codigo http
     * @param level      nivel de log
     * @param msg        mensaje a mostrar
     */
    public GenericHTTPException(HttpStatus httpStatus, Level level, String msg) {
        super();
        this.httpStatus = httpStatus;
        this.logLevel = level;
        String message = "status=" + httpStatus.value() + " reason_phrase=\"" + httpStatus.getReasonPhrase() + "\" msg=\"" + msg + "\"" +  new Date();
        this.clientMessage = message;
        log.log(logLevel, message);
    }

    /**
     * @param httpStatus codigo http
     * @param level      nivel de log
     * @param msg        mensaje a mostrar
     * @param urnCatalog urn del catalogo
     */
    public GenericHTTPException(HttpStatus httpStatus, Level level, String msg, String urnCatalog) {
        super();
        this.httpStatus = httpStatus;
        this.logLevel = level;
        String message = "status=" + httpStatus.value() + " reason_phrase=\"" + httpStatus.getReasonPhrase() + "\" " +
            "msg=\"" + msg + "\" urnCatalog=\"" + urnCatalog + "\"" +  new Date();
        this.clientMessage = message;
        log.log(Level.SEVERE, message);
    }

    /**
     * @param httpStatus codigo http
     * @param level      nivel de log
     * @param msg        mensaje a mostrar
     * @param urnCatalog urn del catalogo
     * @param urnOffer   urn de la oferta
     */
    public GenericHTTPException(HttpStatus httpStatus, Level level, String msg, String urnCatalog, String urnOffer) {
        super();
        this.httpStatus = httpStatus;
        this.logLevel = level;
        String message = "status=" + httpStatus.value() + " reason_phrase=\"" + httpStatus.getReasonPhrase() + "\" " +
            "msg=\"" + msg + "\" urnCatalog=\"" + urnCatalog + "\" urnOffer=\"" + urnOffer + "\"" +  new Date();
        this.clientMessage = message;
        log.log(Level.SEVERE, message);
    }


    /**
     * Constructor que recibe la operacion y datos de la excepcion
     *
     * @param operation operacion que ocurrió la excepción
     * @param cause     causa de la excepción
     * @param trace     traza de la excepción
     */
    public GenericHTTPException(String operation, String cause, String trace) {
        super();
        String message = "operation=" + "\"" + operation + "\"" + " cause=" + "\"" + cause + "\"" + " trace=" + "\"" + trace + "\"" +  new Date();
        this.clientMessage = message;
        log.info(message);
    }

    public String getClientMessage() {
        return clientMessage;
    }

    public void setClientMessage(String clientMessage) {
        this.clientMessage = clientMessage;
    }
}
