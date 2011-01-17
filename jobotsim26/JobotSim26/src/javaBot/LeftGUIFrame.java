/*
 * Created on Mar 20, 2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 * 
 * @version $Revision: 1.1 $
 */
package javaBot;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class LeftGUIFrame extends JPanel
{
	private JFrame mainFrame;
	private JScrollPane jscroll;
	private PhysicalObject PORobot;
	private RobotGUI robotGUI;
	
	public LeftGUIFrame( JFrame frame )
	{
		mainFrame = frame;
		initJScrollPane();
	}

	private void initJScrollPane()
	{
		jscroll = new JScrollPane();
		jscroll.getViewport().add(this, null);
		jscroll.setPreferredSize( new Dimension( 210, mainFrame.getHeight() ) );
		jscroll.setBounds(0,0,180, mainFrame.getHeight() );
	}
	
	public JScrollPane getJScrollPane()
	{
		return jscroll;
	}
	
	private void refresh()
	{
		this.validate();
		this.repaint();
		jscroll.validate();
		jscroll.repaint();
	}

	public void showGUI( PhysicalObject selectedObject, RobotGUI robotGUI )
	{
		clearPanel();
		this.robotGUI = robotGUI;
		PORobot = selectedObject;
		this.robotGUI.setVisible( false );
		//this.removeAll();
		this.add( this.robotGUI.getPanels() );
		this.setVisible( true );
		
		this.robotGUI.setVisibleInLeftFrame( true );
		refresh();
	}

	public void clearPanel()
	{
		if( robotGUI != null ) robotGUI.setVisibleInLeftFrame( false );
		this.removeAll();
		refresh();
	}

	public boolean isShowingGuiFromRobot( PhysicalObject selectedObject )
	{
		return ( PORobot.equals( selectedObject ) );
	}
}
