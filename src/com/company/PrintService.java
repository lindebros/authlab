package com.company;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrintService extends Remote{

    void print(String username, String password, String filename, String printer) throws RemoteException;

    String queue(String username, String password) throws RemoteException;

    void topQueue(String username, String password, int job) throws RemoteException;

    void start(String username, String password) throws RemoteException;

    void stop(String username, String password) throws RemoteException;

    void restart(String username, String password) throws RemoteException;

    String status(String username, String password) throws RemoteException;

    String readConfig(String username, String password, String parameter) throws RemoteException;

    void setConfig(String username, String password, String parameter, String value) throws RemoteException;

    boolean check(String username, String password) throws RemoteException;

    void setAccount(String username, String password) throws RemoteException;
}
