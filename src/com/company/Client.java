package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    public static void main(String[] args) throws IOException, NotBoundException {
        PrintService service = (PrintService) Naming.lookup("rmi://localhost:5099/print");

        service.setAccount("acc1", "abcdefg");
        System.out.println(service.print("acc1", "abcdefg","Hey Server", "printer1"));

        service.setAccount("acc2","asdasd");
        System.out.println(service.print("acc2", "asdasd","Hey Server", "printer2"));

        run(service);

    }
    private static void run(PrintService service) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean run = true;

        String userName = "";
        String pswrd = "";
        String parameter = "";
        String value = "";

        System.out.println("User name:");
        userName = br.readLine();
        System.out.println("Password:");
        pswrd = br.readLine();

        System.out.println(" - type '-help' to get commands");
        while (run){
            switch (br.readLine()){
                case "-help":
                    System.out.println(" The following are the implemented:");
                    System.out.println(" 'quit' : Shuts down the user client.");
                    System.out.println(" 'logout' : Logs out this user to log into another.");
                    System.out.println(" 'print' : Prints a specific file at a specific printer");
                    System.out.println(" 'queue' : Prints the printing queue.");
                    System.out.println(" 'topQueue' : Moves a specific job to top of the queue.");
                    System.out.println(" 'start' : Starts the printing service.");
                    System.out.println(" 'stop' : Stops the printing service.");
                    System.out.println(" 'restart' : Restarts the printing service.");
                    System.out.println(" 'status' : Prints the status of the server.");
                    System.out.println(" 'rConfig' : Prints the configuration of specific parameter.");
                    System.out.println(" 'sConfig' : Sets a parameter to a specific value."+ "\n---|||---");

                    break;
                case "quit":
                    run = false;
                    break;
                case "logout":
                    System.out.println("User name:");
                    userName = br.readLine();
                    System.out.println("Password:");
                    pswrd = br.readLine();
                    System.out.println("---|||---");
                    break;
                case "print":
                    System.out.println("File name:");
                    String fileName = br.readLine();
                    System.out.println("Printer:");
                    String printer = br.readLine();
                    System.out.println(service.print(userName,pswrd,fileName,printer)+ "\n---|||---");
                    break;
                case "queue":
                    System.out.println(service.queue(userName,pswrd)+ "\n---|||---");
                    break;
                case "topQueue":
                    int job = Integer.parseInt(br.readLine());
                    System.out.println(service.topQueue(userName,pswrd,job)+ "\n---|||---");
                    break;
                case "start":
                    System.out.println(service.start(userName,pswrd)+ "\n---|||---");
                    break;
                case "stop":
                    System.out.println(service.stop(userName,pswrd)+ "\n---|||---");
                    break;
                case "restart":
                    System.out.println(service.restart(userName,pswrd)+ "\n---|||---");
                    break;
                case "status":
                    System.out.println(service.status(userName,pswrd)+ "\n---|||---");
                    break;
                case "rConfig":
                    System.out.println("Parameter:");
                    parameter = br.readLine();
                    System.out.println(service.readConfig(userName,pswrd,parameter)+ "\n---|||---");
                    break;
                case "sConfig":
                    System.out.println("Parameter:");
                    parameter = br.readLine();
                    System.out.println("Value:");
                    value = br.readLine();
                    System.out.println(service.setConfig(userName,pswrd,parameter,value) + "\n---|||---");
                    break;
                default:
                    System.out.println("Not such command.\n---|||---");
                    break;
            }
        }
    }
}
