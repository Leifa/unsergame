package de.malteundleif.unsergame;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

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
			if (m>4) m=0;
		}
		else if (Gdx.input.isKeyJustPressed(Keys.UP)) {
			m--;
			if (m<0) m=4;
		}


		switch (m) {
		case 0:
			game.fontWhite.draw(game.batch,  "Start Game", 520, 600);
			game.font.draw(game.batch,  "Instructions", 515, 500);
			game.font.draw(game.batch,  "Highscores", 520, 400);
			game.font.draw(game.batch,  "Options", 545, 300);
			game.font.draw(game.batch,  "Quit", 570, 200);
			break;

		case 1:
			game.font.draw(game.batch,  "Start Game", 520, 600);
			game.fontWhite.draw(game.batch,  "Instructions", 515, 500);
			game.font.draw(game.batch,  "Highscores", 520, 400);
			game.font.draw(game.batch,  "Options", 545, 300);
			game.font.draw(game.batch,  "Quit", 570, 200);
			break;

		case 2:
			game.font.draw(game.batch,  "Start Game", 520, 600);
			game.font.draw(game.batch,  "Instructions", 515, 500);
			game.fontWhite.draw(game.batch,  "Highscores", 520, 400);
			game.font.draw(game.batch,  "Options", 545, 300);
			game.font.draw(game.batch,  "Quit", 570, 200);
			break;

		case 3:
			game.font.draw(game.batch,  "Start Game", 520, 600);
			game.font.draw(game.batch,  "Instructions", 515, 500);
			game.font.draw(game.batch,  "Highscores", 520, 400);
			game.fontWhite.draw(game.batch,  "Options", 545, 300);
			game.font.draw(game.batch,  "Quit", 570, 200);
			break;

		case 4:
			game.font.draw(game.batch,  "Start Game", 520, 600);
			game.font.draw(game.batch,  "Instructions", 515, 500);
			game.font.draw(game.batch,  "Highscores", 520, 400);
			game.font.draw(game.batch,  "Options", 545, 300);
			game.fontWhite.draw(game.batch,  "Quit", 570, 200);
			break;

		default:
			break;
		}
        game.batch.end();


		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			switch (m) {
			case 0:
				game.setScreen(new PlayScreen(game));
				break;

			case 1:
				break;

			case 2:
				game.setScreen(new HighScoreScreen(game));
				break;

			case 3:
				break;

			case 4:
				System.exit(0);
				break;

			default:
				break;							
			}
		}
	}
}
