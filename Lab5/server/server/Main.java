package server;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Main {

    static boolean isNumber(String strToCheck) {
        try {
            Integer.parseInt(strToCheck);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException, AlreadyBoundException {

        Server server = new Server();

        Scanner input = new Scanner(System.in);

        String strInput;

        do {

            System.out.println("insert port number");
            strInput = input.nextLine();
            //strInput = "6666";

        } while (!isNumber(strInput));

        Registry reg = LocateRegistry.createRegistry(Integer.parseInt(strInput));
        reg.bind("server", server);

        server.start();

    }
}
