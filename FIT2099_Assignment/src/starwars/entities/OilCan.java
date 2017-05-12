package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;

/**
 * Oil cans to oil droids.
 */
public class OilCan extends SWEntity {
    public OilCan(MessageRenderer m) {
        super(m);
        setHitpoints(100);
        capabilities.add(Capability.OIL);
    }

    public void use() {
        takeDamage(1);
        if (getHitpoints() <= 0)
            setHitpoints(1);
    }
}
