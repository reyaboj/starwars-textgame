package starwars;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.space.World;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.actions.Take;
import starwars.entities.*;
import starwars.entities.actors.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a world in the Star Wars universe. 
 * 
 * @author ram
 */
/*
 * Change log
 * 2017-02-02:  Render method was removed from Middle Earth
 * 				Displaying the Grid is now handled by the TextInterface rather 
 * 				than by the Grid or MiddleWorld classes (asel)
 */
public class SWWorld extends World {
	/**
	 * Maps for this world.
	 */
	private Map<SWMap, SWGrid> maps;

	/**
	 * Current map.
	 */
	private SWMap currentMap;
	
	/**The entity manager of the world which keeps track of <code>SWEntities</code> and their <code>SWLocation</code>s*/
	private static final EntityManager<SWEntityInterface, SWLocation> entityManager = new EntityManager<SWEntityInterface, SWLocation>();

	public enum SWMap {
		START,
		YAVIN_IV,
		DEATH_STAR
	}

	/**
	 * Constructor of <code>SWWorld</code>. This will initialize the <code>SWLocationMaker</code>
	 * and the grid.
	 */
	public SWWorld() {
		SWLocation.SWLocationMaker factory = SWLocation.getMaker();

		maps = new HashMap<>();
		maps.put(SWMap.START, new SWGrid(factory));
		maps.put(SWMap.YAVIN_IV, new SWGrid(2, 2, factory));
		maps.put(SWMap.DEATH_STAR, new SWGrid(factory));
		currentMap = SWMap.START;

		space = maps.get(SWMap.START);
	}

	/** 
	 * Returns the height of the <code>Grid</code>. Useful to the Views when rendering the map.
	 * 
	 * @author ram
	 * @return the height of the grid
	 */
	public int height() {
		return space.getHeight();
	}
	
	/** 
	 * Returns the width of the <code>Grid</code>. Useful to the Views when rendering the map.
	 * 
	 * @author ram
	 * @return the height of the grid
	 */
	public int width() {
		return space.getWidth();
	}
	
