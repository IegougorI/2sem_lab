package client;

import java.net.*;
import java.io.*;
import java.util.*;

import database.CommandForServer;
import database.Flat;
import database.Console;
import database.MessageForClient;
import server.User;

import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;


public class Client {
    private ByteBuffer byteBuffer = ByteBuffer.allocate(16384);
    private int port;
    private String host;
    private static Scanner scanner = new Scanner(System.in, "UTF-8");
    private DatagramChannel clientChannel;
    private Selector selector;
    private String login;
    private ArrayList<String> history = new ArrayList<String>();

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            clientChannel = DatagramChannel.open();
            clientChannel.connect(new InetSocketAddress("127.0.0.1", this.port));
            clientChannel.configureBlocking(false);
            selector = Selector.open();
            clientChannel.register(selector, SelectionKey.OP_WRITE);
        } catch (IOException e) {e.printStackTrace();}

    }
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 8000);
        client.run();
    }
    public void run() {
        try {
            String login;
            String password;
            User user;
            System.out.println("You need to log in");
            while (true) {
                while (true){
                    System.out.println("Enter login: ");
                    login = scanner.nextLine();
                    System.out.println("Enter password");
                    password = scanner.nextLine();
                    if (!login.isEmpty() && !password.isEmpty()) break;
                    if (password.isEmpty() && !login.isEmpty()) {
                        System.out.println("password must be not empty. Try again");
                    }
                    if (login.isEmpty() && !password.isEmpty()){
                        System.out.println("login must be not empty. Try again");
                    }
                    if (login.isEmpty() && password.isEmpty()){
                        System.out.println("login and password must be not empty. Try again");
                    }

                }

                user = new User(login, password);
                send(new CommandForServer<User>("add_user ", user));
                String check = receive().getMessage();
                System.out.println(check);
                if ((check.equals("User signed in successfully")) || (check.equals("User was signed"))) break;
                else {
                    System.out.println("Try again");
                }
            }


            String[] commands;
            while (true) {
                try {
                    System.out.println("Enter command");
                    commands = scanner.nextLine().trim().split(" ");
                    history.add(commands[0]);
                    if (history.size() > 6) {

                        for (byte i = 1; i <= 5; ) {
                            history.set(i - 1, history.get(i));
                            i++;
                        }
                        history.remove(5);
                    }

                    switch (commands[0]) {
                        case "help":
                        case "info":
                        case "show":
                        case "clear":
                        case "print_descending":
                            send(new CommandForServer<>(commands[0], ""));
                            System.out.println(receive().getMessage());
                            break;
                        case "exit":
                            System.out.println("Disconnecting...");
                            clientChannel.close();
                            System.exit(0);
                            break;
                        case "insert":
                        case "update":
                            send(new CommandForServer<Flat>(commands[0] + " " + commands[1] + " " + login, Console.getElement(scanner)));
                            System.out.println(receive().getMessage());
                            break;
                        case "remove":
                        case "remove_lower_id":
                        case "execute_script":
                        case "remove_greater_id":
                            send(new CommandForServer<>(commands[0] + " " + commands[1] + " " + login, ""));
                            System.out.println(receive().getMessage());
                            break;
                        case "count_greater_than_number_of_rooms":
                        case "filter_less_than_view":
                            send(new CommandForServer<>(commands[0] + " " + commands[1], ""));
                            System.out.println(receive().getMessage());
                            break;
                        case "history":
                            System.out.println(history.toString());
                            break;
                        default:
                            System.out.println("There is no command: " + commands[0] + "\nUse 'help' to see all commands");
                            break;
                    }
                }catch(ArrayIndexOutOfBoundsException e) {
                    System.out.println("Incorrect input. Try again.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void send(CommandForServer<?> command) throws IOException {
        byteBuffer.put(serialize(command));
        byteBuffer.flip();
        DatagramChannel channel = null;
        while (channel == null) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                if (key.isWritable()) {
                    channel = (DatagramChannel) key.channel();
                    channel.write(byteBuffer);
                    channel.register(selector, SelectionKey.OP_READ);
                    break;
                }
            }
        }
        byteBuffer.clear();
    }

    private MessageForClient receive() throws IOException, ClassNotFoundException {
        DatagramChannel channel = null;
        while (channel == null) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                if (key.isReadable()) {
                    channel = (DatagramChannel) key.channel();
                    channel.read(byteBuffer);
                    byteBuffer.flip();
                    channel.register(selector, SelectionKey.OP_WRITE);
                    break;
                }
            }
        }
        return deserialize();
    }

    private byte[] serialize(CommandForServer<?> command) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(command);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }

    private MessageForClient deserialize() throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        MessageForClient message = (MessageForClient) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        byteBuffer.clear();
        return message;
    }


}


