package src.main.controller;

import src.main.service.CoordinatorService;
import src.main.view.CLIView;

/**
 * Controller class for coordinator
 */
public class AppController {
    
    private CLIView cli;
    private CoordinatorService coordinatorService;
    
    /**
     * Public constructor for coordinator controller with a defualt cli view
     */
    public AppController(CoordinatorService coordinatorService,CLIView view) {
        this.coordinatorService = coordinatorService;
        this.cli = view;
    }

    public void start() {
        cli.start();
        while (true) {
            String cmd = cli.getUserInput();
            if (cmd.equals("exit")) {
                cli.shutdown();
                break;
            }
            if (cmd.equals("1")) {
                cli.printDefaultProtocolSelect();
                boolean isLogging = cli.isLogging();
                String Result = handleDefaultProtocol(isLogging);
                cli.showMessage(Result);
                // cli.shutdown();
                break;
            } else {
                cli.printUnknownProtocol();
            }
            
        }
    }

    private String handleDefaultProtocol(boolean isLogging) {
        coordinatorService.start();
        int time = 0;
        int urlCount = 0;
        int articleParsed = 0;
        return "Protocol completed in " + time + "s\n"
        + "URL fetched " + urlCount + ", Article parsed " + articleParsed;
    }
}