	/**
	 * Set up the world, setting descriptions for locations and placing items and actors
	 * on the grid.
	 * 
	 * @author 	ram
	 * @param 	iface a MessageRenderer to be passed onto newly-created entities
	 */
	public void initializeWorld(MessageRenderer iface) {
		SWLocation loc;
		SWGrid startGrid = maps.get(SWMap.START);
		// Set default location string
		for (int row=0; row < startGrid.getHeight(); row++) {
			for (int col=0; col < startGrid.getWidth(); col++) {
				loc = startGrid.getLocationByCoordinates(col, row);
				loc.setLongDescription("SWWorld (" + col + ", " + row + ")");
				loc.setShortDescription("SWWorld (" + col + ", " + row + ")");
				loc.setSymbol('.');				
			}
		}

		// BadLands
		for (int row = 5; row < 8; row++) {
			for (int col = 4; col < 7; col++) {
				loc = startGrid.getLocationByCoordinates(col, row);
				loc.setLongDescription("Badlands (" + col + ", " + row + ")");
				loc.setShortDescription("Badlands (" + col + ", " + row + ")");
				loc.setSymbol('b');
			}
		}
		
		//Ben's Hut
		loc = startGrid.getLocationByCoordinates(5, 6);
		loc.setLongDescription("Ben's Hut");
		loc.setShortDescription("Ben's Hut");
		loc.setSymbol('H');
		
		Direction [] patrolmoves = {CompassBearing.EAST, CompassBearing.EAST,
                CompassBearing.SOUTH,
                CompassBearing.WEST, CompassBearing.WEST,
                CompassBearing.SOUTH,
                CompassBearing.EAST, CompassBearing.EAST,
                CompassBearing.NORTHWEST, CompassBearing.NORTHWEST};
		
		BenKenobi ben = BenKenobi.getBenKenobi(iface, this, patrolmoves);
		ben.setSymbol("B");
		ben.takeDamage(40);
		loc = startGrid.getLocationByCoordinates(4,  5);
		entityManager.setLocation(ben, loc);


		// Luke
		loc = startGrid.getLocationByCoordinates(5,9);
		Player luke = new Player(Team.GOOD, 100, iface, this);
		luke.setShortDescription("Luke");
		entityManager.setLocation(luke, loc);
		luke.resetMoveCommands(loc);

		// Generic droids
		Droid droid = new Droid(null, Team.NEUTRAL, 200, iface, this);
		droid.setHitpoints(0);
		droid.setSymbol("DX");
		droid.setShortDescription("DX");
		entityManager.setLocation(droid, startGrid.getLocationByCoordinates(3,7));

		Droid d2 = new Droid(null, Team.NEUTRAL, 100, iface, this);
		d2.setHitpoints(0);
		d2.setSymbol("DD");
		d2.setShortDescription("DD");
		entityManager.setLocation(d2, startGrid.getLocationByCoordinates(1, 7));

		Droid d3 = new Droid(null, Team.NEUTRAL, 100, iface, this);
		d3.setHitpoints(0);
		d3.setSymbol("D3");
		d3.setShortDescription("D3");
		entityManager.setLocation(d3, startGrid.getLocationByCoordinates(4, 8));

		// C3P0 Droid
		C3P0 c3p0 = new C3P0(null, Team.NEUTRAL, 200, iface, this);
		c3p0.setSymbol("C3");
		entityManager.setLocation(c3p0, startGrid.getLocationByCoordinates(0,0));

		// R2D2
		R2D2 r2d2 = new R2D2(null, Team.NEUTRAL, 200, iface, this);
		r2d2.setSymbol("R2");
		entityManager.setLocation(r2d2, startGrid.getLocationByCoordinates(0, 7));

		// Damaged droid
		Droid d4 = new Droid(null, Team.NEUTRAL, 100, iface, this);
		d4.setSymbol("D4");
		d4.setHitpoints(50);
		d4.setShortDescription("D4");
		d4.setLongDescription("D4");
		entityManager.setLocation(d4, startGrid.getLocationByCoordinates(1, 7));
		
		// Beggar's Canyon 
		for (int col = 3; col < 8; col++) {
			loc = startGrid.getLocationByCoordinates(col, 8);
			loc.setShortDescription("Beggar's Canyon (" + col + ", " + 8 + ")");
			loc.setLongDescription("Beggar's Canyon  (" + col + ", " + 8 + ")");
			loc.setSymbol('C');
			loc.setEmptySymbol('='); // to represent sides of the canyon
		}
		
		// Moisture Farms
		for (int row = 0; row < 10; row++) {
			for (int col = 8; col < 10; col++) {
				loc = startGrid.getLocationByCoordinates(col, row);
				loc.setLongDescription("Moisture Farm (" + col + ", " + row + ")");
				loc.setShortDescription("Moisture Farm (" + col + ", " + row + ")");
				loc.setSymbol('F');
				
				// moisture farms have reservoirs
				entityManager.setLocation(new Reservoir(iface), loc);				
			}
		}
		
		// Ben Kenobi's hut
		/*
		 * Scatter some other entities and actors around
		 */
		// a canteen
		loc = startGrid.getLocationByCoordinates(3,1);
		SWEntity canteen = new Canteen(iface, 10,0);
		canteen.setSymbol("o");
		canteen.setHitpoints(500);
		entityManager.setLocation(canteen, loc);
		canteen.addAffordance(new Take(canteen, iface));

		loc = startGrid.getLocationByCoordinates(4, 5);
		SWEntity benCanteen = new Canteen(iface, 10, 10);
		benCanteen.setSymbol("o");
		benCanteen.setHitpoints(500);
		entityManager.setLocation(benCanteen, loc);
		benCanteen.addAffordance(new Take(benCanteen, iface));

		// an oil can treasure
		loc = startGrid.getLocationByCoordinates(1,5);
		OilCan oilcan = new OilCan(iface);
		oilcan.setShortDescription("an oil can");
		oilcan.setLongDescription("an oil can, which would theoretically be useful for fixing robots");
		oilcan.setSymbol("x");
		oilcan.setHitpoints(100);
		// add a Take affordance to the oil can, so that an actor can take it
		entityManager.setLocation(oilcan, loc);
		oilcan.addAffordance(new Take(oilcan, iface));
		
		// a lightsaber
		LightSaber lightSaber = new LightSaber(iface);
		loc = startGrid.getLocationByCoordinates(5,5);
		entityManager.setLocation(lightSaber, loc);
		
		// A blaster 
		Blaster blaster = new Blaster(iface);
		loc = startGrid.getLocationByCoordinates(3, 4);
		entityManager.setLocation(blaster, loc);
		
		// A Tusken Raider
		TuskenRaider tim = new TuskenRaider(10, "Tim", iface, this);
		
		tim.setSymbol("T");
		loc = startGrid.getLocationByCoordinates(4,3);
		entityManager.setLocation(tim, loc);
		
		
		TuskenRaider kim = new TuskenRaider(10, "Kim", iface, this);
		
		kim.setSymbol("K");
		loc = startGrid.getLocationByCoordinates(4,4);
		entityManager.setLocation(kim, loc);

		
		TuskenRaider rim = new TuskenRaider(10, "Rim", iface, this);
		
		rim.setSymbol("R");
		loc = startGrid.getLocationByCoordinates(4,1);
		entityManager.setLocation(rim, loc);
		
		
		TuskenRaider jim = new TuskenRaider(10, "Jim", iface, this);
		
		jim.setSymbol("J");
		loc = startGrid.getLocationByCoordinates(4,2);
		entityManager.setLocation(jim, loc);

		// add Uncle Owen and Aunt Beru
		SWHuman owen = new SWHuman(Team.NEUTRAL, 9999, iface, this);
		SWHuman beru = new SWHuman(Team.NEUTRAL, 9999, iface, this);
		owen.setShortDescription("Uncle Owen");
		beru.setShortDescription("Aunt Beru");
		owen.setLongDescription("Uncle Owen");
		beru.setLongDescription("Aunt Beru");
		owen.setSymbol("U");
		beru.setSymbol("A");
		loc = startGrid.getLocationByCoordinates(8, 1);
		entityManager.setLocation(owen, loc);
		entityManager.setLocation(beru, loc);



		// add falcon
		Falcon startFalcon = new Falcon(SWMap.START, this, iface);
		loc = startGrid.getLocationByCoordinates(0,9);
		entityManager.setLocation(startFalcon, loc);


		/*Populate Yavin IV*/
		SWGrid yavin = maps.get(SWMap.YAVIN_IV);

		for (int row = 0; row < yavin.getHeight(); row++) {
			for (int col = 0; col < yavin.getWidth(); col++) {
				loc = yavin.getLocationByCoordinates(col, row);
				loc.setShortDescription("YavinIV(" + col + ", " + row + ")");
				loc.setLongDescription("YavinIV(" + col + ", " + row + ")");
				loc.setSymbol('Y');
			}
		}

		// add ackbar
		loc = yavin.getLocationByCoordinates(0, 0);
		entityManager.setLocation(new Ackbar(iface, this), loc);

		// add mon mothma
		loc = yavin.getLocationByCoordinates(1, 0);
		MonMothma monMothma = new MonMothma(iface, this);
		entityManager.setLocation(monMothma, loc);

		// add falcon
		Falcon yavinFalcon = new Falcon(SWMap.YAVIN_IV, this, iface);
		loc = yavin.getLocationByCoordinates(0, 1);
		entityManager.setLocation(yavinFalcon, loc);


		/*Initialize Death Star*/
		SWGrid deathStar = maps.get(SWMap.DEATH_STAR);
		for (int row = 0; row < deathStar.getHeight(); row++) {
			for (int col = 0; col < deathStar.getWidth(); col++) {
				loc = deathStar.getLocationByCoordinates(col, row);
				loc.setSymbol('D');
				loc.setShortDescription("DeathStar(" + col + "," + row + ")");
				loc.setLongDescription("DeathStar(" + col + "," + row + ")");
			}
		}

		// Darth Vader
		DarthVader vader = new DarthVader(iface, this);
		loc = deathStar.getLocationByCoordinates(4, 4);
		entityManager.setLocation(vader, loc);

		// Princess
		PrincessLeia princess = new PrincessLeia(iface, this);
		entityManager.setLocation(princess, deathStar.getLocationByCoordinates(0, 0));

		// 5 StormTroopers
		SWLocation[] trooperLocs = {
				deathStar.getLocationByCoordinates(0, 7),
				deathStar.getLocationByCoordinates(7, 0),
				deathStar.getLocationByCoordinates(1, 8),
				deathStar.getLocationByCoordinates(3, 8),
				deathStar.getLocationByCoordinates(7, 1),
		};

		for (int i = 0; i < trooperLocs.length; i++) {
			StormTroopers trooper = new StormTroopers(iface, this);
			entityManager.setLocation(trooper, trooperLocs[i]);
		}

		// Add Falcon
		Falcon deathStarFalcon = new Falcon(SWMap.DEATH_STAR, this, iface);
		entityManager.setLocation(deathStarFalcon, deathStar.getLocationByCoordinates(9, 9));

		// connect all falcons
		startFalcon.connectToFalcon(yavinFalcon);
		startFalcon.connectToFalcon(deathStarFalcon);

		yavinFalcon.connectToFalcon(startFalcon);
		yavinFalcon.connectToFalcon(deathStarFalcon);

		deathStarFalcon.connectToFalcon(startFalcon);
		deathStarFalcon.connectToFalcon(yavinFalcon);

		// add win/lose tests
		addCondition(GameState.WIN, w -> vader.isDead());
		addCondition(GameState.WIN, w -> monMothma.isHappy());

		addCondition(GameState.LOSE, w -> luke.isDead());
		addCondition(GameState.LOSE, w -> luke.getTeam() == Team.EVIL);
		addCondition(GameState.LOSE, w -> princess.isDead());
		addCondition(GameState.LOSE, w -> entityManager.whereIs(r2d2) == null);
	}

