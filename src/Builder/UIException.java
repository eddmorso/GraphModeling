package Builder;

public class UIException extends RuntimeException {

    String message;

    public UIException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
