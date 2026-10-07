package customermanager;

public class ValidationException extends Exception {
    public enum Field { NAME, PROVINCE }

    private final Field field;

    public ValidationException(String message, Field field) {
        super(message);
        this.field = field;
    }

    public Field getField() {
        return field;
    }
}
