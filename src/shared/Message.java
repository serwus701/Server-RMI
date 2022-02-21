package shared;

import java.sql.Timestamp;

public class Message {
    private final String message;
    private final String whoAdded;
    private Timestamp whenAdded;

    public Message(String myMessage, String myWhoAdded) {
        message = myMessage;
        whoAdded = myWhoAdded;
        whenAdded = new Timestamp(System.currentTimeMillis());
    }

    public void setWhenAdded(Timestamp myWhenAdded) {
        whenAdded = myWhenAdded;
    }

    public Timestamp getWhenAdded() {
        return whenAdded;
    }

    public String getWhoAdded() {
        return whoAdded;
    }

    public String getMessage() {
        return message;
    }
}
