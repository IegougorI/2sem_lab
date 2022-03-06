package server;

import java.io.*;
import java.net.InetAddress;
import java.util.Scanner;

public class User implements Serializable {
    private String username;
    private String password;
    private InetAddress address;
    private int port;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String addNewUser(User user) throws IOException {
        String login;
        String password;

        FileReader fr = new FileReader("User");
        Scanner scanner = new Scanner(fr);


        while (scanner.hasNextLine()){
            String[] x = scanner.nextLine().split(" ");
            login = x[0];
            password = x[1];
            if (login.equals(user.getUsername()) && password.equals(user.getPassword())){
                return "User signed in successfully";
            }
            if (login.equals(user.getUsername()) && !password.equals(user.getPassword())){
                return "Incorrect password";
            }
        }
        FileWriter fw = new FileWriter("User", true);
        fw.write(user.getUsername() + " " + user.getPassword()  + "\n");
        fw.flush();
        fw.close();
        fr.close();
        return "User was signed";
    }
}
