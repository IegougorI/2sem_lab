package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.Exchanger;
import database.*;
import database.MessageForClient;

public class ServerProcessing implements Runnable{
    private FlatCollection collection;
    private DatagramSocket socket;
    private Exchanger<CommandForServer> exchangerCommand;
    private Exchanger<MessageForClient> exchangerMessage;
    private InetAddress address;
    private int port;
    private MessageForClient message;
    
    public ServerProcessing(DatagramSocket socket, FlatCollection collection, Exchanger<CommandForServer> exchangerCommand, Exchanger<MessageForClient> exchangerMessage) {
        this.socket = socket;
        this.collection= collection;
        this.exchangerCommand = exchangerCommand;
        this.exchangerMessage = exchangerMessage;
        
    }

    @Override
    public void run() {
        long id;
        Flat flat;
        User user;
        String check;
        String key;
        while(true) {
            try {
                String owner;
                CommandForServer command = exchangerCommand.exchange(null);
                address = command.getAddress();
                port = command.getPort();
                switch (command.getCommandName().split(" ")[0]) {
                    case "add_user":
                        user = (User) command.getArgument();
                        message = new MessageForClient(user.addNewUser(user), address, port);
                        break;
                    case "help":
                        message = new MessageForClient("All commands : " + Commands.show(),address,port);
                        break;
                    case "info":
                        message = new MessageForClient(collection.info(), address, port);
                        break;
                    case "show":
                        message = new MessageForClient(collection.show(), address, port);
                        break;
                    case "print_descending":
                        message = new MessageForClient(collection.print_descending(), address, port);
                        break;
                    case "insert":
                        Console.getStartID(collection);
                        flat=(Flat)command.getArgument();
                        owner=command.getCommandName().split(" ")[2];
                        flat.setOwner(owner);
                        message = new MessageForClient(collection.insert(
                                command.getCommandName().split(" ")[1],
                                (Flat)command.getArgument()), address, port);

                        break;
                    case "update":
                        flat=(Flat)command.getArgument();
                        owner=command.getCommandName().split(" ")[2];
                        flat.setOwner(owner);
                        message = new MessageForClient(collection.update(
                                command.getCommandName().split(" ")[1],
                                (Flat)command.getArgument(), owner), address, port);
                        break;
                    case "remove":
                        owner = command.getCommandName().split(" ")[2];
                        message = new MessageForClient(collection.remove(
                                command.getCommandName().split(" ")[1], owner), address, port);
                        break;
                    case "execute_script":
                        Console.getStartIDforExecute(collection);
                        owner = command.getCommandName().split(" ")[2];
                        message = new MessageForClient(Console.executeFile(
                                command.getCommandName().split(" ")[1], collection, owner), address, port);

                        break;
                    case "remove_greater_id":
                        owner = command.getCommandName().split(" ")[2];
                        message = new MessageForClient(collection.remove_greater_key(
                                command.getCommandName().split(" ")[1], owner), address, port);
                        break;
                    case "remove_lower_id":
                        owner = command.getCommandName().split(" ")[2];
                        message = new MessageForClient(collection.remove_lower_key(
                                command.getCommandName().split(" ")[1], owner), address, port);
                        break;
                    case "count_greater_than_number_of_rooms":
                        message = new MessageForClient(collection.count_greater_than_number_of_rooms(
                                command.getCommandName().split(" ")[1]), address, port);
                        break;
                    case "filter_less_than_view":
                        message = new MessageForClient(collection.filter_less_than_view(
                                command.getCommandName().split(" ")[1]), address, port);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + command.getCommandName().split(" ")[0]);
                }
                System.out.println(message);
                exchangerMessage.exchange(message);

            } catch (Exception e) {e.printStackTrace();}
        }




    }
    private byte[] serialize(MessageForClient message) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(message);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }
    private void send(MessageForClient message) throws IOException {
        byte[] sendBuffer = serialize(message);
        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, port);
        socket.send(sendPacket);
    }
}
