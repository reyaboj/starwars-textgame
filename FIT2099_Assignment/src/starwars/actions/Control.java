package starwars.actions;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.*;

/**
 * Mind control affordance.
 * @author Syed Arham Ali Rizvi
 */
public class Control extends SWAffordance {
    /**The direction to control in*/
    private Direction direction;

    /**The world*/
    private SWWorld world;

    /**
     * Create mind control affordance.
     *
     * @param target the target actor to move
     * @param ctrlDir the direction to move in
     * @param m the message renderer
     * */
    public Control(SWEntityInterface target, Direction ctrlDir, MessageRenderer m, SWWorld w) {
        super(target, m);
        world = w;
        direction = ctrlDir;
    }

    public boolean canDo(SWActor a) {
        return SWAction.getEntitymanager().seesExit((SWEntityInterface) target, direction)
                && a.getForceLevel() != SWActor.Force.NONE
                && ((SWActor)target).getForceLevel() == SWActor.Force.NONE;
    }

    public void act(SWActor a) {
        world.moveEntity((SWActor)getTarget(), direction);
    }

    @Override
    public String getDescription() {
        return "Control " + target.getShortDescription() + ": move " + direction.toString();
    }
}
