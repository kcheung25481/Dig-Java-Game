import javax.swing.*;
import java.awt.*;
import java.io.*; 
import java.awt.image.*;
import javax.imageio.*;

class StoreStation extends Building{ //Class StoreStation of the superclass Building
  
  private PlayerDrill player; //Store the player object
  BufferedImage image; //Store the sprite of the building
  
  /*
   * StoreStation
   * This is a constructor for an object of the Building superclass
   * @param Two integers containing the objects dimensions
   * @param Two integers that hold the objects position
   */
  StoreStation(int x, int y, int wide, int high){
    super(x,y,wide,high);
  }
  
  /*
   * loadSprites
   * This method loads the sprites of the buildings. Returns nothing.
   */
  public void loadSprites() { 
     try { //Try catch to see if the image exists
       image = ImageIO.read(new File("store.png")); //Find and set the image
     } catch(Exception e) { 
       System.out.println("error loading sprite");}; //Error message
   }
 
   /*
   * draw
   * This method draws the sprites of the buildings on the screen relative to the map. Returns nothing.
   * @param Graphics
   * @param The camera object
   */
   public void draw(Graphics g, Camera camera) { 
    //Fixes the edges of the building if it goes off screen
    int edgeFixY= camera.posY;
    int edgeFixX= camera.posX;
    
     //Draw the sprite on screen
    g.drawImage(image, getX()-edgeFixX, getY()-edgeFixY, null);
   }
  
   /*
   * doAction
   * This method opens up the shop and allows the player to upgrade their drill. Returns nothing
   * @param The player object
   */
  public void doAction(PlayerDrill player){
    
    this.player = player; //Temporary storage of the player object
    
    final JFrame main = new JFrame(); //Main frame of the buy window
    
    main.setSize(500,500);  // set the size of my window to 400 by 400 pixels
    main.setResizable(true);  // set my window to allow the user to resize it
    
    main.setLayout(new FlowLayout()); //Set the layout to flow
    
    //Create health upgrade button and add action listener
    JButton upgradeHealth = new JButton("Buy Health Upgrade");
    upgradeHealth.addActionListener(new upgradeHealthButtonListener());
    
    //Create fuel upgrade button and add action listener
    JButton upgradeFuel = new JButton("Buy Fuel Upgrade");
    upgradeFuel.addActionListener(new upgradeFuelButtonListener());
    
    //Create heat resistance upgrade button and add action listener
    JButton upgradeHeatResistance = new JButton("Buy Heat Resistance Upgrade");
    upgradeHeatResistance.addActionListener(new upgradeHeatResistanceButtonListener());
    
    //Create smelter upgrade button and add action listener
    JButton upgradeSmelter = new JButton("Buy Smelter Upgrade");
    upgradeSmelter.addActionListener(new upgradeSmelterButtonListener());
    
    //Create cargo upgrade button and add action listener
    JButton upgradeCargo = new JButton("Buy Cargo Upgrade");
    upgradeCargo.addActionListener(new upgradeCargoButtonListener());
    
    //Add the buttons to the main frame
    main.add(upgradeHealth);
    main.add(upgradeFuel);
    main.add(upgradeHeatResistance);
    main.add(upgradeSmelter);
    main.add(upgradeCargo);
    
    //Display the frame on the screen
    main.pack();
    main.setVisible(true);
    
    //Update the player in the class
    player = this.player;
  }
  
  //Increases the tier if this button is pressed
  class upgradeHealthButtonListener implements java.awt.event.ActionListener{
    public void actionPerformed(java.awt.event.ActionEvent event)  {
      player.upgradeHealth();
      System.out.println("Your Health tier has been upgraded");
    }
  }
  
  //Increases the tier if this button is pressed
  class upgradeFuelButtonListener implements java.awt.event.ActionListener{
    public void actionPerformed(java.awt.event.ActionEvent event)  {
      player.upgradeFuel();
      System.out.println("Your Fuel tier has been upgraded");
    }
  }
  
  //Increases the tier if this button is pressed
  class upgradeHeatResistanceButtonListener implements java.awt.event.ActionListener{
    public void actionPerformed(java.awt.event.ActionEvent event)  {
      player.upgradeHeat();
      System.out.println("Your Heat Resistance tier has been upgraded");
    }
  }
  
  //Increases the tier if this button is pressed
  class upgradeSmelterButtonListener implements java.awt.event.ActionListener{
    public void actionPerformed(java.awt.event.ActionEvent event)  {
      player.upgradeSmelter();
      System.out.println("Your Smelter tier has been upgraded");
    }
  }
  
  //Increases the tier if this button is pressed
  class upgradeCargoButtonListener implements java.awt.event.ActionListener{
    public void actionPerformed(java.awt.event.ActionEvent event)  {
      player.upgradeCargo();
      System.out.println("Your Cargo tier has been upgraded");
    }
  }
}