package edu.monash.fit2099.demo.conwayslife;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

/**
 * WARNING: this might not be the best design to follow :)
 */
public class Tree extends Ground {
	private int age = 0;

	public Tree() {
		super('+');
		addCapability(Status.ALIVE);
	}

	@Override
	public void tick(Location location) {
		super.tick(location);
		age++;
		if (age == 10)
			setDisplayChar('t');
		if (age == 20)
			setDisplayChar('T');
	}
}
