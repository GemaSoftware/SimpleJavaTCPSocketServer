package main;

import java.io.*;
import java.net.Socket;

public class ClientListener extends Thread{

    private final Socket currentSocket;

    private BufferedReader tcpInput;
    private PrintWriter tcpOutput;

    //initialzer for the client listener object. Sets up the bufferedreader and printwriter
    //using the socket passed.
    public ClientListener(Socket user){
        this.currentSocket = user;

        try{
            //Sets up the buffered reader to read input from tcp socket.
            this.tcpInput = new BufferedReader(new InputStreamReader(user.getInputStream()));
            //Sets up a printwriter that allows us to send data to tcp socket w/o converting to bytes.
            this.tcpOutput = new PrintWriter(user.getOutputStream(), true);
        } catch (IOException e){
            e.printStackTrace();
        }
        //starts thread.
        this.start();
    }

    //when thread starts, will listen to client connected via socket.
    public void run(){
        try{
            //client listener.
            while (true) {
                //reads line from client on tcp socket.
                String data = tcpInput.readLine();
                if (data != null) {
                    //Could add custom functionality here depending on what user sends.
                    System.out.println("User said: "  + data);
                    tcpOutput.println(data);
                    System.out.flush();
                } else {
                    //If user disconnects, brought here. Removes from hashmap, and listening thread terminates.
                    System.out.println("User Disconnected.");
                    SocketListener.connectedUsers.remove(currentSocket);
                    System.out.flush();
                    break;
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public PrintWriter getTcpOutput() {
        return tcpOutput;
    }

}
