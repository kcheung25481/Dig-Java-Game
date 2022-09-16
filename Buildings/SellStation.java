import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*; 

class SellStation extends Building{ //Class SellStation of the superclass Building
  
  BufferedImage image; //Store the sprite of the building
  
  /*
   * SellStation
   * This is a constructor for an object of the Building superclass
   * @param Two integers containing the objects dimensions
   * @param Two integers that hold the objects position
   */
  SellStation(int x, int y, int wide, int high){
    super(x,y,wide,high); //Call the superclass constructor 
  }
  
  /*
   * doAction
   * This method sells the player's cargo. Returns nothing
   * @param The player object
   */
  public void doAction(PlayerDrill player){
    player.sellCargo(); //Call the sell cargo method in the player object
  }
  
  /*
   * loadSprites
   * This method loads the sprites of the buildings. Returns nothing.
   */
  public void loadSprites() { 
     try { //Try catch to see if the image exists
       image = ImageIO.read(new File("sell.png")); //Find and set the image
     } catch(Exception e) { 
       System.out.println("error loading sprite");}; //Error message
   }
 
  /*
   * draw
   * This method draws the sprites of the buildings on the screen relative to the map. Returns nothing.
   * @param Graphics
   * @param The camera object
   */
   public void draw(Graphics g,  Camera camera) { 
     //Fixes the edges of the building if it goes off screen
    int edgeFixY= camera.posY;
    int edgeFixX= camera.posX;
    
    //Draw the sprite on screen
    g.drawImage(image, getX()-edgeFixX, getY()-edgeFixY, null);
   }
}