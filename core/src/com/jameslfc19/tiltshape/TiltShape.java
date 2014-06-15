package com.jameslfc19.tiltshape;

import com.badlogic.gdx.Game;


public class TiltShape extends Game {
	
	@Override
	public void create () {
		setScreen(new LoadingScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
}
