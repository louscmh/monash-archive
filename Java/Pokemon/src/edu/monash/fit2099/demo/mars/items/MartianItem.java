package edu.monash.fit2099.demo.mars.items;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.items.Item;

public class MartianItem extends Item{

	public MartianItem(String name, char displayChar, boolean portable) {
		super(name, displayChar, portable);
	}

	public void addSampleAction(Action newAction){
		this.addAction(newAction);
	}

}
