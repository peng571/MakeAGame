package com.makeagame.first.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.makeagame.core.MakeAGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(960,540);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new MakeAGame().getApplication();
        }
}