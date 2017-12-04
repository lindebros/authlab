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
import java.util.stream.Stream;

public class PrintServantA extends UnicastRemoteObject implements PrintService {

    private int nxtSalt = 1;


    PrintServantA() throws RemoteException {
        super();
    }

    @Override
    public String print(String username, String password, String filename, String printer) throws RemoteException {
        if (check(username, password) && verifyAccessControl(username, "print")){
            return "ALLOWED";
        }
        return "DENIED";
    }

    @Override
    public String queue(String username, String password) throws RemoteException {
        if (check(username, password) && verifyAccessControl(username, "queue")){
            return "ALLOWED";
        }
        return "DENIED";
    }

    @Override
    public String topQueue(String username, String password, int job) throws RemoteException {
        if (check(username, password) && verifyAccessControl(username, "topQueue")){
            return "ALLOWED";
        }
        return "DENIED";
    }

    @Override
    public String start(String username, String password) throws RemoteException {
        if (check(username, password) && verifyAccessControl(username, "start")){
            return "ALLOWED";
        }
        return "DENIED";
    }

    @Override
    public String stop(String username, String password) throws RemoteException {
        if (check(username, password) && verifyAccessControl(username, "stop")){
            return "ALLOWED";
        }
        return "DENIED";
    }

    @Override
    public String restart(String username, String password) throws RemoteException {
        if (check(username, password) && verifyAccessControl(username, "restart")){
            return "ALLOWED";
        }
        return "DENIED";
    }

    @Override
    public String status(String username, String password) throws RemoteException {
        if (check(username, password) && verifyAccessControl(username, "status")){
            return "ALLOWED";
        }
        return "DENIED";
    }

    @Override
    public String readConfig(String username, String password, String parameter) throws RemoteException {
        if (check(username, password) && verifyAccessControl(username, "readConfig")){
            return "ALLOWED";
        }
        return "DENIED";
    }

    @Override
    public String setConfig(String username, String password, String parameter, String value) throws RemoteException {
        if (check(username, password) && verifyAccessControl(username, "setConfig")){
            return "ALLOWED";
        }
        return "DENIED";
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
        if (!check(username, password)) {

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
                    str = str +System.lineSeparator();

                    Writer output;
                    output = new BufferedWriter(new FileWriter("passwords.txt",true));
                    output.append(str);
                    output.close();
                    nxtSalt++;


                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean verifyAccessControl(String username, String operation) throws RemoteException {
        try {
            return (Files.lines(Paths.get("access_control_list.txt")).anyMatch(
               str -> {
                   if (str.length() > 0){
                       String[] strList = str.split(" ");
                       if (strList[0].equals(username)){
                           for (String s : strList){
                               if (s.equals(operation)){
                                   return true;
                               }
                           }
                       }
                   }
                   return false;
               }
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
