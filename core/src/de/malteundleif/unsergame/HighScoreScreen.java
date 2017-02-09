package de.malteundleif.unsergame;


import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class HighScoreScreen extends ScreenAdapter {

	UnserGame game;
	float[] scoresX;
	
	public HighScoreScreen(UnserGame game) {
		this.game = game;
        calculateCoordinates();
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0.06f, 0.31f, 0.55f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.bigFont.draw(game.batch, "Highest Scores!", 390, 730);
		for (int i = 0; i < HighScoreList.NUMBER_OF_SCORES; i++) {
			if (game.highScoreList.scores[i] >= 0) {
				game.font.draw(game.batch, (i+1) + ".", 400, 520 - 30*i);
				game.font.draw(game.batch, game.highScoreList.names[i], 450, 520 - 30*i);
				game.font.draw(game.batch, "" + game.highScoreList.scores[i], scoresX[i], 520 - 30*i);
			}
		}
        game.font.draw(game.batch,  "For menu press enter", 440, 60);
		game.batch.end();
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) game.setScreen(new MenuScreen(game));
	}

	private void calculateCoordinates() {
		GlyphLayout layout = new GlyphLayout();
		scoresX = new float[HighScoreList.NUMBER_OF_SCORES];
		for (int i = 0; i < HighScoreList.NUMBER_OF_SCORES; i++) {
			layout.setText(game.font, "" + game.highScoreList.scores[i]);
			scoresX[i] = 800 - layout.width;
		}
	}
}
