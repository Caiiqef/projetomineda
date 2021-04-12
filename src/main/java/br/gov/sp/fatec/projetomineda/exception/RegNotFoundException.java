package br.gov.sp.fatec.projetomineda.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RegNotFoundException extends RuntimeException {

    private static final Long serialVersionUID = 3298964660511939080L;

    public RegNotFoundException(){
        super();
    }
    
    public RegNotFoundException(String message){
        super(message);
    }

    public RegNotFoundException(Throwable cause){
        super(cause);
    }

    public RegNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}