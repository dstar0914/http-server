package http;

public enum HttpStatus {

    OK(200),
    FOUND(302);

    private int code;

    public int getCode() {
        return code;
    }

    HttpStatus(int code) {
        this.code = code;
    }

}
