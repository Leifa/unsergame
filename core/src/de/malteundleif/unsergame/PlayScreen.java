package de.malteundleif.unsergame;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class PlayScreen extends ScreenAdapter {
    
    public static final int BORDER = 20;
    public static final float POWERUP_CHANCE = 1/300f;
    public static final float START_DEATHBALL_CHANCE = 0.05f;
    public static final int LEVEL_DURATION = 1200;
    public static final int NUMBER_OF_POWERUPS = 5;
    public static final int LIFE_START = 3;
    public static final int LIFE_MAX = 5;
    
    Dodger dodger;
    int life;
    int score;
    boolean gameover;
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
    
    UnserGame game;
    
    public PlayScreen(UnserGame game) {
        this.game = game;
        dodger = new Dodger();
        fallingObjectList = new ArrayList<FallingObject>();
        framecolor = colors[0];
        newgame();
    }

    @Override
    public void render(float delta) {
        framecounter++;        
        if (gameover) this.game.setScreen(new GameOverScreen(game, score));
        else {
            
            updatePositions();
            
            if (framecounter % LEVEL_DURATION == 0) levelUp();

            if (slowmoTimer > 0) {
                slowmoTimer--;
                if (game.random.nextFloat() <= chance / 5f) createDeathBall();
            } else if (game.random.nextFloat()<=chance) {
                createDeathBall();
            }
            if (bombTimer > 0) bombTimer--;

            if (game.random.nextFloat()<=POWERUP_CHANCE) createPowerUp();

            checkcollision();
            if (bombActivated) explode();

            drawGame();
        }
    }
    
    public void createDeathBall() {
        DeathBall ball = new DeathBall(framecounter, game.random.nextInt(6));
        fallingObjectList.add(ball);
    }

    public void createPowerUp() {
        PowerUp powerUp = new PowerUp(game.random.nextInt(NUMBER_OF_POWERUPS));
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
    
    public void leveldown() {
    	level--;
    	chance -= 0.015;
    	dodger.setAcceleration(dodger.acc - 0.2f);
    	dodger.setMaxSpeed(dodger.speedxmax - 2);
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
        game.soundLifeMinus.play();
        fallingObjectList.remove(index);
        if (life==0) {
            gameover=true;  
        }
    }

    public void newgame () {
        gameover = false;
        dodger = new Dodger();
        fallingObjectList = new ArrayList<FallingObject>();
        life = LIFE_START;
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
                game.soundLifePlus.play();
            } else {
                score += 10;
                game.soundLifePlus2.play();
            }
            break;

        case PowerUp.ACCBOOST:
            dodger.setAcceleration(dodger.acc + 0.2f);
            dodger.setMaxSpeed(dodger.speedxmax + 2);
            game.soundAccBoost.play();
            break;

        case PowerUp.SMALL:
            if (dodger.width > 40) {
                dodger.width -= 10;
                dodger.x += 5;
            }
            game.soundSmall.play();
            break;

        case PowerUp.SLOWMO:
        	if (framecounter%LEVEL_DURATION<390) leveldown();
            if (slowmoTimer>0) framecounter += slowmoTimer;
            framecounter -= 390;
        	slowmoTimer = 390;
            game.soundSlowmo.stop();
            game.soundSlowmo.play();
            break;
           
        case PowerUp.BOMB:
            bombActivated = true;
            break;
        }
        fallingObjectList.remove(index);

    }

    public void explode() {
        game.soundLifeMinus.play();
        bombTimer = 90;
        for (int i = 0; i < fallingObjectList.size(); i++) {
            if (fallingObjectList.get(i) instanceof DeathBall) {
                fallingObjectList.remove(i);
                score += 3;
                i--;
            }
        }
        bombActivated = false;
    }
    
    
    public void drawGame() {
        Gdx.gl.glClearColor(framecolor.r, framecolor.g, framecolor.b, framecolor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.sr.begin(ShapeType.Filled);
        game.sr.setColor(0.11f, 0.29f, 0.45f, 1);
        game.sr.rect(BORDER,  BORDER,  1200-2*BORDER, 800-2*BORDER);
        game.sr.setColor(1, 1, 1, 1);
        game.sr.rect(dodger.x, dodger.y, dodger.width, dodger.height);
        game.sr.end();
        
        game.batch.begin();
        for (int i=0; i<LIFE_MAX; i++) {
            game.batch.draw(game.imgHeartEmpty, 1145-i*45, 750, 30, 30);
        }
        for (int i=0; i<life; i++) {
            game.batch.draw(game.imgHeart, 1145-i*45, 750, 30, 30);
        }
        for (int i = 0; i < fallingObjectList.size(); i++) {
            if (fallingObjectList.get(i) instanceof PowerUp) {
                switch (((PowerUp)fallingObjectList.get(i)).type) {
                case PowerUp.LIFEPLUS:
                    game.batch.draw(game.imgLifePlus, fallingObjectList.get(i).x - fallingObjectList.get(i).radius, fallingObjectList.get(i).y- fallingObjectList.get(i).radius, 40, 40);
                    break;
                case PowerUp.SMALL:
                    game.batch.draw(game.imgSmall, fallingObjectList.get(i).x - fallingObjectList.get(i).radius, fallingObjectList.get(i).y- fallingObjectList.get(i).radius, 40, 40);
                    break;
                case PowerUp.ACCBOOST:
                    game.batch.draw(game.imgAccBoost, fallingObjectList.get(i).x - fallingObjectList.get(i).radius, fallingObjectList.get(i).y- fallingObjectList.get(i).radius, 40, 40);
                    break;
                case PowerUp.SLOWMO:
                    game.batch.draw(game.imgSlowmo, fallingObjectList.get(i).x - fallingObjectList.get(i).radius, fallingObjectList.get(i).y- fallingObjectList.get(i).radius, 40, 40);
                    break;
                case PowerUp.BOMB:
                    game.batch.draw(game.imgBomb, fallingObjectList.get(i).x - fallingObjectList.get(i).radius, fallingObjectList.get(i).y - fallingObjectList.get(i).radius, 40, 40);
                }
            } else if (fallingObjectList.get(i) instanceof DeathBall) {
                DeathBall db = ((DeathBall) fallingObjectList.get(i));
                game.batch.draw(game.imgSkulls[db.scullGraphic], db.x-db.radius, db.y-db.radius, 2*db.radius, 2*db.radius);
            }
        }
        if (bombTimer > 0) {
            if (bombTimer > 30 && bombTimer <= 90) game.batch.draw(game.imgSmoke1, 950, 250, 150, 150);
            if (bombTimer > 24 && bombTimer <= 84) game.batch.draw(game.imgSmoke2, 250, 600, 140, 140);
            if (bombTimer > 18 && bombTimer <= 78) game.batch.draw(game.imgSmoke1, 100, 150, 150, 150);
            if (bombTimer > 12 && bombTimer <= 72) game.batch.draw(game.imgSmoke2, 750, 500, 120, 120);
            if (bombTimer > 06 && bombTimer <= 66) game.batch.draw(game.imgSmoke1, 300, 050, 130, 130);
            if (bombTimer > 00 && bombTimer <= 60) game.batch.draw(game.imgSmoke2, 550, 400, 130, 130);
        }
        game.font.draw(game.batch, "Score: "+score, 35, 770);
        game.font.draw(game.batch, "frame: "+framecounter, 550, 80);
        game.batch.end();
    }

}
