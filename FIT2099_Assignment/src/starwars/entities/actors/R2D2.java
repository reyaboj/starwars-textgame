package starwars.entities.actors;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Move;
import starwars.entities.actors.behaviors.Patrol;

/**
 * R2D2 droid.
 * @author Md Istiaque Al Jobayer
 */
public class R2D2 extends Droid {
    /**The patrol route*/
    private Patrol patrol;

    /**
     * Create R2D2 droid.
     *
     * @param owner the owner of the droid
     * @param team the team it belongs to
     * @param hitpoints the initial health
     * @param m the message renderer for printing to the view
     * @param world the containing world
     */
    public R2D2(SWActor owner, Team team, int hitpoints, MessageRenderer m, SWWorld world) {
        super(owner, team, hitpoints, m, world);
        Direction[] patrolPath = {
                CompassBearing.EAST, CompassBearing.EAST, CompassBearing.EAST,
                CompassBearing.EAST, CompassBearing.EAST,
                CompassBearing.WEST, CompassBearing.WEST, CompassBearing.WEST,
                CompassBearing.WEST, CompassBearing.WEST
        };
        patrol = new Patrol(patrolPath);
    }

    /**
     * R2D2's default behavior. If owned, this is not triggered. When not owned, R2D2 will patrol east 5 positions and
     * then west 5 positions. While patrolling, it will:
     * <ul>
     *     <li>disassemble any disabled droids (to parts) on the patrol path if its inventory is empty</li>
     *     <li>repair any disabled droids on the patrol path if it's holding droid parts from another droid</li>
     * </ul>
     */
    @Override
    public void actDefault() {
        scheduler.schedule(new Move(patrol.getNext(), messageRenderer, world), this, 1);
    }

    /**
     * Get short description for R2D2.
     * @return short description
     */
    @Override
    public String getShortDescription() {
        return "R2D2";
    }

    /**
     * Get long description for R2D2.
     * @return long description
     */
    @Override
    public String getLongDescription() {
        return "R2D2, Astromech Droid";
    }

}
