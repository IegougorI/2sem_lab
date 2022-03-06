package database;

import java.io.Serializable;
import java.net.InetAddress;

public class CommandForServer<A> implements Serializable {
    private String commandName;
    private A argument;
    private InetAddress address;
    private int port;

    public CommandForServer(String commandName, A argument){
        this.commandName = commandName;
        this.argument = argument;
    }

    public CommandForServer(String commandName, A argument, InetAddress address, int port) {
        this.commandName = commandName;
        this.argument = argument;
        this.port = port;
        this.address = address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public String getCommandName() {
        return commandName;
    }

    public A getArgument() {
        return argument;
    }

    public void setArgument(A argument) {
        this.argument = argument;
    }
}