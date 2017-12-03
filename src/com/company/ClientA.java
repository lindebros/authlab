package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientA {

    public static void main(String[] args) throws IOException, NotBoundException {
        PrintService service = (PrintService) Naming.lookup("rmi://localhost:5099/print");

        run(service);

    }
    private static void run(PrintService service) throws IOException {

        service.setAccount("Alice", "abc123");
        service.setAccount("Bob","abc123");
        service.setAccount("Cecilia","abc123");
        service.setAccount("David","abc123");
        service.setAccount("Erica","abc123");
        service.setAccount("Fred","abc123");
        service.setAccount("George","abc123");

        String[] names = {"Alice", "Bob", "Cecilia", "David", "Erica", "Fred", "George"};
        // Every user tries to print
        System.out.println("PRINT operation:");
        for (String s: names){
            System.out.println(s + " calls operation PRINT: " + service.print(s, "abc123", "file1", "print1"));
        }
        // Every user tries to call queue.
        System.out.println("\nQueue operation:");
        for (String s: names){
            System.out.println(s + " calls operation QUEUE: " + service.queue(s, "abc123"));
        }
        // Every user tries to call topQueue.
        System.out.println("\nTopQueue operation:");
        for (String s: names){
            System.out.println(s + " calls operation TOPQUEUE: " + service.topQueue(s,"abc123",1));
        }
        // Every user tries to call start.
        System.out.println("\nStart operation:");
        for (String s: names){
            System.out.println(s + " calls operation START: " + service.start(s,"abc123"));
        }
        // Every user tries to call stop.
        System.out.println("\nStop operation:");
        for (String s: names){
            System.out.println(s + " calls operation STOP: " + service.stop(s,"abc123"));
        }
        // Every user tries to call restart.
        System.out.println("\nRestart operation:");
        for (String s: names){
            System.out.println(s + " calls operation RESTART: " + service.restart(s,"abc123"));
        }
        // Every user tries to call status.
        System.out.println("\nStatus operation:");
        for (String s: names){
            System.out.println(s + " calls operation STATUS: " + service.status(s,"abc123"));
        }
        // Every user tries to call readConfig.
        System.out.println("\nReadConfig operation:");
        for (String s: names){
            System.out.println(s + " calls operation READCONFIG: " + service.readConfig(s,"abc123","ink"));
        }
        // Every user tries to call setConfig.
        System.out.println("\nSetConfig operation:");
        for (String s: names){
            System.out.println(s + " calls operation SETCONFIG: " + service.setConfig(s,"abc123","ink","full"));
        }

    }
}
