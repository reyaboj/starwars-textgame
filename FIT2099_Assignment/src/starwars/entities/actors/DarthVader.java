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
import starwars.actions.Attack;
import starwars.actions.ForceChoke;
import starwars.actions.Move;
import starwars.entities.LightSaber;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.AttackNeighbours;


/**
 * 
 * @author Owner SYED ARHAM ALI RIZVI
 *
 */
public class DarthVader extends SWActor{
	
	/**
	 * Health/Hitpoints of the Actor
	 */
	public static final int HEALTH = 10000;

	/**
	 * Create Darth Vader.
	 * @param m
	 * @param w
	 */
	public DarthVader(MessageRenderer m, SWWorld w) {
		super(Force.TRAINED, Team.EVIL, HEALTH, m, w);
		LightSaber dvWeapon = new LightSaber(m);
		setItemCarried(dvWeapon);
		setSymbol("V");
	}
	
	/**
	 * 
	 */
	@Override
	public void act() {
		// get entities at position
		EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
		List<SWEntityInterface> entities = em.contents(em.whereIs(this));

		// check for Luke's existence
		Optional<SWEntityInterface> luke = entities.stream()
				.filter(e -> e instanceof Player).findFirst();
		
		// check for any Actor's existence who is not luke
		Optional<SWEntityInterface> actor = entities.stream()
				.filter(e -> e instanceof SWActor && !(e instanceof Player) && e != this).findFirst();

		if (luke.isPresent()) {
			
			if(Math.random()>=0.5){ //attempt to turn luke to the dark side
				Player p = (Player)luke.get();
				if (p.getForceLevel() != SWActor.Force.TRAINED) {
					p.setTeam(Team.EVIL);
					say(getShortDescription() + " has converted " + p.getShortDescription() + " to the dark side!");
				} else {
					boolean convertSuccess = Math.random() < 0.25;
					if (convertSuccess) {
						p.setTeam(Team.EVIL);
						say(getShortDescription() + " has converted " + p.getShortDescription() + " to the dark side!");
					} else {
						say(getShortDescription() + " has failed to convert " + p.getShortDescription() + " to the dark side!");
					}
				}
			}else{ //attack luke with a lightsaber
				scheduler.schedule(new Attack(luke.get(), messageRenderer), this, 1);
			}
		}else if(actor.isPresent()){ //any actor besides luke
			
			if(Math.random()<0.5){ //Force choke the actor
				ForceChoke frCk = new ForceChoke(actor.get(), messageRenderer);
				scheduler.schedule(frCk, this, 1);
				
			}else{	//attack with lightsaber

				scheduler.schedule(new Attack(actor.get(), messageRenderer), this, 1);
				
			}
		}else{ //else move randomly
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
	 * Darth Vader's Short description.
     * @return the description
	 */
	@Override
	public String getShortDescription() {
		return " Darth Vader";
	}

    /**
     * Darth Vader's long description.
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
