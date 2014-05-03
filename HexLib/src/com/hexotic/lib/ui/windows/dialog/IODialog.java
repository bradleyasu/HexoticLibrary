package com.hexotic.lib.ui.windows.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileSystemView;

import com.hexotic.lib.ui.buttons.SoftButton;
import com.hexotic.lib.ui.input.textfield.BubbleTextField;
import com.hexotic.lib.ui.input.textfield.ModernTextField;
import com.hexotic.lib.ui.panels.SimpleScroller;
import com.hexotic.lib.ui.windows.components.FileExplorer;
import com.hexotic.lib.ui.windows.components.FileExplorerListener;

public class IODialog extends JDialog implements ActionListener{
	
	private String resource = "/resources/images/explorer/";
	
	private List<File> stack = new ArrayList<File>();
	private int stackPointer = 0;
	private FileExplorer explorer;
	private ModernTextField currentPath;
	private ModernTextField selectedFileInput;
	private File selectedFile;
	
	public static void main(String[] args){
		new IODialog(null, "test", "ign");
	}
	
	public IODialog(JFrame parent, String title, String message) {
		super(parent, title, true);
		if (parent != null) {
			Dimension parentSize = parent.getSize(); 
			Point p = parent.getLocation(); 
			setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		} else {
			setLocation(100,100);
		}
		
		
		init();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	private void init(){
		JPanel container = new JPanel();
		container.setPreferredSize(new Dimension(600, 380));
		container.setLayout(new BorderLayout());
		
		/* TOP */
		JPanel navigation = new JPanel(new FlowLayout(FlowLayout.LEFT));
		navigation.setBackground(new Color(0xfdfdfd));
		navigation.setPreferredSize(new Dimension(600, 40));
		
		JLabel backBtn = new JLabel(new ImageIcon(getIcon("back")));
		backBtn.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(stackPointer>0){
					stackPointer--;
					currentPath.setText(stack.get(stackPointer).getAbsolutePath());
					explorer.setLocation(stack.get(stackPointer));
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		
		JLabel forwardBtn = new JLabel(new ImageIcon(getIcon("forward")));
		forwardBtn.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(stackPointer < stack.size()-1){
					stackPointer++;
					currentPath.setText(stack.get(stackPointer).getAbsolutePath());
					explorer.setLocation(stack.get(stackPointer));
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		navigation.add(backBtn);
		navigation.add(forwardBtn);
		
		currentPath = new ModernTextField();
		currentPath.setPreferredSize(new Dimension(400, 20));
		navigation.add(currentPath);
		
		
		container.add(navigation, BorderLayout.NORTH);
		
		/* SELECTION */
		
		stack.add(FileSystemView.getFileSystemView().getRoots()[0]);
		explorer = new FileExplorer(new File("/"));
		JScrollPane scroller = new JScrollPane(explorer);
		scroller.getVerticalScrollBar().setUI(new SimpleScroller());
		scroller.setBorder(BorderFactory.createLineBorder(new Color(0xdadada)));
		scroller.getVerticalScrollBar().setPreferredSize(new Dimension(5,10));
		scroller.getVerticalScrollBar().setUnitIncrement(20);
		container.add(scroller, BorderLayout.CENTER);
		explorer.addFileExplorerListener(new FileExplorerListener(){
			@Override
			public void rootChanged(File file) {
				if(file != null){
					if(file.isDirectory()){
						stack.add(file);
						stackPointer++;
						currentPath.setText(file.getAbsolutePath());
						selectedFileInput.setText("");
					} else {
						selectedFileInput.setText(file.getName());
					}
					selectedFile = file;
				}
			}
			
			@Override
			public void fileSelected(File file){
				
			}
		});
		/* BOTTOM */
		JPanel options= new JPanel();
		options.setBackground(new Color(0xfdfdfd));
		options.setPreferredSize(new Dimension(600, 60));
		
		
		selectedFileInput = new ModernTextField();
		selectedFileInput.setPreferredSize(new Dimension(400, 20));
		options.add(selectedFileInput);  
		
		
		SoftButton open = new SoftButton("Open");
		open.setBackgroundColor(new Color(0xf2f2f2));
		open.setForegroundColor(new Color(0x323232));
		open.setPreferredSize(new Dimension(100,25));
		open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				File file = new File(selectedFileInput.getText());
				if(file != null && file.isDirectory()){
					explorer.setLocation(file);
				}
			}
		});
		
		
		SoftButton cancel = new SoftButton("Cancel");
		cancel.setBackgroundColor(new Color(0xf2f2f2));
		cancel.setForegroundColor(new Color(0x323232));
		cancel.setPreferredSize(new Dimension(100,25));
		
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cancel();
			}
		});
		
		options.add(open);
		options.add(cancel);
		
		
		container.add(options, BorderLayout.SOUTH);
		
		getContentPane().add(container, BorderLayout.CENTER);
	}
	
	public void cancel(){
		this.dispose();
	}
	
	public void actionPerformed(ActionEvent e) {
		setVisible(false); 
		dispose();
	}
		    
	private URL getIcon(String type){
		return this.getClass().getResource(resource+type+".png");
	}	    

}
