package server;

import shared.DataStringConverter;
import shared.Message;
import shared.RMIClient;

import java.rmi.RemoteException;

public class User {
    private final String username;
    private final RMIClient rmiClient;

    public User(String myUsername, RMIClient client) {
        rmiClient = client;
        username = myUsername;
    }

    boolean isConnected() {
        try {
            rmiClient.check();
        } catch (RemoteException e) {
            return false;
        }
        return true;
    }

    public String getUsername() {
        return username;
    }

    public void sendMessage(Message message) throws RemoteException {

        try {
            rmiClient.addMessage(DataStringConverter.messageToString(message));
        } catch (RemoteException e) {

        }
    }
}
