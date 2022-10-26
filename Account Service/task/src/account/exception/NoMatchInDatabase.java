package account.exception;

public class NoMatchInDatabase extends RuntimeException {

    public NoMatchInDatabase(String message) {
        super(message);
    }
}
