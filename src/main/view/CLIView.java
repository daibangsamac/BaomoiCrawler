package src.main.view;

import java.util.Scanner;

/**
 * Command Line Interface Application View class
 * @version 1.0
 * @author Nguyen Huu Quang
 */
public class CLIView {
 
    private boolean isLogging = false;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Default Constructor for cli view
     */
    public CLIView() {
    }

    public void start() {
        openCMD();
        printStarter();
    }

    private void openCMD() {
        try {
            Runtime.getRuntime().exec("cmd.exe");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printDefaultProtocolSelect() {
        System.out.println("Starting default protocols:");
    }

    public void printUnknownProtocol() {
        System.out.println("Unknown protocol");
    }

    public void printStarter() {
        System.out.println("=== Bao Moi Crawler started ===");
        System.out.println("Type 'exit' to quit.");
        System.out.println("Please select mode");
        System.out.println("1. Default");
    }

    public String getUserInput() {
        return scanner.nextLine();
    }

    public boolean isLogging() {
        return isLogging;
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void shutdown() {
        System.out.println("Shutting down ...");
    }
}
