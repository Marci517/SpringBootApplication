package edu.bbte.idde.bmim2214.dataaccess.exceptions;

public class CarExceptionNoId extends Exception {
    public CarExceptionNoId(String message) {
        super(message);
    }

    public CarExceptionNoId(String message, Throwable cause) {
        super(message, cause);
    }
}
