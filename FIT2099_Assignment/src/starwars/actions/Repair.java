package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.DroidParts;
import starwars.entities.actors.Droid;

/**
 * Repair affordance on droids.
 * @author Md Istiaque Al Jobayer
 */
public class Repair extends SWAffordance {
    public Repair(SWEntityInterface target, MessageRenderer m) {
        super(target, m);
        assert target instanceof Droid;
    }

    /**
     * Check if the repair can be done. At present, it can be done iff. the actor is carrying droid parts and the droid
     * target is disabled (i.e. dead).
     *
     * @param 	a the <code>SWActor</code> being queried
     * @return <code>True</code> if droid can be repaired, <code>False</code> otherwise
     */
    @Override
    public boolean canDo(SWActor a) {
        SWEntityInterface invItem = a.getItemCarried();
        Droid targetDroid = (Droid) getTarget();
        if (targetDroid.isDead() && invItem instanceof DroidParts)
            return true;
        return false;
    }

    /**
     * Description of repair affordance.
     * @return the description
     */
    @Override
    public String getDescription() {
        return "Repair " + getTarget().getShortDescription();
    }

    /**
     * Restore target droid to full health and use up the droid parts.
     *
     * @param a the <code>SWActor</code> who performs this action.
     */
    @Override
    public void act(SWActor a) {
        a.setItemCarried(null);
        Droid d = (Droid)getTarget();
        d.setHitpoints(d.getMaxHitpoints());
        a.say(a.getShortDescription() + " repaired " + d.getShortDescription());
    }
}
