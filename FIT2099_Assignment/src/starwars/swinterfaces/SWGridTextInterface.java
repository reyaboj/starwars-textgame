package starwars.swinterfaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.monash.fit2099.gridworld.GridRenderer;
import edu.monash.fit2099.simulator.matter.ActionInterface;
import edu.monash.fit2099.simulator.matter.EntityManager;
import starwars.SWEntityInterface;
import starwars.SWGrid;
import starwars.SWLocation;
import starwars.SWWorld;

/**
 * This is the text based user interface for the simulation. Is responsible for outputting a 
 * text based map and messages on the console and obtaining user selection of commands from the console
 * <p>
 * Its operations are controlled by the <code>SWGridController</code>.
 * 
 * @author Asel
 */
/*
 * Changelog
 * 2017-02-19	: Show banner method added. I used a boolean attribute to show the banner only once with the map render. (asel)
 */
public class SWGridTextInterface implements GridRenderer {
	/**The world*/
	private static SWWorld world;
	
	/**If or not to show the banner*/
	private static boolean showBanner;
	
	private static Scanner instream;
	
	/**
	 * Constructor for the <code>SWGridTextInterface</code>. Will set showBanner flag to true to
	 * show the text banner with the first map render.
	 * 
	 * @param 	world the world
	 * @pre 	world should not be null
	 */
	public SWGridTextInterface(SWWorld world) {
		SWGridTextInterface.world = world;
		instream = new Scanner(System.in);
		//set the show banner to true so that the banner would be displayed on the first map render
		showBanner = true;
	}
	
	
	/**
	 * Returns a string consisting of the symbol of the <code>SWLocation loc</code>, a colon ':' followed by 
	 * any symbols of the contents of the <code>SWLocation loc</code> and/or empty spaces of the <code>SWLocation loc</code>.
	 * <p>
	 * All string returned by this method are of a fixed length and doesn't contain any line breaks.
	 * 
	 * @author 	Asel
	 * @param 	loc for which the string is required
	 * @pre		all symbols and empty spaces should not be line break characters
	 * @return 	a string in the format location symbol of <code>loc</code> + : + symbols of contents of <code>loc</code> + any empty characters of <code>loc</code>
	 * @post	all strings returned are of a fixed size
	 */
	private String getLocationString(SWLocation loc) {
		final EntityManager<SWEntityInterface, SWLocation> em = SWWorld.getEntitymanager();
		
		//all string would be of locationWidth length
		final int locationWidth = 8;
		
		StringBuffer emptyBuffer = new StringBuffer();
		char es = loc.getEmptySymbol(); 
		
		for (int i = 0; i < locationWidth - 2; i++) { 	//add two less as one character is reserved for the location symbol and the other for the colon (":")
			emptyBuffer.append(es);						
		}									  			
			
		//new buffer buf with a symbol of the location + :
		StringBuffer buf = new StringBuffer(loc.getSymbol() + ":"); 
		
		//get the Contents of the location
		List<SWEntityInterface> contents = em.contents(loc);
		
		
		if (contents == null || contents.isEmpty())
			buf.append(emptyBuffer);//add empty buffer to buf to complete the string buffer
		else {
			for (SWEntityInterface e: contents) { //add the symbols of the contents
				buf.append(e.getSymbol());
			}
		}
		buf.append(emptyBuffer); //add the empty buffer again since the symbols of the contents that were added might not actually filled the location upto locationWidth
		
		//set a fixed length
		buf.setLength(locationWidth);
		
		return buf.toString();		
	}
	
	/**
	 * Display the simulation banner. This method will only be called once for each instance.
	 * 
	 * Based on code from the original Eiffel version of this program, and originally 
	 * generated by the UNIX program figlet.
	 * 
	 * @author ram
	 */
	public static void showBanner() {
		String [] lines = { 
				" ____  _              __        __",
				"/ ___|| |_ __ _ _ __  \\ \\      / /_ _ _ __ ___",
				"\\___ \\| __/ _` | '__|  \\ \\ /\\ / / _` | '__/ __|",
				" ___) | || (_| | |      \\ V  V / (_| | |  \\__ \\",
				"|____/ \\__\\__,_|_|       \\_/\\_/ \\__,_|_|  |___/",
			"",
			"A long time ago in a galaxy far, far away",
			"",
			"The laws of physics were compressed onto a",
			"",
			"two-dimensional grid, and time was composed of",
			"",
			"discrete instants.  And some other stuff happened",
			"",
			"but waiting for it to scroll down is boring."};
		
		for(String line: lines) {
			System.out.println(line);
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		showBanner = false;
	}
	
	@Override
	public void displayMap() {
		
		//show the banner if it has not been displayed before
		if (showBanner) {
			showBanner();
		}

		SWGrid grid = world.getCurrentGrid();
		String buffer = "\n";
		final int gridHeight = grid.getHeight();
		final int gridWidth  = grid.getWidth();
		
	
		for (int row = 0; row< gridHeight; row++){ //for each row
			for (int col = 0; col< gridWidth; col++){ //each column of a row
				
				SWLocation loc = (SWLocation) grid.getLocationByCoordinates(col, row);
				
				//construct the string of a location to be displayed on the text interface
				buffer = buffer + "|"+ getLocationString(loc)+"| ";
			}
			buffer += "\n"; //new row
		}
		
		System.out.println(buffer); //print the grid on the screen
		
	}

	@Override
	public void displayMessage(String message) {
		System.out.println(message);		
	}


	@Override
	public ActionInterface getSelection(ArrayList<ActionInterface> cmds) {
		
		//assertion for the precondition
		assert cmds.size()>0:"command list for the actor is empty";
							
		Collections.sort(cmds);//sorting the actions for a prettier output

		//construct the commands to be displayed in the console
		for (int i = 0; i < cmds.size(); i++) {
			System.out.println(i + 1 + " " + cmds.get(i).getDescription());
		}
		
		int selection = 0; //set to zero to trigger the loop
		while (selection < 1 || selection > cmds.size()) {//loop until a valid command has been obtained
			System.out.println("Enter command:");
			
			try{
				selection = (instream.nextInt());
			}catch (InputMismatchException e) { //catching any non integer inputs
			    instream.next(); // this consumes the invalid input
			}
		}
	
		return cmds.get(selection-1);//return the action selected		
	}
 	

}
