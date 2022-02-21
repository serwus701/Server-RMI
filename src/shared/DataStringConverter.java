package shared;

import java.sql.Timestamp;

public class DataStringConverter {

    public static String messageToString(Message message) {
        String toReturn = "";
        String messageToSend;
        String username;

        messageToSend = message.getMessage().replace("!", "");
        toReturn += messageToSend;
        toReturn += "!";

        username = message.getWhoAdded().replace("!", "");
        toReturn += username;
        toReturn += "!";

        toReturn += String.valueOf(message.getWhenAdded().getYear());
        toReturn += "-";
        toReturn += String.valueOf(message.getWhenAdded().getMonth());
        toReturn += "-";
        toReturn += String.valueOf(message.getWhenAdded().getDate());
        toReturn += "-";
        toReturn += String.valueOf(message.getWhenAdded().getHours());
        toReturn += "-";
        toReturn += String.valueOf(message.getWhenAdded().getMinutes());
        toReturn += "-";
        toReturn += String.valueOf(message.getWhenAdded().getSeconds());


        return toReturn;
    }

    public static Message stringToMessage(String serverOutput) {

        String[] messageAttributes = serverOutput.split("!");
        String messageText = messageAttributes[0];
        String author = messageAttributes[1];
        String[] timeAttributes = messageAttributes[2].split("-");

        Timestamp whenAdded = new Timestamp(System.currentTimeMillis());

        whenAdded.setYear(Integer.parseInt(timeAttributes[0]));
        whenAdded.setMonth(Integer.parseInt(timeAttributes[1]));
        whenAdded.setDate(Integer.parseInt(timeAttributes[2]));
        whenAdded.setHours(Integer.parseInt(timeAttributes[3]));
        whenAdded.setMinutes(Integer.parseInt(timeAttributes[4]));
        whenAdded.setSeconds(Integer.parseInt(timeAttributes[5]));

        Message messageToReturn = new Message(messageText, author);
        messageToReturn.setWhenAdded(whenAdded);

        return messageToReturn;
    }

    public static String getBetterTimeFormat(Timestamp currTime) {
        return (currTime.getYear() + 1900) + "-" + (currTime.getMonth() + 1) + "-" + currTime.getDate() + " " + currTime.getHours() + ":" + currTime.getMinutes() + ":" + currTime.getSeconds();
    }
}
