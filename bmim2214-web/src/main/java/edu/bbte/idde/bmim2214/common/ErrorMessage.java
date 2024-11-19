package edu.bbte.idde.bmim2214.common;

public class ErrorMessage {

    private final String error;
    private final String message;

    public ErrorMessage(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
