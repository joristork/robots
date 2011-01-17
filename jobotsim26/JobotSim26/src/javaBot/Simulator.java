package javaBot;

import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.Hashtable;
import java.util.Vector;
import javaBot.agents.BallLover;
import javaBot.agents.DanceAgent;
import javaBot.agents.DanceAgent2;
import javaBot.agents.MazeAgent;
import javaBot.maze.MazeRobot;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;

import sun.java2d.InvalidPipeException;

// Working on: 
// TODO: Include new muVium version and WS compiler 
// TODO: Include different servo types 
// TODO: Include IR sensor correct range settings 
// TODO: RCjobot is drawn as circle 
// TODO: UVMrobot is smaller than standard robot 
// TODO: Robot does not detect other robot as obstacle 
// TODO: Find out why Ball Lover does not work any more 
// TODO: MazeAgent does not work properly 
// TODO: Check all demo programs 
// TODO: Synchronize all version numbers 
// TODO: Test setting of new robot type through both interfaces 
// TODO: Docs should describe start and stop commands 
// TODO: CellTest does not work OK 
// TODO: Making UVMdemo agent from dummy does not work 

/** 
 * Ver 0.0 - 08-06-2004 Ver 0.1 - 03-07-2004 - Included dummy Jobot creation Ver 
 * 0.2 - 31-08-2004 - Updated DIP settings Ver 0.3 - 23-09-2004 - Cleaned up 
 * small problems: vector fix, setState, stop, ball rolling included new unit 
 * tests Ver 0.4 - 30-12-2004 - Included IR ball sensor Corrected error in 
 * rotation direction Ver 0.5 - 17-02-2005 - Included updated Sharp sensor 
 * characteristics 
 */
/** 
 * Main class to start the simulation. The rendering of the frames happens in 
 * this class and all interaction with the virtual world is also called from 
 * here. The Timer keeps the sequence Update-Repaint running refreshrate (25) 
 * times a second until the Simulator is paused or quittted. 
 */
/**
 * @version $Revision: 1.1 $ last changed Feb 27, 2006
 * 
 */
public class Simulator extends JPanel
{
	public static final int				DEFAULT_WORLD_PANE_HEIGHT		= 480;
	private static final int			MESSAGE_PANE_ROWS				= 8;
	// Prune: maximum number of lines in messagepane TextArea before cleanup
	private static final int			MESSAGE_PANE_MAXLINES			= 1000;
	private static final String			WINDOW_TITLE					= "JoBot Simulation Environment 1.0.26";
	private static final Color			GRID_COLOR						= new Color(0.9f, 0.9f,
																				0.9f);
	private static final int			NO_MOUSE_ACTION					= 0;
	private static final int			ROTATE_MOUSE_ACTION				= 1;
	private static final int			MOVE_MOUSE_ACTION				= 2;
	private static final int			VELOCITY_MOUSE_ACTION			= 3;
	private static final int			SHOW_POPUP_MOUSE_ACTION			= 4;

	
	//mouse buttons
	private static final int 			LEFT_MOUSE_BUTTON				= 1;
	private static final int			RIGHT_MOUSE_BUTTON				= 3;
	
	
	
	// Available layers in the simulator JLayeredPane worldPane
	public static String				IMAGE_FILE						= new String("");
	public static Image					IMAGE							= null;
	public static final Integer			UNDEFINED_LAYER					= new Integer(-1);
	public static final Integer			BACKGROUND_LAYER				= new Integer(0);
	public static final Integer			MAZE_LAYER						= new Integer(1);
	public static final Integer			NON_MOVABLES_LAYER				= new Integer(2);
	public static final Integer			MOVABLES_LAYER					= new Integer(3);

	// Action Listeners
	private SimulatorActionListener		actionListener					= new SimulatorActionListener();
	private SimulatorComponentListener	componentListener				= new SimulatorComponentListener();
	private SimulatorMouseListener		mouseListener					= new SimulatorMouseListener();
	private SimulatorItemListener		itemListener					= new SimulatorItemListener();

	// 
	private static final int			IS_ROBOT						= 0;
	private static final int			IS_NO_ROBOT						= 1;

	// changed for bug 17
	// private JCheckBoxMenuItem showRescuefield = new JCheckBoxMenuItem("Show
	// rescuefield");
	// private JCheckBoxMenuItem showSoccerfield = new JCheckBoxMenuItem("Show
	// soccerfield");
	// private JCheckBoxMenuItem showDancefloor = new JCheckBoxMenuItem("Show
	// dancefloor");

	private ButtonGroup					fields							= new ButtonGroup();
	private static final String			SHOW_EMPTY_FIELD				= "Show empty field";
	private JRadioButtonMenuItem		showEmptyfield					= new JRadioButtonMenuItem(
																				SHOW_EMPTY_FIELD);

	private static final String			SHOW_MAZE						= "Show maze";
	private JRadioButtonMenuItem		showMaze						= new JRadioButtonMenuItem(
																				SHOW_MAZE);

	private static final String			SHOW_RESCUEFIELD				= "Show rescuefield";
	private JRadioButtonMenuItem		showRescuefield					= new JRadioButtonMenuItem(
																				SHOW_RESCUEFIELD);

	private static final String			SHOW_SOCCERFIELD				= "Show soccerfield (single)";
	private JRadioButtonMenuItem		showSoccerfield					= new JRadioButtonMenuItem(
																				SHOW_SOCCERFIELD);

	private static final String			SHOW_SOCCERFIELDDBL				= "Show soccerfield (double)";
	private JRadioButtonMenuItem		showSoccerfieldDbl				= new JRadioButtonMenuItem(
																				SHOW_SOCCERFIELDDBL);

	private static final String			SHOW_DANCEFLOOR					= "Show dancefloor";
	private JRadioButtonMenuItem		showDancefloor					= new JRadioButtonMenuItem(
																				SHOW_DANCEFLOOR);

	private JCheckBoxMenuItem			pause							= new JCheckBoxMenuItem(
																				"Pause");

	private static final String			MENU_SIMULATION					= "Simulation";
	private static final String			NEW								= "New";
	private static final String			QUIT							= "Quit";

	private static final String			MENU_VIEW						= "View";

	// Show/hide grid
	private boolean						grid							= true;
	private static final String			SHOW_GRID						= "Show grid";
	private JCheckBoxMenuItem			showGrid						= new JCheckBoxMenuItem(
																				SHOW_GRID);

	private static final String			SHOW_LABELS						= "Show labels";
	private JCheckBoxMenuItem			showLabels						= new JCheckBoxMenuItem(
																				SHOW_LABELS);

	private static final String			SHOW_SENSOR_LINES				= "Show sensor lines";
	private JCheckBoxMenuItem			showSensorLines					= new JCheckBoxMenuItem(
																				SHOW_SENSOR_LINES);

//	/** Disabled for "Field Boundaries" function*/	
//	private static final String			FIELD_BOUNDARIES				= "Field Boundaries";
//	private JCheckBoxMenuItem			fieldBoundaries					= new JCheckBoxMenuItem(
//																				FIELD_BOUNDARIES);

	private static final String			AUTO_SCROLL						= "AutoScroll";
	private JCheckBoxMenuItem			autoScrollItem					= new JCheckBoxMenuItem(
																				AUTO_SCROLL);
	/** default AutoScroll Output mode */
	private boolean						autoScrollState					= true;

	// is set to a reasonable value upon creation
	/** The scale of the GUI */
	public static double				pixelsPerMeter					= Double.NaN;

	/** Number of repaints() per second */
	private int							refreshRate						= 25;

	/** The world that keeps the Movables and nonMovables */
	private World						world;

	// GUI component stuff
	/** timervalue of last repaint that was done */
	private long						previousFrame					= 0;
	/** the Timer that makes the world tick at refreshrate times a second */
	private Timer						timer							= null;
	/** the layered graphical pane of the world */
	private JLayeredPane				worldPane;
	private JTextArea					messageTextArea;
	private JScrollPane					messagePane;

	// Popupmenu stuff
	private JPopupMenu					jpopRobot;
	private JPopupMenu					jpopNonRobot;
	private boolean						mouseFrameDone					= false;
	private int							mouseAction;
	private PhysicalObject				selectedObject;
	private PhysicalObject				leftMenuObject;
	private double						initialRotation				= 0;

	// Similator speed
	private static final int			NORMAL_SPEED					= 1;
	private static final int			MAX_SPEED						= 8;
	private static int					simulatorSpeed					= NORMAL_SPEED;

