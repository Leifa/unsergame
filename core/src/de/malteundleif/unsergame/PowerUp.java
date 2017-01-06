package de.malteundleif.unsergame;

import java.util.Random;


public class PowerUp extends FallingObject {
	int type;
	public static final int LIFEPLUS = 0;
	public static final int ACCBOOST = 1;
	public static final int SMALL = 2;
	public static final int SLOWMO = 3;
	public static final int BOMB = 4;


	public PowerUp(int type) {
		super();
		this.type = type;
		Random r = new Random();
		x = 1200*r.nextFloat();
		radius = 20;
		speedy = (-5f+2f*r.nextFloat());
		speedx = 0;
	}

}