package com.makeagame.first.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.makeagame.core.Bootstrap;
import com.makeagame.firstgame.MakeAGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(Bootstrap.screamWidth(), Bootstrap.screamHeight());
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new MakeAGame().getEngine();
        }
}