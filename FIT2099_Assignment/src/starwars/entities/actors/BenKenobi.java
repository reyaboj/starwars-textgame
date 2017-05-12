package starwars.entities.actors;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.*;
import starwars.actions.*;
import starwars.entities.Canteen;
import starwars.entities.LightSaber;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.AttackNeighbours;
import starwars.entities.actors.behaviors.Patrol;

import java.util.List;
import java.util.Optional;

/**
 * Ben (aka Obe-Wan) Kenobi.  
 * 
 * At this stage, he's an extremely strong critter with a <code>Lightsaber</code>
 * who wanders around in a fixed pattern and neatly slices any Actor not on his
 * team with his lightsaber.
 * 
 * Note that you can only create ONE Ben, like all SWLegends.
 * @author rober_000
 *
 */
public class BenKenobi extends SWLegend {
	private enum ActorState {
		PATROL_AND_ATTACK,
		DRINK_TAKE, DRINK_LOOP, DRINK_DROP,
		TRAIN
	}

	private static BenKenobi ben = null; // yes, it is OK to return the static instance!

	private Patrol path;

	private ActorState state = ActorState.PATROL_AND_ATTACK;

	private Canteen holdingCanteen;
	private SWEntityInterface droppedItem;

	private BenKenobi(MessageRenderer m, SWWorld world, Direction [] moves) {
		super(Team.GOOD, 1000, m, world);
		path = new Patrol(moves);
		this.setShortDescription("Ben Kenobi");
		this.setLongDescription("Ben Kenobi, an old man who has perhaps seen too much");
		LightSaber bensweapon = new LightSaber(m);
		setItemCarried(bensweapon);
		setForceLevel(Force.TRAINED);
		addAffordance(new TrainForce(this, messageRenderer));
	}

	public static BenKenobi getBenKenobi(MessageRenderer m, SWWorld world, Direction [] moves) {
		ben = new BenKenobi(m, world, moves);
		ben.activate();
		return ben;
	}
	
	@Override
	protected void legendAct() {
		if (isDead())
			return;

		switch (state) {
			case PATROL_AND_ATTACK:
				// get entities at position
				EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
				List<SWEntityInterface> entities = em.contents(em.whereIs(this));

				// check for Luke's existence
				Optional<SWEntityInterface> luke = entities.stream()
						.filter(e -> e instanceof Player).findFirst();

				if (luke.isPresent()) {
					state = ActorState.TRAIN;
					break;
				}

				// check vicinity for non-empty canteens
				Optional<SWEntityInterface> canteen = entities.stream()
						.filter(e -> e instanceof Canteen && !((Canteen) e).isEmpty())
						.findFirst();

				if (canteen.isPresent() && getHitpoints() < getMaxHitpoints()) {
					state = ActorState.DRINK_TAKE;
					holdingCanteen = (Canteen) canteen.get();
					droppedItem = getItemCarried();
					if (getItemCarried() != null) {
						scheduler.schedule(new Leave(getItemCarried(), messageRenderer), this, 1);
					}
					break;
				}

				// otherwise, attack any hostile neighbors
				AttackInformation attack = AttackNeighbours.attackLocals(ben, ben.world, true, true);
				if (attack != null) {
					say(getShortDescription()
							+ " suddenly looks sprightly and attacks " + attack.entity.getShortDescription());
					scheduler.schedule(attack.affordance, ben, 1);
				} else {
					Direction nextDirection = path.getNext();
					say(getShortDescription() + " moves " + nextDirection);
					Move benMove = new Move(nextDirection, messageRenderer, world);
					scheduler.schedule(benMove, this, 1);
				}
				break;
			case DRINK_TAKE:
				scheduler.schedule(new Take(holdingCanteen, messageRenderer), this, 1);
				state = ActorState.DRINK_LOOP;
				break;
			case DRINK_LOOP:
				// dump drink if full or canteen is empty
				if (getHitpoints() == getMaxHitpoints() || holdingCanteen.isEmpty()) {
					state = ActorState.DRINK_DROP;
					scheduler.schedule(new Leave(getItemCarried(), messageRenderer), this, 1);
					holdingCanteen = null;
					break;
				}
				// keep drinking
				scheduler.schedule(new Drink(holdingCanteen, messageRenderer), this, 1);
				break;
			case DRINK_DROP:
				// pick up dropped item and resume patrolling
				scheduler.schedule(new Take(droppedItem, messageRenderer), this, 1);
				state = ActorState.PATROL_AND_ATTACK;
				droppedItem = null;
				break;
			case TRAIN:
				state = ActorState.PATROL_AND_ATTACK;
				break;
		}
	}

}
