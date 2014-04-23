package com.hexotic.lib.ui.input.textfield;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BasicTextField extends JPanel{
	private String placeholder;
	private Font placeholderFont = new Font("Arial", Font.BOLD, 14);
	private JTextField field;
	
	public BasicTextField(){
		this("");
	}
	
	public BasicTextField(String placeholder){
		field = new JTextField("");
		this.setLayout(new BorderLayout());
		this.add(field);
		this.setOpaque(false);
		this.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		field.setOpaque(false);
		field.setBorder(BorderFactory.createEmptyBorder());
	}
	
	public void setText(String text){
		field.setText(text);
	}
	
	public String getText(){
		return field.getText();
	}
	
	public void setPlaceholderFont(Font font){
		this.placeholderFont = font;
	}
	
	public void setPlaceholder(String placeholder){
		this.placeholder = placeholder;
	}

	public String getPlaceholder(){
		return placeholder;
	}
	
	public Font getPlaceholderFont(){
		return placeholderFont;
	}
	
	public JTextField getField(){
		return field;
	}
	
}
