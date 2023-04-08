package flappyBirdGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.sql.*;

public class getName implements ActionListener {
	JFrame x;
	JLabel l;
	JButton b;
	JTextField t;
	String str="";
	int score;
	getName()
	{
		x=new JFrame("Enter Name");
		x.setLayout(null);
		x.setSize(300,200);
		x.getContentPane().setBackground(new Color(0x5E372E));
		x.setResizable(false);
		l=new JLabel();
		b=new JButton("Enter");
		t=new JTextField();
		l.setBounds(50,50,100,50);
		l.setText("Enter Name");
		l.setForeground(new Color(0xFFFFFF));
		l.setFont(new Font("AngryBirds",1,13));
		b.setBounds(100, 120, 80, 20);
		b.setFont(new Font("AngryBirds",1,10));
		b.setBackground(new Color(0x739957));
		b.setForeground(new Color(0xFFFFFF));
		b.setOpaque(true);
		b.setBorderPainted(false);
		b.addActionListener(this);
		t.setBackground(new Color(0xFFE3A0));
		t.setForeground(new Color(0x000000));
		t.setFont(new Font("AngryBirds",1,13));
		t.setBounds(120,60,100,30);
		x.add(b);
		x.add(l);
		x.add(t);
		x.setVisible(true);
	}
	void setVal(int score)
	{
		this.score=score;
		
	}
	void updateTable(String s)
	{
		@SuppressWarnings("unused")
		String cur;
		@SuppressWarnings("unused")
		int id;
		Connection con=null;
		try {
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/newdb","root","12345678");
			@SuppressWarnings("unused")
			Statement stmt = con.createStatement();
			PreparedStatement str = con.prepareStatement("insert into highscore(name,score) values(?,?)");
			str.setInt(2,score);
			str.setString(1, s);
			System.out.println("entered");
			@SuppressWarnings("unused")
			int i =str.executeUpdate();
			str=con.prepareStatement("delete from highscore where name= ? and score= ? ");
			str.setString(1,"Guest");
			str.setInt(2, score);
			i=str.executeUpdate();
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b)
		{
			str=t.getText();
			t.setText("");
			updateTable(str);
			x.dispose();
			@SuppressWarnings("unused")
			menu m=new menu();
		}
	}
}
