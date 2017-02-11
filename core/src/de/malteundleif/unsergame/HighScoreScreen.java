package de.malteundleif.unsergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class HighScoreScreen extends ScreenAdapter {

	UnserGame game;
	float[] scoresX;
	
	float leftBorder;
	float rightBorder;
	float text1X;
	float text2X;
	
	public HighScoreScreen(UnserGame game) {
		this.game = game;
        calculateCoordinates();
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0.06f, 0.31f, 0.55f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.bigFont.draw(game.batch, "Highest Scores!", text1X, 730);
		for (int i = 0; i < HighScoreList.NUMBER_OF_SCORES; i++) {
			if (game.highScoreList.scores[i] >= 0) {
				game.font.draw(game.batch, (i+1) + ".", leftBorder, 520 - 30*i);
				game.font.draw(game.batch, game.highScoreList.names[i], leftBorder + 50, 520 - 30*i);
				game.font.draw(game.batch, "" + game.highScoreList.scores[i], scoresX[i], 520 - 30*i);
			}
		}
        game.font.draw(game.batch,  "For menu press enter", text2X, 60);
		game.batch.end();
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) game.setScreen(new MenuScreen(game));
	}
	
    private void calculateCoordinates() {
        leftBorder = (UnserGame.WIDTH - 400) / 2;
        rightBorder = UnserGame.WIDTH - leftBorder;
        GlyphLayout layout = new GlyphLayout();
        layout.setText(game.bigFont, "Highest Scores!");
        text1X = (UnserGame.WIDTH - layout.width)/2;
        layout.setText(game.font, "For menu press enter");
        text2X = (UnserGame.WIDTH - layout.width)/2;
        scoresX = new float[HighScoreList.NUMBER_OF_SCORES];
        for (int i = 0; i < HighScoreList.NUMBER_OF_SCORES; i++) {
            layout.setText(game.font, "" + game.highScoreList.scores[i]);
            scoresX[i] = rightBorder - layout.width;
        }
    }
}
