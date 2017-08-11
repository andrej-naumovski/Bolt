package mk.edu.ukim.feit.bolt.api.models;

/**
 * Created by andrejnaumovski on 8/9/17.
 */
public class GenericResponse {
    private int statusCode;
    private String message;

    public GenericResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
