package TCP;


import info.Result;
import info.Operation;
import command.RegisterOperation;
import MetroTCP.MetroUserCard;
import MetroTCP.MetroUser;
import info.Sex;
import info.RegForm;



import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.io.IOException;


public class TCPClient implements Runnable {
    private final Socket client;
    private Operation command;
    private Result result;

    public TCPClient(String host, int port) throws IOException {
        this.client = new Socket(host, port);
    }

    public Result getResult() {
        return result;
    }

    public void setOperation(Operation command) {
        this.command = command;
    }


    @Override
    public void run() {
        try {
            System.out.println("Connected to server\n");

            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            out.writeObject(command);
            out.flush();
            System.out.println("Submitted a command for execution\n");
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            result = (Result) in.readObject();
            System.out.println("Result:\n " + result);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Problem in client run", e);
        } finally {
            try {
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
            }
        }
    }


    public static void main(String[] args) {
        try {
            TCPClient client = new TCPClient("localhost", 9400);
            RegForm form = new MetroUserCard("112",
                    new MetroUser("Viktor", "Barbinov", Sex.MAN, "01.01.2001"), "KhNU");
            Operation command = new RegisterOperation(form);
            System.out.println("Command: " + command);
            client.setOperation(command);
            Thread thread = new Thread(client);
            thread.start();
            System.out.println("The client has been started");
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The client fihished his work");
            System.out.println("Status: " + client.getResult().getStatus());
            System.out.println("Result: " + client.getResult().getResult());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}