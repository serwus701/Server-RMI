package server;

import shared.Message;
import shared.RMIClient;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Chat {

    //private final ArrayList<Message> usersMessages = new ArrayList<>();
    private final ArrayList<User> users = new ArrayList<>();
    private final Server server;
    boolean exit;

    public Chat(Server serv) {
        server = serv;
        exit = false;
    }

    public void addUser(String name, RMIClient client) {
        User temp = new User(name, client);
        users.add(temp);
    }

    public int getUsersSize() {
        return users.size();
    }

    private void sendMessages(Message messageToSendToAll) throws RemoteException {
        for (int i = 0; i < users.size(); i++) {
            users.get(i).sendMessage(messageToSendToAll);
        }
    }

    public void userExit(int i) {
        Timestamp tempTime = new Timestamp(System.currentTimeMillis());

        System.out.println("User " + users.get(i).getUsername() + " has left server at " + Server.getBetterTimeFormat(tempTime));
        server.getMyLogs().write("User " + users.get(i).getUsername() + " has left server at " + Server.getBetterTimeFormat(tempTime));

        server.usernameRemove(users.get(i).getUsername());
        users.remove(i);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void getMessage(String name, String command) throws RemoteException {
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getUsername().equals(name)){
                commandHandling(command, i);
            }
        }
    }

    public void commandHandling(String command, int i) throws RemoteException {

        String[] words = command.split(" ");

        switch (words[0]) {
            case "say" -> {
                command = command.replaceFirst("say ", "");
                Message myMessage = new Message(command, users.get(i).getUsername());

                //usersMessages.add(myMessage);
                System.out.println("[" + myMessage.getWhoAdded() + "] " + myMessage.getMessage() + " (" + Server.getBetterTimeFormat(myMessage.getWhenAdded()) + ")");

                server.getMyLogs().write("[" + myMessage.getWhoAdded() + "] " + myMessage.getMessage() + " (" + Server.getBetterTimeFormat(myMessage.getWhenAdded()) + ")");

                sendMessages(myMessage);
            }
            case "channels" -> {
                User temp = users.get(i);
                users.remove(i);

                boolean hasJoined = false;

                while (!hasJoined) {
                    //server.addToChannel(temp.getUsername());
                }

            }
            case "exit" -> userExit(i);
        }
    }

    void checkForInactiveUsers(){
        for (int i = 0; i < users.size(); i++) {
            if (!users.get(i).isConnected()) {
                userExit(i);
            }
        }
    }


}
