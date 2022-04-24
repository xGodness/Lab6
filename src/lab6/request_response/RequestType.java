package lab6.request_response;

import java.io.Serializable;

public enum RequestType implements Serializable {
    LOAD("Load"),
    CREATE("Create"),
    EXECUTE("Execute"),
    EXIT("Exit");

    private String label;

    RequestType(String requestType) {
         label = requestType;
    }

    public static RequestType valueOfLabel(String label) {
        for (RequestType e : values()) {
            if (e.label.equalsIgnoreCase(label)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return label;
    }

}
