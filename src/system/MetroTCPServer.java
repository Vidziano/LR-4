package system;

import java.io.IOException;
import java.text.ParseException;

import MetroTCP.MetroUserCard;
import MetroTCP.MetroUserBank;
import MetroTCP.MetroUser;
import info.Sex;
import info.Service;
import info.RegUsers;
import TCP.TCPServer;

public class MetroTCPServer implements Service {
    private final TCPServer server;
    private Thread thread = null;
    private final RegUsers users;

    public MetroTCPServer(TCPServer server, RegUsers users) {
        this.server = server;
        this.users = users;
    }

    public MetroTCPServer(RegUsers users, int serverPort) throws IOException {
        this.server = new TCPServer(users, serverPort);
        this.users = users;
    }

    public TCPServer getServer() {
        return server;
    }

    @Override
    public void startService() {
        this.thread = new Thread(server);
        this.thread.start();
    }

    @Override
    public void stopService() {
        this.server.stop();
    }

    public static void main(String[] args) {
        try {
            RegUsers bank = new MetroUserBank();
            bank.register(new MetroUserCard("111",
                    new MetroUser("Vlad", "Kovaliov", Sex.MAN, "11.01.2001"), "KhNU"));
            System.out.println(bank);
            TCPServer server = new TCPServer(bank, 9400);
            Service service = new MetroTCPServer(server, bank);
            service.startService();
            System.out.println("The server has been started");
            try (java.util.Scanner in = new java.util.Scanner(System.in);) {
                System.out.println("\tType stop to finish the server work");
                String answer = "go";
                while (!answer.equalsIgnoreCase("stop")) {
                    answer = in.next();
                }
            }
            service.stopService();
            System.out.println("The server has been stopped");
            System.out.println(bank);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}
