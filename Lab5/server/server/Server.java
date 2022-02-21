package server;

import shared.RMIClient;
import shared.RMIServer;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Server implements RMIServer {

    private final ArrayList<String> activeUsernames;
    private final ArrayList<Chat> allChannels;
    private final FileManagement myLogs;
    private Timestamp tempTime;
    private boolean exit;

    public Server() throws IOException {
        UnicastRemoteObject.exportObject(this, 0);
        activeUsernames = new ArrayList<>();
        allChannels = new ArrayList<>();
        myLogs = new FileManagement();
        exit = false;
    }

    public void start() {

        allChannels.add(new Chat(this));

        tempTime = new Timestamp(System.currentTimeMillis());

        System.out.println("Server started at " + getBetterTimeFormat(tempTime));
        myLogs.write("Server started at " + getBetterTimeFormat(tempTime));

        while (!exit) {
            for (int i = 0; i < allChannels.size(); i++) {
                allChannels.get(i).checkForInactiveUsers();
            }
        }
    }

    public void usernameRemove(String name) {
        for (int i = 0; i < activeUsernames.size(); i++) {
            if (name.equals(activeUsernames.get(i))) {
                activeUsernames.remove(i);
                return;
            }
        }
    }

    @Override
    public boolean addToChannel(String name, String channel, RMIClient client) {

        int channelToAdd;

        if (isNumber(channel))
            channelToAdd = Integer.parseInt(channel) - 1;
        else
            channelToAdd = -2;

        if ((channelToAdd < 0) || (channelToAdd > allChannels.size() - 1)) {

            if (channelToAdd == -1) {
                allChannels.add(new Chat(this));
                return false;
            }
        }

        if ((channelToAdd >= 0) && (channelToAdd <= allChannels.size() - 1)) {
            allChannels.get(channelToAdd).addUser(name, client);

            tempTime = new Timestamp(System.currentTimeMillis());

            System.out.println("User " + name + " has been added to channel " + (channelToAdd + 1) + " at " + getBetterTimeFormat(tempTime));
            myLogs.write("User " + name + " has been added to channel " + (channelToAdd + 1) + " at " + getBetterTimeFormat(tempTime));

            return true;
        } else return false;
    }

    public static String getBetterTimeFormat(Timestamp currTime) {
        return (currTime.getYear() + 1900) + "-" + (currTime.getMonth() + 1) + "-" + currTime.getDate() + " " + currTime.getHours() + ":" + currTime.getMinutes() + ":" + currTime.getSeconds();
    }

    @Override
    public void writeCommand(String username, String command) throws RemoteException {
        for (int i = 0; i < allChannels.size(); i++) {
            for (int j = 0; j < allChannels.get(i).getUsersSize(); j++) {
                if (allChannels.get(i).getUsers().get(j).getUsername().equals(username)) {
                    allChannels.get(i).getMessage(username, command);
                }
            }
        }
    }

    public FileManagement getMyLogs() {
        return myLogs;
    }

    private void stop() {

        exit = true;
        myLogs.close();
    }

    @Override
    public ArrayList<Integer> showChannels() {

        ArrayList<Integer> toReturn = new ArrayList<>();

        for (Chat eachChannel : allChannels
        ) {
            toReturn.add(eachChannel.getUsersSize());
        }
        return toReturn;
    }

    @Override
    public boolean addUser(String name) throws RemoteException {

        if (isNameOnList(name, activeUsernames)) {
            return false;
        }
        activeUsernames.add(name);

        tempTime = new Timestamp(System.currentTimeMillis());

        System.out.println("User " + name + " has joined at " + getBetterTimeFormat(tempTime));
        myLogs.write("User " + name + " has joined at " + getBetterTimeFormat(tempTime));

        return true;
    }

    private static boolean isNumber(String numberToCheck) {
        try {
            Integer.parseInt(numberToCheck);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private boolean isNameOnList(String name, ArrayList<String> list) {
        for (String eachName : list
        ) {
            if (eachName.equals(name))
                return true;
        }
        return false;
    }

}
