package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.actors.BenKenobi;

/**
 * Force user training affordance.
 */
public class TrainForce extends SWAffordance {
    /**
     * Create force training affordance.
     * @param target
     * @param m
     */
    public TrainForce(SWEntityInterface target, MessageRenderer m) {
        super(target, m);
        assert target instanceof BenKenobi;
    }

    /**
     * Check if actor can be trained.
     * @param a the actor
     */
    public boolean canDo(SWActor a) {
        return a.getForceLevel() == SWActor.Force.UNTRAINED;
    }

    /**
     * Train the actor.
     * @param a the actor to train
     */
    public void act(SWActor a) {
        a.setForceLevel(SWActor.Force.TRAINED);
    }

    /**
     * Force training affordance description.
     * @return the description
     */
    public String getDescription() {
        return target.getShortDescription() + " offers to train you in force";
    }
}
