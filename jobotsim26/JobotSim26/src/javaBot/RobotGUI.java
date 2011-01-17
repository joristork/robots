package javaBot;

//TODO: Check if current robot has a sound sensor
//TODO: If sound sensor, show and process graphics window
//TODO: Check if robot has a mouse sensor
//TODO: If mouse sensor, display image and coordinates
//TODO: If sound sensor, check if real or simulated sensor
//TODO: Generate sound patterns from simulated sensor
//TODO: Simulated sound sensor must read in patterns from file and display
//TODO: If ultrasonic sensor, display only first graph
//TODO: Define method to select simulated or real sensor
//TODO: Include Sampler and FFT in UVM robot
//TODO: Use samples and select files in simulator
//TODO: If graph is closed, stop collecting data
//TODO: Graph display only on request
//TODO: Include new sensors in system
//TODO: Bij wisseling van agent treden er problemen op
//TODO: Implement selection of robot type

/**
 * Ver 0.0 - 11-08-2004 Ver 0.1 - 03-07-2004 -     Implemented Servo values
 * Included DoCommand webservice interfaces (preliminary)
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * The RobotGUI has a Frame that shows various info items about the status of a robot and allows for
 * interaction with the robot line selecting an agent, changing dipswitches and sending commands.
 */
public class RobotGUI implements IGUI
{
	/** RobotGUI Window title */
	private String					WINDOW_TITLE			= "RobotGUI";
	private JFrame					robotGUI;
	private JPanel					mainPanel;
	private Container               container ;

	/** SIM agent selection box */
	private JComboBox				agentCombo;
	/** UVM agent selection box */
	private JComboBox				uvmAgentCombo;

	// Input output text fields
	private JTextField				txtInput;
	private JTextField				txtOutput;
	private JTextField				checkBoxStatus;
	//private Border border = new Border;

	// What robot this GUI belong to
	private Robot					robot;

	// Timer for timed value updates
	private Timer					t;

	// Actor values
	private JProgressBar[]			actorStatus;

	// Three dipswitches
	private JCheckBox[]				dipswitches				= new JCheckBox[4];
	private JTextField[]			labelswiches			= new JTextField[4];

	// Four colored leds
	private JRadioButton[]			ledStatus				= new JRadioButton[4];

	//  Sensor values
	private JProgressBar[]			sensorStatus;

	//  Five colored leds
	private JButton[]				recStatus				= new JButton[5];
	private String[]				chars					= {"A", "E", "I", "O", "U"};

	private JButton					showAudioGraph			= new JButton("Open audiograph");
	private JButton					showMouseSensor			= new JButton("Open mouse sensor");
	//bug 50
	private int						behaviorStatus[]		= new int[4];

	//Boolean die aangeeft of grafieken aan het meten zijn
	boolean							measuring				= false;
	private RobotGUIActionListener	robotGUIActionlistener	= new RobotGUIActionListener();

	private Simulator				simulator;
	
	private static final int 		WINDOW_HEIGHT 			= 900;
	
	private boolean visibleInLeftFrame = false;

