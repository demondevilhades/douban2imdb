package hades.douban2imdb.exception;

@SuppressWarnings("serial")
public class HttpForbiddenException extends RuntimeException {

    public HttpForbiddenException() {
        super();
    }

    public HttpForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public HttpForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpForbiddenException(String message) {
        super(message);
    }

    public HttpForbiddenException(Throwable cause) {
        super(cause);
    }
}
