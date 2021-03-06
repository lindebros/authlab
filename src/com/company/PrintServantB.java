package com.company;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;

public class PrintServantB extends UnicastRemoteObject implements PrintService {

    private int nxtSalt = 1;

    private String roles;
    private String subjects;

    PrintServantB( String roles, String subjects) throws RemoteException {
        super();
        this.roles = roles;
        this.subjects =subjects;
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

    }

    @Override
    public boolean verifyAccessControl(String username, String operation) throws RemoteException {
        try{
            return Files.lines(Paths.get(subjects)).anyMatch(
              str -> {
                  if (str.length() > 0){
                      String[] strList = str.split(" ");
                      if (strList[0].equals(username)){
                          try {
                              return Files.lines(Paths.get(roles)).anyMatch(
                                      str2->{
                                          if (str2.length() > 0){
                                              String[] strList2 = str2.split(" ");
                                              for(int i = 1;i<strList.length;i++) {
                                                  if (strList2[0].equals(strList[i])) {
                                                      for (String s : strList2) {
                                                          if (s.equals(operation)) {
                                                              return true;
                                                          }
                                                      }
                                                  }
                                              }
                                          }
                                          return false;
                                      }
                              );
                          } catch (IOException e) {
                              e.printStackTrace();
                          }
                      }
                  }
                  return  false;
              }
            );
        }catch (IOException e){
            e.printStackTrace();
        }

        return false;
    }


}
