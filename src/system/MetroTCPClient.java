package system;

import java.io.IOException;
import java.text.ParseException;

import MetroTCP.MetroUserCard;
import MetroTCP.MetroUser;
import info.Sex;
import info.Operation;
import info.Service;
import info.RegForm;
import TCP.TCPClient;
import command.RegisterOperation;
import command.PayOperation;
import command.RefillOperation;
import command.GetBalanceOperation;
import command.GetUserInfoOperation;

public class MetroTCPClient implements Service {
    private final TCPClient client;
    private final Thread thread;

    public MetroTCPClient(TCPClient client) {
        this.client = client;
        this.thread = new Thread(this.client);
    }

    public MetroTCPClient(String host, int port) throws IOException {
        this.client = new TCPClient(host, port);
        this.thread = new Thread(this.client);
    }

    public void setOperation(Operation command) {
        this.client.setOperation(command);
    }

    public Thread getThread() {
        return thread;
    }

    public TCPClient getClient() {
        return this.client;
    }

    @Override
    public void startService() {
        thread.start();
    }

    @Override
    public void stopService() {
        this.thread.interrupt();
    }

    public static void main(String[] args) {
        try {
            MetroTCPClient service = null;

            // 1. Зареєструвати картку
            TCPClient client = new TCPClient("localhost", 9400);
            service = new MetroTCPClient(client);
            RegForm form = new MetroUserCard("112", new MetroUser("Viktor", "Barbinov", Sex.MAN, "01.01.2001"), "KhNU");
            Operation command = new RegisterOperation(form);
            System.out.println("Command: " + command);
            service.setOperation(command);
            service.startService();
            System.out.println("The client has been started");
            service.getThread().join();
            System.out.println(service.getClient().getResult().getStatus());
            System.out.println(service.getClient().getResult().getResult());
            System.out.println();


            // 2. Отримати залишок коштів на картці
            client = new TCPClient("localhost", 9400);
            service = new MetroTCPClient(client);
            command = new GetBalanceOperation("112");
            System.out.println("Command: " + command);
            service.setOperation(command);
            service.startService();
            System.out.println("The client has been started");
            service.getThread().join();
            System.out.println(service.getClient().getResult().getStatus());
            System.out.println(service.getClient().getResult().getResult());
            System.out.println();

            // 3. Спробувати оплатити поїздку (недостатньо грошей)
            client = new TCPClient("localhost", 9400);
            service = new MetroTCPClient(client);
            command = new PayOperation("112", 50);
            System.out.println("Command: " + command);
            service.setOperation(command);
            service.startService();
            System.out.println("The client has been started");
            service.getThread().join();
            System.out.println(service.getClient().getResult().getStatus());
            System.out.println(service.getClient().getResult().getResult());
            System.out.println();

            // 4. Поповнити рахунок
            client = new TCPClient("localhost", 9400);
            service = new MetroTCPClient(client);
            command = new RefillOperation("112", 100);
            System.out.println("Command: " + command);
            service.setOperation(command);
            service.startService();
            System.out.println("The client has been started");
            service.getThread().join();
            System.out.println(service.getClient().getResult().getStatus());
            System.out.println(service.getClient().getResult().getResult());
            System.out.println();

            // 5. Оплатити поїздку після поповнення рахунку
            client = new TCPClient("localhost", 9400);
            service = new MetroTCPClient(client);
            command = new PayOperation("112", 25);
            System.out.println("Command: " + command);
            service.setOperation(command);
            service.startService();
            System.out.println("The client has been started");
            service.getThread().join();
            System.out.println(service.getClient().getResult().getStatus());
            System.out.println(service.getClient().getResult().getResult());
            System.out.println();


            // 6. Отримати залишок коштів на картці
            client = new TCPClient("localhost", 9400);
            service = new MetroTCPClient(client);
            command = new GetBalanceOperation("112");
            System.out.println("Command: " + command);
            service.setOperation(command);
            service.startService();
            System.out.println("The client has been started");
            service.getThread().join();
            System.out.println(service.getClient().getResult().getStatus());
            System.out.println(service.getClient().getResult().getResult());
            System.out.println();

            // 7. Вивести інформацію про клієнта
            client = new TCPClient("localhost", 9400);
            service = new MetroTCPClient(client);
            command = new GetUserInfoOperation("112");
            System.out.println("Command: " + command);
            service.setOperation(command);
            service.startService();
            System.out.println("The client has been started");
            service.getThread().join();
            System.out.println(service.getClient().getResult().getStatus());
            System.out.println(service.getClient().getResult().getResult());
            System.out.println();

        } catch (ParseException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}