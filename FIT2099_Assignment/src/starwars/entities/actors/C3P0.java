package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.Team;

/**
 * C3P0 Droid -- Human/Cyborg Relations
 */
public class C3P0 extends Droid {
    private String[] monologue = {
            "Artoo says that the chances of survival are 725 to 1. Oh dear...",
            "We're doomed.",
            "I am C-3PO, human-cyborg relations.",
            "R2D2 where are you?",
            "May the Force be with you.",
            "Where am I?",
            "Life is meaningless.",
            "Oh dear, I might be lost."
    };

    public C3P0(SWActor owner, Team team, int hitpoints, MessageRenderer m, SWWorld world) {
        super(owner, team, hitpoints, m, world);
    }

    @Override
    public void actDefault() {
        String randomMonologue = monologue[(int)(Math.random() * monologue.length)];
        say(getShortDescription() + " says: " + randomMonologue);
    }

    @Override
    public String getShortDescription() {
        return "C3P0";
    }

    @Override
    public String getLongDescription() {
        return "C3P0, Human-Cyborg Relations";
    }
}
