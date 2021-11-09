package uy.com.banca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Map<String, String> showBadRequestMessage(HttpInternalErrorException e) {

        Map<String, String> response = new HashMap<>();
        String message = e.getClientMessage();
        response.put("status", "400");
        response.put("error", "Bad Request");
        response.put("message", message);
        return response;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    Map<String, String> showNotFoundMessage(HttpNotFoundException e) {

        Map<String, String> response = new HashMap<>();

        response.put("status", "404");
        response.put("error", "Not found");
        response.put("message", e.getClientMessage());
        return response;
    }
}
