package recreation;

import java.awt.Rectangle;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

	public GameFrame() {
		
		this.add(new GamePanel());
		this.setTitle("Snake");
		this.pack();
		this.setVisible(true);

	}
}
