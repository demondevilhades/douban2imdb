package hades.douban2imdb.exception;

@SuppressWarnings("serial")
public class HttpErrorException extends RuntimeException {

    public HttpErrorException() {
        super();
    }

    public HttpErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public HttpErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpErrorException(String message) {
        super(message);
    }

    public HttpErrorException(Throwable cause) {
        super(cause);
    }
}
