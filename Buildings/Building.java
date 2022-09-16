import java.awt.*;

abstract class Building { //Abstract class for all buildings on the surface
  
  //Initialize variables
  private int x,y; //Store the x and y position
  private int high, wide; //Store the height and width
  Rectangle boundingBox; //Create a collideable box
  
  /*
   * Building
   * This is a constructor for an object of Building
   * @param Two integers containing the objects dimensions
   * @param Two integers that hold the objects position
   */
  Building(int x, int y, int wide, int high){ //Constructor
    this.x = x; //Set x position
    this.y = y; //Set y position
    this.wide = wide; //Set width
    this.high = high; //Set height
    boundingBox = new Rectangle(x,y,wide,high); //Create a bounding box based on the dimensions
  }
  
  
  /*
   * getX
   * This method returns the current x position of the object
   * @return The x position of the object
   */
  int getX () {
    return x;
  }
  
  /*
   * getY
   * This method returns the current y position of the object
   * @return The y position of the object
   */
  int getY () {
    return y;
  }
  
  /*
   * getHigh
   * This method returns the height of the object
   * @return The height of the object
   */
  int getHigh () {
    return high;
  }
  
  /*
   * getWide
   * This method returns the width of the object
   * @return The width of the object
   */
  int getWide () {
    return wide;
  }
  
  /*
   * loadSprites
   * This method loads the sprites of the buildings. Returns nothing.
   */
  abstract void loadSprites();
  
  /*
   * draw
   * This method draws the sprites of the buildings on the screen relative to the map. Returns nothing.
   * @param Graphics
   * @param The camera object
   */
  abstract void draw(Graphics g, Camera camera);
  
  /*
   * doAction
   * This method preformes the action that the building is meant to do. Returns nothing
   * @param The player object
   */
  abstract void doAction(PlayerDrill player);
}