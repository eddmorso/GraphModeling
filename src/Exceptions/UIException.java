package Exceptions;

public class UIException extends RuntimeException {

    private String message;

    public UIException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
