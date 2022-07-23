package recreation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;


// tweaks
/*
 * 
 * 2) code intersection against grid wall
 * 3) display score
 * 4) 
 * */
public class GamePanel extends JPanel implements ActionListener {

	// fields
	char snakeDirection;
	boolean isRunning = true;

	int DELAY = 200;
	int appleX, appleY;
	final int boardSize = 20;
	final int spaceSize = 20;
	int snakeSize = 1; 
	int lastX, lastY;
	Timer timer;
	
	int[] snakeX = new int[boardSize * boardSize];
	int[] snakeY = new int[boardSize * boardSize];

	public GamePanel() {
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(boardSize * spaceSize, 
				boardSize * spaceSize));
		
		this.addKeyListener(new MyKeyAdapter());

		startGame();
	}

	public void startGame() {
		placeSnake();
		placeApple();

		timer = new Timer(DELAY, this);
		timer.start();

	}

	private void placeSnake() {

		//	random

		int randX = (int)(Math.random() * boardSize);
		int randY = (int)(Math.random() * boardSize);

		snakeX[0] = randX;
		snakeY[0] = randY;

		lastX = snakeX[0];
		lastY = snakeY[0];
	}


	public void paintComponent(Graphics g)
	{		
		super.paintComponent(g);
		draw(g);
	}

	private void draw(Graphics g) {
		if(isRunning) {
			for(int i = 1; i < boardSize; i ++) {
				g.drawLine(i * spaceSize, 0, i * spaceSize, boardSize * spaceSize);
				g.drawLine(0, i * spaceSize, boardSize * spaceSize, i * spaceSize);
			}

			g.setColor(new Color(0,153,0));
			g.fillRect(snakeX[0] * spaceSize, snakeY[0] * spaceSize, spaceSize, spaceSize);
			
			g.setColor(Color.green);
			for(int a = 1; a < snakeSize; a ++) {
				g.fillRect(snakeX[a] * spaceSize, snakeY[a] * spaceSize, spaceSize, spaceSize);

			}

			g.setColor(Color.red);
			g.fillRect(appleX * spaceSize, appleY * spaceSize, spaceSize, spaceSize);
	
		
			g.drawString("SCORE : " + (snakeSize - 1), 100, 100);
		}

		else {
			gameOver(g);
		}
	}

	public void gameOver(Graphics g) {
		g.drawString("GAME OVER", 100, 100);
	}
	
	public void moveSnake(char snakeDirection) {
		for(int i = snakeSize - 1; i >= 1; i --) {
			snakeX[i] = snakeX[i - 1];
			snakeY[i] = snakeY[i - 1];
		}

		if(snakeDirection == 'w') {
			snakeY[0] --;
		}
		if(snakeDirection == 'a') {
			snakeX[0] --;
		}
		if(snakeDirection == 's') {
			snakeY[0] ++;
		}
		if(snakeDirection == 'd') {
			snakeX[0] ++;
		}

		checkCollision();
	}

	private void placeApple() {
		appleX = (int)(Math.random() * boardSize);
		appleY = (int)(Math.random() * boardSize);
	}

	private void checkCollision() {
		if(snakeX[0] == appleX && snakeY[0] == appleY) {
			consumeApple();

		}
		
		if(snakeX[0] > 20 || snakeX[0] < 0) {
			
			isRunning = false;
		}
		if(snakeY[0] > 20 || snakeY[0] < 0) {
			isRunning = false;
		}
		
		for(int i = 1; i < snakeSize; i ++) {
			if(snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
				System.out.println("bbbbbb");
				isRunning = false;
			}
		}
		
	}

	private void consumeApple() {
		
		snakeX[snakeSize] = lastX;
		snakeY[snakeSize] = lastY;
		
		snakeSize ++;
		
		placeApple();
		
		if(DELAY > 50) {
			DELAY -= 15;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(isRunning) {
			System.out.println("x : " + snakeX[0]);
			System.out.println("y : " + snakeY[0]);
			lastX = snakeX[snakeSize - 1];
			lastY = snakeY[snakeSize - 1];
			moveSnake(snakeDirection);
			repaint();
		}
	}

	public class MyKeyAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyChar() == 'w') {
				if(snakeDirection != 's') {
					snakeDirection = e.getKeyChar();
				}
			}
			else if(e.getKeyChar() == 'a') {
				if(snakeDirection != 'd') {
					snakeDirection = e.getKeyChar();
				}
			}
			else if(e.getKeyChar() == 's') {
				if(snakeDirection != 'w') {
					snakeDirection = e.getKeyChar();
				}
			}
			else if(e.getKeyChar() == 'd') {
				if(snakeDirection != 'a') {
					snakeDirection = e.getKeyChar();
				}
			}
		}
	}
}
