package TCP;

import MetroTCP.MetroUserBank;
import MetroTCP.MetroUserCard;
import MetroTCP.MetroUser;
import info.RegUsers;
import info.Sex;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;

public class TCPServer implements Runnable {
    private final RegUsers users;
    private final ServerSocket serverSocket;
    private boolean isStopped = false;

    private Thread runningThread =null;

    public TCPServer(RegUsers users, int serverPort) throws IOException {
        this.users = users;
        this.serverSocket = new ServerSocket(serverPort);
    }

    public void run() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }
        while (!isStopped()) {
            try {
                Socket clientSocket = this.serverSocket.accept();
                System.out.println("New client connection:" + clientSocket.getInetAddress());
                new Thread(new TCPWorker(clientSocket, users)).start();
            } catch (IOException e) {
                if (isStopped()) {
                    System.out.println("Server Stopped.");
                    return;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
        }
        System.out.println("Server Stopped.");
    }

    private synchronized boolean isStopped() { return this.isStopped; }

    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    public static void main(String[] args){
        try {
            RegUsers bank = new MetroUserBank();
            bank.register(new MetroUserCard("111",
                    new MetroUser("Vlad", "Kovaliov", Sex.MAN, "11.01.2001"), "KhNU"));
            System.out.println(bank);
            TCPServer server = new TCPServer(bank, 9400);
            new Thread(server).start();
            System.out.println("The server has been started");
            try {
                Thread.sleep(20 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Stopping Server");
            server.stop();
            System.out.println(bank);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}