	// Slide bar for the speed
	private JSlider						speedSlider;
	private JLabel						speedSliderLabel;
	private JLabel						speedSliderWarningLabel;

	// Play and pauze buttons
	private ButtonGroup					action_play_or_pauze;
	private JToggleButton				action_play_button;
	private JToggleButton				action_pauze_button;

	private static final String			SPEED_SLIDER_WARNING_IMAGE		= Simulator
																				.getRelativePath("./resources/warningsign.png");
	private static final String			SPEED_SLIDER_WARNING_MESSAGE	= "When using UVM robots an increased speed can produce an inaccurate simulation";

	private static final String			ACTION_PLAY_BUTTON_IMAGE		= Simulator
																				.getRelativePath("./resources/play.gif");
	private static final String			ACTION_PAUZE_BUTTON_IMAGE		= Simulator
																				.getRelativePath("./resources/pause.gif");
	private LeftGUIFrame left; 
	

	/** hashtable containing robot-robotWindow relations */
	private Hashtable					guiRegistry						= new Hashtable();

	/**
	 * Default constructor
	 * 
	 * @param width
	 *        Width of the GUI
	 * @param height
	 *        Height of the GUI
	 */
	public Simulator(double width, double height)
	{
		super();

		addComponentListener(componentListener);

		World.WIDTH = width;
		World.HEIGHT = height;
		world = new World(this);
	}

	/**
	 * Starts the simulation
	 */
	public void run()
	{
		Debug.DEBUG_LEVEL = Debug.DEBUG_LEVEL_DEBUG;
		Debug.printInfo(WINDOW_TITLE + " starting up");
		
		setOpaque(true);
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		

//		add(, java.awt.BorderLayout.CENTER);
//		add(, java.awt.BorderLayout.SOUTH);

		// Create and set up the window.
		JFrame frame = new JFrame(WINDOW_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JSplitPane center = new JSplitPane(JSplitPane.VERTICAL_SPLIT, getWorldPane(), getMessagePane());
		center.setBackground( Color.WHITE );
		center.setOpaque( true );
		center.setOneTouchExpandable(true);
		
		
		left = new LeftGUIFrame( frame );
		//this.paintAll( this.getGraphics() );
		
		JSplitPane main = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, left.getJScrollPane(), center );
		main.setOneTouchExpandable(true);
		//main.setAutoscrolls( true );
		
		add(new JPanel(), BorderLayout.NORTH);
		add( main, BorderLayout.CENTER );		
		
		// Create and set up the content pane and menu.
		frame.setContentPane(this);
		frame.setJMenuBar(this.getMenuBar());

		// Align and display the window.
		frame.pack();

		DisplayMode scr = frame.getGraphicsConfiguration().getDevice().getDisplayMode();
		frame.setLocation((scr.getWidth() - frame.getWidth()) / 2, (scr.getHeight() - frame
				.getHeight()) / 2);
		frame.setResizable(true);
		frame.setVisible(true);

		// Add line representations
		Line.init();
		worldPane.add(Line.linePane);
		worldPane.addComponentListener(componentListener);

		// Init popupmenu
		jpopRobot = objMenu(IS_ROBOT);
		jpopNonRobot = objMenu(IS_NO_ROBOT);

		// Create timer for screen refresh
		timer = new Timer((1000 / refreshRate) / simulatorSpeed, actionListener);
		previousFrame = System.currentTimeMillis();
		timer.start();

		// Forward all output to stdout to the simulator
		System.setOut(new java.io.PrintStream(new SimulatorOutputStream(this)));
		worldPane.validate();
		worldPane.repaint();
	}

	/**
	 * No introduction needed..
	 * 
	 * @param args
	 *        Usage: java Simulator [single|double]
	 */
	public static void main(String[] args)
	{
		double width;
		double height;
		int selection = -1;

		// Uncomment to allow for playingfield SIZE selection
		if (args.length == 1)
		{
			if (args[0].equals("single"))
			{
				selection = 0;
			}
			else if (args[0].equals("double"))
			{
				selection = 1;
			}
		}
		else
		{
			System.out.println("Usage: java Simulator [single/double]\nUsing default field SIZE");
		}

		switch (selection)
		{
			case 0: // RoboCup Jr. single fieldsize
				width = 1.20; // officially: 1.19
				height = 0.90; // officially: 0.87

				break;

			case 1: // RoboCup Jr. double fieldsize
				width = 1.80; // officially: 1.83
				height = 1.20; // officially: 1.22

				break;

			default:
			case 3:
				width = 5.1;
				height = 3.0;

				break;
		}

		Simulator.pixelsPerMeter = DEFAULT_WORLD_PANE_HEIGHT / height;

		// Create the new simulator thread
		new Simulator(width, height).run();
	}

	/**
	 * Update the Simulator GUI with the current state of the world on every
	 * timer refreshrate tick
	 */
	public void update()
	{
		// Clear the lines
		LineGraphicalRepresentation.lines.clear();

		// Allow for mouse action
		mouseFrameDone = false;

		long currentFrame = System.currentTimeMillis();
		world.update(simulatorSpeed * ((currentFrame - previousFrame) / 1000.0));
		previousFrame = currentFrame;

		Vector movableObjects = world.getMovableObjects();
		Vector nonMovableObjects = world.getNonMovableObjects();
		GraphicalRepresentation component = null;

		for (int i = 0; i < movableObjects.size(); i++)
		{
			MovableObject object = (MovableObject) movableObjects.get(i);
			component = object.getGraphicalRepresentation();
			if (component != null)
			{
				// Add the MouseListener if not done already
				if (component.getMouseListeners().length == 0)
				{
					component.addMouseListener(mouseListener);
				}
				// Add the MouseMotionListener if not done already
				if (component.getMouseMotionListeners().length == 0)
				{
					component.addMouseMotionListener(mouseListener);
				}
				// add the Movable component to the Simulator worldPane if not
				// already here
				if (!this.isAncestorOf(component))
				{
					if (component.layer == UNDEFINED_LAYER)
					{
						worldPane.add(component, MOVABLES_LAYER, -1);
					}
					else
					{
						worldPane.add(component, component.layer, -1);
					}
				}
			}
		}

		for (int i = 0; i < nonMovableObjects.size(); i++)
		{
			NonMovableObject object = (NonMovableObject) nonMovableObjects.get(i);
			component = object.getGraphicalRepresentation();
			if (component != null)
			{
				// Add the MouseListener if not done already
				if (component.getMouseListeners().length == 0)
				{
					component.addMouseListener(mouseListener);
				}
				// Add the MouseMotionListener if not done already
				if (component.getMouseMotionListeners().length == 0)
				{
					component.addMouseMotionListener(mouseListener);
				}

				// add the NonMovable component to the Simulator worldPane if
				// not already here
				if (!this.isAncestorOf(component))
				{
					// Debug.printDebug("component.getWidth() = " +
					// component.getWidth());
					if (component.layer == UNDEFINED_LAYER)
					{
						worldPane.add(component, NON_MOVABLES_LAYER, -1);
					}
					else
					{
						worldPane.add(component, component.layer, -1);
					}
				}
			}
		}
		// do a paintComponent of this Simulator JPanel
		worldPane.repaint();
	}

	/**
	 * Convert a Location object to a pixel location (point)
	 * 
	 * @param worldLocation
	 *        Location in the world
	 * @return Point in the correct place in pixels
	 */
	public static Point toPixelCoordinates(Location worldLocation)
	{
		int x = meterToPixel(worldLocation.getX());
		int y = meterToPixel(World.HEIGHT) - meterToPixel(worldLocation.getY());

		return new java.awt.Point(x, y);
	}

	/**
	 * Converts meters to pixels in the simulator
	 * 
	 * @param meters
	 *        number of meters
	 * @return int number of pixels
	 */
	public static int meterToPixel(double meters)
	{
		return (int) ((meters * pixelsPerMeter) + .5);
	}

	/**
	 * Print a message to the message pane
	 * 
	 * @param s
	 *        Message to show
	 */
	public void printMessage(String s)
	{
		messageTextArea.append(s);

		int lines = messageTextArea.getLineCount();
		// cleanup output window to prevent memory overflow
		if (lines > MESSAGE_PANE_MAXLINES)
		{
			messageTextArea.setText("NOTE: Output buffer cleaned up after " + MESSAGE_PANE_MAXLINES
					+ " lines\n");
			return;
		}

		if (autoScrollState)
		{
			messageTextArea.getCaret().setDot(messageTextArea.getText().length());

		}
	}
	
