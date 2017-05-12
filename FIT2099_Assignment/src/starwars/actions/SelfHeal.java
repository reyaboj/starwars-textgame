package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.entities.actors.R2D2;

/**
 * Self-healing action.
 * @author Md Istiaque Al Jobayer
 */
public class SelfHeal extends SWAction {
    /**The message to display on performing the action*/
    private String message;

    /**The number of hitpoints to heal*/
    private final int heal;

    /**
     * Create self-healing action.
     * @param healpoints the number of points to heal
     * @param m the
     */
    public SelfHeal(int healpoints, String msg, MessageRenderer m) {
        super(m);
        message = msg;
        heal = healpoints;
    }

    /**
     * Heal self.
     */
    @Override
    public void act(SWActor a) {
        if (a.getHitpoints() + heal > a.getMaxHitpoints())
            a.setHitpoints(a.getMaxHitpoints());
        else
            a.setHitpoints(a.getHitpoints() + heal);
    }

    /**
     * Check if actor can heal self.
     */
    @Override
    public boolean canDo(SWActor a) {
        return a instanceof R2D2;
    }

    /**
     * Get description of self-healing.
     * @return the message
     */
    public String getDescription() {
        return this.message;
    }

    /**
     * Get duration of self-heal.
     * @return the duration
     */
    @Override
    public int getDuration() {
        return 1;
    }

}
