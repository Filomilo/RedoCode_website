package com.redocode.backend.Excpetions;

import lombok.AllArgsConstructor;
import lombok.Getter;


public class RequestHadndlingException extends Exception{
    public RequestHadndlingException(String message)
    {
        super(message);
    }
}
