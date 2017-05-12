package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Drink;
import starwars.actions.Fill;

/**
 * A canteen that can be used to contain water.
 * 
 * It can be filled at a Reservoir, or any other Entity
 * that has a Dip affordance.
 * 
 * Please note that drinking from the canteen is currently 
 * unimplemented
 * 
 * 
 * @author Robert Merkel
 * 
 */
public class Canteen extends SWEntity implements Fillable, Drinkable {

	private int capacity;
	private int level;
	
	public Canteen(MessageRenderer m, int capacity, int initialLevel)  {
		super(m);
		this.shortDescription = "a canteen";
		this.longDescription = "a slightly battered aluminium canteen";
		
		this.capacity = capacity;
		this.level= initialLevel;
		capabilities.add(Capability.FILLABLE);
		this.addAffordance(new Fill(this, m));
		this.addAffordance(new Drink(this, m));
	}

	public void fill() {
	
		level = capacity;
	}
	@Override 
	public String getShortDescription() {
		return shortDescription + " [" + level + "/" + capacity + "]";
	}
	
	@Override
	public String getLongDescription () {
		return longDescription + " [" + level + "/" + capacity + "]";
	}

	public void drink() {
		level -= 1;
	}

	public boolean isEmpty() {
		return level <= 0;
	}
}
