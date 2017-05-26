package starwars.entities.actors.behaviors;

import edu.monash.fit2099.simulator.matter.Affordance;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.actions.Leave;
import starwars.actions.Take;

import java.util.Arrays;
import java.util.Optional;

/**
 * Helper for picking up and dropping items.
 * @author Md Istiaque Al Jobayer
 */
public class ItemLeaveTake {
    /**
     * Get the take affordance for an entity.
     *
     * @param item the item
     * @return an optional affordance to take the item
     */
    public static Optional<Affordance> getTake(SWEntityInterface item) {
        return Arrays.stream(item.getAffordances())
                .filter(aff -> aff instanceof Take)
                .findFirst();
    }

    /**
     * Get the leave affordance for an entity.
     *
     * @param item the item
     * @return an optional affordance to leave the item
     */
    public static Optional<Affordance> getLeave(SWEntityInterface item) {
        return Arrays.stream(item.getAffordances())
                .filter(aff -> aff instanceof Leave)
                .findFirst();
    }
}