	/**
	 * Default constructor. 
	 *
	 * @param robot The robot to link to this GUI
	 */
	public RobotGUI(Robot robot, Simulator simulator)
	{
		super();
		this.robot = robot;
		this.simulator = simulator;
		WINDOW_TITLE += (" for " + robot.name);

		//Create and set up the window.
		robotGUI = new JFrame(WINDOW_TITLE);
		robotGUI.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		robotGUI.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				setVisible(false);
			}
		});
		this.init();
	}
	

	

	/**
	 * 
	 * @return the visibility of robotGUI frame
	 */
	public boolean isVisible()
	{
		return robotGUI.isVisible();
	}
	
	public JPanel getPanels()
	{
		return mainPanel;
	}

	
	/**
	 * This function sets a dipSwitch and executes
	 * the executeDipSwitch function
	 * 
	 * @param dipSwitch The Switch to be selected
	 */
	public void setDipSwitch(int dipSwitch)
	{
		if (dipSwitch == 0) return;
		dipswitches[dipSwitch - 1].setSelected(true);
		robotGUIActionlistener.executeDipSwitch();
	}

	/**
	 * Relay docommand(int) to docommand(int,int,int,int) 
	 * 
	 * @see RobotGUI#doCommand(int, int, int, int)
	 * @param command int the command
	 */
	private void doCommand(int command)
	{
		doCommand(command, UVMRobot.COMMAND_NULL_PARAMETER, UVMRobot.COMMAND_NULL_PARAMETER,
				UVMRobot.COMMAND_NULL_PARAMETER);
	}

	/**
	 * Relay docommand(int) to docommand(int,int,int,int) 
	 * 
	 * @see RobotGUI#doCommand(int, int, int, int)
	 * @param command int command
	 * @param param0 int the command parameter
	 */
	private void doCommand(int command, int param0)
	{
		doCommand(command, param0, UVMRobot.COMMAND_NULL_PARAMETER, UVMRobot.COMMAND_NULL_PARAMETER);
	}

	/**
	 * Send a robot command 
	 * 
	 * @see UVMRobot 
	 * @see UVMRobot#vector(int, int, int)
	 * @see UVMRobot#drive(int, int, int)
	 * @see UVMRobot#setState(int)
	 * @see UVMRobot#getState()
	 *
	 * @param command int the command
	 * @param param0 int a command parameter
	 * @param param1 int a command parameter
	 * @param param2 int a command parameter
	 */
	private void doCommand(int command, int param0, int param1, int param2)
	{
		UVMRobot aRobot = (UVMRobot) robot;

		switch (command)
		{
			case UVMRobot.COMMAND_VECTORDRIVE:
				aRobot.vector(param0, param1, param2);

				break;

			case UVMRobot.COMMAND_DRIVE:
				aRobot.drive(param0, param1, param2);

				break;

			case UVMRobot.COMMAND_STOP:
				aRobot.setState(0);

				break;

			case UVMRobot.COMMAND_START:
				aRobot.setState(param0);

				break;

			case UVMRobot.COMMAND_GETSTATE:

				int value = aRobot.getState();
				txtOutput.setText("" + value);

				break;

			case UVMRobot.COMMAND_REPORTSTATE:

				aRobot.reportState(param0);

				break;

			default:
				txtInput.setText("Unknown");
		}
	}

	/**
	 *  Called from the actionlistener to send the text the user typed to the Robot using doCommand().
	 *  Will only work on UVMRobots
	 *  
	 *  @see RobotGUI#doCommand(int, int, int, int)
	 *  
	 */
	private void doSendCommand()
	{
		//		Send command to the robot
		// Parse the command
		String[] cmd = txtInput.getText().split(" ");

		if (robot instanceof UVMRobot)
		{
			// Check what command to do
			UVMRobot aRobot = (UVMRobot) robot;

			if (cmd.length == 1)
			{
				doCommand(getCommandCode(cmd[0]));
			}
			else if (cmd.length == 4)
			{
				try
				{
					doCommand(getCommandCode(cmd[0]), Integer.parseInt(cmd[1]), Integer
							.parseInt(cmd[2]), Integer.parseInt(cmd[3]));
				}
				catch (Exception e)
				{
					Debug.printError("Last three arguments should be integer values");

					return;
				}
			}
			else if (cmd.length == 2)
			{
				int args;

				try
				{
					int cmdCode = getCommandCode(cmd[0]);

					if (cmdCode == UVMRobot.COMMAND_SENSOR)
					{
						args = Integer.parseInt(cmd[1]);

						int value = aRobot.getSensor(args);
						txtOutput.setText("" + value);
					}
					else if (cmdCode == UVMRobot.COMMAND_START)
					{
						doCommand(UVMRobot.COMMAND_START, Integer.parseInt(cmd[1]));
					}
					else
					{
						Debug.printError("Invalid command, expected sensor command");

						return;
					}
				}
				catch (Exception e)
				{
					Debug.printError("Sensor number should be integer value");

					return;
				}
			}
			else
			{
				Debug.printError("No valid command entered, must be 1 or 4 elements long");

				return;
			}
		}
		else
		{
			Debug.printError("Current robot is not a UVMRobot, this feature will not work");

			return;
		}

		// Show the command that was sent
		Debug.printInfo("Sending command: " + txtInput.getText());

		// Empty the textfield
		txtInput.setText("");
	}

	private RobotGUI getRobotGUI()
	{
		return this;
	}

	/**
	 * The ActionListener for the RobotGUI textFieldchanges, Timer action, comboboxchanges, etc
	 *
	 * 
	 */
	private class RobotGUIActionListener implements ActionListener
	{
		private static final String	SWITCH	= "switch";

		/**
		 * Handles the timer, buttons, dipscwitchchanges and comboboxchange events for the RobotGUI
		 *
		 * @param arg0 the actionEvent.
		 */
		public void actionPerformed(ActionEvent arg0)
		{
			String actionCmd = arg0.getActionCommand();
			if (actionCmd == null)
			{
				// timer action every 500 msec
				updateReadings();
				return;
			}

			if (actionCmd.equals("Reset Hard") || actionCmd.equals("Reset Soft"))
			{
				// Reset the robot
				if (robot.getAgent() != null)
				{
					robot.getAgent().kill();
					robot.setAgent(null);
				}
				robot.reset();
				if (robot instanceof UVMRobot)
				{
					((UVMRobot) robot).resetRealAgent(arg0.getActionCommand().equals("Reset Hard"));
				}
				return;
			}
			if (actionCmd.equals("Program"))
			{
				if (robot instanceof UVMRobot)
				{
					((UVMRobot) robot).programRealRobot();
				}
				return;
			}
			if (actionCmd.equals("Run"))
			{
				if (robot instanceof UVMRobot)
				{
					((UVMRobot) robot).runRealAgent();
				}
				return;
			}
			// YEAH
			if (arg0.getSource() == showAudioGraph)
			{
				Hashtable guiRegistry = (Hashtable) simulator.getGuiRegistry();

				if (guiRegistry.containsKey(robot))
				{
					Vector v = (Vector) guiRegistry.get(robot);

					for (int i = 0; i < v.size(); i++)
					{
						IGUI igui = (IGUI) v.get(i);
						if (igui instanceof GraphGUI)
						{
							igui.setVisible(true);
							return;
						}
					}

					GraphGUI graphGUI = new GraphGUI(robot, getRobotGUI());
					simulator.linkGuiToRobot(robot, graphGUI);
				}

				return;
			}
			if (arg0.getSource() == showMouseSensor)
			{
			
				Thread opticalMouseAdaptor = new Thread(new OpticalMouseAdaptor(robot));
				opticalMouseAdaptor.start();
							
				
			}

			if (actionCmd.startsWith(SWITCH))
			{
				executeDipSwitch();
				return;
			}

			if (actionCmd.equals("comboBoxChanged"))
			{
				// if first item is selected to nothing since it's just a description like "select a agent"
				if (((JComboBox) arg0.getSource()).getSelectedIndex() == 0)
				{
					Debug.printInfo("No agent selected");
					return;
				}
				// Agent selected
				if (robot instanceof UVMRobot)
				{ // TODO: document this. Why is this done HERE?
					UVMRobot r = (UVMRobot) robot;
					adaptXMLFile();
					r.startAgent();
				}
				else
				{
					// If there is already an agent using this robot, remove it            
					if (robot.getAgent() != null)
					{
						robot.getAgent().kill();
						robot.setAgent(null);
					}
					// Reset the robot
					robot.reset();
					// Now create the new selected agent on the robot
					// Debug.printDebug("javaBot.agents." + agent.toString());
					try
					{
						// SIM agent selected from simagentCombobox
						if (arg0.getSource().equals(agentCombo))
						{
							String agentType = agentCombo.getSelectedItem().toString();
							Class agentClass = ClassLoader.getSystemClassLoader().loadClass(
									"javaBot.agents." + agentType);
							javaBot.agents.Agent agentInstance = (javaBot.agents.Agent) agentClass
									.newInstance();
							agentInstance.setRobot(robot);
							robot.setAgent(agentInstance);
							robot.name = agentType;
							robotGUI.setTitle("RobotGUI for " + agentType);

							new Thread(agentInstance).start();
						}
						else
						{ // other combobox selected so it must be a real UVM agent
							// TODO: the use of this code is not clear yet. Not working.
							String agentType = uvmAgentCombo.getSelectedItem().toString();
							Class agentClass = ClassLoader.getSystemClassLoader().loadClass(
									"javaBot.agents." + agentType);
							robot.setAgent(null);
							com.muvium.UVMRunnable agentInstance = (com.muvium.UVMRunnable) agentClass
									.newInstance();

							new Thread(agentInstance).start();
							// Code copied from Mainline						
							//					UVMRobot r = new UVMRobot("UVMRobot", World.WIDTH/2, World.HEIGHT/2, true);
							//					new Thread(new RobotGUI((Robot) r)).start();
						}
					}
					catch (Exception e)
					{
						// please note: all robots need to have a parameter-free constructor and need to check regularly 
						// if robot.Agent==null)"
						Debug.printError("Error loading agent class)" + e.toString());
					}

					return;
				}
			}
		}

		private void executeDipSwitch()
		{
			int behaviorValue = 0;

			for (int i = 0; i < behaviorStatus.length; i++)
			{
				behaviorStatus[i] = 0;
			}

			if (dipswitches[0].isSelected())
			{
				behaviorValue += 1;
				behaviorStatus[0] = 1;
			}

			if (dipswitches[1].isSelected())
			{
				behaviorValue += 2;
				behaviorStatus[1] = 2;
			}

			if (dipswitches[2].isSelected())
			{
				behaviorValue += 4;
				behaviorStatus[2] = 4;
			}

			if (dipswitches[3].isSelected())
			{
				behaviorValue += 8;
				behaviorStatus[3] = 8;
			}

			if (robot instanceof UVMRobot)
			{
				((UVMRobot) robot).setPortB(behaviorValue);
			}
			else
			{
				robot.setState(behaviorValue);
			}

			Debug.printInfo("DIP switch set to: " + behaviorValue);

			displayNewBehaviorValue();
			return;
		}

		private void displayNewBehaviorValue()
		{
			String sTest;
			String txtOut;
			int iTotal = 0;
			txtOutput.setText("");
			txtOutput.setForeground(Color.RED);
			checkBoxStatus.setText("");
			checkBoxStatus.setForeground(Color.blue);
			for (int i = 0; i < behaviorStatus.length; i++)
			{

				sTest = txtOutput.getText();

				txtOut = " " + behaviorStatus[i];
				iTotal += behaviorStatus[i];

				if (i == behaviorStatus.length - 1)
					txtOut += "=";
				else
					txtOut += " + ";

				//txtOutput.setText(sTest + txtOut); dit werk ook 
			}
			txtOutput.setText("   " + behaviorStatus[0] + " + " + behaviorStatus[1] + " + "
					+ behaviorStatus[2] + " + " + behaviorStatus[3] + " = ");
			txtOutput.setText(txtOutput.getText());
			checkBoxStatus.setText("" + iTotal);
		}
	}

	/**
	 * Start the GUI
	 */
	public void init()
	{
		Debug.printInfo("Starting RobotGUI for " + robot.name);

		//Make panel for our graphs
		JPanel Graphs = new JPanel();

		//Make panel for our detectors
		JPanel Detectors = new JPanel();

		//Set up graph panel
		Graphs.setVisible(true);
		Graphs.setLayout(new java.awt.GridLayout(1, 2));

		Detectors.setLayout(new java.awt.GridLayout(1, 5));

		//Maak indicatoren
		for (int i = 0; i < recStatus.length; i++)
		{
			recStatus[i] = new JButton(chars[i]);
			Detectors.add(recStatus[i]);
		}
		Detectors.setVisible( true );
		
		
		//Container cp = robotGUI.getContentPane();
		mainPanel = new JPanel();
		mainPanel.setLayout( new BorderLayout());
		mainPanel.setPreferredSize( new Dimension( 180, WINDOW_HEIGHT ) );
		mainPanel.setMinimumSize( new Dimension( 180, WINDOW_HEIGHT) );
		
		//mainPanel.add( Graphs );
		//mainPanel.add( Detectors );
		
		//Add the leds
		mainPanel.add(createLeds());
		
		//Add the sensor output
		createSensors(mainPanel);
		createActors( mainPanel );
		createButtons( mainPanel );
		
		//Add the agent selector
		mainPanel.add(createAgentSelect());

		//Add the dipswitches
		createDipSwitches( mainPanel );
		
		//Add the input/output text fields
		mainPanel.add(createInputOutput());
		//		Add the createCheckBoxStatus
		mainPanel.add(createCheckBoxStatus());

		//Add the buttons
		//cp.add(createButtons());
		//Add dummy for alignment
		mainPanel.add(new JPanel());
		//Align and display the window.
		
		mainPanel.setVisible( true);
		mainPanel.validate();
		mainPanel.repaint();
		
		robotGUI.getContentPane().add( mainPanel );
		
		robotGUI.pack();
        //this.setContainer(mainPanel);
		//robotGUI.setSize(360, 325);
		robotGUI.setSize(180, WINDOW_HEIGHT);
		robotGUI.setResizable(false);
		robotGUI.setVisible(false);
		

		// Initialize and start the timer
		t = new Timer(500, robotGUIActionlistener);
		t.start();
	}

	// Round a number to 2 decimal places 
	private double Rounded(double d)
	{
		if (Double.isInfinite(d) || Double.isNaN(d))
		{
			return 0;
		}
		return (int) d;
		//		return ((int) ((d * 100) + ((d > 0) ? .5 : (-.5)))) / 100.0;
	}

	/**
	 *
	 */
	private void adaptXMLFile()
	{
		String agentClassName = uvmAgentCombo.getSelectedItem().toString();
		String xmlFile = System.getProperty("user.dir") + "\\joBot.xml";

		try
		{
			BufferedReader myInput = new BufferedReader(new InputStreamReader(new FileInputStream(
					xmlFile)));
			String file = "";
			String aLine;

			while (null != (aLine = myInput.readLine()))
			{
				file = file + aLine;
			}

			myInput.close();

			Pattern paternFile = Pattern.compile("<UVMRUNNABLE-FILENAME>+</UVMRUNNABLE-FILENAME>");
			Matcher match = paternFile.matcher(file);

			String dir = System.getProperty("user.dir");

			match.replaceAll("<UVMRUNNABLE-FILENAME>" + dir + "\\" + agentClassName + ".class"
					+ "</UVMRUNNABLE-FILENAME>");
			paternFile = Pattern.compile("<UVMRUNNABLE-CLASSFILE>+</UVMRUNNABLE-CLASSFILE>");
			match = paternFile.matcher(file);
			match.replaceAll("<UVMRUNNABLE-CLASSFILE>javaBot.agents." + agentClassName
					+ "</UVMRUNNABLE-CLASSFILE>");

			FileOutputStream fos = new FileOutputStream("out.dat");
			Writer w = new BufferedWriter(new OutputStreamWriter(fos, "Cp850"));
			w.write(file);
			w.flush();
			w.close();
		}
		catch (Exception e)
		{
			Debug.printError(e.toString());
		}
	}

	/**
	 * Create the agent dropdown comboboxes and fill them by reading the javabot.agents package files
	 * 
	 * @return JPanel the panel with three comoboxes to select Sim, UVM or JPB2 agents from
	 */
	private JPanel createAgentSelect()
	{
		JPanel cp = new JPanel();
		//cp.setBounds(180, 5, 170, 110);
		cp.setBounds(5,10, 170, 110);
		cp.setBackground(Color.LIGHT_GRAY);
		cp.setBorder(BorderFactory.createEtchedBorder());
		cp.setLayout(null);

		
		JLabel robotName = new JLabel( WINDOW_TITLE);
		robotName.setBounds(5, 5, 160, 20);
		robotName.setForeground(Color.BLUE);
		cp.add( robotName );
		
		// SIM agents are subclasses of javaBot.agents.JobotAgent
		agentCombo = fillAgentCombo(new JComboBox(), "agents", "javaBot.agents.JobotAgent");
		agentCombo.setBounds(5, 30, 160, 20);
		agentCombo.setBackground(Color.WHITE);
		// the first item on pos 0 is just a description
		agentCombo.insertItemAt("Select a (sim) agent", 0);
		agentCombo.setSelectedIndex(0);
		agentCombo.setName("agentCombo");
		agentCombo.addActionListener(robotGUIActionlistener);
		cp.add(agentCombo);

		// REAL agents are subclasses of UVMRunnable
		uvmAgentCombo = new JComboBox();
		uvmAgentCombo = fillAgentCombo(new JComboBox(), "agents", "com.muvium.UVMRunnable");
		uvmAgentCombo.setBounds(5, 55, 160, 20);
		uvmAgentCombo.setBackground(Color.WHITE);
		// the first item on pos 0 is just a description
		uvmAgentCombo.insertItemAt("Select a (real) agent", 0);
		uvmAgentCombo.setName("uvmAgentCombo");
		uvmAgentCombo.setSelectedIndex(0);
		uvmAgentCombo.addActionListener(robotGUIActionlistener);
		cp.add(uvmAgentCombo);

		/* Unused comboboxes - please document first before enabling!
		 *
		 uvmJPB2Combo = new JComboBox();
		 uvmJPB2Combo.setBounds(5, 70, 160, 20);
		 uvmJPB2Combo.setBackground(Color.WHITE);
		 // the first item on pos 0 is just a description
		 uvmJPB2Combo.insertItemAt("Select a (JPB2) agent",0);
		 uvmJPB2Combo.setSelectedIndex(0);
		 uvmJPB2Combo.setName("uvmJPB2Combo");
		 uvmJPB2Combo.addActionListener(robotGUIActionlistener);
		 cp.add(uvmJPB2Combo);
		 
		 uvmRCJrCombo = new JComboBox();
		 uvmRCJrCombo.setBounds(5, 100, 160, 20);
		 uvmRCJrCombo.setBackground(Color.WHITE);
		 // the first item on pos 0 is just a description
		 uvmRCJrCombo.insertItemAt("Select a (RCJr) agent",0);
		 uvmRCJrCombo.setSelectedIndex(0);
		 uvmRCJrCombo.setName("uvmRCJrCombo");
		 uvmRCJrCombo.addActionListener(robotGUIActionlistener);
		 cp.add(uvmRCJrCombo);
		 */

		return cp;
	}

	/**
	 * Fill one of the RobotGUI comboboxes with the classnames found in the agents package to that an agent can be chosen
	 * @param aComboBox the new combobox to be filled with items
	 * @param aPath path to the agent class files folder
	 * @param aClass a baseclass like jobotAgent where the agents must be children of
	 * @return JCombobox - the new filled combobox
	 */
	private JComboBox fillAgentCombo(JComboBox aComboBox, String aPath, String aClass)
	{
		java.io.File[] agents;

		try
		{
			// Get file listing for the javaBot.agents directory
			agents = new java.io.File(Simulator.getRelativePath("./" + aPath + "/")).listFiles();
		}
		catch (Exception e)
		{
			Debug.printError("Error in " + aPath + " dropdown, unable to load directory info: "
					+ e.toString());
			return aComboBox;
		}

		// Loop through the resulting files
		for (int i = 0; i < agents.length; i++)
		{
			if (agents[i].isFile())
			{
				// Try to use classloader to load the classname
				ClassLoader cl = ClassLoader.getSystemClassLoader();
				String agentName = agents[i].getName();
				agentName = agentName.substring(0, agentName.indexOf("."));

				if (agentName.length() < 2)
				{
					continue;
				}

				String superClass = "";
				try
				{
					// Get the superclass's name
					if (cl.loadClass("javaBot." + aPath + "." + agentName).getSuperclass() != null)
					{
						superClass = cl.loadClass("javaBot." + aPath + "." + agentName)
								.getSuperclass().getName();
					}
				}
				catch (Exception e)
				{
					Debug.printError("Error in " + aPath + " dropdown, unable to load class: "
							+ agentName + " " + e.toString());
					return aComboBox;
				}

				// If the superclass is an Agent or a UVMRunnable, show it in the dropdown
				if (superClass.equals(aClass))
				{
					aComboBox.addItem(agentName);
				}
			}
		}

		return aComboBox;
	}

	/** Create the dipswitches panel
	 * 
	 * @return JPanel
	 */
	private void createDipSwitches( JPanel mainPanel )
	{
		JPanel cp = new JPanel();

		//cp.setBorder(border);
		cp.setBackground(Color.RED);
		cp.setBounds(5, 120, 170, 50);
		//cp.setBackground(Color.LIGHT_GRAY);
		//cp.setBorder(BorderFactory.createEtchedBorder());
		//cp.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
		cp.setBorder(new TitledBorder(null, "  Dip Switches", TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.PLAIN, 10)));
		//cp.setToolTipText("Switches value display");
		cp.setLayout( null);
		
		for (int i = 0; i < dipswitches.length; i++)
		{
			int dipPlacing = 25 * i;
			
			dipswitches[i] = new JCheckBox();
			dipswitches[i].setBackground(Color.RED);
			dipswitches[i].setActionCommand("switch" + i);
			dipswitches[i].addActionListener(robotGUIActionlistener);
			dipswitches[i].setBounds( dipPlacing + 5, 12, 16, 16);
			cp.add(dipswitches[i]);

			labelswiches[i] = new JTextField("" + (i + 1));
			labelswiches[i].setBackground(Color.RED);
			labelswiches[i].setForeground(Color.white);
			labelswiches[i].setBorder(BorderFactory.createLineBorder(Color.red, 0));
			labelswiches[i].setEditable(false);
			labelswiches[i].setBounds( dipPlacing + 12, 28, 16, 16);
			//labelswiches[i].setAlignmentY(180);

			cp.add(labelswiches[i]);
		}
		
		mainPanel.add( cp );
	}

	/** Create the input/output textfields
	 * 
	 * @return JPanel - the panel with input and output textfields
	 */
	private JPanel createInputOutput()
	{
		JPanel cp = new JPanel();
		cp.setBounds(5, 190, 170, 60);
		cp.setBackground(Color.LIGHT_GRAY);
		cp.setBorder(BorderFactory.createEtchedBorder());
		cp.setLayout(null);

		txtInput = new JTextField("");
		txtInput.setBounds(5, 33, 160, 20);
		txtInput.setActionCommand("sendCommand");
		txtInput.addActionListener(new SendCommandActionListener());
		cp.add(txtInput);

		txtOutput = new JTextField("");
		txtOutput.setBounds(5, 8, 160, 20);
		//txtOutput.setEnabled(false);
		txtOutput.setEditable(false);
		cp.add(txtOutput);

		return cp;
	}

	//bug 50

	private JPanel createCheckBoxStatus()
	{
		JPanel cp = new JPanel();
		cp.setBounds(5, 170, 170, 25);
		cp.setBackground(Color.LIGHT_GRAY);
		cp.setBorder(BorderFactory.createEtchedBorder());
		cp.setLayout(null);
		
		txtOutput = new JTextField("   0 + 0 + 0 + 0 = ");
		txtOutput.setBackground(Color.LIGHT_GRAY);
		txtOutput.setBounds(2, 2, 132, 20);
		txtOutput.setEditable(false);
		txtOutput.setForeground( Color.RED );
		cp.add(txtOutput);

		checkBoxStatus = new JTextField("0");
		//checkBoxStatus.setForeground(Color.RED);
		//checkBoxStatus.setBackground(Color.GREEN);
		checkBoxStatus.setBounds(133, 2, 32, 20);
		checkBoxStatus.setEditable(false);
		checkBoxStatus.setAlignmentX( JTextField.RIGHT_ALIGNMENT );
		cp.add(checkBoxStatus);

		return cp;
	}

	/** Create the leds radiobuttons panel 
	 * 
	 * @return JPanel - the LEDS panel
	 */
	private JPanel createLeds()
	{
		JPanel cp = new JPanel();
		cp.setBounds(5,250, 170, 32);
		//cp.setBackground(Color.LIGHT_GRAY);
		//cp.setBorder(BorderFactory.createEtchedBorder());
		cp.setBorder(new TitledBorder(null, "  LED Display", TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.PLAIN, 10)));
		cp.setLayout(null);

		for (int i = 0; i < robot.getLeds().length; i++)
		{
			ledStatus[i] = new JRadioButton();
			ledStatus[i].setBounds((30 * i) + 20, 12, 16, 16);
			//ledStatus[i].setBackground(Color.LIGHT_GRAY);
			ledStatus[i].setForeground(robot.getLeds()[i].getColor());
			ledStatus[i].setSelected(false);
			cp.add(ledStatus[i]);
		}

		return cp;
	}

	// Create the sensor output
	private void createSensors( JPanel mainPanel)
	{
		JPanel cp = new JPanel();

		cp.setBounds(5, 280, 170, (robot.getSensors().length * 50));
		//cp.setBackground(Color.LIGHT_GRAY);
		//cp.setBorder(BorderFactory.createEtchedBorder());
		cp.setBorder(new TitledBorder(null, "  Sensors", TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.PLAIN, 10)));
		cp.setLayout(null);

		// Initialize sensor status labels
		int sensorPlacing = 0;
		sensorStatus = new JProgressBar[robot.getSensors().length];

		for (int i = 0; i < robot.getSensors().length; i++)
		{
			sensorPlacing = (45*i);
			
			JLabel sensor = new JLabel("" + (i) + ". "  + robot.getSensors()[i].getName());
			sensor.setBounds(5, sensorPlacing + 8, 160, 20);
			sensor.setForeground(Color.DARK_GRAY);
			cp.add( sensor );
			
			
			
			JProgressBar sensorProgress = new JProgressBar();
			sensorProgress.setValue(0);
			sensorProgress.setStringPainted(true);
			
			int max =  robot.getSensors()[i].getMaxValue();
			//System.out.println( " max: "  + max );
			sensorProgress.setMaximum( max );
			//sensorProgress.setString("29");
			sensorProgress.setFont(new Font("Dialog", Font.PLAIN, 10));
			sensorProgress.setBounds(5, sensorPlacing + 25, 160, 20);
			
			sensorStatus[i] = sensorProgress;
			cp.add(sensorStatus[i]);
		}

		
		cp.validate();
		cp.repaint();
		
		mainPanel.add( cp );

	}
	
	private void createActors( JPanel mainPanel ) 
	{
		
		JPanel cp = new JPanel();

		
		int startPosY = 280 + (robot.getSensors().length * 50);
		int endPosY = (robot.getActors().length * 50);
		
		cp.setBounds(5, startPosY, 170, endPosY );
		//cp.setBackground(Color.LIGHT_GRAY);
		//cp.setBorder(BorderFactory.createEtchedBorder());
		cp.setBorder(new TitledBorder(null, "  Actors", TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.PLAIN, 10)));
		cp.setLayout(null);

