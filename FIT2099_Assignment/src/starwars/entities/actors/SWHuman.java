package starwars.entities.actors;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Attack;

import java.util.Arrays;
import java.util.Optional;

/**
 * Humanoid actor.
 * @author Syed Arham Ali Rizvi
 */
public class SWHuman extends SWActor {
    public SWHuman(Team team, int hitpoints, MessageRenderer m, SWWorld world) {
        super(team, hitpoints, m, world);
        Optional<Affordance> attackAff = Arrays.stream(this.getAffordances())
                .filter(a -> a instanceof Attack).findFirst();
        if (attackAff.isPresent())
            this.removeAffordance(attackAff.get());
    }

    @Override
    public void act() {
        // do nothing
    }
}
