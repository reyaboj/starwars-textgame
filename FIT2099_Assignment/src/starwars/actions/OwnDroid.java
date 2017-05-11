package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.actors.Droid;

/**
 * Affordance that allows droids to be owned.
 */
public class OwnDroid extends SWAffordance {

    /**
     * Initialize affordance to allow actors to own droids.
     *
     * @param target
     * @param m
     */
    public OwnDroid(SWEntityInterface target, MessageRenderer m) {
        super(target, m);
        assert target instanceof Droid;  // precondition (java forces superclass constructor to be called first)
    }

    /**
     * Set droid's owner and display message on view.
     *
     * @param a the <code>SWActor</code> who performs this action.
     */
    @Override
    public void act(SWActor a) {
        ((Droid)target).setOwner(a);
        a.say(a.getShortDescription() + " now owns " + target.getShortDescription());
    }

    /**
     * Description of affordance.
     *
     * @return the description to be displayed
     */
    @Override
    public String getDescription() {
        return "Claim ownership of " + target.getShortDescription();
    }

    /**
     * A droid can only be owned if it has no owner.
     *
     * @param 	a the <code>SWActor</code> being queried
     * @return <code>True</code> if droid has owner, <code>False</code> otherwise
     */
    @Override
    public boolean canDo(SWActor a) {
        return !((Droid)target).hasOwner();
    }
}
