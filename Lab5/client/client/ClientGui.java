package client;

import shared.Message;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientGui {

    public static void welcomePanel(Client client) throws RemoteException {
        boolean isConnected = false;

        System.out.println("Insert your username");

        while (!isConnected) {

            client.setUsername(getUserInput());

            isConnected = client.server.addUser(client.getUsername());
            if (!isConnected)
                System.out.println("your username is already taken. Come up with new username:");
        }

        System.out.println("successfully connected");
    }


    public static void changeChannel(Client client) throws RemoteException {

        client.server.showChannels();

        boolean isAddedToChannel = false;
        String tempChannel;

        while (!isAddedToChannel) {

            tempChannel = showChannels(client.refreshChannelsToJoin());
            isAddedToChannel = client.server.addToChannel(client.getUsername(), tempChannel, client);
        }

        System.out.println("successfully joined");
    }

    public static String showChannels(ArrayList<Integer> channelList) {

        for (int i = 0; i < channelList.size(); i++) {
            System.out.println((i + 1) + " Channel: (" + channelList.get(i) + ")");
        }
        System.out.println("Which channel do you want to join?");
        System.out.println("Insert 0 to add a new channel");

        return getUserInput();

    }

    public static void connectedMenu(Client client, ArrayList<Message> messagesToShow) throws RemoteException {

        while (!client.getExit()) {
            //showMessages(messagesToShow);
            System.out.println("Insert commands:");
            String input = getUserInput();

            String[] words = input.split(" ");

            switch (words[0]) {
                case "say" -> {
                    if (words.length > 1) {
                        System.out.println("przed say");
                        client.server.writeCommand(client.getUsername(), input);
                    }
                }
                case "exit" -> {
                    if (words.length == 1) {
                        client.server.writeCommand(client.getUsername(), input);
                        client.exitLoop();
                    }
                }
                /*
                case "channels" -> {
                    client.pauseReadingChannels();
                    client.sendMessage(input);
                    client.changeChannel(false);
                    client.pauseReadingChannels();
                }
                 */
                default -> System.out.println("incorrect command");
            }
        }
    }

    public static void showMessages(ArrayList<Message> messagesToShow) {
        //for (Message message : messagesToShow
        //) {
            System.out.println("[" + messagesToShow.get(messagesToShow.size()-1).getWhoAdded() + "] " + messagesToShow.get(messagesToShow.size()-1).getMessage() + " (" + messagesToShow.get(messagesToShow.size()-1).getWhenAdded() + ")");
        //}
    }

    public static String getUserInput() {
        Scanner myScanner = new Scanner(System.in);

        return myScanner.nextLine();
    }
}
