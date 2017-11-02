package com.company;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrintService extends Remote{

    String echo(String input) throws RemoteException;

    String print(String filename, String printer) throws RemoteException;

    String queue() throws RemoteException;

    void topQueue(int job) throws RemoteException;

    void start() throws RemoteException;

    void stop() throws RemoteException;

    void restart() throws RemoteException;

    String status() throws RemoteException;

    String readConfig(String parameter) throws RemoteException;

    void setConfig(String parameter, String value) throws RemoteException;

    boolean check(String username, String password);
}
