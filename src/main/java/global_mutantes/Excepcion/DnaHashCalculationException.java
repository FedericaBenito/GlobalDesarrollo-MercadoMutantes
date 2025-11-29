package global_mutantes.Excepcion;

public class DnaHashCalculationException extends RuntimeException{
    public DnaHashCalculationException(String message) {
        super(message);
    }

    public DnaHashCalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}