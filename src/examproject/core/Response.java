package examproject.core;

public class Response {

    public boolean success;
    public String info = "";

    public Response(boolean success) {
        this.success = success;
    }

    public Response(boolean success, String info) {
        this.success = success;
        this.info = info;
    }
}
