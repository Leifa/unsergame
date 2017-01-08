package de.malteundleif.unsergame;

import java.util.Random;

public class DeathBall extends FallingObject {

	int scullGraphic;
	
	public DeathBall (int framecounter, int scullGraphic) {
	    this.scullGraphic = scullGraphic;
		float factor = (3600+0.75f*framecounter)/3600f;
		Random r = new Random();
		x = 1200*r.nextFloat();
		radius = 4 + 20*r.nextFloat();
		speedy = (-6f+1f*r.nextFloat())*factor;
		speedx = (-1.5f+3f*r.nextFloat())*factor;
	}
	
}
