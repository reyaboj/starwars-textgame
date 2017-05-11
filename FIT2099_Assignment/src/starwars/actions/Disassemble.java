package starwars.actions;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.*;
import starwars.entities.DroidParts;
import starwars.entities.actors.Droid;

/**
 * Droid disassemble affordance.
 * @author Md Istiaque Al Jobayer
 */
public class Disassemble extends SWAffordance {

    /**
     * Create droid disassemble affordance.
     *
     * @param target
     * @param m
     */
    public Disassemble(SWEntityInterface target, MessageRenderer m) {
        super(target, m);
        assert target instanceof Droid;
    }

    /**
     * Check if actor can disassemble the droid.
     * @return <code>True</code> if the droid is dead; <code>False</code> otherwise.
     */
    @Override
    public boolean canDo(SWActor a) {
        return ((Droid)getTarget()).isDead();
    }

    /**
     * Removes the droid from the map and replaces it with droid parts.
     */
    @Override
    public void act(SWActor a) {
        EntityManager<SWEntityInterface, SWLocation> em = SWAction.getEntitymanager();
        Droid droidTarget = (Droid)getTarget();
        SWLocation loc = em.whereIs(droidTarget);
        em.remove(droidTarget);
        em.setLocation(new DroidParts(messageRenderer), loc);
    }

    /**
     * Description for the disassemble affordance.
     */
    @Override
    public String getDescription() {
        return "Disassemble " + getTarget().getShortDescription();
    }
}
