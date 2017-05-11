package starwars.entities.actors;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.matter.ActionInterface;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.*;
import starwars.actions.Move;
import starwars.actions.OwnDroid;

import java.util.List;

/**
 * Droids which have a default behavior designated at creation time and if they have an owner, they follow that owner.
 */
public class Droid extends SWActor {
    /** The owning actor */
    private SWActor owner;

    /**
     * Create a droid with an owner, team, initial hitpoints, and the world and message renderer object.
     *
     * @param owner the owner to follow
     * @param team the team of the droid
     * @param hitpoints the initial health
     * @param m the message rendering object to display messages on the View
     * @param world the containing world
     */
    public Droid(SWActor owner, Team team, int hitpoints, MessageRenderer m, SWWorld world) {
        super(team, hitpoints, m, world);
        setOwner(owner);
        addAffordance(new OwnDroid(this, m));
    }

    /**
     * Check if this is owned.
     *
     * @return <code>True</code> if owned, <code>False</code> otherwise.
     */
    public boolean hasOwner() {
        return owner != null;
    }

    /**
     * The droid's behavior. It can be owned, or free. If it is free, it does whatever is dictated by the
     * @{link actDefault} method. If it is owned, it follows the owner.
     */
    public void act() {
        if (hasOwner())
            scheduler.schedule(follow(), this, 1);
        else
            actDefault();
    }

    /**
     * The droid's default behavior. By default it simply reports the position on the map. This can be overriden in
     * subclasses to implement different defaults.
     */
    public void actDefault() {
        say(getShortDescription() + " is at " + world.getEntityManager().whereIs(this).getShortDescription());
    }

    /**
     * Obtain the action that moves the droid to the owner's location.
     *
     * @return the action to move the droid to the owner
     */
    private ActionInterface follow() {
        assert owner != null;

        Direction moveDirection = null;  // to store which direction to move
        EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
        SWLocation loc = em.whereIs(this);
        boolean found = false;  // assume we don't know where owner is
        for (Direction d : Grid.CompassBearing.values()) {  // look around us
            if (found)
                break;

            Location neighbor = loc.getNeighbour(d);
            List<SWEntityInterface> neighborObjs = world.getEntityManager().contents((SWLocation)neighbor);

            if (neighborObjs == null)  // FIXME: ugly hack because engine code is buggy; docs say never returns null
                continue;

            for (SWEntityInterface entity : neighborObjs) {  // check all entities at that location for owner
                if (entity == owner) {
                    moveDirection = d;
                    found = true;
                }
            }
        }

        return new Move(moveDirection, messageRenderer, world);
    }

    /**
     * Get the droid's current owner.
     *
     * @return
     */
    public SWActor getOwner() {
        return owner;
    }

    /**
     * Set the droid's owner.
     * @param owner
     */
    public void setOwner(SWActor owner) {
        this.owner = owner;
        if (hasOwner())
            setTeam(owner.getTeam());
    }
}