	/**
	 * Initializes menuBar
	 * 
	 * @return JMenuBar
	 */
	private JMenuBar getMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(getMenuSimulation());
		menuBar.add(getMenuView());
		menuBar.add(getMenuInsert());
		menuBar.add(getMenuOutput());

		menuBar.add(getMenuSeparator(40, 50));

		menuBar = setActionButtons(menuBar);

		menuBar.add(getMenuSeparator(20, 50));
		menuBar.add(getSliderLabel());
		menuBar.add(getSlider());
		menuBar.add(getWarningButton());

		return menuBar;
	}

	/**
	 * Makes the label with the warning sign
	 * 
	 * @return the label
	 */
	private JLabel getWarningButton()
	{
		speedSliderWarningLabel = new JLabel();
		speedSliderWarningLabel.setIcon(new ImageIcon(SPEED_SLIDER_WARNING_IMAGE));
		speedSliderWarningLabel.setToolTipText(SPEED_SLIDER_WARNING_MESSAGE);
		speedSliderWarningLabel.setVisible(false);
		return speedSliderWarningLabel;
	}

	/**
	 * Creates a separator for the menu to separate menu items As a default, the
	 * color is set as grey.
	 * 
	 * @param width
	 *        the width of the separator
	 * @param height
	 *        the height of the separator
	 * @return JSeparator
	 */
	private JSeparator getMenuSeparator(int width, int height)
	{
		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(width, height));
		separator.setForeground(new Color(204, 204, 204));

		return separator;
	}

	/**
	 * Creates the menu for the simulation
	 * 
	 * @return JMenu
	 */
	private JMenu getMenuSimulation()
	{
		JMenu menuSimulation = new JMenu(MENU_SIMULATION);
		menuSimulation.add(new JMenuItem(NEW));
		menuSimulation.addSeparator();
		menuSimulation.add(new JMenuItem("JuniorDemo"));
		menuSimulation.add(new JMenuItem("RescueDemo"));
		menuSimulation.add(new JMenuItem("SoccerDemo"));
		menuSimulation.add(new JMenuItem("SpeechDemo"));
		menuSimulation.add(new JMenuItem("MouseDemo"));
		menuSimulation.addSeparator();
		menuSimulation.add(new JMenuItem("MazePath Follower"));
		menuSimulation.add(new JMenuItem("MazeAgent Demo"));
		menuSimulation.add(new JMenuItem("Balls and Followers"));
		menuSimulation.add(new JMenuItem("BallLover vs. BallLover"));
		menuSimulation.add(new JMenuItem("Dancing joBots"));
		menuSimulation.addSeparator();
		menuSimulation.add(new JMenuItem(QUIT));

		// Add ActionListeners to all children
		Component[] items = menuSimulation.getMenuComponents();
		getMenuAction(items);

		return menuSimulation;
	}

	/**
	 * Initializes jMenu
	 * 
	 * @return JMenu
	 */
	private JMenu getMenuView()
	{
		JMenu menuCommands = new JMenu(MENU_VIEW);
		menuCommands.add(pause);
		showGrid.setState(grid);
		menuCommands.add(showGrid);
		showLabels.setState(GraphicalRepresentation.addLabels);
		menuCommands.add(showLabels);
		showSensorLines.setState(LineGraphicalRepresentation.showLines);
		menuCommands.add(showSensorLines);
//		/** Disabled for "Field Boundaries" function*/	
//		fieldBoundaries.setState(true);
//		menuCommands.add(fieldBoundaries);

		menuCommands.addSeparator();
		// changed for bug 17
		fields.add(showEmptyfield);
		showEmptyfield.setSelected(true);
		fields.add(showMaze);
		fields.add(showRescuefield);
		fields.add(showSoccerfield);
		fields.add(showSoccerfieldDbl);
		fields.add(showDancefloor);

		menuCommands.add(showEmptyfield);
		menuCommands.add(showMaze);
		menuCommands.add(showRescuefield);
		menuCommands.add(showSoccerfield);
		menuCommands.add(showSoccerfieldDbl);
		menuCommands.add(showDancefloor);

		// Add ActionListeners to all children
		Component[] items = menuCommands.getMenuComponents();
		getMenuAction(items);

		menuCommands.remove(pause);
		return menuCommands;
	}

	/**
	 * Creates the action button (play and pauze ). Default the action is pauze.
	 * 
	 * @return ButtonGroup
	 */
	private JMenuBar setActionButtons(JMenuBar menuBar)
	{
		action_play_or_pauze = new ButtonGroup();

		action_play_button = new JToggleButton();
		action_play_button.setIcon(new ImageIcon(ACTION_PLAY_BUTTON_IMAGE));
		action_play_button.setMaximumSize(new Dimension(24, 20));
		action_play_button.setActionCommand("play");
		action_play_button.addActionListener(actionListener);
		action_play_button.setSelected(true);

		action_pauze_button = new JToggleButton();
		action_pauze_button.setIcon(new ImageIcon(ACTION_PAUZE_BUTTON_IMAGE));
		action_pauze_button.setMaximumSize(new Dimension(24, 20));
		action_pauze_button.setActionCommand("pauze");
		action_pauze_button.addActionListener(actionListener);
		action_pauze_button.setSelected(false);

		action_play_or_pauze.add(action_play_button);
		action_play_or_pauze.add(action_pauze_button);

		// action_play_or_pauze.setSelected( action_play_button, true );

		// action_play_or_pauze.setIcon(new
		// ImageIcon(ACTION_PAUZE_BUTTON_IMAGE));

		menuBar.add(action_pauze_button);
		menuBar.add(action_play_button);

		return menuBar;
	}

	/**
	 * Creates the label for the speed slider
	 * 
	 * @return JLabel
	 */
	private JLabel getSliderLabel()
	{
		// ---- label for the slider
		speedSliderLabel = new JLabel();
		speedSliderLabel.setText("Speed:   ");
		speedSliderLabel.setFont(new Font("Dialog", Font.BOLD, 12));

		return speedSliderLabel;
	}

	/**
	 * Creates the speed slider
	 * 
	 * @return JSlider
	 */
	private JSlider getSlider()
	{
		speedSlider = new JSlider();
		speedSlider.setSnapToTicks(true);
		speedSlider.setMinimum(NORMAL_SPEED);
		speedSlider.setMaximum(MAX_SPEED);
		speedSlider.setMinorTickSpacing(1);
		speedSlider.setMajorTickSpacing(1);
		speedSlider.setPaintLabels(true);
		speedSlider.setMaximumSize(new Dimension(200, 30));
		speedSlider.setValue(simulatorSpeed);
		speedSlider.addChangeListener(actionListener);

		return speedSlider;
	}

	// TODO is this still needed?
	/**
	 * Initializes menuInsert
	 * 
	 * @return JMenu
	 */
	private JMenu getMenuInsert()
	{
		JMenu menuInsert = new JMenu("Insert");

		JMenu submenuObject = new JMenu("Object");
		menuInsert.add(submenuObject);
		submenuObject.add(new JMenuItem("Ball"));
		submenuObject.add(new JMenuItem("Wall"));
		submenuObject.add(new JMenuItem("Victim"));

		JMenu submenuRobot = new JMenu("Robot");
		menuInsert.add(submenuRobot);
		submenuRobot.add(new JMenuItem("joBot"));
		submenuRobot.add(new JMenuItem("joBotJr"));
		submenuRobot.add(new JMenuItem("RCJoBot"));
		submenuRobot.add(new JMenuItem("LightBot"));
		submenuRobot.add(new JSeparator());
		submenuRobot.add(new JMenuItem("UVMDemo"));
		submenuRobot.add(new JMenuItem("JPB2Demo"));
		submenuRobot.add(new JMenuItem("JPB2JrDemo"));
		submenuRobot.add(new JSeparator());
		submenuRobot.add(new JMenuItem("RescueBot"));
		submenuRobot.add(new JMenuItem("SoccerBot"));
		submenuRobot.add(new JMenuItem("MouseBot"));

		// Add ActionListeners to all children
		Component[] items = menuInsert.getMenuComponents();
		getMenuAction(items);

		return menuInsert;
	}

	/**
	 * Initializes menuOutput
	 * 
	 * @return JMenu
	 */
	private JMenu getMenuOutput()
	{
		JMenu menuOutput = new JMenu("Output");
		menuOutput.add(new JMenuItem("0 - Off"));
		menuOutput.add(new JMenuItem("2 - Error level"));
		menuOutput.add(new JMenuItem("3 - Info level"));
		menuOutput.add(new JMenuItem("4 - Debug level"));

		autoScrollItem.setState(autoScrollState);
		menuOutput.add(autoScrollItem);

		// Add ActionListeners to all children
		Component[] items = menuOutput.getMenuComponents();
		getMenuAction(items);

		return menuOutput;
	}

	/**
	 * Recursive function to add actionlisteners and itemlisteners
	 * 
	 * @param items
	 *        MenuItems
	 */
	public void getMenuAction(Component[] items)
	{

		for (int i = 0; i < items.length; i++)
		{
			if (items[i] instanceof JCheckBoxMenuItem)
			{
				((JMenuItem) items[i]).addItemListener(itemListener);
			}
			else if (items[i] instanceof JRadioButtonMenuItem)
			{
				((JMenuItem) items[i]).addItemListener(itemListener);
			}
			else if (items[i] instanceof JMenu)
			{
				Component[] subItems = ((JMenu) items[i]).getMenuComponents();
				getMenuAction(subItems);
			}
			else if (items[i] instanceof JMenuItem)
			{
				((JMenuItem) items[i]).addActionListener(actionListener);
			}
		}
	}

	/**
	 * Initializes worldPane
	 * 
	 * @return JLayeredPane
	 */
	public JLayeredPane getWorldPane()
	{
		if (worldPane == null)
		{
			worldPane = new WorldPane();
			
			int pixelWidth = meterToPixel(World.WIDTH);
			int pixelHeight = meterToPixel(World.HEIGHT);
			worldPane.setPreferredSize(new Dimension(pixelWidth, pixelHeight));
			worldPane.setMinimumSize(new Dimension(pixelWidth, pixelHeight));
		}

		return worldPane;
	}
	/**
	 * Subclasses should be generic reusable object.
	 * This is not (Sorry can't help this code stinks).
	 * 
	 * This class is only used to override the paintComponent(Graphics g) method. 
	 * The only reason this class exists is that it's better then using a anonymous inner class.
	 */
	private class WorldPane extends JLayeredPane{
		
		/**
		 * Method for repainting the grid on the wordlpane
		 * @see javax.swing.JComponent.paintComponent(Graphics g)
		 */
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			// (re)painting canvas and grid
			if (grid && ( worldPane != null))
			{
				Rectangle bounds = worldPane.getBounds();
				int xmin = (int) bounds.getX() - 1;
				int ymin = (int) bounds.getY() - 1;
				int xmax = xmin + (int) bounds.getWidth();
				int ymax = ymin + (int) bounds.getHeight();
				double distance = World.GRID_SIZE;

				/*
				 * Grid starts in the top left corner instead of world coordinates
				 * (0,0) because Maze does so too.
				 */
				g.setColor(GRID_COLOR);

				double i = 0;
				int x = xmin;

				while (x < xmax)
				{
					g.drawLine(x, ymin, x, ymax);
					i += distance;
					x = (meterToPixel(i) + xmin);
				}

				i = 0;

				int y = ymin;

				while (y < ymax)
				{
					g.drawLine(xmin, y, xmax, y);
					i += distance;
					y = (meterToPixel(i) + ymin);
				}
			}
		}
	}

	/**
	 * Initializes messagePanel
	 * 
	 * @return JPanel
	 */
	private JScrollPane getMessagePane()
	{
		messagePane = new JScrollPane();
		messagePane.setBackground(Color.WHITE);
		messagePane.getViewport().add(getMessageTextArea(), null);
		messagePane.setBorder(new TitledBorder("Server Messages"));

		return messagePane;
	}

	/**
	 * Initializes messageTextArea
	 * 
	 * @return JTextArea
	 */
	private JTextArea getMessageTextArea()
	{
		messageTextArea = new JTextArea();
		messageTextArea.setRows(MESSAGE_PANE_ROWS);
		messageTextArea.setEditable(false);
		messageTextArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
		messageTextArea.setLineWrap(true);
		messageTextArea.setCaret(new ConsoleCaret());

		return messageTextArea;
	}
	
	public RobotGUI getRobotGUI()
	{
		RobotGUI rgui = null;
		
		if (selectedObject instanceof Robot)
		{
			if (guiRegistry.containsKey(selectedObject))
			{
				Vector v = (Vector) guiRegistry.get(selectedObject);

				for (int i = 0; i < v.size(); i++)
				{
					IGUI igui = (IGUI) v.get(i);
					if (igui instanceof RobotGUI)
					{
						//igui.setVisible(true);
						rgui = (RobotGUI)igui;
					}
				}
			}
			else
			{
				RobotGUI robotGUI = new RobotGUI((Robot) selectedObject, getSimulator());

				Vector guis = new Vector(1);
				guis.add(robotGUI);

				// This causes the frame to pop to the front (Hotfix)
				//robotGUI.setVisible(true);

				guiRegistry.put(selectedObject, guis);
				rgui = robotGUI;
			}
		}
		else
		{
			Debug.printInfo("The selected object is not a robot");
		}

		
		
		return rgui;
	}

	/**
	 * Remove a graphical representation from the gui, this method should only
	 * be called by World when an object is removed from the PhysicalObject
	 * vectors. Calling this method without removing the object itself is
	 * pointless.
	 * 
	 * @param component
	 *        TODO PARAM: DOCUMENT ME!
	 */
	public void removeGraphicalRepresentation(javax.swing.JComponent component)
	{
		worldPane.remove(component);
	}

	/**
	 * Removes the movable objects from the world prior to starting a field
	 * demonstration. Function removeGraphicalRepresentation doesn't seem to be
	 * mandatory in order to remove an object.
	 * 
	 * If the MovableObject is a Robot: Kill its thread and remove its GUIs
	 */
	private void clearMovableObjectsfromWorld()
	{
		Vector movableObjects = world.getMovableObjects();
		int movableobsSize = movableObjects.size();

		// Always remove object at position 0 in vector until the vector is
		// empty, because
		// by removing an object form a vector its size reduces.
		for (int i = 0; i < movableobsSize; i++)
		{
			if (movableObjects.get(0) instanceof Robot)
			{
				Robot robot = (Robot) movableObjects.get(0);
				Vector guis = (Vector) guiRegistry.get(robot);

				if (robot.getAgent() != null)
				{
					if (robot.getAgent().isRunning() == true)
					{
						robot.getAgent().kill();
					}
				}
				
				if (guis != null)
				{
					for (int j = 0; j < guis.size(); j++)
					{
						IGUI igui = (IGUI) guis.get(j);
						igui.destroy();
					}
				}

				guiRegistry.remove((Robot) movableObjects.get(0));
			}
			world.removeMovableObject((MovableObject) movableObjects.get(0));
		}
		
		left.removeAll();
	}

	/**
	 * Remove all IDragable Object (Movable and NonMovable) from the world.
	 * 
	 */
	private void clearIDragableObjectsfromWorld()
	{
		Vector movableObjects = world.getMovableObjects();
		Vector nonMovableObjects = world.getNonMovableObjects();
		int movableobSize = movableObjects.size();
		int nonMovableobSize = nonMovableObjects.size();

		for (int i = 0; i < movableobSize; i++)
		{
			if (movableObjects.get(i) instanceof IDragable)
			{
				world.removeMovableObject((MovableObject) movableObjects.get(i));
			}
		}

		for (int i = 0; i < nonMovableobSize; i++)
		{
			if (nonMovableObjects.get(i) instanceof IDragable)
			{
				world.removeNonMovableObject((NonMovableObject) nonMovableObjects.get(i));
			}
		}
	}

	/**
	 * Create a RobotGUI for a given Robot. And register the created RobotGUI to
	 * the guiRegistry.
	 * 
	 * @param r
	 *        Robot to Create the RobotGUI for
	 */
	private void addRobotToGui(Robot r)
	{
		RobotGUI robotGUI = new RobotGUI(r, this);

		Vector guis = new Vector(1);
		guis.add(robotGUI);

		PhysicalObject robotPO = (PhysicalObject)r;
		left.showGUI( robotPO, robotGUI );
			
		guiRegistry.put(r, guis);

		world.addMovableObject(r);
	}

	/**
	 * Link a GUI to a Robot
	 * 
	 * @param r
	 *        The robot to link the GUI to.
	 * @param gui
	 *        The GUI to link to the Robot
	 */
	public void linkGuiToRobot(Robot r, IGUI gui)
	{
		Vector guis = (Vector) guiRegistry.get(r);
		guis.add(gui);
	}

	private Simulator getSimulator()
	{
		return this;
	}

	public Hashtable getGuiRegistry()
	{
		return guiRegistry;
	}

	/**
	 * The AComponentListener that handles Window resize events
	 * 
	 * @version $Revision$ last changed 20-02-2006
	 * 
	 */
	private class SimulatorComponentListener implements ComponentListener
	{
		public void componentHidden(ComponentEvent e)
		{
		}

		public void componentMoved(ComponentEvent e)
		{
		}

		public void componentShown(ComponentEvent e)
		{
		}

		public void componentResized(ComponentEvent e)
		{
			world.resize(World.WIDTH, World.HEIGHT);
		}
	}

	/**
	 * The ActionListener that handles Timer tick events and selecten Menu
	 * events
	 * 
	 * @version $Revision$ last changed 20-02-2006
	 */
	private class SimulatorActionListener implements ActionListener, ChangeListener
	{
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e)
		{
			// the timer tick is done many times a second so check that first
			if (e.getActionCommand() == null)
			{
				// Frame Refresh timer: call Simulator's update
				update();
				return;
			}

			if (e.getActionCommand().equals(NEW))
			{
				clearMovableObjectsfromWorld();
				showEmptyfield.doClick();
				showEmptyfield.setSelected(true);
				clearIDragableObjectsfromWorld();

				return;
			}

			if (e.getActionCommand().equals("Quit"))
			{
				System.exit(0);
				return;
			}

			if (e.getActionCommand().equals("MazePath Follower"))
			{
				clearMovableObjectsfromWorld();
				showMaze.setSelected(true);

				MazeRobot mr = new MazeRobot("MazeMan", (World.GRID_SIZE / 2), World.HEIGHT
						- (World.GRID_SIZE / 2));
				new Thread(new MazeAgent(mr)).start();
				world.addMovableObject(mr);

				return;
			}

			if (e.getActionCommand().equals("0 - Off"))
			{
				Debug.DEBUG_LEVEL = Debug.DEBUG_LEVEL_NONE;
				System.out.println("Debug level is now: off");
				return;
			}

			if (e.getActionCommand().equals("2 - Error level"))
			{
				Debug.DEBUG_LEVEL = Debug.DEBUG_LEVEL_ERROR;
				System.out.println("Debug level is now: Error");
				return;
			}

			if (e.getActionCommand().equals("3 - Info level"))
			{
				Debug.DEBUG_LEVEL = Debug.DEBUG_LEVEL_INFO;
				System.out.println("Debug level is now: Info");
				return;
			}

			if (e.getActionCommand().equals("4 - Debug level"))
			{
				Debug.DEBUG_LEVEL = Debug.DEBUG_LEVEL_DEBUG;
				System.out.println("Debug level is now: Debug");
				return;
			}

			if (e.getActionCommand().equals("Ball"))
			{
				Ball b = new Ball();
				b.setVelocityX(0);
				b.setVelocityY(0);
				world.addMovableObject(b);

				return;
			}

			if (e.getActionCommand().equals("Wall"))
			{
				Wall b = new Wall();
				// b.setVelocityX(0);
				// b.setVelocityY(0);
				// b.name = "Wall";
				// world.addMovableObject(b);
				world.addNonMovableObject(b);

				return;
			}

			if (e.getActionCommand().equals("joBot"))
			{
				Robot r = new Jobot("Jobot", World.WIDTH / 2, World.HEIGHT / 2);
				addRobotToGui(r);

				return;
			}

			if (e.getActionCommand().equals("joBotJr"))
			{
				Robot r = new JPB2JrRobot("Jobot Jr", World.WIDTH / 2, World.HEIGHT / 2);
				addRobotToGui(r);

				return;
			}

			if (e.getActionCommand().equals("RCJoBot"))
			{
				Robot r = new RCJobot("RCJobot", World.WIDTH / 2, World.HEIGHT / 2);
				addRobotToGui(r);

				return;
			}
			
			if (e.getActionCommand().equals("RescueBot"))
			{
				Robot r = new JPBRescueRobot("RescueJobot", World.WIDTH / 2, World.HEIGHT / 2);
				addRobotToGui(r);

				return;
			}

			if (e.getActionCommand().equals("SoccerBot"))
			{
				Robot r = new JPBSoccerRobot("SoccerJobot", World.WIDTH / 2, World.HEIGHT / 2);
				addRobotToGui(r);

				return;
			}

			if (e.getActionCommand().equals("MouseBot"))
			{
				Robot r = new JPBMouseRobot("MouseJobot", World.WIDTH / 2, World.HEIGHT / 2);
				addRobotToGui(r);

				return;
			}

			if (e.getActionCommand().equals("LightBot"))
			{
				Robot r = new LightBot();
				addRobotToGui(r);

				return;
			}

			if (e.getActionCommand().equals("Victim"))
			{
				Victim b = new Victim();
				world.addMovableObject(b);

				return;
			}

			if (e.getActionCommand().equals("UVMDemo"))
			{
				Robot r = new JPBRobot("UVMDemo", World.WIDTH / 2, World.HEIGHT / 2);
				addRobotToGui(r);

				return;
			}

			if (e.getActionCommand().equals("JuniorDemo"))
			{
				clearMovableObjectsfromWorld();
				showSoccerfieldDbl.setSelected(true);
				Robot r = new JPB2JrRobot("JuniorDemo", World.WIDTH / 2, World.HEIGHT / 2);
				RobotGUI robotGUI = new RobotGUI(r, getSimulator());
				world.addMovableObject(r);
				Ball b = new Ball();
				b.setLocation(new Location(2.9, World.HEIGHT - 0.9));
				world.addMovableObject(b);
				Vector guis = new Vector(2);
				guis.add(robotGUI);
				guiRegistry.put(r, guis);
				
				//shows the gui in the left panel
				PhysicalObject robotPO = (PhysicalObject)r;
				left.showGUI( robotPO, robotGUI );
				
				robotGUI.setDipSwitch(0);
				return;
			}

				if (e.getActionCommand().equals("RescueDemo"))
			{
				clearMovableObjectsfromWorld();
				showRescuefield.setSelected(true);
				Robot r = new JPBRescueRobot("RescueDemo", World.WIDTH / 2, World.HEIGHT / 2);
				r.diameter=0.12;
				r.setLocation(new Location(r.diameter*0.9, r.diameter));
				r.rotation = 2 * Math.PI;
				Victim v = new Victim();
				world.addMovableObject(v);

				RobotGUI robotGUI = new RobotGUI(r, getSimulator());
				Vector guis = new Vector(2);
				guis.add(robotGUI);
				guiRegistry.put(r, guis);
				world.addMovableObject(r);
				
				//shows the gui in the left panel
				PhysicalObject robotPO = (PhysicalObject)r;
				left.showGUI( robotPO, robotGUI );
				
				robotGUI.setDipSwitch(2);
				return;
			}

			if (e.getActionCommand().equals("SoccerDemo"))
			{
				clearMovableObjectsfromWorld();
				showSoccerfieldDbl.setSelected(true);
				Robot r = new JPBSoccerRobot("SoccerDemo", 0.15, World.HEIGHT / 2);
				r.diameter=0.12;
				r.rotation = 0.5 * Math.PI;
				Ball b = new Ball();
				b.setLocation(new Location(0.0, World.HEIGHT));
				world.addMovableObject(b);

				RobotGUI robotGUI = new RobotGUI(r, getSimulator());
				Vector guis = new Vector(2);
				guis.add(robotGUI);
				guiRegistry.put(r, guis);
				world.addMovableObject(r);
				
				//shows the gui in the left panel
				PhysicalObject robotPO = (PhysicalObject)r;
				left.showGUI( robotPO, robotGUI );
				
				robotGUI.setDipSwitch(1);
				return;
			}

			if (e.getActionCommand().equals("SpeechDemo"))
			{
				clearMovableObjectsfromWorld();
				showSoccerfieldDbl.setSelected(true);
				Robot r = new JPBSoccerRobot("SpeechDemo", World.WIDTH / 2, World.HEIGHT / 2);
				r.diameter=0.12;
				r.setLocation(new Location(r.diameter*0.9, r.diameter));
				Ball b = new Ball();
				world.addMovableObject(b);

				RobotGUI robotGUI = new RobotGUI(r, getSimulator());
				Vector guis = new Vector(2);
				guis.add(robotGUI);
				guiRegistry.put(r, guis);
				world.addMovableObject(r);
				
				//shows the gui in the left panel
				PhysicalObject robotPO = (PhysicalObject)r;
				left.showGUI( robotPO, robotGUI );
				
				robotGUI.setDipSwitch(0);
				return;
			}

			if (e.getActionCommand().equals("MouseDemo"))
			{
				clearMovableObjectsfromWorld();
				showRescuefield.setSelected(true);
				Robot r = new JPBMouseRobot("MouseDemo", World.WIDTH / 2, World.HEIGHT / 2);
				r.diameter=0.12;
				r.setLocation(new Location(r.diameter*0.9, r.diameter));
				r.rotation = 2 * Math.PI;

				RobotGUI robotGUI = new RobotGUI(r, getSimulator());
				Vector guis = new Vector(2);
				guis.add(robotGUI);
				guiRegistry.put(r, guis);
				world.addMovableObject(r);
				
				//shows the gui in the left panel
				PhysicalObject robotPO = (PhysicalObject)r;
				left.showGUI( robotPO, robotGUI );
				
				robotGUI.setDipSwitch(0);
				return;
			}

			if (e.getActionCommand().equals("JPB2Demo"))
			{
				Robot r = new JPB2Robot("JPB2Demo", World.WIDTH / 2, World.HEIGHT / 2);
				addRobotToGui(r);

				return;
			}

			if (e.getActionCommand().equals("JPB2JrDemo"))
			{
				Robot r = new JPB2JrRobot("JuniorDemo", World.WIDTH / 2, World.HEIGHT / 2);
				addRobotToGui(r);

				return;
			}

			if (e.getActionCommand().equals("MazeAgent Demo"))
			{
				clearMovableObjectsfromWorld();
				showMaze.setSelected(true);

				// Create a Jobot
				Jobot j = new Jobot();

				new Thread(new MazeAgent(j)).start();
				world.addMovableObject(j);
				return;
			}

			if (e.getActionCommand().equals("Balls and Followers"))
			{
				clearMovableObjectsfromWorld();
				showSoccerfield.setSelected(true);
				showLabels.setState(true);
				showSensorLines.setState(false);

				// Create some heavy balls
				for (int i = 0; i < 1; i++)
				{
					Ball b = new Ball();
					b.diameter = 0.2;
					b.mass = 1.5;
					b.setFriction(0.6);
					world.addMovableObject(b);
				}

				// Create some small robots that love the ball
				for (int i = 0; i < 22; i++)
				{
					LightBot j = new LightBot();
					// Ideally you want to change the diameter of the robots, to make 
					// the demo nicer.. though the collision detection in SoccerWalls.java
					// is not compatible with diameters below 0.10!!! Hence the following
					// line, and the commented : j.diameter = 0.5;
					world.resize(5.1, 3.0);
					//j.diameter = 0.05;
					
					j.mass = 1;
					j.setLocation(new Location(Math.random() * World.WIDTH, Math.random()
							* World.HEIGHT));
					j.name = "B" + i;
					new Thread(new BallLover(j)).start();
					world.addMovableObject(j);
				}
				return;
			}

			if (e.getActionCommand().equals("BallLover vs. BallLover"))
			{
				clearMovableObjectsfromWorld();
				showSoccerfield.setSelected(true);

				// add ball and two BallLovers and start!
				Ball b = new Ball();
				b.mass = 3;
				System.out.println(b.mass);
				b.setLocation(new Location(World.WIDTH / 2, World.HEIGHT / 2));
				world.addMovableObject(b);

				LightBot j = new LightBot();
				j.setLocation(new Location(World.WIDTH - 1, World.HEIGHT - 1));
				j.rotation = .75 * Math.PI;
				new Thread(new BallLover(j)).start();
				world.addMovableObject(j);

				j = new LightBot();
				j.setLocation(new Location(1, 1));
				j.rotation = 1.75 * Math.PI;
				new Thread(new BallLover(j)).start();
				world.addMovableObject(j);
			}

			if (e.getActionCommand().equals("Dancing joBots"))
			{
				clearMovableObjectsfromWorld();
				showDancefloor.setSelected(true);

				/**
				 * Create some Jobots, put them on the right position and give
				 * the jobots the right rotation
				 */
				Jobot j = new Jobot();
				j.setLocation(new Location((World.WIDTH / 2) + 0.4, World.HEIGHT / 2));
				j.rotation = Math.PI / 2;

				Jobot j1 = new Jobot();
				j1.setLocation(new Location((World.WIDTH / 2) - 0.4, World.HEIGHT / 2));
				j1.rotation = 1.5 * Math.PI;

				Jobot j2 = new Jobot();
				j2.setLocation(new Location((World.WIDTH / 2) - 1.2, World.HEIGHT / 2));
				j2.rotation = Math.PI;

				Jobot j3 = new Jobot();
				j3.setLocation(new Location((World.WIDTH / 2) + 1.2, World.HEIGHT / 2));

				// add the jobots to the World
				world.addMovableObject(j);
				world.addMovableObject(j1);
				world.addMovableObject(j2);
				world.addMovableObject(j3);

				// assign and start the correct behaviours
				new Thread(new DanceAgent2(j)).start();
				new Thread(new DanceAgent2(j1)).start();
				new Thread(new DanceAgent(j2)).start();
				new Thread(new DanceAgent(j3)).start();
			}

			// ****************
			// Popup menu
			// ****************
			if (e.getActionCommand().equals("Remove object"))
			{
				if (selectedObject instanceof IDragable)
				{
					if (selectedObject instanceof MovableObject)
					{
						world.removeMovableObject((MovableObject) selectedObject);

						if (selectedObject instanceof Robot)
						{
							Robot robot = (Robot) selectedObject;
							if( left.isShowingGuiFromRobot( selectedObject ) )
							{
								left.clearPanel();
							}

							if (robot.getAgent() != null)
							{
								if (robot.getAgent().isRunning() == true)
								{
									robot.getAgent().kill();
								}
							}
						}

						if (guiRegistry.containsKey(selectedObject))
						{
							Vector v = (Vector) guiRegistry.get(selectedObject);

							for (int i = 0; i < v.size(); i++)
							{
								IGUI igui = (IGUI) v.get(i);
								igui.destroy();
							}

							guiRegistry.remove((Robot) selectedObject);
						}
						else
						{
							Debug.printInfo("Robot does not exist");
						}
					}
					else if (selectedObject instanceof NonMovableObject)
					{
						world.removeNonMovableObject((NonMovableObject) selectedObject);
					}
				}
				mouseAction = NO_MOUSE_ACTION;

				return;
			}

			if (e.getActionCommand().equals("Open Robot GUI"))
			{
				
				//checken of er al een extern window is
				
				if( selectedObject instanceof Robot )
				{
					//shows left panel;
					left.showGUI( selectedObject, getRobotGUI() );
				}
			}
			
			if( e.getActionCommand().equals("Open External Robot GUI"))
			{
				if ( left.isShowingGuiFromRobot( selectedObject ) )
				{
					left.clearPanel();
				}
				
				RobotGUI rgui = getRobotGUI();
				rgui.init();
				mouseAction = NO_MOUSE_ACTION;
				rgui.setVisible( true );
			}

			if (e.getActionCommand().equals("pauze") || e.getActionCommand().equals("play"))
			{
				// if a button is pressed, trigger the command play
				if (action_pauze_button.isSelected())
				{
					action_pauze_button.setSelected(true);
					action_play_button.setSelected(false);
				}
				else
				{
					action_pauze_button.setSelected(false);
					action_play_button.setSelected(true);
				}

				action_pauze_button.repaint();
				action_play_button.repaint();

				// System.out.println(e.getActionCommand() + " pressed!");
				pause.setSelected(e.getActionCommand().equals("pauze"));

			}

			// if (e.getActionCommand().equals("play"))
			// {
			//				
			// //if the play button is pressed, trigger the pauze command
			// action_pauze_button.setSelected( true );
			// action_play_button.setSelected( false );
			//				
			// // action_play_or_pauze.setActionCommand("pauze");
			// // action_play_or_pauze.setIcon(new
			// ImageIcon(ACTION_PAUZE_BUTTON_IMAGE));
			// // action_play_or_pauze.repaint();
			// //
			// System.out.println("play pressed!");
			// pause.setSelected(false);
			// }

		}

		

		/**
		 * Handles the change of the speed slider
		 */
		public void stateChanged(ChangeEvent e)
		{

			if (e.getSource().equals(speedSlider))
			{
				int sliderValue = speedSlider.getValue();

				if (sliderValue == NORMAL_SPEED)
				{
					// if the slider has normal speed, hide the warninglabel.
					speedSliderWarningLabel.setVisible(false);
				}
				else
				{
					// else show the warninglabel.
					speedSliderWarningLabel.setVisible(true);
				}
				speedSliderWarningLabel.repaint();

				setSimulatorSpeed(sliderValue);
			}

		}
	}

	/**
	 * Creates the popup menu for objects in the sim
	 * 
	 * @param type
	 *        The type of the object, IS_ROBOT means the object is a Robot,
	 *        IS_NO_ROBOT means the object is not a robot
	 * @return The popup menu
	 */
	private JPopupMenu objMenu(int type)
	{
		JPopupMenu jpop = new JPopupMenu();
		jpop.add("Remove object");
		if (type == IS_ROBOT)
		{
			jpop.add("Open Robot GUI");
			jpop.add("Open External Robot GUI");
		}
			

		// Add ActionListeners to all children
		Component[] items = jpop.getComponents();

		for (int i = 0; i < items.length; i++)
		{
			if (items[i] instanceof JMenuItem)
			{
				JMenuItem item = (JMenuItem) items[i];
				item.addActionListener(actionListener);
			}
		}

		jpop.setOpaque(true);

		return jpop;
	}

	/**
	 * Created on 20-02-2006 Copyright: (c) 2006 Company: Dancing Bear Software
	 * 
	 * @version $Revision$ last changed 20-02-2006 TODO CLASS: DOCUMENT ME!
	 */
	private class SimulatorItemListener implements ItemListener
	{
		/**
		 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
		 */
		public void itemStateChanged(ItemEvent e)
		{
			if ((e.getItem() instanceof JCheckBoxMenuItem))
			{
				JCheckBoxMenuItem item = (JCheckBoxMenuItem) e.getItem();
				boolean state = item.getState(); // item selected true or not
				doCBItemChange(item, state);
			}
			else if ((e.getItem() instanceof JRadioButtonMenuItem))
			{
				JRadioButtonMenuItem item = (JRadioButtonMenuItem) e.getItem();
				String text = item.getText();

				// clearMovableObjectsfromWorld();
				if (text.equals(SHOW_EMPTY_FIELD))
				{
					showEmptyField();
				}
				else if (text.equals(SHOW_MAZE))
				{
					showMaze();
				}
				else if (text.equals(SHOW_RESCUEFIELD))
				{
					showRescueField();
				}
				else if (text.equals(SHOW_DANCEFLOOR))
				{
					showDanceFloor();
				}
				else if (text.equals(SHOW_SOCCERFIELD))
				{
					showSoccerField(false);
				}
				else if (text.equals(SHOW_SOCCERFIELDDBL))
				{
					showSoccerField(true);
				}
			}
			else
			{
				System.out.println("Current button is not supported" + e.getItem().getClass());
			}
		}

		/**
		 * Handles the CheckBoxitemStateChanged events
		 * 
		 * @param menuItemText
		 *        the menu-option that is set/reset
		 * @param state
		 *        the new state it is in
		 */
		public void doCBItemChange(JCheckBoxMenuItem item, boolean state)
		{
			String text = item.getText();
			if (text.equals("Pause"))
			{
				if (state == true)
				{
					// Set previousFrame to the time elapsed since last
					// frame
					previousFrame = System.currentTimeMillis() - previousFrame;
					timer.stop();
					Debug.printInfo("Simulation paused");
					Debug.printDebug("Time elapsed since previous update is " + previousFrame
							+ " ms");
				}
				else
				{
					/*
					 * Set previousFrame to the current time frame minus the
					 * time already elapsed between the last update and pauseing
					 * the simulation as if time stood still during the paused
					 * period.
					 */
					previousFrame = System.currentTimeMillis() - previousFrame;
					timer.start();
					Debug.printInfo("Simulation unpaused");
				}

				return;
			}
//			/** Disabled for "Field Boundaries" function*/	
//			else if (text.equals("Field Boundaries"))
//			{
//				Vector movableObjects = world.getMovableObjects();
//
//				for (int i = 0; i < movableObjects.size(); i++)
//				{
//					if (movableObjects.get(i) instanceof Robot)
//					{
//						((Robot) movableObjects.get(i)).setCollideWithBoundaries(fieldBoundaries
//								.getState());
//					}
//				}
//			}
			else if (text.equals(SHOW_GRID))
			{
				grid = state;
				repaint();
				return;
			}
			else if (text.equals(SHOW_LABELS))
			{
				GraphicalRepresentation.addLabels = state;

				int n = worldPane.getComponentCount();

				for (int i = 0; i < n; i++)
				{
					Component component = worldPane.getComponent(i);

					if (component instanceof GraphicalRepresentation)
					{
						((GraphicalRepresentation) component).setLabeled(state);
					}
				}
				repaint();
				return;
			}
			else if (text.equals(SHOW_SENSOR_LINES))
			{
				LineGraphicalRepresentation.showLines = state;
				repaint();
				return;
			}
			else if (text.equals(AUTO_SCROLL))
			{
				autoScrollState = state;
				System.out.println("AutoScroll set " + state);

				if (!autoScrollState && messageTextArea.getText().length() > 0)
					messageTextArea.getCaret().setDot(messageTextArea.getText().length() - 1);

				return;
			}
		}

		/**
		 * 
		 */
		private void showEmptyField()
		{
			world.setPlayingfield(null);
			world.setDanceFloor(null);
			world.setRescueField(null);
			world.setMaze(null);
			world.resize(4.25, 2.5);
			Simulator.IMAGE_FILE = "";
		}

		/**
		 * 
		 */
		private void showSoccerField(boolean doubleField)
		{
			NonMovableObject socWalls = new SoccerWalls("soccerwall", World.HEIGHT);

			if (world.getPlayingfield() == null)
			{
				world.setPlayingfield(new PlayingField());
				world.addNonMovableObject(world.getPlayingfield());
				world.addNonMovableObject(socWalls);
			}
			else
			{
				world.removeNonMovableObject(world.getPlayingfield());

				for (int i = 0; i < world.getNonMovableObjects().size(); i++)
				{
					if (world.getNonMovableObjects().get(i) instanceof SoccerWalls)
					{
						world.getNonMovableObjects().remove(i);
					}
				}

				Debug.printDebug("Removing NonMovableObject soccerwall");
				world.setPlayingfield(null);
			}

			if (doubleField)
				world.resize(1.83, 1.22);
			else
				world.resize(1.19, 0.87);

			return;
		}

		/**
		 * 
		 */
		private void showDanceFloor()
		{
			if (world.getDanceFloor() == null)
			{
				world.setDanceFloor(new DanceFloor());
				world.addNonMovableObject(world.getDanceFloor());
			}
			else
			{
				world.removeNonMovableObject(world.getDanceFloor());
				world.setDanceFloor(null);
			}
			world.resize(10.0, 5.0);
		}

		/**
		 * 
		 */
		private void showRescueField()
		{
			if (world.getRescueField() == null)
			{
				world.setRescueField(new RescueField());
				world.addNonMovableObject(world.getRescueField());
			}
			else
			{
				world.removeNonMovableObject(world.getRescueField());
				world.setRescueField(null);
			}
			world.resize(1.2, 1.0);
			return;
		}

		/**
		 * 
		 */
		private void showMaze()
		{
			if (world.getMaze() == null)
			{
				// Simulator.pixelsPerMeter = 200;
				world.setMaze(new javaBot.maze.Maze());
				world.addNonMovableObject(world.getMaze());
			}
			else
			{
				world.removeNonMovableObject(world.getMaze());
				world.setMaze(null);
			}

			return;
		}
	}

	/**
	 * MouseListener for Simulator Handles the popupmenu and the click,
	 * click+ctrl, click+shift actions
	 * <p>
	 * 
	 * @version $Revision$ last changed 20-02-2006
	 */
	private class SimulatorMouseListener implements MouseListener, MouseMotionListener
	{
		/**
		 * Handles the popupmenu and the click, click+ctrl, click+shift actions
		 * 
		 * @param mouseEvent
		 *        MouseEvent argument
		 */
		public void mousePressed(MouseEvent mouseEvent)
		{
			Component component = mouseEvent.getComponent();
			selectedObject = ((GraphicalRepresentation) component).getPhysicalObject();

			if ((mouseEvent.getButton() == RIGHT_MOUSE_BUTTON) && !robotPopupsVisible())
			{
				// Show popup window
				mouseAction = SHOW_POPUP_MOUSE_ACTION;

				// The pulldown menu depends on the type of the selected object
				if (selectedObject instanceof Robot)
				{
					// when robot, show robot Pulldown at mouse coordinates
					jpopRobot.show(component, mouseEvent.getX(), mouseEvent.getY());
				}
				else
				{
					// no robot, so show non-robot pulldown at mouse coordinates
					jpopNonRobot.show(component, mouseEvent.getX(), mouseEvent.getY());
				}

				// 
			}
			else if ((mouseEvent.getButton() == LEFT_MOUSE_BUTTON) )
			{
				handleLeftMouseButton(mouseEvent, component);
			}
		}

		private boolean robotPopupsVisible()
		{
			return (jpopNonRobot.isVisible() && jpopRobot.isVisible() );
		}

		private void handleLeftMouseButton(MouseEvent mouseEvent, Component component)
		{
			selectedObject = ((GraphicalRepresentation) component).getPhysicalObject();
			
			if( selectedObject instanceof IDragable )
			{
				if (mouseEvent.isShiftDown())
				{
					mouseAction = VELOCITY_MOUSE_ACTION;
				}
				else if (mouseEvent.isControlDown())
				{
					mouseAction = ROTATE_MOUSE_ACTION;
	
					double dx = mouseEvent.getX() - ((GraphicalRepresentation) component).getOffsetX();
					double dy = -mouseEvent.getY() + ((GraphicalRepresentation) component).getOffsetY();
					initialRotation = Math.atan2(dy, dx) - selectedObject.rotation;
				}
				else
				{
					mouseAction = MOVE_MOUSE_ACTION;
					
					// First stop the object
					if (selectedObject instanceof MovableObject)
					{
						((MovableObject) selectedObject).setVelocityX(0);
						((MovableObject) selectedObject).setVelocityY(0);
					}
					
					
					
				}
			}
		}

		/**
		 * Handles the rotation and moving of objects by click+drag or
		 * click+ctrl+drag
		 * 
		 * @param mouseEvent
		 *        MouseEvent argument
		 */
		public void mouseDragged(MouseEvent mouseEvent)
		{
			Location l;

			// MouseFrameDone checks if the mouse events have been handled for
			// the
			// current frame, otherwise the event is fired way too often
			if (mouseFrameDone == false)
			{
				switch (mouseAction)
				{
					case ROTATE_MOUSE_ACTION:

						// Rotate the selected object
						l = selectedObject.getLocation();

						double dx = mouseEvent.getX()
								- ((GraphicalRepresentation) (mouseEvent.getComponent())).getOffsetX();
						double dy = -mouseEvent.getY()
								+ ((GraphicalRepresentation) (mouseEvent.getComponent())).getOffsetY();
						selectedObject.setRotation(Math.atan2(dy, dx) - initialRotation);

						if (selectedObject instanceof MovableObject)
							((MovableObject) selectedObject).setRotationSpeed(0);

						break;

					case MOVE_MOUSE_ACTION:

						// Move the selected object to the new mouse location
						l = selectedObject.getLocation();

						// set the location
						double newX = (l.getX() + (mouseEvent.getX() / pixelsPerMeter))
								- (selectedObject.diameter / 2);
						double newY = l.getY() - (mouseEvent.getY() / pixelsPerMeter)
								+ (selectedObject.diameter / 2);

						if (newX < 0)
						{
							newX = 0 + (selectedObject.diameter / 2);
						}

						if (newY < 0)
						{
							newY = 0 + (selectedObject.diameter / 2);
						}

						if (newX > World.WIDTH)
						{
							newX = World.WIDTH - (selectedObject.diameter / 2);
						}

						if (newY > World.HEIGHT)
						{
							newY = World.HEIGHT - (selectedObject.diameter / 2);
						}

						l.setX(newX);
						l.setY(newY);

						selectedObject.setLocation(l);

						break;

					case VELOCITY_MOUSE_ACTION:

						// Give the selected object some speed
						if (selectedObject instanceof MovableObject)
						{
							((MovableObject) selectedObject).setVelocityX(mouseEvent.getX()
									/ pixelsPerMeter);
							((MovableObject) selectedObject).setVelocityY(-mouseEvent.getY()
									/ pixelsPerMeter);
						}

						break;
				}

				mouseFrameDone = true;
			}
		}

		/**
		 * Handles throwing objects around in the gui
		 * 
		 * @param mouseEvent
		 *        MouseEvent argument
		 */
		public void mouseReleased(MouseEvent mouseEvent)
		{
			if (mouseAction != NO_MOUSE_ACTION)
			{
				mouseAction = NO_MOUSE_ACTION;
				// Debug.printInfo("An act of God has occurred");
			}
		}

		/**
		 * not used
		 * 
		 * @param mouseEvent
		 *        MouseEvent argument
		 */
		public void mouseMoved(MouseEvent mouseEvent)
		{
		}

		/**
		 * not used
		 * 
		 * @param arg0
		 *        MouseEvent argument
		 */
		public void mouseClicked(MouseEvent arg0)
		{
		}

		/**
		 * not used
		 * 
		 * @param arg0
		 *        MouseEvent argument
		 */
		public void mouseEntered(MouseEvent arg0)
		{
		}

		/**
		 * not used
		 * 
		 * @param arg0
		 *        MouseEvent argument
		 */
		public void mouseExited(MouseEvent arg0)
		{
		}
	}

	/**
	 * Get the path relative to currect working dir
	 * 
	 * @param path
	 *        filename
	 * @return String full relative pathname to resource (imagefile)
	 */
	public static String getRelativePath(String path)
	{
		try
		{
			URL dirUrl = Debug.class.getResource("./"); // get my directory
			URL fileUrl = new java.net.URL(dirUrl, path); // get a related
			// file
			return fileUrl.getPath().replaceAll("%20", " "); // fix replaced
			// spaces
		}
		catch (Exception e)
		{
			Debug.printError("Error: " + e.toString());
			return null;
		}
	}

	public static int getSimulatorSpeed()
	{
		return simulatorSpeed;
	}

	public static void setSimulatorSpeed(int simulatorSpeed)
	{
		Simulator.simulatorSpeed = simulatorSpeed;
	}

	/**
	 * This class is used as workaround for the console scrolling bug
	 * This bug is only present in JDK 1.5 on Linux (X11)
	 */
	private class ConsoleCaret extends DefaultCaret
	{
		
		/**
		 * If the field is invalid when setting the mouse cursor
		 * revalidate and try again.
		 */
		protected void adjustVisibility(Rectangle nloc)
		{
			try
			{
				super.adjustVisibility(nloc);
			}
			catch (InvalidPipeException ipex)
			{
				messageTextArea.revalidate();
				adjustVisibility(nloc);
			}
		}
	}
}


