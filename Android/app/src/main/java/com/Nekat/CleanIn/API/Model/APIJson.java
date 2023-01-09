package com.Nekat.CleanIn.API.Model;

public class APIJson<AnyType> {
    private int status;
    private String message;
    private AnyType data;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public AnyType getData() {
        return data;
    }
}
