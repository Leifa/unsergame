package de.malteundleif.unsergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

public class GameOverScreen extends ScreenAdapter {
    
    UnserGame game;
    int score;
    
    public GameOverScreen(UnserGame game, int score) {
        this.game = game;
        this.score = score;
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Keys.SPACE)) game.setScreen(new PlayScreen(game));;
        
        Gdx.gl.glClearColor(0.06f, 0.31f, 0.55f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.font.draw(game.batch, "Game Over!", 590, 390);
        game.font.draw(game.batch,  "Your score is "+score, 590, 360);
        game.font.draw(game.batch,  "To restart press space", 570, 280);
        game.batch.end();
    }

}
