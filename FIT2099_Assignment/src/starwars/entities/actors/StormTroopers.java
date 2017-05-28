package starwars.entities.actors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.SWActor.Force;
import starwars.actions.Attack;
import starwars.actions.Move;
import starwars.actions.Radio;
import starwars.entities.Blaster;
import starwars.entities.LightSaber;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.AttackNeighbours;

/**
 * 
 * @author Owner Syed Arham Ali Rizvi
 *
 */
public class StormTroopers extends SWActor{
	/**
	 * HEALTH of the Actor
	 */
	public static final int HEALTH = 100;
	
	/**
	 * 
	 * @param team EVIL
	 * @param hitpoints HEALTH = 100
	 * @param m
	 * @param world
	 * 
	 * Assigned with a blaster when they're created
	 */
	public StormTroopers(MessageRenderer m, SWWorld world) {
		super(Team.EVIL, HEALTH, m, world);
		// TODO Auto-generated constructor stub
		Blaster stormTroopersWeapon = new Blaster(m);
		setItemCarried(stormTroopersWeapon);
	}

	/**
	 * They attack who ever is not on team.EVIL
	 * They miss 75% of their attacks
	 * If they dont attack, they radio for backup, which creates another stormTrooper on the same location
	 */
	@Override
	public void act() {
		// TODO Auto-generated method stub
		if (isDead()) {
			return;
		}
		say(describeLocation());
		
		// get entities at position
		EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
		List<SWEntityInterface> entities = em.contents(em.whereIs(this));

		// check for Team.EVIL existence
		Optional<SWEntityInterface> target = entities.stream()
				.filter(e -> e instanceof SWActor && ((SWActor)(e)).getTeam() != Team.EVIL)
				.findFirst();
										  //!an instance of StormTroopers and !an instanceof Team.EVIL	
		
		
		
		if (target.isPresent()) { //if it's not from team.EVIL, attack

			if(Math.random()<0.25){ //25% chance of attacking
				say(getShortDescription() + " has attacked " + target.get().getShortDescription());
				scheduler.schedule(new Attack(target.get(), messageRenderer), this, 1);
			
			}else{ //misses target
				say("Storm Trooper shoots wildly");
			}
			
			
		}else if (Math.random() < 0.05){ //Radio for backup
			
				//Call radio over here, which creates a new StormTroopers
				
				Radio r = new Radio(world, messageRenderer);
				scheduler.schedule(r, this, 1);
				
				
		}else{ //move randomly
				
				ArrayList<Direction> possibledirections = new ArrayList<Direction>();

				// build a list of available directions
				for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
					if (SWWorld.getEntitymanager().seesExit(this, d)) {
						possibledirections.add(d);
					}
				}

				Direction heading = possibledirections.get((int) (Math.floor(Math.random() * possibledirections.size())));
				say(getShortDescription() + "is heading " + heading + " next.");
				Move myMove = new Move(heading, messageRenderer, world);

				scheduler.schedule(myMove, this, 1);
			}
		
		}
	
	/**
	 * StormTroopers' Short description.
     * @return the description
	 */
	@Override
	public String getShortDescription() {
		return " Storm Trooper";
	}

    /**
     * StormTroopers' long description.
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
