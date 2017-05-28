package starwars.entities.actors;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.matter.EntityInterface;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.*;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Mon Mothma.
 * @author Md Istiaque Al Jobayer
 */
public class MonMothma extends SWActor {

    /**
     * Mon Mothma's happy state
     *
     * He will be happy if Luke arrives with Princess Leia and R2D2
     * */
    private boolean happy = false;

    private String happyMessage = "I am happy";

    /**
     * Create Mon Mothma.
     *
     * @param m message renderer
     * @param world the world
     */
    public MonMothma(MessageRenderer m, SWWorld world) {
        super(Team.GOOD, 1000, m, world);
        setSymbol("MOT");
        setLongDescription("Mon Mothma");
        setShortDescription("Mon Mothma");
    }

    /**
     * Mon Mothma reacts to Luke arriving at his location by checking if Princess Leia and R2D2 are with him.
     * If they are not, he will complain.
     */
    @Override
    public void act() {
        EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
        Optional<SWEntityInterface> player = em.contents(em.whereIs(this)).stream()
                .filter(e -> e instanceof Player).findFirst();

        boolean companions = companionsFoundAround(em.whereIs(this));

        // scan around the player and complain if Leia and R2 aren't with him
        if (player.isPresent()) {
            if (!companions)
                say(getShortDescription() + " says: What are you doing here, farmboy? Bring us General Organa and the plans!");
            else {
                happy = true;
            }
        }
    }

    private boolean companionsFoundAround(SWLocation loc) {
        EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
        boolean foundLeia = false;
        boolean foundR2 = false;

        // check around every bearing
        for (Direction d : Grid.CompassBearing.values()) {
            Location dLoc = loc.getNeighbour(d);
            for (SWEntityInterface e : em.contents((SWLocation)dLoc)) {
                if (e instanceof PrincessLeia)
                    foundLeia = true;
                if (e instanceof R2D2)
                    foundR2 = true;
            }
        }

        return foundLeia && foundR2;
    }

    /**
     * Check whether Mon Mothma is happy with Luke.
     *
     * @return <code>True</code> if happy, <code>False</code> otherwise
     */
    public boolean isHappy() {
        return happy;
    }
}
