package client;

import shared.DataStringConverter;
import shared.Message;
import shared.RMIClient;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Client implements RMIClient {

    shared.RMIServer server;
    private final ArrayList<Message> visibleMessages;
    private boolean exit;
    private String username;

    public Client() throws RemoteException {
        UnicastRemoteObject.exportObject(this, 0);
        exit = false;
        visibleMessages = new ArrayList<>();
    }

    public void setUsername(String name) {
        username = name;
    }

    public void exitLoop() {
        exit = true;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void check(){
        //r u ok?
    }

    public void startConnection(String ip, int port) throws IOException, NotBoundException {

        Registry registry = LocateRegistry.getRegistry(ip, port);
        this.server = (shared.RMIServer) registry.lookup("server");

        ClientGui.welcomePanel(this);

        ClientGui.changeChannel(this);

        run();
    }

    ArrayList<Integer> refreshChannelsToJoin() throws RemoteException {
        return this.server.showChannels();
    }

    public void run() throws RemoteException {
        while (!exit)
            ClientGui.connectedMenu(this, visibleMessages);
    }

    public boolean getExit() {
        return exit;
    }

    @Override
    public void addMessage(String message) throws RemoteException {
        visibleMessages.add(DataStringConverter.stringToMessage(message));
        ClientGui.showMessages(visibleMessages);
    }
}
