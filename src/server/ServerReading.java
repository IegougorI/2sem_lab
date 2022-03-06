package server;

import java.io.*;
import java.net.*;
import database.*;
import java.nio.ByteBuffer;
import java.util.concurrent.Exchanger;

public class ServerReading implements Runnable{
    private DatagramSocket socket;
    private Exchanger<CommandForServer> exchangerCommand;
    private InetAddress address;
    private FlatCollection collection;
    private int port;


    public ServerReading(DatagramSocket socket, FlatCollection collection, Exchanger<CommandForServer> exchangerCommand) {
        this.exchangerCommand = exchangerCommand;
        this.collection = collection;
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true){
            MessageForClient messageForClient = new MessageForClient("");
            ByteBuffer buffer = ByteBuffer.allocate(65536);

            try {

                CommandForServer command = getCommand();
                exchangerCommand.exchange(command);
                System.out.println(command.getCommandName());


            } catch (Exception e) {e.printStackTrace(); break;}
        }
    }
    private CommandForServer deserialize(DatagramPacket getPacket, byte[] buffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(getPacket.getData());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        CommandForServer command = (CommandForServer) objectInputStream.readObject();
        command.setAddress(getPacket.getAddress());
        command.setPort(getPacket.getPort());
        byteArrayInputStream.close();
        objectInputStream.close();
        return command;
    }

    private CommandForServer getCommand() throws IOException, ClassNotFoundException {
        byte[] getBuffer = new byte[socket.getReceiveBufferSize()];
        DatagramPacket getPacket = new DatagramPacket(getBuffer, getBuffer.length);
        socket.receive(getPacket);
        address = getPacket.getAddress();
        port = getPacket.getPort();
        return deserialize(getPacket, getBuffer);
    }
}
