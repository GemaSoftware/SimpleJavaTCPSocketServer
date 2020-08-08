package main;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //Starts up Server socket listener on separate thread.
        SocketListener tcpListener = new SocketListener();
        tcpListener.start();
        //Now listening to new connections

        //basic testing to broadcast message to all connected clients.
        System.out.println();
        Scanner scanner = new Scanner(System.in);

        //main loop
        while(true){
            String message = scanner.nextLine();
            tcpListener.broadcastMessage(message);
        }

    }

}
