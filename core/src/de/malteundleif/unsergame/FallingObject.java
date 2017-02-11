package de.malteundleif.unsergame;

import java.util.Random;

public class FallingObject {

	float x;
	float y;
	float radius;
	float speedx;
	float speedy;


	public FallingObject () {
		y = UnserGame.HEIGHT;
		Random r = new Random();
		x = UnserGame.WIDTH*r.nextFloat();
	}


	public void update(boolean slowmo) {
		if (slowmo) {
			x += speedx*0.2;
			y += speedy*0.2;
		} else {
			x += speedx;
			y += speedy;
		}
	}

	public boolean checkCollision(Dodger dodger) {
		boolean coll = false;
		if (dodger.x <= x && x <= dodger.x+dodger.width && dodger.y-radius <= y && y <= dodger.y+dodger.height+radius) {
			coll=true;
		}
		if (dodger.x-radius <= x && x <= dodger.x+dodger.width+radius && dodger.y <= y && y <= dodger.y+dodger.height) {
			coll=true;
		}

		if ((x-dodger.x)*(x-dodger.x)+(y-dodger.y)*(y-dodger.y)<=radius*radius) {
			coll=true;
		}
		if ((x-dodger.x)*(x-dodger.x)+(y-(dodger.y+dodger.height))*(y-(dodger.y+dodger.height))<=radius*radius) {
			coll=true;
		}
		if ((x-(dodger.x+dodger.width))*(x-(dodger.x+dodger.width))+(y-(dodger.y+dodger.height))*(y-(dodger.y+dodger.height))<=radius*radius) {
			coll=true;
		}
		if ((x-dodger.x)*(x-dodger.x)+(y-(dodger.y+dodger.height))*(y-(dodger.y+dodger.height))<=radius*radius) {
			coll=true;
		}
		return coll;
	}
}