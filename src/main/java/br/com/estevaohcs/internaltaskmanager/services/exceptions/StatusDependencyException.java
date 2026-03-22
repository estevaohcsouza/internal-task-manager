package br.com.estevaohcs.internaltaskmanager.services.exceptions;

public class StatusDependencyException extends RuntimeException {

    public StatusDependencyException(String msg) {
        super(msg);
    }

}