/**
 * Subclasses the java.io.OutputStream to create an output stream which directs
 * the output to the text box in a simulator window. This class can be used to
 * set System.out to output to the simulator window using the following code:<br>
 * <code> System.setOut(new java.io.PrintStream(new 
 *         SimulatorOutputStream(mySimulator))); </code>
 */

class SimulatorOutputStream extends java.io.OutputStream
{
	private Simulator	simulator;

	/**
	 * Creates a new SimulatorOutputStream object.
	 * 
	 * @param simulator
	 *        the simulator to direct the output to.
	 */
	public SimulatorOutputStream(Simulator simulator)
	{
		this.simulator = simulator;
	}

	/**
	 * Closes this output stream and releases any system resources associated
	 * with this stream.
	 */
	public void close()
	{
		simulator = null;
	}

	/**
	 * Writes the specified byte to this output stream.
	 * 
	 * @param i
	 *        the<code>byte</code>.
	 */
	public void write(int i)
	{
		if (simulator == null)
		{
			return;
		}

		byte[] b = new byte[1];
		b[0] = (byte) i;
		write(b, 0, 1);
		System.err.print("0");
	}

	/**
	 * Writes <code>len</code> bytes from the specified byte array starting at
	 * offset <code>off</code> to this output stream.
	 * 
	 * @param b
	 *        data array
	 * @param off
	 *        start index
	 * @param len
	 *        length
	 */
	public void write(byte[] b, int off, int len)
	{
		if (simulator == null)
		{
			return;
		}

		simulator.printMessage(new String(b, off, len));
	}
}
