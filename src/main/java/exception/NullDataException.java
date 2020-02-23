package exception;

public class NullDataException extends RuntimeException {
    public NullDataException(String message) {
        super(message);
    }

    public NullDataException() {
        super();
    }
}
