package uy.com.banca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Excepcion que se lanza en caso que no se encuentra el
 * elemento buscado y se debe devolver a los clientes error HTTP 404
 *
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Not Found")
public class HttpNotFoundException extends GenericHTTPException {

    private static Logger log = Logger.getLogger(HttpNotFoundException.class.getName());


    /**
     * constructor por defecto
     */
    public HttpNotFoundException(){
        super(HttpStatus.NOT_FOUND, Level.WARNING);

    }

    /**
     * Sobrecarga de constructor que se le envia urn
     * asociado a la falla
     * @param urn urn asociada a la falla
     */
    public HttpNotFoundException(String urn){
        super(HttpStatus.NOT_FOUND, Level.WARNING,urn);
    }


}

