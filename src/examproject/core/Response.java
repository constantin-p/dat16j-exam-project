package examproject.core;

public class Response {
    public boolean status;
    public String info = "";

    public Response(boolean status) {
        this.status = status;
    }

    public Response(boolean status, String info) {
        this.status = status;
        this.info = info;
    }
}
