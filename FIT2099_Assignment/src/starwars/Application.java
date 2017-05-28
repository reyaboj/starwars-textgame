package starwars;

import edu.monash.fit2099.simulator.space.World;
import edu.monash.fit2099.simulator.time.Scheduler;
import starwars.swinterfaces.SWGridController;

/**
 * Driver class for the Star Wars package with <code>GridController</code>.  Contains nothing but a main().
 * 
 * @author ram
 */
/*
 * Change log
 * 2017-02-02	The TextInterface handles the responsibly of displaying the grid not the SWGrid or SWWorld classes (asel)
 * 2017-02-10	GridController controls the interactions with the user and will determine which UI it should use to do this. 
 * 			    Therefore there is tight coupling with the user interfaces and the driver. The application no longer has to worry about the
 * 				UI(asel)
 * 2017-02-19	Removed the show banner method. The text interface will deal with showing the banner. (asel)
 */

public class Application {
	public static void main(String args[]) {
		SWWorld world = new SWWorld();
		
		//Grid controller controls the data and commands between the UI and the model
		SWGridController uiController = new SWGridController(world);
		
		Scheduler theScheduler = new Scheduler(1, world);
		SWActor.setScheduler(theScheduler);
		
		// set up the world
		world.initializeWorld(uiController);
	
		// kick off the scheduler
		while(world.getGameState() == World.GameState.CONTINUE) {
			uiController.render();
			theScheduler.tick();
		}

		String[] victoryMsg = {
				"____    ____  __    ______ .___________.  ______   .______     ____    ____ \n" +
						"\\   \\  /   / |  |  /      ||           | /  __  \\  |   _  \\    \\   \\  /   / \n" +
						" \\   \\/   /  |  | |  ,----'`---|  |----`|  |  |  | |  |_)  |    \\   \\/   /  \n" +
						"  \\      /   |  | |  |         |  |     |  |  |  | |      /      \\_    _/   \n" +
						"   \\    /    |  | |  `----.    |  |     |  `--'  | |  |\\  \\----.   |  |     \n" +
						"    \\__/     |__|  \\______|    |__|      \\______/  | _| `._____|   |__|     \n" +
						"                                                                            "
		};

		String[] lossMsg = {
				" _______   _______  _______  _______     ___   .___________. _______  _______  \n" +
						"|       \\ |   ____||   ____||   ____|   /   \\  |           ||   ____||       \\ \n" +
						"|  .--.  ||  |__   |  |__   |  |__     /  ^  \\ `---|  |----`|  |__   |  .--.  |\n" +
						"|  |  |  ||   __|  |   __|  |   __|   /  /_\\  \\    |  |     |   __|  |  |  |  |\n" +
						"|  '--'  ||  |____ |  |     |  |____ /  _____  \\   |  |     |  |____ |  '--'  |\n" +
						"|_______/ |_______||__|     |_______/__/     \\__\\  |__|     |_______||_______/ \n" +
						"                                                                               "
		};

		if (world.getGameState() == World.GameState.WIN){
			for (String line : victoryMsg)
				uiController.render(line);
		} else {
			for (String line : lossMsg)
				uiController.render(line);
		}
	}
}
