package com.company;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class PrintServant extends UnicastRemoteObject implements PrintService {

    private int nxtSalt = 1;

    protected PrintServant() throws RemoteException {
        super();
    }

    @Override
    public String print(String username, String password, String filename, String printer) throws RemoteException {
        if (check(username, password)){
            return "Printing " + filename + " from the printer " + printer +".";
        }
        return "Could not print document.";
    }

    @Override
    public String queue(String username, String password) throws RemoteException {
        if (check(username, password)){
            return "Printing queue.";
        }
        return "Could not print queue";
    }

    @Override
    public String topQueue(String username, String password, int job) throws RemoteException {
        if (check(username, password)){
            return "Moving job " + job + " to top of queue.";
        }
        return "Could not change queue.";
    }

    @Override
    public String start(String username, String password) throws RemoteException {
        if (check(username,password)){
            return "Starting printing service.";
        }
        return "Could not start printing service.";
    }

    @Override
    public String stop(String username, String password) throws RemoteException {
        if (check(username, password)){
            return "Stopping printing service.";
        }
        return "Could not stop printing service.";
    }

    @Override
    public String restart(String username, String password) throws RemoteException {
        if (check(username, password)){
            return "Restarting print server.";
        }
        return "Could not restart print server.";
    }

    @Override
    public String status(String username, String password) throws RemoteException {
        if (check(username, password)){
            return "This printer is doing well.";
        }
        return "Cannot tell status of print service.";
    }

    @Override
    public String readConfig(String username, String password, String parameter) throws RemoteException {
        if (check(username, password)){
            return "Returning configurations of print server.";
        }
        return "Could not return configurations.";
    }

    @Override
    public String setConfig(String username, String password, String parameter, String value) throws RemoteException {
        if (check(username, password)){
            return "Changing parameter " + parameter +" to " + value;
        }
        return "Could not change parameter " + parameter;
    }

    public boolean check(String username, String password) throws RemoteException {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            return (Files.lines(Paths.get("passwords.txt")).anyMatch(
                str -> {
                    if (!str.equals("")) {
                        String[] strlst = str.split(" ");
                        sha.update((password + strlst[0]).getBytes());
                        String s = null;

                        try {
                            s = new String(sha.digest(),"UTF-16");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        return (strlst[1].equals(username) && strlst[2].equals(s));
                    }
                    return false;
                }
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void setAccount(String username, String password) throws RemoteException {
        if (!check(username,password)){

            try {
                if (Files.lines(Paths.get("passwords.txt")).anyMatch(
                        str-> (str.contains(username))
                )){
                    return;
                }

                try {

                    String str = nxtSalt + " " + username;
                    MessageDigest sha = MessageDigest.getInstance("SHA-256");
                    sha.update((password + nxtSalt).getBytes());
                    str += " " + new String(sha.digest(), "UTF-16");
                    str = System.lineSeparator() + str;

                    Writer output;
                    output = new BufferedWriter(new FileWriter("passwords.txt",true));
                    output.append(str);
                    output.close();
                    nxtSalt++;


                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }



}
