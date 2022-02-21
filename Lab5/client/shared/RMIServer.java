package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIServer extends Remote {


    boolean addUser(String name) throws RemoteException;

    boolean addToChannel(String name, String channel, RMIClient client) throws RemoteException;

    void writeCommand(String username, String command) throws RemoteException;

    ArrayList<Integer> showChannels() throws RemoteException;

}