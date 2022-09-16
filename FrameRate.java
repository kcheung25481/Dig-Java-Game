import java.awt.*;
import java.awt.event.*;

class FrameRate { 

  String frameRate; //to display the frame rate to the screen
  long lastTimeCheck; //store the time of the last time the time was recorded
  long deltaTime; //to keep the elapsed time between current time and last time
  int frameCount; //used to cound how many frame occurred in the elasped time (fps)

  //Constructor for the class FrameRate
  public FrameRate() { 
    lastTimeCheck = System.currentTimeMillis(); //Check when the last time the framerate was checked
    frameCount=0; //Set the frame count to 0
    frameRate="0 fps";
  }
  
  /*
   * update 
   * This method updates the time check and calcuates the frames per second. Returns nothing
   */
  public void update() { 
  long currentTime = System.currentTimeMillis();  //get the current time
    deltaTime += currentTime - lastTimeCheck; //add to the elapsed time
    lastTimeCheck = currentTime; //update the last time var
    frameCount++; // everytime this method is called it is a new frame
    if (deltaTime>=1000) { //when a second has passed, update the string message
      frameRate = frameCount + " fps" ;
      frameCount=0; //reset the number of frames since last update
      deltaTime=0;  //reset the elapsed time     
    }
  }
  
  /*
   * draw 
   * This draws the framerate in the top left hand corner of the player screen. Returns nothing
   */
   public void draw(Graphics g, int x, int y) {
      g.drawString(frameRate,x,y); //display the frameRate
   }
   

}