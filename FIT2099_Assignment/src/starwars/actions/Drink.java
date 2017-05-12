package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.Drinkable;

/**
 * Drink affordance for canteens.
 * @author Syed Arham Ali Rizvi
 */
public class Drink extends SWAffordance {

    /**
     * Create drink affordance for target.
     * @param target
     * @param m
     */
    public Drink(SWEntityInterface target, MessageRenderer m) {
        super(target, m);
        assert target instanceof Drinkable;
    }

    public boolean canDo(SWActor a) {
        return a.getHitpoints() < a.getMaxHitpoints() && !((Drinkable)target).isEmpty();
    }

    /**
     * Drink from the drinkable and heal the actor.
     */
    @Override
    public void act(SWActor a) {
        a.setHitpoints(a.getHitpoints() + 20);
        ((Drinkable)target).drink();
    }

    /**
     * Get description for drink affordance.
     * @return the description
     */
    @Override
    public String getDescription() {
        return "Drink from " + getTarget().getShortDescription();
    }
}
