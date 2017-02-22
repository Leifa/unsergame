package de.malteundleif.unsergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class HighScoreList {
    
    public static final int NUMBER_OF_SCORES = 10;

    public int[] scores;
    public String[] names;
    
    public HighScoreList() {
        scores = new int[NUMBER_OF_SCORES];        
        names = new String[NUMBER_OF_SCORES];        
    }
    
    public void emptyList() {
        for (int i = 0; i < NUMBER_OF_SCORES; i++) {
            scores[i] = -1;
            names[i] = "";
        }
    }
    
    public void load() {
        Preferences prefs = Gdx.app.getPreferences("My Preferences");
        for (int i = 0; i < NUMBER_OF_SCORES; i++) {
            scores[i] = prefs.getInteger("score" + i, -1);
            names[i] = prefs.getString("name" + i, "");
        }        
    }
    
    public void save() {
        Preferences prefs = Gdx.app.getPreferences("My Preferences");
        for (int i = 0; i < NUMBER_OF_SCORES; i++) {
            prefs.putString("name" + i, names[i]);
            prefs.putInteger("score" + i, scores[i]);
        }
        prefs.flush();
    }
    
    public boolean isScoreGoodEnough(int score) {
        return score > scores[NUMBER_OF_SCORES-1];
    }
    
    public void makeEntry(String name, int score) {
        // Prüfen, ob der Score in die Liste kommt
        if (!isScoreGoodEnough(score)) return;
        // Platz ermitteln
        int place = NUMBER_OF_SCORES - 1;
        while (place > 0 && score > scores[place-1]) place--;
        // Alle schlechteren Scores einen Platz nach unten schieben
        for (int i = NUMBER_OF_SCORES - 2; i >= place; i--) {
            scores[i+1] = scores[i];
            names[i+1] = names[i];
        }
        // Score eintragen
        scores[place] = score;
        names[place] = name;
    }
    
    public void resetHighscoreList() {
    	for (int i = 0; i < NUMBER_OF_SCORES; i++) {
            scores[i] = 0000;
            names[i] = "";
        }
    }


}
