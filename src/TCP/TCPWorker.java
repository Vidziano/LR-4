package TCP;

import info.RegUsers;
import info.Result;
import info.Operation;

import java.io.IOException;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TCPWorker implements Runnable {
    private final Socket client;
    private final RegUsers users;

    public TCPWorker(Socket client, RegUsers users) {
        this.client = client;
        this.users = users;
    }

    @Override
    public void run() {
        try (ObjectInputStream input = new ObjectInputStream(client.getInputStream());
             ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream())) {
            Operation operation = (Operation) input.readObject();
            Result result;
            synchronized (this.users) {
                result = operation.execute(this.users);
            }
            output.writeObject(result);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
