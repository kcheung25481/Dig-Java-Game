import java.awt.*;

abstract public class Tile { //Abstract class for all minerals
  
  private int x; //Store the x position
  private int y; //Store the y position
  Rectangle boundingBox; //Create a collideable box
 
  /*
   * Tile
   * This is a constructor for an object of Tile
   * @param Two integers that hold the objects position
   */
  Tile (int x, int y) {
    this.x = x; //Set x position
    this.y = y; //Set y position
    boundingBox = new Rectangle (y * 60, x * 60, 60, 60); //Create a bounding box based on the dimensions
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
   * getValue
   * This method returns the value in dollars of the object
   * @return The value of the object
   */
  abstract int getValue();
  
}