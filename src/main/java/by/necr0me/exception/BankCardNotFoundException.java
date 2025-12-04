package by.necr0me.exception;

public class BankCardNotFoundException extends RuntimeException {
    public BankCardNotFoundException(String message) {
        super(message);
    }
}
