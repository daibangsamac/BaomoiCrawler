package src.main;

import src.main.controller.AppController;
import src.main.service.CoordinatorService;
import src.main.view.CLIView;



/**
 * Main class
 * <p>This is the main class that runs the crawlers.</p>
 * <p>It creates multiple Crawler instances for the each topic and starts the crawling process.</p>
 * @version 2.0
 * @author Nguyen Huu Quang
 */
public class Main {
	/** 
	 * Main method
	 * This run the application by default with a cli-based interface
	 * @param args Command line arguments
	*/
    public static void main(String[] args) {
		CLIView cliView = new CLIView();
		CoordinatorService coordinatorService = new CoordinatorService();
		AppController app = new AppController(coordinatorService, cliView);
		
		app.start();
		
	}
	
}
