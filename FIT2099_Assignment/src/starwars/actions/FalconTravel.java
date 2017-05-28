package starwars.actions;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.*;
import starwars.entities.Falcon;

/**
 * Use the falcon to travel.
 * @author Md Istiaque Al Jobayer
 */
public class FalconTravel extends SWAffordance {
    private SWWorld world;

    /**
     * Create a falcon travel affordance that takes an actor across maps.
     * @param targetFalcon the destination falcon
     * @param w the world
     * @param m the message renderer
     */
    public FalconTravel(Falcon targetFalcon, SWWorld w, MessageRenderer m) {
        super(targetFalcon, m);
        world = w;
    }

    /**
     * Any actor can travel using falcon.
     *
     * @param 	a the <code>SWActor</code> being queried
     * @return
     */
    @Override
    public boolean canDo(SWActor a) { return true; }

    /**
     * Take the actor to the target map.
     *
     * @param a the <code>SWActor</code> who performs this action.
     */
    @Override
    public void act(SWActor a) {
        EntityManager<SWEntityInterface, SWLocation> em = SWAction.getEntitymanager();
        SWLocation targetLoc = em.whereIs(getTarget());

        SWLocation actorLoc = em.whereIs(a);
        // grab all followers and place them at target location
        for (Direction d : Grid.CompassBearing.values()) {
            SWLocation loc = (SWLocation) actorLoc.getNeighbour(d);
            for (SWEntityInterface e : em.contents(loc)) {
                if (e.getFollowed() == a) {
                    em.setLocation(e, targetLoc);
                }
            }
        }

        for (SWEntityInterface e : em.contents(actorLoc)) {
            if (e.getFollowed() == a)
                em.setLocation(e, targetLoc);
        }

        em.setLocation(a, targetLoc);
        a.resetMoveCommands(targetLoc);
        world.setCurrentMap(((Falcon)getTarget()).getMap());
        a.say("Travelling to " + targetLoc.getLongDescription());
    }

    @Override
    public String getDescription() {
        return "Falcon travel to " + world.getEntityManager().whereIs(getTarget()).getLongDescription();
    }
}
