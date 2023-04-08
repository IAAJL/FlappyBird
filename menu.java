package flappyBirdGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;



@SuppressWarnings("unused")
public class menu implements ActionListener  {
	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	JFrame f;
	JPanel p;
	JButton b1,b2,b3;
	JLabel l;
	JLabel l2;
	int height, width;
	public menu()
	{
		f= new JFrame("Menu");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		height =  (int)screenSize.getHeight();
		width = (int)screenSize.getWidth();
		f.setSize(width,height);
		f.setResizable(true);
		f.getContentPane().setBackground(new Color(0x8BDE5A));
		//f.setOpaque(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		displayM();
		f.setVisible(true);
	}
	public void displayM()
	{
		p=new JPanel();
		p.setSize(width,height);
		p.setLayout(null);
		p.setBackground(new Color(0x739957));
		l2=new JLabel();
		l2.setText("FLAPPY BIRD");
		l2.setForeground(new Color(0xFFE3A0));
		l2.setFont(new Font("AngryBirds",1,100));
		l2.setBounds(590, 300, 1000, 80);
		p.add(l2);
		b1=new JButton("Start");
		b1.setBounds(775, 450, 120, 80);
		b1.setFont(new Font("AngryBirds",Font.PLAIN,19));
		b1.setFocusable(false);
		b1.setForeground(new Color(0x772F21));
		b1.setBackground(new Color(0xFFE3A0));
		Border border = BorderFactory.createLineBorder(new Color(0x1E140A));
		b1.addActionListener(new ActionListener() {
			
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				f.setVisible(false);
				SwingUtilities.updateComponentTreeUI(f);
				f.dispose();
				flappybird q = new flappybird();
				q.startgame();
			}
		});
		p.add(b1);
		b2=new JButton("Highscore");
		b2.setBounds(775, 550, 120, 80);
		b2.setFont(new Font("AngryBirds",Font.PLAIN,19));
		b2.setFocusable(false);
		b2.setForeground(new Color(0x772F21));
		b2.setBackground(new Color(0xFFE3A0));
		Border bo = BorderFactory.createLineBorder(new Color(0x1E140A));
		b2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				f.getContentPane().remove(p);
				f.getContentPane().add(runh());
				SwingUtilities.updateComponentTreeUI(f);
			}
		});
		p.add(b2);
		b3=new JButton("Clear");
		b3.setBounds(775, 650, 120, 80);
		b3.setFont(new Font("AngryBirds",Font.PLAIN,19));
		b3.setFocusable(false);
		b3.setForeground(new Color(0x772F21));
		b3.setBackground(new Color(0xFFE3A0));
		Border b = BorderFactory.createLineBorder(new Color(0x1E140A));
		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Connection con = null;
				try{
					con=DriverManager.getConnection("jdbc:mysql://localhost:3306/newdb","root","12345678");
					Statement stmt = con.createStatement();
					stmt.execute("truncate table highscore");
					ResultSet r  = stmt.executeQuery("select * from highscore order by score desc");
					System.out.println("id\tname\tscore");
					while(r.next())
					{
						System.out.println(r.getInt("id")+"\t"+r.getString("name")+"\t"+r.getInt("score"));
					}
					System.out.println("Created ");
					con.close();
				}
				catch(Exception f)
				{
					System.out.println(f);
				}
			}
		});
		p.add(b3);
		f.getContentPane().add(p);
		SwingUtilities.updateComponentTreeUI(f);
	}
	public JPanel runh()
	{
		JButton b2 = new JButton("Return");
		JPanel p2=new JPanel();
		p2.setLayout(null);
		p2.setSize(width,height);
		p2.setBackground(new Color(0x739957));
		JLabel h[]=new JLabel[10];
//		JLabel h = new JLabel();
		String s;
		int i=0,j=220;
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/newdb","root","12345678");
			Statement stmt = con.createStatement();
			ResultSet r = stmt.executeQuery("select * from highscore order by score desc");
			while(r.next()&&i<10)
			{
				h[i]=new JLabel();
				h[i].setBounds(800, j, 500, 30);
				s=r.getString("name")+"-"+String.valueOf(r.getInt("score"));
				h[i].setText(s);
				h[i].setForeground(new Color(0x5E372E));
				h[i].setFont(new Font("AngryBirds",1,20));
				j+=40;
				p2.add(h[i]);
				System.out.println(s);
				i++;
			}
			b2.setBounds(775,j+20,120,80);
			b2.setBackground(new Color(0xFFE3A0));
			b2.setForeground(new Color(0x772F21));
			b2.setFont(new Font("AngryBirds",1,19));



			b2.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					f.getContentPane().remove(p2);
					displayM();
				}
			});
			p2.add(b2);
			
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		return p2;
		
	}
	public static void main(String[] args) {
		menu m = new menu();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}
