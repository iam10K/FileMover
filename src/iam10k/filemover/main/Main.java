package iam10k.filemover.main;

import iam10k.filemover.filemover.FileMover;

import java.util.logging.Level;

/**
 * Created by iam10k on 2/25/2016.
 * Github.com/iam10k
 */
public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            printHelp();
        } else if (args.length == 1) {
            switch (args[0]) {
                case "run":
                    FileMover fileMover = new FileMover();
                    if (!fileMover.init()) {
                        System.out.println("Failed...");
                        System.exit(3);
                    }

                    fileMover.run();
                case "templates":
                    // TODO: Open Templates GUI
                case "settings":
                    // TODO: Open Settings GUI
                case "errors":
                    printErrorCodes();
                    System.exit(0);
                default:
                    printHelp();
                    System.exit(1);
            }
        } else {
            System.out.println("Too many arguments. Exiting.");
            System.exit(1);
        }
    }

    private static void printHelp() {
        System.out.println("###### File Mover ######");
        System.out.println(" run        : Runs the program");
        System.out.println(" templates  : Opens templates menu");
        System.out.println(" settings   : Opens settings menu");
        System.out.println(" errors     : Print error codes");
    }

    private static void printErrorCodes() {
        System.out.println("###### File Mover ######");
        System.out.println("  0 : Successful run. Exited Successfully.");
        System.out.println("  1 : Error with arguments.");
        System.out.println("  2 : Settings error.");
        System.out.println("  3 : Error initializing FileMover.");
    }
}

// Useful http://stackoverflow.com/questions/10459323/java-program-running-in-background
