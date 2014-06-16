package com.jameslfc19.tiltshape;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.jameslfc19.tiltshape.Assets.Fonts;

public class GameScreen implements Screen{
	Game game;
	SpriteBatch batch;
	Random random;
	
	protected OrthographicCamera camera;
	float height = 1700;
	float width = 1100;
	private float physicalWidth = Gdx.graphics.getWidth();
	private float physicalHeight = Gdx.graphics.getHeight();
	protected float viewportHeight;
	protected float viewportWidth;

	int score;
	float timelimit;
	int colour;
	int shape;
	boolean isPlaying = false;
	boolean isTouched = false;
	boolean invertedColour = false;
	
	float x;
	float y;
	
	int highscore = 0;
	
	//Sprites
	Sprite grid;
	Sprite scoreBox;
	Sprite highscoreBox;
	Sprite red;
	Sprite blue;
	Sprite green;
	Sprite circle_white;
	Sprite triangle_white;
	Sprite square_white;
	
	public class Colours{static final int RED = 2; static final int BLUE = 1; static final int GREEN = 0;}
	public class Shapes{static final int SQUARE = 0; static final int TRIANGLE = 1; static final int CIRCLE = 2;}
	
	HashMap<Integer, Sprite[]> shapes = new HashMap<Integer, Sprite[]>();
	
	public GameScreen(Game game){
		this.game = game;
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		random = new Random();
		camera.setToOrtho(false, viewportWidth, viewportHeight);
		score = 0;
		timelimit = 4;
		x = viewportWidth/2;
		y = viewportHeight/2;
		initImages();
	}

	@Override
	public void render(float delta) {
		if(invertedColour) Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
		else Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		if(isPlaying) gameLogic(delta);
		if(!isPlaying && Gdx.input.isTouched() && !isTouched) isPlaying = true;
		grid.draw(batch);
		scoreBox.draw(batch);
		highscoreBox.draw(batch);
		
		switch(colour){
			default: System.out.println("No colour");
			case 2: red.draw(batch);
			break;
			case 1: blue.draw(batch);
			break;
			case 0: green.draw(batch);
			break;
		}
		for(int i=0;i<=2;i++){
			Sprite[] sprite = shapes.get(i);
			for(int z=0;z<=2;z++){
				if(sprite[z] != null){
					sprite[z].draw(batch);
				}
			}
		}
		switch(shape){
		default: System.out.println("No shape");
		case 0: square_white.draw(batch);
		break;
		case 1: triangle_white.draw(batch);
		break;
		case 2: circle_white.draw(batch);
		break;
	}
		if(Gdx.input.isTouched() && !isTouched ){
			if(isValidPosition()){
				shapes.get(shape)[colour].setScale(1.2f);
				change();
				score++;
			} else {
				gameOver();
				shapes.get(shape)[colour].setScale(1f);
			}
		}
		if(invertedColour) Fonts.Bebas72.setColor(0.25f, 0.25f, 0.25f, 1);
		else Fonts.Bebas72.setColor(0.75f, 0.75f, 0.75f, 1);
		Fonts.Bebas72.draw(batch, "Colour", grid.getX()+grid.getWidth()-Fonts.Bebas72.getBounds("Colour").width, grid.getY()+grid.getHeight()+325);
		Fonts.Bebas72.draw(batch, "Time Limit", grid.getX(), grid.getY()+grid.getHeight()+325);
		Fonts.Bebas72.draw(batch, ""+(double)Math.round(timelimit * 10) / 10, grid.getX(), grid.getY()+grid.getHeight()+225);
		Fonts.Bebas72.draw(batch, "Score", grid.getX(), grid.getY()-50);
		Fonts.Bebas72.draw(batch, ""+score, scoreBox.getX()+scoreBox.getWidth()/2-Fonts.Bebas72.getBounds(""+score).width/2, grid.getY()-200);
		Fonts.Bebas72.draw(batch, "Highscore", grid.getX()+grid.getWidth()-Fonts.Bebas72.getBounds("Highscore").width, grid.getY()-50);
		Fonts.Bebas72.draw(batch, ""+highscore, highscoreBox.getX()+highscoreBox.getWidth()/2-Fonts.Bebas72.getBounds(""+highscore).width/2, grid.getY()-200);
		Fonts.Bebas72.setColor(Color.WHITE);
		if(!isPlaying)Fonts.Bebas72.draw(batch, "Click to play", viewportWidth/2-Fonts.Bebas72.getBounds("Click to play").width/2, viewportHeight/2-Fonts.Bebas72.getBounds("Click to play").height/2);
		batch.end();
		
		if(Gdx.input.isTouched()){
			isTouched = true;
		} else {
			isTouched = false;
		}
	}
	
