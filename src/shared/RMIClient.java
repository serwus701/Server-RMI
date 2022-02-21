package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClient extends Remote {
    void addMessage(String message) throws RemoteException;
    void check() throws RemoteException;
}
