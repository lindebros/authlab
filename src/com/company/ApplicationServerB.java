package com.company;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ApplicationServerB {

    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(5299);
        registry.rebind("print", new PrintServantB());
    }
}
