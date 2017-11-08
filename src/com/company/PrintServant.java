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

    private int ink_cyan;
    private int ink_magenta;
    private int ink_yellow;
    private int paper_a4;
    private int paper_a3;
    boolean isRunning;

    private int nxtSalt = 1;

    List<String> queue = new ArrayList<>();

    protected PrintServant() throws RemoteException {
        super();
        ink_cyan = 100;
        ink_magenta = 100;
        ink_yellow = 100;
        paper_a3 = 100;
        paper_a4 = 100;
        isRunning = false;
    }

    @Override
    public void print(String username, String password, String filename, String printer) throws RemoteException {
        if (check(username, password) && isRunning){
            queue.add(filename + " : " + printer);
        }
    }

    @Override
    public String queue(String username, String password) throws RemoteException {
        if (check(username, password) && isRunning){
            String s = "";
            int i =0;
            while (i < queue.size()){
                s += (++i) + " : " + queue.get(i-1);
            }
            return s;
        }
        return null;
    }

    @Override
    public void topQueue(String username, String password, int job) throws RemoteException {
        if (check(username, password) && isRunning){
            return;
        }
    }

    @Override
    public void start(String username, String password) throws RemoteException {
        if (check(username, password)){
            isRunning = true;
        }
    }

    @Override
    public void stop(String username, String password) throws RemoteException {
        if (check(username, password)){
            isRunning = false;
        }
    }

    @Override
    public void restart(String username, String password) throws RemoteException {
        if (check(username, password)){
            return ;
        }
    }

    @Override
    public String status(String username, String password) throws RemoteException {
        if (check(username, password)){
            return "This printer is doing well.";
        }
        return null;
    }

    @Override
    public String readConfig(String username, String password, String parameter) throws RemoteException {
        if (check(username, password) && isRunning){
            switch (parameter){
                case "ink_cyan":
                    return "Printer has " + ink_cyan + "% cyan ink.";
                case "ink_magenta":
                    return "Printer has " + ink_magenta + "% magenta ink.";
                case "ink_yellow":
                    return "Printer has " + ink_yellow + "% yellow ink.";
                case "paper_A4":
                    return "Printer has " + paper_a4 + "% A4 paper.";
                case "paper_A3":
                    return "Printer has " + paper_a3 + "% A3 paper.";
                default:
                    return "Parameter not found";
            }

        }
        return null;
    }

    @Override
    public void setConfig(String username, String password, String parameter, String value) throws RemoteException {
        if (check(username, password) && isRunning){
            switch (parameter){
                case "ink_cyan":
                    ink_cyan = Integer.parseInt(value);
                    break;
                case "ink_magenta":
                    ink_magenta = Integer.parseInt(value);
                    break;
                case "ink_yellow":
                    ink_yellow = Integer.parseInt(value);
                    break;
                case "paper_A4":
                    paper_a4 = Integer.parseInt(value);
                    break;
                case "paper_A3":
                    paper_a3 = Integer.parseInt(value);
                    break;
                default:
                    break;
            }

        }
    }

    public boolean check(String username, String password) throws RemoteException {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            if (Files.lines(Paths.get("passwords.txt")).anyMatch(
                    str -> {
                        try {
                            String[] strlst = str.split(" ");
                            sha.update((password + strlst[0]).getBytes());
                            return (str.contains(username) && str.contains(new String(sha.digest(), "UTF-8")));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            return false;
                        }

                    }
            )){
                return  true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
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
                    str += " " + new String(sha.digest(), "UTF-8");
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
