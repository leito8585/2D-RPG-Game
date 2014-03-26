package com.leito.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.leito.game.entities.Player;
import com.leito.game.gfx.Font;
import com.leito.game.gfx.Screen;
import com.leito.game.gfx.SpriteSheet;
import com.leito.game.level.Level;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 480;
	public static final int HEIGHT = WIDTH / 16 * 9;
	public static final int SCALE = 2;
	public static final String NAME = "GAME";
	
	private JFrame frame;
	
	private boolean running = false;
	private int updateCount = 0;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	private Screen screen;
	private InputHandler input;
	private Level level;
	private Player player;
	
	public Game(){
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void init(){
		screen = new Screen(WIDTH, HEIGHT);
		input = new InputHandler(this);
		level = new Level("/levels/spawn.png");
		player = new Player(SpriteSheet.PLAYER, level, 0, 0, input, "Cycek");
		level.addEntity(player);
	}
	
	private synchronized void start() {
		new Thread(this).start();	
		running = true;
	}
	
	private synchronized void stop() {
		running = false;
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/64D;
		
		int ticks = 0;
		int frames = 0;
		
		long lasTimer = System.currentTimeMillis();
		double delta = 0;
		
		init();
		
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			
			while(delta >= 1){
				ticks++;
				update();
				delta -= 1;
				shouldRender = true;
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(shouldRender){
				frames++;
				render();
			}
			
			if(System.currentTimeMillis() - lasTimer >= 1000){
				lasTimer += 1000;
				System.out.println(frames + " FPS, " + ticks + " UPS || Player X/Y: " + player.x + "/" + player.y);
				frames = 0;
				ticks = 0;
			}
		}
		stop();
	}
	
	public void update(){
		updateCount++;
		level.update();
	}
	
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
			
		int xOffset = player.x - (screen.width / 2);
		int yOffset = player.y - (screen.height / 2);
		
		level.renderTiles(screen, xOffset, yOffset);
		level.renderEntities(screen);
				
		for(int y = 0; y < screen.height; y++){
			for(int x = 0; x < screen.width; x++){
				pixels[x + y * WIDTH] = screen.pixels[x + y * screen.width];
			}
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] arg){
		new Game().start();
	}

}
