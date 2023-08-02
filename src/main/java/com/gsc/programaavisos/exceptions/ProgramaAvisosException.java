package com.gsc.programaavisos.exceptions;

public class ProgramaAvisosException extends RuntimeException {

    public ProgramaAvisosException(String s) {
        super(s);
    }

    public ProgramaAvisosException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
