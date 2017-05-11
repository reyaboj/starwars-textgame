package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWEntity;
import starwars.actions.Take;

/**
 * Disassembled droid.
 * @author Md Istiaque Al Jobayer
 */
public class DroidParts extends SWEntity {
    /**
     * Create droid parts.
     */
    public DroidParts(MessageRenderer m) {
        super(m);
        this.shortDescription = "Droid parts";
        this.longDescription = "Droid parts which can repair disabled droids";
        setSymbol("DP");
        addAffordance(new Take(this, m));
    }
}
