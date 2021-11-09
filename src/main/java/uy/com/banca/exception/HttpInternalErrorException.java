package uy.com.banca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Excepcion que se lanza en caso que que los parametros esten mal HTTP 400
 *
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "Bad Request")
public class HttpInternalErrorException extends GenericHTTPException {

    private static Logger log = Logger.getLogger(HttpInternalErrorException.class.getName());


    String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * constructor por defecto
     */
    public HttpInternalErrorException(){
        super(HttpStatus.INTERNAL_SERVER_ERROR, Level.SEVERE);
    }

    /**
     * Sobrecarga de constructor que se le envia texto asociado a la falla
     * @param message para mostrar en la excepcion
     */
    public HttpInternalErrorException(String message){
        super(HttpStatus.INTERNAL_SERVER_ERROR, Level.SEVERE);
        setClientMessage(message);
    }


}