//		 Initialize actor status labels
		actorStatus = new JProgressBar[robot.getActors().length];
		int actorPlacing = 0;
		
		for (int o = 0; o < robot.getActors().length; o++)
		{
			actorPlacing = 50*o;
			
			JLabel actor = new JLabel( "" + (o) + ". " + robot.getActors()[o].getName());
			//actor.setBounds(5, (25*o)+(25*i)+(19 * (o + i)) + 5, 160, 20);
			actor.setBounds(5,  actorPlacing + 7, 160, 20);
			actor.setForeground(Color.DARK_GRAY);
			cp.add( actor );
			
			JProgressBar actorProgress = new JProgressBar();
			actorProgress.setValue(0);
			actorProgress.setStringPainted(true);
			actorProgress.setMaximum(100);
			actorProgress.setFont(new Font("Dialog", Font.PLAIN, 10));
			actorProgress.setBounds(5, actorPlacing + 25, 160, 20);
			
			actorStatus[o] = actorProgress;
			cp.add(actorStatus[o]);
		}

		cp.validate();
		cp.repaint();
		mainPanel.add( cp );
		
	}
	
	private void createButtons( JPanel mainPanel )
	{
		// Add button to open audiograph

		JPanel cp = new JPanel();

		
		int startPosY = 280 + (robot.getSensors().length * 50) + (robot.getActors().length * 50) + 10;
		int endPosY = 100;
		
		cp.setBounds(5, startPosY, 170, endPosY );
		//cp.setBackground(Color.LIGHT_GRAY);
		//cp.setBorder(BorderFactory.createEtchedBorder());
		cp.setBorder(new TitledBorder(null, "  Buttons", TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.PLAIN, 10)));
		cp.setLayout(null);
		
		showAudioGraph.setBounds(5, 25, 160, 20);

		cp.add(showAudioGraph, BorderLayout.SOUTH);
		showAudioGraph.addActionListener(robotGUIActionlistener);
		
		// Add button to open mouse sensor
		showMouseSensor.setBounds(5, 50, 160, 20);
		cp.add(showMouseSensor, BorderLayout.SOUTH);
		showMouseSensor.addActionListener(robotGUIActionlistener);
		
		
		cp.validate();
		cp.repaint();
		mainPanel.add( cp );
		
	}

	// Update the displayed actor values
	private void updateActorValues()
	{
		for (int i = 0; i < robot.getActors().length; i++)
		{
			double speed = robot.getActors()[i].getPercentageSpeed();

			actorStatus[i].setValue((int)Rounded(speed));
		}
	}

	// Update the displayed leds
	private void updateLedReadings()
	{
		for (int i = 0; i < robot.getLeds().length; i++)
		{
			//Debug.printDebug(robot.leds[i].getValue() + "");
			ledStatus[i].setSelected(robot.getLeds()[i].getValue() == 1.0);
		}
	}

	// Update all the readings
	private void updateReadings()
	{
		updateLedReadings();
		updateSensorReadings();
		updateActorValues();
	}

	// Update the displayed sensor values
	private void updateSensorReadings()
	{
		int val = 0;

		for (int i = 0; i < robot.getSensors().length; i++)
		{
			if (robot.getSensors() != null)
			{
				//Debug.printDebug("Updating sensor display " +
				// robot.sensorValues[i]);
				if (robot.getSensors()[i].getValue() >= 0)
				{
					val = (int) robot.getSensors()[i].getValue();
					//if( val > sensorStatus[i].getMaximum() ) sensorStatus[i].setMaximum( val );
					//System.out.println("value sensor: " + val );
					sensorStatus[i].setValue(val);
					sensorStatus[i].setString( "" + val );
				}
				else
				{
					sensorStatus[i].setValue(0);
				}
			}
			else
			{
				Debug.printError("Unable to read sensorValues!");
			}
		}
	}

	/**
	 * Convert a String to the corresponding UVMRobot commandcode<p>
	 * [JC] -- Edited to use constants 1/aug/04
	 * @see UVMRobot
	 * @return int - the UVMRobot commandcode
	 */
	private int getCommandCode(String cmd)
	{
		int ret = 0;

		if (cmd.equalsIgnoreCase("Start"))
		{
			ret = UVMRobot.COMMAND_START;
		}

		if (cmd.equalsIgnoreCase("setState"))
		{
			ret = UVMRobot.COMMAND_START;
		}

		if (cmd.equalsIgnoreCase("getState"))
		{
			ret = UVMRobot.COMMAND_GETSTATE;
		}
		
		if (cmd.equalsIgnoreCase("reportState"))
		{
			ret = UVMRobot.COMMAND_REPORTSTATE;
		}

		if (cmd.equalsIgnoreCase("Stop"))
		{
			ret = UVMRobot.COMMAND_STOP;
		}

		if (cmd.equalsIgnoreCase("Sensor"))
		{
			ret = UVMRobot.COMMAND_SENSOR;
		}

		if (cmd.equalsIgnoreCase("Vector"))
		{
			ret = UVMRobot.COMMAND_VECTORDRIVE;
		}

		if (cmd.equalsIgnoreCase("VectorDrive"))
		{
			ret = UVMRobot.COMMAND_VECTORDRIVE;
		}

		if (cmd.equalsIgnoreCase("Drive"))
		{
			ret = UVMRobot.COMMAND_DRIVE;
		}

		if (cmd.equalsIgnoreCase("Action"))
		{
			ret = UVMRobot.COMMAND_ACTION;
		}


		return ret;
	}

	/**
	 * The ActionListener that handles the commands typed in the textfield input field. 
	 * <br> Internal class of RobotGUI
	 * 
	 */
	class SendCommandActionListener implements ActionListener
	{
		/**
		 * The action is given to the doSendCommand
		 * @see RobotGUI#doSendCommand()
		 * @param e ActionEvent 
		 */
		public void actionPerformed(ActionEvent e)
		{
			doSendCommand();
		}
	}

	/**
	 * Sets the visibility of this GUI
	 *
	 * @param visible boolean 
	 */
	public void setVisible(boolean visible)
	{
		robotGUI.setVisible(visible);
		robotGUI.validate();
		robotGUI.repaint();
		
	}

	/**
	 * Destroys this frame
	 */
	public void destroy()
	{
		robotGUI.dispose();
		t.stop();
	}

	/**
	 * @param container The container to set.
	 */
	private void setContainer(Container container) {
		this.container = container;
	}

	/**
	 * @return Returns the container.
	 */
	public Container getContainer() {
		return container;
	}

	public boolean isVisibleInLeftFrame()
	{
		return visibleInLeftFrame;
	}


	public void setVisibleInLeftFrame(boolean visible)
	{
		visibleInLeftFrame = visible;
	}
}
