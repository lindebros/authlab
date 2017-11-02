package com.company;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrintServant extends UnicastRemoteObject implements PrintService {

    protected PrintServant() throws RemoteException {
        super();
    }

    @Override
    public String print(String username, String password, String filename, String printer) throws RemoteException {
        if (check(username, password)){
            return null;
        }
        return null;
    }

    @Override
    public String queue(String username, String password) throws RemoteException {
        if (check(username, password)){
            return null;
        }
        return null;
    }

    @Override
    public void topQueue(String username, String password, int job) throws RemoteException {
        if (check(username, password)){
            return;
        }
    }

    @Override
    public void start(String username, String password) throws RemoteException {
        if (check(username, password)){
            return;
        }
    }

    @Override
    public void stop(String username, String password) throws RemoteException {
        if (check(username, password)){
            return;
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
            return null;
        }
        return null;
    }

    @Override
    public String readConfig(String username, String password, String parameter) throws RemoteException {
        if (check(username, password)){
            return null;
        }
        return null;
    }

    @Override
    public void setConfig(String username, String password, String parameter, String value) throws RemoteException {
        if (check(username, password)){
            return;
        }
    }

    @Override
    public boolean check(String username, String password) throws RemoteException {
        return false;
    }

    @Override
    public String addUser(String username, String password) throws RemoteException {
        if (check(username,password)){
            return null;
        }
        return null;
    }

}
