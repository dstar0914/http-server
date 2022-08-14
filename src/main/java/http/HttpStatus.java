package http;

public enum HttpStatus {

    OK(200);

    private int code;

    public int getCode() {
        return code;
    }

    HttpStatus(int code) {
        this.code = code;
    }

}
