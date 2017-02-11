package de.malteundleif.unsergame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.malteundleif.unsergame.UnserGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = UnserGame.WIDTH;
		config.height = UnserGame.HEIGHT;
		new LwjglApplication(new UnserGame(), config);
	}
}
