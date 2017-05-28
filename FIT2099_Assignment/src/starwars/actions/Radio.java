package starwars.actions;

import java.util.List;
import java.util.Optional;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.entities.actors.Player;
import starwars.entities.actors.StormTroopers;

public class Radio extends SWAction {
	private SWWorld world;
	
	public Radio(SWWorld world, MessageRenderer m) {
		super(m);
		this.world = world;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		return a instanceof StormTroopers;
	}

	@Override
	public void act(SWActor a) {
		// TODO Auto-generated method stub
		StormTroopers st = new StormTroopers(messageRenderer, world);
		SWAction.getEntitymanager().setLocation(st, SWAction.getEntitymanager().whereIs(a));
		a.say(a.getShortDescription() + " radios for backup");
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Radio for backup";
	}

	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		return 1;
	}

}
