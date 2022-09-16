class Ironium extends Tile { //Class of the superclass Tile
  
  private int value = 30; //Stores the value of the ore
  
  /*
   * Ironium
   * This is a constructor for an object of the Tile superclass
   * @param Two integers that hold the objects position
   */
  Ironium (int x, int y) {
    super(x,y);
  }
  
  /*
   * getX
   * This method returns the current x position of the object
   * @return The x position of the object
   */
  int getX () {
    return super.getX();
  }
  
  /*
   * getY
   * This method returns the current y position of the object
   * @return The y position of the object
   */
  int getY () {
    return super.getY();
  }
  
  /*
   * getValue
   * This method returns the value in dollars of the object
   * @return The value of the object
   */
  int getValue () {
    return value;
  }
  
}