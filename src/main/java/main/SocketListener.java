package main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class SocketListener extends Thread{

    public static HashMap<Socket, ClientListener> connectedUsers = new HashMap<>();

    public void run() {
        try {
            //Sets up tcp socket listener on localhost @ port 1234
            ServerSocket listener = new ServerSocket(1234, 10, InetAddress.getLocalHost());
            System.out.println("Server now listening at " + listener.getLocalSocketAddress());

            //Continues to listen for new connections and sets them up accordingly..
            while(true){
                Socket connectedUser = listener.accept();
                System.out.println("User connected from " + connectedUser.toString());

                //Start listening to users connected on new thread. Map connected client socket
                //and listening thread to hashmap
                ClientListener clientListener = new ClientListener(connectedUser);
                connectedUsers.put(connectedUser, clientListener);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //sends a message to all connected clients.
    public void broadcastMessage(String message){
        connectedUsers.forEach((sock, client) -> {
            client.getTcpOutput().println(message);
        });
    }



}
