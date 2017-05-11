package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.Team;

/**
 * C3P0 Droid -- Human/Cyborg Relations
 */
public class C3P0 extends Droid {

    /** Monologue pool to select from */
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

    /**
     * Initialize C3P0 droid.
     *
     * @param owner the owner
     * @param team the team it should belong to
     * @param hitpoints the initial hitpoints
     * @param m message renderer for displaying on the view
     * @param world the containing world
     */
    public C3P0(SWActor owner, Team team, int hitpoints, MessageRenderer m, SWWorld world) {
        super(owner, team, hitpoints, m, world);
    }

    /**
     * C3P0's default behavior. C3P0 has a 10% chance of saying something.
     */
    @Override
    public void actDefault() {
        if (Math.random() >= 0.1)  // 10% chance of saying something
            return;
        String randomMonologue = monologue[(int)(Math.random() * monologue.length)];
        say(getShortDescription() + " says: " + randomMonologue);
    }

    /**
     * C3P0's short description.
     * @return the description
     */
    @Override
    public String getShortDescription() {
        return "C3P0";
    }

    /**
     * C3P0's long description.
     * @return the description
     */
    @Override
    public String getLongDescription() {
        return "C3P0, Human-Cyborg Relations";
    }
}
