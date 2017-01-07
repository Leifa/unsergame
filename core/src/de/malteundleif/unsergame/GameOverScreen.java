package de.malteundleif.unsergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class GameOverScreen extends ScreenAdapter {
    
    UnserGame game;
    int score;
    
    boolean inputMode;
    
    String name;
    
    float text1X, text2X, text3X;
    float[] scoresX;
    
    boolean flash;
    int flashCounter;
    
    public GameOverScreen(UnserGame game, int score) {
        this.game = game;
        this.score = score;
        name = "";
        if (game.highScoreList.isScoreGoodEnough(score)) {
            inputMode = true;
        }
        calculateCoordinates();
    }
    
    @Override
    public void render(float delta) {
        flashCounter++;
        if (flashCounter >= 5) {
            flashCounter = 0;
            flash = !flash;
        }
        if (inputMode) {
            if (Gdx.input.isKeyJustPressed(Keys.ANY_KEY)) {
                if (Gdx.input.isKeyJustPressed(Keys.A)) name += "A";
                else if (Gdx.input.isKeyJustPressed(Keys.B)) name += "B";
                else if (Gdx.input.isKeyJustPressed(Keys.C)) name += "C";
                else if (Gdx.input.isKeyJustPressed(Keys.D)) name += "D";
                else if (Gdx.input.isKeyJustPressed(Keys.E)) name += "E";
                else if (Gdx.input.isKeyJustPressed(Keys.F)) name += "F";
                else if (Gdx.input.isKeyJustPressed(Keys.G)) name += "G";
                else if (Gdx.input.isKeyJustPressed(Keys.H)) name += "H";
                else if (Gdx.input.isKeyJustPressed(Keys.I)) name += "I";
                else if (Gdx.input.isKeyJustPressed(Keys.J)) name += "J";
                else if (Gdx.input.isKeyJustPressed(Keys.K)) name += "K";
                else if (Gdx.input.isKeyJustPressed(Keys.L)) name += "L";
                else if (Gdx.input.isKeyJustPressed(Keys.M)) name += "M";
                else if (Gdx.input.isKeyJustPressed(Keys.N)) name += "N";
                else if (Gdx.input.isKeyJustPressed(Keys.O)) name += "O";
                else if (Gdx.input.isKeyJustPressed(Keys.P)) name += "P";
                else if (Gdx.input.isKeyJustPressed(Keys.Q)) name += "Q";
                else if (Gdx.input.isKeyJustPressed(Keys.R)) name += "R";
                else if (Gdx.input.isKeyJustPressed(Keys.S)) name += "S";
                else if (Gdx.input.isKeyJustPressed(Keys.T)) name += "T";
                else if (Gdx.input.isKeyJustPressed(Keys.U)) name += "U";
                else if (Gdx.input.isKeyJustPressed(Keys.V)) name += "V";
                else if (Gdx.input.isKeyJustPressed(Keys.W)) name += "W";
                else if (Gdx.input.isKeyJustPressed(Keys.X)) name += "X";
                else if (Gdx.input.isKeyJustPressed(Keys.Y)) name += "Y";
                else if (Gdx.input.isKeyJustPressed(Keys.Z)) name += "Z";
                else if (Gdx.input.isKeyJustPressed(Keys.SPACE)) name += " ";
                else if (Gdx.input.isKeyJustPressed(Keys.BACKSPACE)) {
                    if (name.length() > 0) name = name.substring(0, name.length()-1);
                }
                else if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
                    game.highScoreList.makeEntry(name, score);
                    game.highScoreList.save();
                    inputMode = false;
                    calculateCoordinates();
                }
                if (name.length() > 10) name = name.substring(0, 10);
            }
        } else {
            if (Gdx.input.isKeyPressed(Keys.SPACE)) game.setScreen(new PlayScreen(game));
        }
        
        Gdx.gl.glClearColor(0.06f, 0.31f, 0.55f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.bigFont.draw(game.batch, "Game Over!", text1X, 730);
        game.font.draw(game.batch,  "Your score is "+score, text2X, 640);        
        if (inputMode) {
            if (flash) {
                game.fontWhite.draw(game.batch, "Enter your name: " + name, text2X-50, 600);
            } else {
                game.font.draw(game.batch, "Enter your name: " + name, text2X-50, 600);
            }            
        }        
        for (int i = 0; i < HighScoreList.NUMBER_OF_SCORES; i++) {
            if (game.highScoreList.scores[i] >= 0) {
                game.font.draw(game.batch, (i+1) + ".", 400, 520 - 30*i);
                game.font.draw(game.batch, game.highScoreList.names[i], 450, 520 - 30*i);
                game.font.draw(game.batch, "" + game.highScoreList.scores[i], scoresX[i], 520 - 30*i);
            }
        }
        game.font.draw(game.batch,  "To restart press space", text3X, 100);
        game.batch.end();
    }
    
    private void calculateCoordinates() {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(game.bigFont, "Game Over!");
        text1X = (1200 - layout.width)/2;
        layout.setText(game.font, "Your score is "+score);
        text2X = (1200 - layout.width)/2;
        layout.setText(game.font, "To restart press space");
        text3X = (1200 - layout.width)/2;
        scoresX = new float[HighScoreList.NUMBER_OF_SCORES];
        for (int i = 0; i < HighScoreList.NUMBER_OF_SCORES; i++) {
            layout.setText(game.font, "" + game.highScoreList.scores[i]);
            scoresX[i] = 800 - layout.width;
        }
    }

}
