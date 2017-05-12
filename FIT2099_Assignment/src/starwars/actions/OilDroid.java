package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.OilCan;
import starwars.entities.actors.Droid;
import starwars.entities.actors.R2D2;

import static starwars.Capability.OIL;

/**
 * Oiling a droid.
 * @author Md Istiaque Al Jobayer
 */
public class OilDroid extends SWAffordance {
    /**
     * Create oiling affordance.
     * @param target
     * @param m
     */
    public OilDroid(SWEntityInterface target, MessageRenderer m) {
        super(target, m);
        assert target instanceof Droid;
    }

    /**
     * Check if actor can use the oiling affordance.
     *
     * @param 	a the <code>SWActor</code> being queried
     * @return <code>True</code> if the actor can oil, <code>False</code> otherwise
     */
    public boolean canDo(SWActor a) {
        SWEntityInterface item = a.getItemCarried();
        return a instanceof R2D2 || (item != null && item.hasCapability(OIL));
    }

    /**
     * Actually oil the droid, draining the oil can in the process. If actor is R2D2, no cost is incurred.
     * @param a the <code>SWActor</code> who performs this action.
     */
    public void act(SWActor a) {
        Droid oilTarget = (Droid) target;
        if (a instanceof R2D2) {
            oilTarget.setHitpoints(oilTarget.getMaxHitpoints());
        } else {
            OilCan can = (OilCan) a.getItemCarried();
            can.use();
            oilTarget.setHitpoints(oilTarget.getMaxHitpoints());
        }
    }

    /**
     * Description for oiling affordance.
     *
     * @return the description
     */
    public String getDescription() {
        return "Oil the droid: " + getTarget().getShortDescription();
    }
}
