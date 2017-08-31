package mk.edu.ukim.feit.bolt.api.models;

/**
 * Created by andrejnaumovski on 8/9/17.
 */
public class GenericResponse<T> {
    private int statusCode;
    private T message;

    public GenericResponse(int statusCode, T message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }
}
