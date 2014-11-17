package com.makeagame.first.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.makeagame.firstgame.Config;
import com.makeagame.firstgame.MakeAGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(Config.screamWidth(), Config.screamHeight());
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new MakeAGame();
        }
}