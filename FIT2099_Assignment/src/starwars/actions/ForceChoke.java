package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.actors.DarthVader;
											
											//should implements SWActionInterface be here?
public class ForceChoke extends SWAffordance{

	public ForceChoke(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		return a instanceof DarthVader;
	}

	@Override
	public void act(SWActor a) {
		// TODO Auto-generated method stub
		SWEntityInterface target = this.getTarget();
		boolean targetIsActor = target instanceof SWActor;
		SWActor targetActor = null;
		
		if (targetIsActor) {
			targetActor = (SWActor) target;
		}
		
		
		if (targetIsActor) {
			target.takeDamage(50); //Force choke does 50 hitpoints damage
		}
		
		
		if (this.getTarget().getHitpoints() <= 0) {  // can't use isDead(), as we don't know that the target is an actor
			target.setLongDescription(target.getLongDescription() + ", that was killed in a fight");
						
			//remove the attack affordance of the dead actor so it can no longer be attacked
			targetActor.removeAffordance(this);

			
		}
		
	}

	/**
	 * A String describing what this <code>ForceChoke</code> action will do
	 * 
	 * @return String comprising "ForceChoke " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return " Force choke " + this.target.getShortDescription();
	}

}
