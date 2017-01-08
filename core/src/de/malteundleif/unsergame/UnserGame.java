package de.malteundleif.unsergame;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class UnserGame extends Game {

	SpriteBatch batch;
	ShapeRenderer sr;
    Texture imgHeart, imgHeartEmpty, imgLifePlus, imgSmall, imgAccBoost, imgSlowmo, imgBomb, imgSmoke1, imgSmoke2;
    Texture[] imgSkulls;
	Sound soundLifePlus, soundLifeMinus, soundSmall, soundAccBoost, soundSlowmo, soundLifePlus2;
	BitmapFont font, fontWhite, bigFont;
    Random random;
    HighScoreList highScoreList;

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
		imgSkulls = new Texture[6];
		for (int i = 0; i < 6; i++) imgSkulls[i] = new Texture("skull" + (i+1) + ".png");
		soundLifePlus = Gdx.audio.newSound(Gdx.files.internal("Life Plus.mp3"));
		soundLifePlus2 = Gdx.audio.newSound(Gdx.files.internal("lifeplus2.mp3"));
		soundLifeMinus = Gdx.audio.newSound(Gdx.files.internal("Life Minus.mp3"));
		soundSmall = Gdx.audio.newSound(Gdx.files.internal("small.mp3"));
		soundAccBoost = Gdx.audio.newSound(Gdx.files.internal("accboost.mp3"));
		soundSlowmo = Gdx.audio.newSound(Gdx.files.internal("slowmo.mp3"));
		
		random = new Random();
		generateFonts();
		
		highScoreList = new HighScoreList();
		highScoreList.load();
		
		this.setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
	    super.render();
	}
	
    private void generateFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("OpenSans-Bold.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-!.: ";
        parameter.size = 30;
        parameter.color = new Color(1f, 0.8f, 0.8f, 1);
        font = generator.generateFont(parameter);
        parameter.color = new Color(1f, 1f, 1f, 1);
        fontWhite = generator.generateFont(parameter);
        parameter.size = 55;
        parameter.color = new Color(1f, 0.8f, 0.8f, 1);
        bigFont = generator.generateFont(parameter);
        generator.dispose();
    }

	@Override
	public void dispose () {
		batch.dispose();
		
		imgLifePlus.dispose();
		imgHeart.dispose();
	}
}
