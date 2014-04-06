package com.hexotic.lib.toast;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Toast extends JDialog{

	public static void main(String[] args){
		Toast.show(0, "", "");
	}
	
	
	public static void show(int toastType, String title, String message){
		new Toast("Test", 4000);
	}
	
	
	
    int miliseconds;
    private Toast(String toastString, int time) {
        this.miliseconds = time;
        setBounds(400, 400, 280,100);
        setUndecorated(true);
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
        getContentPane().add(panel, BorderLayout.CENTER);

        JLabel lblToastString = new JLabel("");
        lblToastString.setText(toastString);
        lblToastString.setFont(new Font("Dialog", Font.BOLD, 12));
        lblToastString.setForeground(Color.WHITE);

        setAlwaysOnTop(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int y = 10;
        setLocation(dim.width-getSize().width, y);
        panel.add(lblToastString);
        setVisible(true);

        if(miliseconds > 10000 || miliseconds < 1000)
            miliseconds = 3000;
        new Thread(){
            public void run() {
                try {
                    Thread.sleep(miliseconds);
                    dispose();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }   
}


