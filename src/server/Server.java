package server;

import database.*;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.concurrent.*;

public class Server {
    private static final int SERVICE_PORT = 8000;

    public static void main(String[] args) throws IOException {
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        ExecutorService fixedPool = Executors.newFixedThreadPool(2);
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);

        SaxMyParser parser = new SaxMyParser();
        FlatCollection collection = new FlatCollection(parser.parse());
//        Console.getStartID(collection);

        ServerConsole serverConsole = new ServerConsole(collection, false);
        serverConsole.start();

        DatagramSocket socket = new DatagramSocket(SERVICE_PORT);


        Exchanger<CommandForServer> exchangerCommand = new Exchanger<>();
        Exchanger<MessageForClient> exchangerMessage = new Exchanger<>();

        fixedPool.submit(new ServerReading(socket, collection, exchangerCommand));
        cachedPool.submit(new ServerProcessing(socket, collection, exchangerCommand, exchangerMessage));
        forkJoinPool.invoke(new ServerWriting(socket, exchangerMessage));
    }
}
