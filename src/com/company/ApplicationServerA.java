package com.company;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ApplicationServerA {

    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(5099);
        try {
            registry.rebind("print", new PrintServantA());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
