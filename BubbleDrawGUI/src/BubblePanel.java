import javax.swing.Timer;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class BubblePanel extends JPanel {
	
	//Create a random object to generate the random color
		Random rand = new Random();
		//Create an ArrayList to store the bubbles
		ArrayList<Bubble> bubbleList;
		//Declare the size variable to store the bubble's size
		int size = 25;
		//Create a timer
		Timer timer;
		int delay = 33;
		//Add the JSlider
		JSlider slider;
		
		//Constructor
	public BubblePanel() {
		//Declare Timer object
		timer = new Timer(delay, new BubbleListener());
		bubbleList = new ArrayList<Bubble>();
		setBackground(Color.BLACK);
		
		JPanel panel = new JPanel();
		//panel.setForeground(new Color(0, 0, 0));
		//panel.setBackground(new Color(240, 240, 240));
		add(panel);
		
		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton)e.getSource();
				if(btn.getText().equals("Pause")) {
					timer.stop();
					btn.setText("Start");
				} else {
					timer.start();
					btn.setText("Pause");
				}
			}
		});
		
		JLabel lblAnimationSpeed = new JLabel("Animation Speed");
		panel.add(lblAnimationSpeed);
		
		slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				//Get the speed value from the timer
				int speed = slider.getValue() + 1;
				//Convert the speed value into the number of milliseconds
				int delay = 1000 / speed;
				//Set the timer's delay to the new value
				timer.setDelay(delay);
				
			}
		});
		slider.setValue(30);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(30);
		slider.setMinorTickSpacing(5);
		slider.setMaximum(120);
		panel.add(slider);
		panel.add(btnPause);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bubbleList = new ArrayList<Bubble>();
				repaint();
			}
		});
		panel.add(btnClear);
		//Call the method testBubbles()
		//testBubbles();
		addMouseListener(new BubbleListener());
		addMouseMotionListener(new BubbleListener());
		addMouseWheelListener(new BubbleListener());
		timer.start();
	}
	
	//Method to describe what the app should do and draw
		public void paintComponent(Graphics canvas) {
			super.paintComponents(canvas);
			for(Bubble b : bubbleList) {
				b.draw(canvas);
			}
		}
		
		public void testBubbles() {
			for(int n = 0; n < 100; n++) {
				int x = rand.nextInt(600);
				int y = rand.nextInt(400);
				int size = rand.nextInt(50);
				bubbleList.add(new Bubble(x, y, size));
			}
			repaint();
		}
	
	private class BubbleListener extends MouseAdapter implements ActionListener {
		//Add event handler for the mouse when the user clicks or presses the mouse
		public void mousePressed(MouseEvent e) {
			//Create a new Bubble and add it to the ArrayList bubbleList
			bubbleList.add(new Bubble(e.getX(), e.getY(), size));
			//Call the repaint() method to refresh the screen and draw BubbleList to canvas
			repaint();
		}
		//Add event handler for the mouse when the user drag the mouse
		public void mouseDragged(MouseEvent e) {
			bubbleList.add(new Bubble(e.getX(), e.getY(), size));
			repaint();
		}
		//Add event handler for the mouse wheel
		public void mouseWheelMoved(MouseWheelEvent e) {
			if(System.getProperty("os.name").startsWith("Mac"))
				size += e.getUnitsToScroll();
			else 
				size -= e.getUnitsToScroll();
			if(size < 3)
				size = 3;
		}
		//Add the event handler 
		public void actionPerformed(ActionEvent e) {
			for(Bubble b : bubbleList)
				b.update();
			repaint();
			
		}
	}
	
	private class Bubble {
		//Instance variable for the x coordinate
		private int x;
		//Instance variable for the y coordinate
		private int y;
		//Instance variable for the bubble's size
		private int size;
		private Color color;
		private int xspeed, yspeed;
		private final int MAX_SPEED = 5;
		
		//Create the constructor to set up the Bubble object
		public Bubble(int newX, int newY, int newSize) {
			//Assign x, y, size to the object's attributes
			x = newX;
			y = newY;
			size = newSize;
			color = new Color(rand.nextInt(256),
					rand.nextInt(256),
					rand.nextInt(256),
					rand.nextInt(256));
			int xspeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;
			int yspeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;
			//Check whether the xspeed equals to 0 it will cause the bubbles 
			//stuck in place while other bubbles float away
			if(xspeed == 0)
				xspeed = 1;
			////Check whether the yspeed equals to 0
			if(yspeed == 0)
				yspeed = 1;
		}
		
		public void draw(Graphics canvas) {
			canvas.setColor(color);
			//Method create to paint fill-in circle
			canvas.fillOval(x - size/2, y - size/2, size, size);
		}
		
		public void update() {
			x += xspeed;
			y += yspeed;
			if(x - size/2 <= 0 || x + size/2 >= getWidth())
				xspeed -= -xspeed;
			if(y - size/2 <= 0 || y + size/2 >= getHeight())
				xspeed -= -yspeed;
		}
	}
	
	

	public static void main(String[] args) {
		
	}
}

		