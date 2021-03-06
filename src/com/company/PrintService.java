package com.company;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrintService extends Remote{

    String print(String username, String password, String filename, String printer) throws RemoteException;

    String queue(String username, String password) throws RemoteException;

    String topQueue(String username, String password, int job) throws RemoteException;

    String start(String username, String password) throws RemoteException;

    String stop(String username, String password) throws RemoteException;

    String restart(String username, String password) throws RemoteException;

    String status(String username, String password) throws RemoteException;

    String readConfig(String username, String password, String parameter) throws RemoteException;

    String setConfig(String username, String password, String parameter, String value) throws RemoteException;

    boolean check(String username, String password) throws RemoteException;

    void setAccount(String username, String password) throws RemoteException;

    boolean verifyAccessControl(String username, String operation) throws  RemoteException;
}
