package com.example.practicacompleta.exceptions;

public class BusyEmailException extends RuntimeException {

    public BusyEmailException(String message) {
        super(message);
    }
}
