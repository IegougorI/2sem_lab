package database;

import java.io.Serializable;
import java.net.InetAddress;
public class MessageForClient implements Serializable{
    private String message;
    private InetAddress address;
    private int port;

    public String getMessage(){
        return message;
    }

    public MessageForClient(String message) {
        this.message = message;
    }

    public MessageForClient(String message, InetAddress address, int port) {
        this.message = message;
        this.address = address;
        this.port = port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
