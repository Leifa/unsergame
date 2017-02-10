package de.malteundleif.unsergame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

public class MenuScreen extends ScreenAdapter {

	UnserGame game;
	int m=0;

	public MenuScreen(UnserGame game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0.06f, 0.31f, 0.55f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.bigFont.draw(game.batch, "Main Menu!", 440, 730);

		if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
			m++;
			if (m>2) m=0;
		}
		else if (Gdx.input.isKeyJustPressed(Keys.UP)) {
			m--;
			if (m<0) m=2;
		}

		if (m==0) game.fontWhite.draw(game.batch,  "Start Game", 520, 500);
		else game.font.draw(game.batch,  "Start Game", 520, 500);
		if (m==1) game.fontWhite.draw(game.batch,  "Highscores", 520, 400);
		else game.font.draw(game.batch,  "Highscores", 520, 400);
		if (m==2) game.fontWhite.draw(game.batch,  "Quit", 570, 300);
		else game.font.draw(game.batch,  "Quit", 570, 300);

        game.batch.end();


		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			switch (m) {
			case 0:
				game.setScreen(new PlayScreen(game));
				break;

			case 1:
				game.setScreen(new HighScoreScreen(game));
				break;

			case 2:
				System.exit(0);
				break;

			default:
				break;							
			}
		}
	}
}
