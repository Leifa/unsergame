package de.malteundleif.unsergame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class MenuScreen extends ScreenAdapter {

	UnserGame game;
	int m=0;
	
	float text1X;
	float text2X;
	float text3X;
	float text4X;

	public MenuScreen(UnserGame game) {
		this.game = game;
		calculateCoordinates();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.06f, 0.31f, 0.55f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.bigFont.draw(game.batch, "Main Menu!", text1X, 730);

		if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
			m++;
			if (m>2) m=0;
		}
		else if (Gdx.input.isKeyJustPressed(Keys.UP)) {
			m--;
			if (m<0) m=2;
		}

		if (m==0) game.fontWhite.draw(game.batch,  "Start Game", text2X, 500);
		else game.font.draw(game.batch,  "Start Game", text2X, 500);
		if (m==1) game.fontWhite.draw(game.batch,  "Highscores", text3X, 400);
		else game.font.draw(game.batch,  "Highscores", text3X, 400);
		if (m==2) game.fontWhite.draw(game.batch,  "Quit", text4X, 300);
		else game.font.draw(game.batch,  "Quit", text4X, 300);

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
	
    private void calculateCoordinates() {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(game.bigFont, "Main Menu!");
        text1X = (UnserGame.WIDTH - layout.width)/2;
        layout.setText(game.font, "Start Game");
        text2X = (UnserGame.WIDTH - layout.width)/2;
        layout.setText(game.font, "Highscores");
        text3X = (UnserGame.WIDTH - layout.width)/2;
        layout.setText(game.font, "Quit");
        text4X = (UnserGame.WIDTH - layout.width)/2;
    }
}