	public void initImages(){
		grid = Assets.getSprite("grid");
		grid.setPosition(viewportWidth/2-grid.getWidth()/2, viewportHeight/2-grid.getHeight()/2);
		scoreBox = Assets.getSprite("scorebox");
		scoreBox.setPosition(grid.getX(), grid.getY()-325);
		highscoreBox = Assets.getSprite("scorebox");
		highscoreBox.setPosition(grid.getX()+grid.getWidth()-highscoreBox.getWidth(), grid.getY()-325);
		red = Assets.getSprite("red");
		red.setPosition(grid.getX()+grid.getWidth()-red.getWidth(), grid.getY()+grid.getHeight()+30);
		blue = Assets.getSprite("blue");
		blue.setPosition(red.getX(), red.getY());
		green = Assets.getSprite("green");
		green.setPosition(red.getX(), red.getY());
			
			Sprite[] squares = new Sprite[3];
			for(int i=0;i<=2;i++){
				Sprite square = Assets.getSprite("square_dark");
				square.setPosition(grid.getX()+115, grid.getY()+106+(307*(i)));
				squares[i] = square;
			}
			shapes.put(Shapes.SQUARE, squares);
			
			Sprite[] triangles = new Sprite[3];
			for(int i=0;i<=2;i++){
				Sprite triangle = Assets.getSprite("triangle_dark");
				triangle.setPosition(grid.getX()+413, grid.getY()+106+(307*(i)));
				triangles[i] = triangle;
			}
			shapes.put(Shapes.TRIANGLE, triangles);
			
			Sprite[] circles = new Sprite[3];
			for(int i=0;i<=2;i++){
				Sprite circle = Assets.getSprite("circle_dark");
				circle.setPosition(grid.getX()+720, grid.getY()+106+(307*(i)));
				circles[i] = circle;
			}
			shapes.put(Shapes.CIRCLE, circles);

		square_white = Assets.getSprite("square_white");
		square_white.setPosition(red.getX()-200, grid.getY()+grid.getHeight()+50);
		triangle_white = Assets.getSprite("triangle_white");
		triangle_white.setPosition(red.getX()-200, grid.getY()+grid.getHeight()+50);
		circle_white = Assets.getSprite("circle_white");
		circle_white.setPosition(red.getX()-200, grid.getY()+grid.getHeight()+50);
	}

	@Override
	public void resize(int width, int height) {
		physicalWidth = Gdx.graphics.getWidth();
		physicalHeight = Gdx.graphics.getHeight();
		calculateAspectRatio();
		camera.setToOrtho(false, viewportWidth, viewportHeight); 
		initImages();
	}
	
	private void calculateAspectRatio(){
		float aspect = width / height;

		if (physicalWidth / physicalHeight >= aspect) {
			viewportHeight = height;
			viewportWidth = viewportHeight * physicalWidth / physicalHeight;
		} else {
			viewportWidth = width;
			viewportHeight = viewportWidth * physicalHeight / physicalWidth;
		}
	}
	
	private void change(){
		shapes.get(shape)[colour].setScale(1);
		timelimit = 4f-(0.025f*score);
		int randomInt;
		do {
			randomInt = random.nextInt(3);
			System.out.println("Same colour");
		} while(colour == randomInt);
		colour = randomInt;

		int randomInt2;
		do {
			randomInt2 = random.nextInt(3);
			System.out.println("Same colour");
		} while(shape == randomInt2);
		shape = randomInt2;
	}
	
	private void gameLogic(float delta){
		timelimit -= delta;
		if(timelimit <= 0){
			gameOver();
		}
		if(score >= highscore) highscore = score;
		
		Vector3 touchPoint = new Vector3();
		camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
		//x = touchPoint.x-currentShape().getWidth()/2;
		//y = touchPoint.y+130;
		//if(x<=grid.getX()+20)x=grid.getX()+20;
		//if(x+currentShape().getWidth()>=grid.getX()+grid.getWidth()-20)x=grid.getX()+grid.getWidth()-20-currentShape().getWidth();
		//if(y<=grid.getY()+20)y=grid.getY()+20;
	}
	
	
	public Sprite currentShape(){
		switch(shape){
		default: System.out.println("No shape");
		case 0: return square_white;
		case 1: return triangle_white;
		case 2: return circle_white;
		}
	}
	
	private boolean isValidPosition(){
		Vector3 touchPoint = new Vector3();
		camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
		Sprite selectedShape = shapes.get(shape)[colour];
		return (
				touchPoint.x>selectedShape.getX()-80 && touchPoint.x<selectedShape.getX()+selectedShape.getWidth()+80 &&
				touchPoint.y>selectedShape.getY()-80 && touchPoint.y<selectedShape.getY()+selectedShape.getHeight()+80);
	}
	
	private void gameOver(){
		isPlaying = false;
		score = 0;
		change();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
