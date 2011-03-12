package com.gemserk.games.artemistest;

import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.artemis.EntitySystem;
import com.artemis.SystemManager;
import com.artemis.World;

public class Game extends BasicGame{
	private World world;

	private Random rand;

	private EntitySystem testsystem;

	public static class Position extends Component{
		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int x,y;
	}
	
	public static class TestSystem extends EntityProcessingSystem {
		ComponentMapper<Position> positionMapper;

		public TestSystem() {
			super(Position.class);
		}
		
		
		@Override
		public void initialize() {
			ComponentMapperAutoInit.config(this,world.getEntityManager());
		}

		@Override
		protected void process(Entity e) {
				Position position = positionMapper.get(e);
				System.out.println("(" + position.x + "," + position.y + ")");
		}
		
		
	}
	

	public Game(String title) {
		super(title);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		world = new World();

		SystemManager systemManager = world.getSystemManager();
		testsystem = systemManager.setSystem(new TestSystem());
		
		systemManager.initializeAll();

		Entity entity = world.createEntity();
		entity.addComponent(new Position(10,10));
		entity.refresh();
		
		System.out.println("Arranco");
		
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		world.setDelta(delta);	
		testsystem.process();
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
	
	}

	public static void main(String[] args) throws SlickException {
		Game tankz = new Game("artemistest");
		AppGameContainer container = new AppGameContainer(tankz);
		container.setDisplayMode(800,600, false);
		container.setAlwaysRender(true);
		container.start();
	}
}
