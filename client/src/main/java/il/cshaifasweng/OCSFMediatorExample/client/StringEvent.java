package il.cshaifasweng.OCSFMediatorExample.client;

public class StringEvent {
    private String message;

    public StringEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
