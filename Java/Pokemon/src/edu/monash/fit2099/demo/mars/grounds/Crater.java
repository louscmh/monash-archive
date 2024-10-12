package edu.monash.fit2099.demo.mars.grounds;

import edu.monash.fit2099.demo.mars.DemoCapabilities;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

public class Crater extends Ground {
	private int age;

	public Crater() {
		super('o');
		age = 0;
	}

	@Override
	public void tick(Location location){
		age++;
		if(age == 10){
			setDisplayChar('#');
		}else if (age == 20){
			setDisplayChar('&');
		}
	}
	
	@Override
	public boolean canActorEnter(Actor a) {
		return a.hasCapability(DemoCapabilities.SPACETRAVELLER);
	}
}