	/*
	 * Render method was removed from here
	 */
	
	/**
	 * Determine whether a given <code>SWActor a</code> can move in a given direction
	 * <code>whichDirection</code>.
	 * 
	 * @author 	ram
	 * @param 	a the <code>SWActor</code> being queried.
	 * @param 	whichDirection the <code>Direction</code> if which they want to move
	 * @return 	true if the actor can see an exit in <code>whichDirection</code>, false otherwise.
	 */
	public boolean canMove(SWActor a, Direction whichDirection) {
		SWLocation where = (SWLocation)entityManager.whereIs(a); // requires a cast for no reason I can discern
		return where.hasExit(whichDirection);
	}
	
	/**
	 * Accessor for the current grid.
	 * 
	 * @author ram
	 * @return the grid
	 */
	public SWGrid getCurrentGrid() {
		return maps.get(getCurrentMap());
	}

	/**
	 * Set the current map of the world.
	 * @param m
	 */
	public void setCurrentMap(SWMap m) {
		this.currentMap = m;
		space = maps.get(m);
	}

	/**
	 * Get the current map of the world.
	 */
	public SWMap getCurrentMap() {
		return this.currentMap;
	}

	/**
	 * Move an actor in a direction.
	 * 
	 * @author ram
	 * @param a the actor to move
	 * @param whichDirection the direction in which to move the actor
	 */
	public void moveEntity(SWActor a, Direction whichDirection) {
		
		//get the neighboring location in whichDirection
		Location loc = entityManager.whereIs(a).getNeighbour(whichDirection);
		
		// Base class unavoidably stores superclass references, so do a checked downcast here
		if (loc instanceof SWLocation)
			//perform the move action by setting the new location to the the neighboring location
			entityManager.setLocation(a, (SWLocation) entityManager.whereIs(a).getNeighbour(whichDirection));
	}

	/**
	 * Returns the <code>Location</code> of a <code>SWEntity</code> in this grid, null if not found.
	 * Wrapper for <code>entityManager.whereIs()</code>.
	 * 
	 * @author 	ram
	 * @param 	e the entity to find
	 * @return 	the <code>Location</code> of that entity, or null if it's not in this grid
	 */
	public Location find(SWEntityInterface e) {
		return entityManager.whereIs(e); //cast and return a SWLocation?
	}

	/**
	 * This is only here for compliance with the abstract base class's interface and is not supposed to be
	 * called.
	 */

	@SuppressWarnings("unchecked")
	public EntityManager<SWEntityInterface, SWLocation> getEntityManager() {
		return SWWorld.getEntitymanager();
	}

	/**
	 * Returns the <code>EntityManager</code> which keeps track of the <code>SWEntities</code> and
	 * <code>SWLocations</code> in <code>SWWorld</code>.
	 * 
	 * @return 	the <code>EntityManager</code> of this <code>SWWorld</code>
	 * @see 	{@link #entityManager}
	 */
	public static EntityManager<SWEntityInterface, SWLocation> getEntitymanager() {
		return entityManager;
	}
}
