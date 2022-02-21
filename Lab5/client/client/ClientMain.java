package client;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Scanner;

public class ClientMain {

    static boolean isNumber(String strToCheck) {
        try {
            Integer.parseInt(strToCheck);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException, NotBoundException {
        Client userThread = new Client();

        Scanner input = new Scanner(System.in);

        String strInput;

        do {

            System.out.println("insert port number");
            strInput = input.nextLine();
            //strInput = "6666";

        } while (!isNumber(strInput));

        int port = Integer.parseInt(strInput);

        System.out.println("insert ip");
        String ip = input.nextLine();
        //String ip = "localhost";

        userThread.startConnection(ip, port);
    }
}
