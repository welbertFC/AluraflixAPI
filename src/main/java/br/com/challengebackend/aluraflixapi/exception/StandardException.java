package br.com.challengebackend.aluraflixapi.exception;

import lombok.Data;

import java.io.Serializable;

@Data
public class StandardException<T> implements Serializable {

    private Integer status;
    private T message;
    private String erro;
    private long timeStamp;
    private String path;

    public StandardException(Integer status, T message, String erro, long timeStamp, String path) {
        this.status = status;
        this.message = message;
        this.erro = erro;
        this.timeStamp = timeStamp;
        this.path = path;
    }
}
