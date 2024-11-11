package edu.bbte.idde.bmim2214.dataaccess.exceptions;

public class CarExceptionDatabase extends Exception {
    public CarExceptionDatabase(String message) {
        super(message);
    }

    public CarExceptionDatabase(String message, Throwable cause) {
        super(message, cause);
    }
}
