package br.com.estevaohcs.internaltaskmanager.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {

    PENDENTE,
    EM_ANDAMENTO,
    CONCLUIDA;

    @JsonCreator
    public static Status fromValue(String value) {
        for (Status status : Status.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null;
    }

}
