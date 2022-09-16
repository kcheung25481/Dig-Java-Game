import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DigMenu extends JFrame{
  
  static JFrame menuFrame; // Declare the main menu frame
  
  //Constructor for the main menu
  DigMenu() { 
    
    int maxX = Toolkit.getDefaultToolkit().getScreenSize().width; //Get the width of the scree
    int maxY = Toolkit.getDefaultToolkit().getScreenSize().height; //Get the height of the screen
    
    menuFrame = new JFrame("DIG"); //Make new frame
    
    JPanel pane = new JPanel(); //Make a new pane
    
    pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS)); //Set up as box layout
    menuFrame.add(pane); //Add the pane to the main frame
    
    JButton newGame = new JButton(" New Game ");  //Make new game button and listener
    newGame.setAlignmentX(Component.CENTER_ALIGNMENT); //Center the button
    newGame.setMaximumSize(new Dimension(maxX/3, maxY/11)); //Set the size of the button
    newGame.addActionListener(new newGameButtonListener()); //Add an action listener to the button
    
    JButton loadGame = new JButton(" Load Game "); //Make load game button and listener
    loadGame.setAlignmentX(Component.CENTER_ALIGNMENT); //Center the button
    loadGame.setMaximumSize(new Dimension(maxX/3, maxY/11)); //Set the size of the button
    loadGame.addActionListener(new loadGameButtonListener()); //Add an action listener to the button
    
    JButton instructions = new JButton(" Instructions "); //Make instructions button and listener
    instructions.setAlignmentX(Component.CENTER_ALIGNMENT); //Center the button 
    instructions.setMaximumSize(new Dimension(maxX/3, maxY/11)); //Set the size of the button
    instructions.addActionListener(new instructionsButtonListener()); //Add an action listener to the button
    
    JButton quitGame = new JButton(" Quit Game "); //Make load game button and listener
    quitGame.setAlignmentX(Component.CENTER_ALIGNMENT); //Center the button
    quitGame.setMaximumSize(new Dimension(maxX/3, maxY/11)); //Set the size of the button
    quitGame.addActionListener(new quitGameButtonListener()); //Add an action listener to the button
    
    //Layout of the main frame
    pane.add(Box.createRigidArea(new Dimension(maxX,maxY/11))); //Blank Space
    pane.add(Box.createRigidArea(new Dimension(maxX,maxY/11))); //Blank Space
    pane.add(newGame); //Add button
    pane.add(Box.createRigidArea(new Dimension(maxX,maxY/11))); //Blank Space
    pane.add(loadGame); //Add button
    pane.add(Box.createRigidArea(new Dimension(maxX,maxY/11))); //Blank Space
    pane.add(instructions);  //Add button
    pane.add(Box.createRigidArea(new Dimension(maxX,maxY/11))); //Blank Space
    pane.add(quitGame); //Add button
    pane.add(Box.createRigidArea(new Dimension(maxX,maxY/11))); //Blank Space
    pane.add(Box.createRigidArea(new Dimension(maxX,maxY/11))); //Blank Space
    
    //Set frame as visable and fullscreen
    menuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    menuFrame.setUndecorated(true);
    menuFrame.setVisible(true);
  }
  
  /*
   * newGameButtonListener 
   * This method starts a new game when in the menu. Returns nothing
   */
  static class newGameButtonListener implements ActionListener{
    public void actionPerformed(ActionEvent event)  {
      System.out.println("Starting new game"); //Start new game
      GridGeneration newMap = new GridGeneration(false); //Create a new map
      menuFrame.dispose(); //Close the menu
    }
  }
  
  /*
   * instructionsButtonListener 
   * This method loads a previous game when in the menu. Returns nothing
   */
  static class loadGameButtonListener implements ActionListener{
    public void actionPerformed(ActionEvent event)  {
      System.out.println("Loading Game");
      GridGeneration newMap = new GridGeneration(true); //Load a previous map if true
      menuFrame.dispose(); //Close the menu
    }
  }
  
  /*
   * instructionsButtonListener 
   * This method opens the game instructions when in the menu. Returns nothing
   */
  static class instructionsButtonListener implements ActionListener{
    public void actionPerformed(ActionEvent event)  {
      
      JFrame main = new JFrame(); //Frame for the instructions
      JTabbedPane tabbed = new JTabbedPane(); //Make the panel tabbed
      main.setSize(500,500); //Set size of the frame
      main.setResizable(false); //Make it so that it is not to be resized
      
      // set up the intro tab
      JPanel tab1 = new JPanel();
      tab1.setLayout(new FlowLayout());
      
      // set up the movement tab  
      JPanel tab2 = new JPanel();
      tab2.setLayout(new FlowLayout());
      
      // set up the stations tab  
      JPanel tab3 = new JPanel();
      tab3.setLayout(new FlowLayout());
      
      // set up the upgrade tab  
      JPanel tab4 = new JPanel();
      tab4.setLayout(new FlowLayout());
      
      // set up the status tab  
      JPanel tab5 = new JPanel();
      tab5.setLayout(new FlowLayout());
      
      //create JTextArea for the intro
      JTextArea info = new JTextArea(0,40); //Set the size of the text box
      info.setLineWrap(true); //Wrap the text
      info.setText("Welcome to the game DIG!                                                                                                Click on the other tabs for more infomation");
      
      //create JTextArea for the movement
      JTextArea info2 = new JTextArea(0,40); //Set the size of the text box
      info2.setLineWrap(true); //Wrap the text
      info2.setText("Use the keyboard keys WASD to move around the map.                                             You will dig automatically when holding a direction against a diggable block.       When flying you are not able to dig");
      
      //create JTextArea for the stations
      JTextArea info3 = new JTextArea(0,40); //Set the size of the text box
      info3.setLineWrap(true); //Wrap the text
      info3.setText("On the surface of the map there are 3 stations:                                                             1. The Repair Station - This station will automatically heal you back to full health at a certain cost based on how much repair you need.                                                2. The Sell Station - This station will automatically sell all minerals you have in your storage.                                                                                                                              3. The Fuel Station - This station will automatically fuel you back to a full tank at a certain cost based on how much fuel you need.                                                             4. Upgrade Station - See the upgrade tab for more details");
      
      //create JTextArea for the upgrade
      JTextArea info4 = new JTextArea(0,40); //Set the size of the text box
      info4.setLineWrap(true); //Wrap the text
      info4.setText("At this station you are able to upgrade your Fuel Tank, Maximum Health, Storage Capacity, Heat Resistance, and Smelter Level for the following prices:                   Tier 1 - 1500                                                                                                                          Tier 2 - 6000                                                                                                                          Tier 3 - 25 000                                                                                                                       Tier 4 - 100 000");
      
      //create JTextArea for the status
      JTextArea info5 = new JTextArea(0,40); //Set the size of the text box
      info5.setLineWrap(true); //Wrap the text
      info5.setText("When you are digging you must consider status of your drill                                       Fuel - This resource is consumed at all times when you are underground. However once you surface you will not consume fuel and are able to buy more at the fuel station. If you run out of fuel, you lose!                                                                         Heat Level -This value increases as you dig deeper towards the core of the earthHealth - You lose health if your heat resistance is not high enough for the depth you are digging at, and you will take damage                                                                 Cargo - This shows how many minerals you have, and how many you can store. Money - You get money by selling the ores you have mined, and can use money to buy upgrades for your drill");
      
      //add all JComponents to the tab panel
      tab1.add(info);
      tab2.add(info2);
      tab3.add(info3);
      tab4.add(info4);
      tab5.add(info5);
      
      //Add both Jpanels to the JTabbedPane
      tabbed.addTab("Introduction",tab1);
      tabbed.addTab("Controls",tab2);
      tabbed.addTab("Stations",tab3);
      tabbed.addTab("Upgrade",tab4);
      tabbed.addTab("Drill Status",tab5);
      
      //Add the tabbed pane to the main panel
      main.add(tabbed);
      
      //Set the main panel to Visible
      main.setVisible(true);

    }
  }
  
  /*
   * quitGameButtonListener 
   * This method closes the program when in the menu. Returns nothing
   */
  static class quitGameButtonListener implements ActionListener{ //Action listener for the button
    public void actionPerformed(ActionEvent event)  {
      System.exit(0); //Exit the program
    }
  }
  
}