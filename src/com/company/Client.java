package com.company;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        PrintService service = (PrintService) Naming.lookup("rmi://localhost:5099/print");

        service.setAccount("acc1", "abcdefg");
        service.setAccount("acc2","1234567");
        service.setAccount("acc2","1234567");


        System.out.println("--- "+ service.print("username", "password","Hey Server", "printer1"));
    }
}
