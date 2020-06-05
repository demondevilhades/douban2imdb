package hades.douban2imdb.exception;

@SuppressWarnings("serial")
public class GetResponesFailedException extends RuntimeException {

    public GetResponesFailedException() {
        super();
    }

    public GetResponesFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public GetResponesFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetResponesFailedException(String message) {
        super(message);
    }

    public GetResponesFailedException(Throwable cause) {
        super(cause);
    }
}
