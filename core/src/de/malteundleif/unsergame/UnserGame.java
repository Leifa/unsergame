package de.malteundleif.unsergame;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class UnserGame extends ApplicationAdapter {

	public static final int BORDER = 20;
	public static final float POWERUP_CHANCE = 1/300f;
	public static final float START_DEATHBALL_CHANCE = 0.05f;
	public static final int LEVEL_DURATION = 1200;
	public static final int NUMBER_OF_POWERUPS = 5;
	public static final int LIFE_MAX = 5;

	SpriteBatch batch;
	ShapeRenderer sr;
	Texture imgHeart, imgHeartEmpty, imgLifePlus, imgSmall, imgAccBoost, imgSlowmo, imgBomb, imgSmoke1, imgSmoke2;
	Sound soundLifePlus, soundLifeMinus, soundSmall, soundAccBoost, soundSlowmo;
	Random r;
	Dodger dodger;
	int life;
	int score;
	boolean gameover;
	BitmapFont font;
	int framecounter;
	float chance;
	int level;
	int slowmoTimer;
	int bombTimer;
	boolean bombActivated = false;
	
	Color[] colors = {
			new Color(0.75f,1.00f,0.24f,1),
			new Color(0.60f,0.80f,0.20f,1),
			new Color(0.50f,0.70f,0.18f,1),
			new Color(0.41f,0.55f,0.13f,1),
			new Color(0.13f,0.55f,0.13f,1),
			new Color(0.80f,0.80f,0.00f,1),
			new Color(0.93f,0.93f,0.00f,1),
			new Color(1.00f,1.00f,0.00f,1),
			new Color(1.00f,0.84f,0.00f,1),
			new Color(1.00f,0.76f,0.15f,1),
			new Color(1.00f,0.45f,0.34f,1),
			new Color(0.80f,0.36f,0.27f,1),
			new Color(0.80f,0.22f,0.00f,0),
			new Color(0.55f,0.11f,0.38f,0)
	};

	Color framecolor;


	ArrayList<FallingObject> fallingObjectList;

	@Override
	public void create () {
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		imgHeart = new Texture("Heart.png");
		imgHeartEmpty = new Texture("HeartEmpty.png");
		imgLifePlus = new Texture("Life Plus.png");
		imgSmall = new Texture("small.png");
		imgAccBoost = new Texture("accboost.png");
		imgSlowmo = new Texture("slowmo.png");
		imgBomb = new Texture("bomb.png");
		imgSmoke1 = new Texture("smoke1.png");
		imgSmoke2 = new Texture("smoke2.png");
		soundLifePlus = Gdx.audio.newSound(Gdx.files.internal("Life Plus.mp3"));
		soundLifeMinus = Gdx.audio.newSound(Gdx.files.internal("Life Minus.mp3"));
		soundSmall = Gdx.audio.newSound(Gdx.files.internal("small.mp3"));
		soundAccBoost = Gdx.audio.newSound(Gdx.files.internal("accboost.mp3"));
		soundSlowmo = Gdx.audio.newSound(Gdx.files.internal("slowmo.mp3"));
		
		r = new Random();
		font = new BitmapFont();

		dodger = new Dodger();

		fallingObjectList = new ArrayList<FallingObject>();

		framecolor = colors[0];
		
		newgame();
	}

	@Override
	public void render () {
		framecounter++;
		if (gameover) {
			drawGameover();
			if (Gdx.input.isKeyPressed(Keys.SPACE)) newgame();
		}

		else {

			updatePositions();



			if (framecounter % LEVEL_DURATION == 0) {
				levelUp();

			}

			if (slowmoTimer > 0) {
				slowmoTimer--;
				if (r.nextFloat() <= chance / 5f) createDeathBall();
			} else if (r.nextFloat()<=chance) {
				createDeathBall();
			}

			if (r.nextFloat()<=POWERUP_CHANCE) {
				createPowerUp();
			}

			checkcollision();
			if (bombActivated) explode();

			drawGame();
		}

	}



	public void createDeathBall() {
		DeathBall ball = new DeathBall(framecounter);
		fallingObjectList.add(ball);
	}

	public void createPowerUp() {
		PowerUp powerUp = new PowerUp(r.nextInt(NUMBER_OF_POWERUPS));
		fallingObjectList.add(powerUp);
	}


	public void levelUp() {
		level++;
		chance += 0.015;
		dodger.setAcceleration(dodger.acc + 0.2f);
		dodger.setMaxSpeed(dodger.speedxmax + 2);
		if (level < colors.length) {
			framecolor = colors[level];
		}
	}



	public void updatePositions() {
		dodger.update();
		for (int i = 0; i < fallingObjectList.size(); i++) {
			boolean slowmo = false;
			if (slowmoTimer > 0 && fallingObjectList.get(i) instanceof DeathBall) slowmo = true;
			fallingObjectList.get(i).update(slowmo);
			if (fallingObjectList.get(i).y < - 25) {
				fallingObjectList.remove(i);
				i--;
				score++;
			}
		}
	}


	public void checkcollision() {
		for (int i = 0; i<fallingObjectList.size(); i++) {
			if (fallingObjectList.get(i).checkCollision(dodger)) {
				if (fallingObjectList.get(i) instanceof DeathBall) deathBallCollision(i);
				else if (fallingObjectList.get(i) instanceof PowerUp) powerUpCollision(i);
				i--;
			}
		}
	}

	public void deathBallCollision(int index) {
		life --;
		soundLifeMinus.play();
		fallingObjectList.remove(index);
		if (life==0) {
			gameover=true;	
		}
	}

	public void newgame () {
		gameover = false;
		dodger = new Dodger();
		fallingObjectList = new ArrayList<FallingObject>();
		life = 3;
		score = 0;
		framecounter = 0;
		slowmoTimer = 0;
		bombTimer =0;
		level = 0;
		framecolor = colors[0];
		chance = START_DEATHBALL_CHANCE;	
	}
	
	
	public void powerUpCollision(int index) {
		switch (((PowerUp)fallingObjectList.get(index)).type) {
		case PowerUp.LIFEPLUS:
			if (life < LIFE_MAX) {
				life++;
				soundLifePlus.play();
			}
			break;

		case PowerUp.ACCBOOST:
			dodger.setAcceleration(dodger.acc + 0.2f);
			dodger.setMaxSpeed(dodger.speedxmax + 2);
			soundAccBoost.play();
			break;

		case PowerUp.SMALL:
			if (dodger.width > 40) dodger.width -= 10;
			soundSmall.play();
			break;

		case PowerUp.SLOWMO:
			slowmoTimer = 390;
			soundSlowmo.stop();
			soundSlowmo.play();
			break;
			
		case PowerUp.BOMB:
			bombActivated = true;
			break;
		}
		fallingObjectList.remove(index);

	}

	public void explode() {
		soundLifeMinus.play();
		bombTimer = 90;
		for (int i = 0; i < fallingObjectList.size(); i++) {
			if (fallingObjectList.get(i) instanceof DeathBall) {
				fallingObjectList.remove(i);
				score += 10;
				i--;
			}
		}
		bombActivated = false;
	}
	
	
	public void drawGameover() {
		Gdx.gl.glClearColor(0.06f, 0.31f, 0.55f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		font.draw(batch, "Game Over!", 590, 390);
		font.draw(batch,  "Your score is "+score, 590, 360);
		font.draw(batch,  "To restart press space", 570, 280);
		batch.end();		
	}

	public void drawGame() {

		Gdx.gl.glClearColor(framecolor.r, framecolor.g, framecolor.b, framecolor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sr.begin(ShapeType.Filled);
		sr.setColor(0.11f, 0.29f, 0.45f, 1);
		sr.rect(BORDER,  BORDER,  1200-2*BORDER, 800-2*BORDER);
		sr.setColor(1, 1, 1, 1);
		sr.rect(dodger.x, dodger.y, dodger.width, dodger.height);
		for (int i = 0; i < fallingObjectList.size(); i++) {
			if (fallingObjectList.get(i) instanceof DeathBall) {
				sr.setColor(1, 1, 1, 1);
				sr.circle(fallingObjectList.get(i).x, fallingObjectList.get(i).y, fallingObjectList.get(i).radius);
			}


		}
		sr.end();
		
		batch.begin();
		for (int i=0; i<LIFE_MAX; i++) {
			batch.draw(imgHeartEmpty, 1145-i*45, 750, 30, 30);
		}
		for (int i=0; i<life; i++) {
			batch.draw(imgHeart, 1145-i*45, 750, 30, 30);
		}
		for (int i = 0; i < fallingObjectList.size(); i++) {
			if (fallingObjectList.get(i) instanceof PowerUp) {
				switch (((PowerUp)fallingObjectList.get(i)).type) {
				case PowerUp.LIFEPLUS:
					batch.draw(imgLifePlus, fallingObjectList.get(i).x - fallingObjectList.get(i).radius, fallingObjectList.get(i).y- fallingObjectList.get(i).radius, 40, 40);
					break;
				case PowerUp.SMALL:
					batch.draw(imgSmall, fallingObjectList.get(i).x - fallingObjectList.get(i).radius, fallingObjectList.get(i).y- fallingObjectList.get(i).radius, 40, 40);
					break;
				case PowerUp.ACCBOOST:
					batch.draw(imgAccBoost, fallingObjectList.get(i).x - fallingObjectList.get(i).radius, fallingObjectList.get(i).y- fallingObjectList.get(i).radius, 40, 40);
					break;
				case PowerUp.SLOWMO:
					batch.draw(imgSlowmo, fallingObjectList.get(i).x - fallingObjectList.get(i).radius, fallingObjectList.get(i).y- fallingObjectList.get(i).radius, 40, 40);
					break;
				case PowerUp.BOMB:
					batch.draw(imgBomb, fallingObjectList.get(i).x - fallingObjectList.get(i).radius, fallingObjectList.get(i).y - fallingObjectList.get(i).radius, 40, 40);
				}

			}
		}
		if (bombTimer > 0) {
			if (bombTimer > 30 && bombTimer <= 90) batch.draw(imgSmoke1, 950, 250, 150, 150);
			if (bombTimer > 24 && bombTimer <= 84) batch.draw(imgSmoke2, 250, 600, 140, 140);
			if (bombTimer > 18 && bombTimer <= 78) batch.draw(imgSmoke1, 100, 150, 150, 150);
			if (bombTimer > 12 && bombTimer <= 72) batch.draw(imgSmoke2, 750, 500, 120, 120);
			if (bombTimer > 06 && bombTimer <= 66) batch.draw(imgSmoke1, 300, 050, 130, 130);
			if (bombTimer > 00 && bombTimer <= 60) batch.draw(imgSmoke2, 550, 400, 130, 130);
			bombTimer --;
		}
		font.draw(batch, "Score: "+score, 35, 770);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		
		imgLifePlus.dispose();
		imgHeart.dispose();
	}
}
