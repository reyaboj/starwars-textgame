package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.Team;

/**
 * Admiral Ackbar.
 * @author Md Istiaque Al Jobayer
 */
public class Ackbar extends SWActor {
    /**
     * Create Admiral Ackbar.
     * @param m message renderer
     * @param world the world
     */
    public Ackbar(MessageRenderer m, SWWorld world) {
        super(Team.GOOD, 1000, m, world);
        setSymbol("ACK");
        setLongDescription("Admiral Ackbar, a mon calamari");
        setShortDescription("Admiral Ackbar");
    }

    /**
     * Admiral Ackbar just stands and says "It's a trap!" 10% of the time.
     */
    @Override
    public void act() {
        if (isDead())
            return;

        if (Math.random() < 0.10)
            say("It's a trap!");
    }
}
