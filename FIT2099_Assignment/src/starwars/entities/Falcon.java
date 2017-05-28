package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWEntity;
import starwars.SWWorld;
import starwars.actions.FalconTravel;

/**
 * Millenium Falcon spaceship for travelling across maps.
 * @author Md Istiaque Al Jobayer
 */
public class Falcon extends SWEntity {
    private SWWorld.SWMap map;
    private SWWorld world;

    public Falcon(SWWorld.SWMap map, SWWorld w, MessageRenderer m) {
        super(m);
        this.map = map;
        this.world = w;
        setShortDescription("Millenium Falcon");
        setLongDescription("Millenium Falcon: Corellian YT-1300f light freighter");
        setSymbol("M");
    }

    public void connectToFalcon(Falcon f) {
        addAffordance(new FalconTravel(f, world, messageRenderer));
    }

    public SWWorld.SWMap getMap() {
        return map;
    }
}
