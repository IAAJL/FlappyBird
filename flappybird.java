package flappyBirdGame;

import java.awt.Color;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.sql.*;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.sql.*;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.io.File;
import java.io. IOException;


@SuppressWarnings("unused")
public class flappybird implements ActionListener, MouseListener, KeyListener 
{
	public static JLabel s1;
	public static JTextField t1;
	public static JButton b1;
	public static int height,width;
	public Renderer renderer ;
	public static flappybird flappybird;
	
	public static Rectangle bird;
	public static JFrame jframe;
	
	public int ticks,ymotion,count;
	
	public static boolean endgame =false;
	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	public static boolean gameover=false,started = true,entered=false;
	public static int score,speed,flag=0;
	public static Timer timer;
	
	public static Random rand;
	
	public static ArrayList<Rectangle> column;
	public static ArrayList<String> name,scr;
	public String path="res/MusicTwo.wav";
	
	@SuppressWarnings("static-access")
	public flappybird() 
	{
		s1=new JLabel();
		t1 = new JTextField("");
		b1=new JButton("Enter");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		height =  (int)screenSize.getHeight();
		width = (int)screenSize.getWidth();
		jframe = new JFrame();
		timer = new Timer(20,this);
		
		renderer = new Renderer();
		jframe.setSize(width,height);
		rand = new Random();
		
		jframe.add(renderer);
		jframe.setTitle("Flappy Bird");
		jframe.setResizable(true);
		
		jframe.addMouseListener(this);
		jframe.addKeyListener(this);
		
		jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
		jframe.setResizable(false);
		jframe.setVisible(true);
	
		
		bird = new Rectangle(width/2 -10,height/2 -10,20,20);
		
		column = new ArrayList<Rectangle>();
		
		name = new ArrayList<String>();
		scr = new ArrayList<String>();
		startgame();

	}
	public static void startgame()
	{
		init();
		
		timer.start();
		
		System.out.println("Done");

	}
	
	
	public static void addColumn(boolean start)
	{
		int space = 300;
		int Width =100;
		int Height = 50 + rand.nextInt(300);
		if(start){
		
			column.add(new Rectangle(width + Width + column.size() * 300,height - Height - 120,Width,Height));
			
			column.add(new Rectangle(width + Width + ((column.size()-1) *300),0,Width,height-Height-space));
		}
		else
		{
			column.add(new Rectangle(column.get(column.size()-1).x + 600,height - Height - 120,Width,Height));
			
			column.add(new Rectangle(column.get(column.size()-1).x, 0 ,Width, height-Height-space));
		}
	}
	
	public static void paintColumn(Graphics g,Rectangle column)
	{
		g.setColor(new Color(0x5E372E));
		g.fillRect(column.x, column.y, column.width, column.height);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ticks++;
		speed=10;
		if(!gameover)
		{
			for(int i=0;i<column.size();i++)
			{
				Rectangle q = column.get(i);
				q.x-=speed;
			}
				
			if(ticks%2==0 && ymotion<15)
			{
				 ymotion+=1;
			}
			
			for(int i=0;i<column.size();i++)
			{
				Rectangle ecolumn = column.get(i);
				if((ecolumn.x+ecolumn.width)<0)
				{
					column.remove(ecolumn);
					if(ecolumn.y==0)
					addColumn(false);
				}
			}
			
			bird.y+=ymotion;
			
			for(Rectangle ecolumn : column)
			{
				if((ecolumn.y == 0) &&(( bird.x + bird.width / 2 )> (ecolumn.x + ecolumn.width / 2-10))  && ((bird.x + bird.width / 2) < (ecolumn.x + ecolumn.width / 2+10))){
					count++;	
					score=count/2;;
				}
				if(ecolumn.intersects(bird)) {
					gameover = true;
					started=false;
					break;
				}
			}
			if( bird.y<0 || bird.y > height-120-bird.height) {
				gameover =true;
				started=false;
			}
			
		}
		
		renderer.repaint();
	}
	
	public static void repaint(Graphics g) {
		g.setColor(new Color(0x87CEEB));
		g.fillRect(0, 0, width, height);
				
		g.setColor(new Color(0x739957));
		g.fillRect(0, height-120, width, 150);
		
		g.setColor(new Color(0xFFE3A0));
		g.fillRect(0, height-120, width, 10);
		
		g.setColor(new Color(0xBF0000));
		g.fillRect(bird.x, bird.y, bird.width, bird.height);
			
		for(Rectangle ecolumn : column)
		{
			paintColumn(g,ecolumn);
		}
		
		g.setColor(Color.black.brighter());
		g.setFont(new Font("AngryBirds",1,40));
		if(!gameover)
		{
			g.drawString("Score : "+String.valueOf(score), width/2-100, 50);
		}
		g.setFont(new Font("AngryBirds",1,100));
		if(gameover) {
			g.drawString("Game Over!", width/2-200, height/2-50);
			g.drawString("Score : "+String.valueOf(score), width/2-150, height/2+50);
			g.setFont(new Font("AngryBirds",1,30));
			
			String str="";
			entered=false;
			displays();
			started=false;

			for(int i =0;i<name.size();i++)
			{
				g.drawString(name.get(i), width/2-140, height/2+100+(30*i));
				g.drawString(scr.get(i), width/2-10, height/2+100+(30*i));
			}
			timer.stop();
			}
		}
			
	public static void displays()
	{
		name.clear();
		scr.clear();
		Connection con = null;
		try {
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/newdb","root","12345678");
		Statement stmt = con.createStatement();
			PreparedStatement p = con.prepareStatement("insert into highscore(name,score) values (?,?)");
			p.setInt(2,score);
			p.setString(1,"Guest");
			int i=p.executeUpdate();
			entered =true;
		
		ResultSet r = stmt.executeQuery("select * from highscore order by score desc");
		i=1;
		while(r.next()&&i<=3)
		{
			name.add(r.getString("name"));
			scr.add(String.valueOf(r.getInt("score")));
			i++;
		}
		System.out.println(name.size());
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public static void init()
	{
		column.clear();
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
	}
	public void jump()
	{
		if (gameover) {
			getName n = new getName();
			n.setVal(getscore());
			flappybird=null;
			
		}
		if(!started)
	{	
		
		flappybird=null;
		ymotion=0;
		started=true;
		gameover=false;
		bird.y=height/2 -10;
		init();
		score=0;
		count=0;
		entered=true;
		jframe.dispose();
	}
	 if (!gameover)
	{
		if (ymotion > 0)
		{
			ymotion = 0;
		}

		ymotion -= 7;
	}
		
	}
	
	
	public static int getscore()
	{
		return score;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		jump();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE)
			jump();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	
}


