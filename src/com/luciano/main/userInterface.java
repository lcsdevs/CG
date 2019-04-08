package com.luciano.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.luciano.utils.flockClass;
import javax.swing.JButton;
import java.awt.event.ActionListener;


public class userInterface extends JPanel{
	 flockClass flock;
	    final int w, h;
	    int grow = 1;
	    
	    public userInterface() {
	        w = 1280;
	        h = 720;
	 
	        setPreferredSize(new Dimension(w, h));
	        setBackground(Color.white);
	        setLayout(null);
	        spawnFlock(grow); 
	        JButton button = new JButton("+");
	        button.setBounds(10, 11, 41, 23);
	        add(button);
	        
	        button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					grow = grow + 10;
				}
			});
	        JButton btnNewButton = new JButton("-");
	        btnNewButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent arg0) {
	        		if(grow > 1) {
	        			grow = grow - 10;	
	        		}else {
	        			grow = grow;
	        		}
	        	}
	        });
	        btnNewButton.setBounds(81, 11, 41, 23);
	        add(btnNewButton);
	        
	        JButton button_1 = new JButton("Líder");
	        button_1.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        	}
	        });
	        button_1.setBounds(144, 11, 63, 23);
	        add(button_1);
	        
	        JButton button_2 = new JButton("Geral");
	        button_2.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        	}
	        });
	        button_2.setBounds(226, 11, 69, 23);
	        add(button_2);
	        
	        JButton button_3 = new JButton("Bando");
	        button_3.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		
	        	}
	        });
	        button_3.setBounds(311, 11, 79, 23);
	        add(button_3);
	 
	        new Timer(10, (ActionEvent e) -> {
	            if (flock.hasLeftTheBuilding(w))
	                spawnFlock(grow);
	            repaint();
	        }).start();
	       
	    }
	 
	    
	    private void spawnFlock(int i) {
	        flock = flockClass.spawn(-300, h * 0.5, i);
	    }
	    
	 
	    @Override
	    public void paintComponent(Graphics gg) {
	        super.paintComponent(gg);
	        Graphics2D g = (Graphics2D) gg;
	        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
	 
	        flock.run(g, w, h);
	    }
	 
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            JFrame f = new JFrame();
	            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            f.setTitle("Flocking Algorithm");
	            f.setResizable(false);
	            f.add(new userInterface(), BorderLayout.CENTER);
	            f.pack();
	            f.setLocationRelativeTo(null);
	            f.setVisible(true);
	        });
	        
	    }
}