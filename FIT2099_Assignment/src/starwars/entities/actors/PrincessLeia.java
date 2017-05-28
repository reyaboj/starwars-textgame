package starwars.entities.actors;

import java.util.List;
import java.util.Optional;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.matter.ActionInterface;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Move;

/**
 * 
 * @author Owner SYED ARHAM ALI RIZVI
 *
 */
public class PrincessLeia extends SWActor {
	/**
	 * Health/Hitpoints of the Actor
	 */
	public static final int HEALTH = 500;
	
	/**
	 * 
	 * @param force She has enough force to resist mind control
	 * @param team	She's on team Good
	 * @param hitpoints	Starts of with 500 HEALTH/Hitpoints
	 * @param m
	 * @param w
	 */
	public PrincessLeia(MessageRenderer m, SWWorld w) {
		super(Force.UNTRAINED, Team.GOOD, HEALTH, m, w);
		setSymbol("P");
		// TODO Auto-generated constructor stub
	}

	/**
	 * If she dies, game over
	 *
	 */
	@Override
	public void act() {
		// TODO Auto-generated method stub
        if (isDead()) {
        	say("Princess leia is dead");
        	return;
        }

		say(getShortDescription() + " is at " + world.getEntityManager().whereIs(this).getShortDescription());
		ActionInterface followLuke = follow();
		if (followLuke != null)
			scheduler.schedule(followLuke, this, 1);
	}
    
    /**
     * Check's if luke is present at the same location as her
     * if so, follows him
     * @return move the same direction as Luke
     */
    private ActionInterface follow() {
       

        Direction moveDirection = null;  // to store which direction to move
        EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
        SWLocation loc = em.whereIs(this);
        boolean found = false;  // assume we don't know where owner is
        for (Direction d : Grid.CompassBearing.values()) {  // look around us
            if (found)
                break;

            Location neighbor = loc.getNeighbour(d);
            List<SWEntityInterface> neighborObjs = world.getEntityManager().contents((SWLocation)neighbor);

            if (neighborObjs == null)
            	continue;
            
            for (SWEntityInterface entity : neighborObjs) {  // check all entities at that location for owner
                if (entity instanceof Player) {
                    moveDirection = d;
                    found = true;
                }
            }
        }

        if (found)
        	return new Move(moveDirection, messageRenderer, world);
        else
        	return null;
    }
    
	/**
	 * PrincessLeia's Short description.
     * @return the description
	 */
	@Override
	public String getShortDescription() {
		return " Princess Leia ";
	}

    /**
     * PrincessLeia's long description.
     * @return the description
     */
	@Override
	public String getLongDescription() {
		return this.getShortDescription();
	}

	/**
	 * 
	 * @return the Short description with the hitpoints and long description
	 */
	private String describeLocation() {
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription();

	}
}
