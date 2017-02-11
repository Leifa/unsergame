package de.malteundleif.unsergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Dodger {

	int width;
	int height;
	float x;
	float y;
	
	float speedx;
	float speedxmin, speedxmax;
	float acc;
	float dec;
	
	public Dodger() {
		width = 120;
		height = 15;
        x = (UnserGame.WIDTH - width) / 2;
        y = 100;
		speedx = 0;
		setAcceleration(0.25f);
		setMaxSpeed(12);
	}
	
	public void update() {
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			if (speedx < 0) {
				speedx += dec;
			} else speedx += acc;
			if (speedx > speedxmax) speedx = speedxmax;

		} else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			if (speedx > 0) {
				speedx -= dec;
			} else speedx -= acc;
			if (speedx < speedxmin) speedx = speedxmin;
			
		} else {
			if (speedx < 0) {
				speedx += acc;
				if (speedx > 0) speedx = 0;
			} else if (speedx > 0) {
				speedx -= acc;
				if (speedx < 0) speedx = 0;
			}
		}
		
		x += speedx;
		if (x >= UnserGame.WIDTH-(width+PlayScreen.BORDER)) {
			speedx = 0;
			x = UnserGame.WIDTH-width-PlayScreen.BORDER;
		}
		if (x <= PlayScreen.BORDER) {
			speedx = 0;
			x = PlayScreen.BORDER;
		}
	}
	
	public void setAcceleration(float newAcc) {
		this.acc = newAcc;
		this.dec = acc*2;
		
	}
	public void setMaxSpeed(float newMax) {
		this.speedxmax = newMax;
		this.speedxmin = -speedxmax;
	}
	
}
