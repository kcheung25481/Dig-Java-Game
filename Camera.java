import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Camera{ //Class used to determine how much of the map to draw based on the players loaction
  
  //Declare variables
  Tile[][] map; //map array
  int posX, posY; //posision of the camera (top left pixel, map is 3000x60000)
  int visibleWidth, visibleHeight;  //Number of tiles that fit on the screen
  int screenWidth, screenHeight;  //Size of screen (should be 1080x1920)
  int edgeFixX, edgeFixY; //Variable used to adjust the posision of tiles based on the camera location
  int boxWidth, boxHeight; //The size of the tiles in pixels (default (60x60)
  int arrayY, arrayX; //Location of the camera on the array (array is 50x1000)
  
  Camera(Tile[][] map){ //Constructor
    
    this.map = map; //sets map
    
    //sets starting location
    posX = 0;
    posY = 0;
    
    //sets screen size
    screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
     
    //sets how many tiles fit on screen (must be a 16:9 ratio)
    visibleWidth=32; //The size of the visible portion of the map
    visibleHeight=18;  
    
    //finds and sets the size of the boxes
    boxWidth = screenWidth/visibleWidth;
    boxHeight = screenHeight/visibleHeight;
    
    //sets starting values for edge fix
    edgeFixX= 0;
    edgeFixY=0;
  }
  
  /*
   * update
   * Method used to update the location of the camera based on player location and update the map stored in camera with new map
   * @param Player to gather coordinates
   * @param 2D Tile array containing map
   */
  public void update(PlayerDrill player, Tile[][] map){
    
    //Since the player spends most time in the center of the screen the camera find cordinates by subtracting the player pos by half its dimentions
    posX = (player.getPosX() - (screenWidth/2));
    posY = (player.getPosY() - (screenHeight/2));
    
    //If the posision found it out of bounds it simply sets it to the max or min depending on where it is out
    if (posX < 0){ //Min left
      posX = 0;
    }else if (posX > 3000 - screenWidth - 1){ //Max right
      posX = 3000 - screenWidth - 1;
    }
    
    if (posY < 0){ //Min up
      posY = 0;
    }else if (posY > 60000 - screenHeight - 1){ //Max down
      posY = 60000 - screenHeight - 1;
    }
    
    this.map=map; //Update map for drawing
    
  }
  
  /*
   * draw
   * Method used to draw the map on screen
   * @param Graphics object
   */
  public void draw(Graphics g){
    
    //System.out.println("(" + posX + "," + posY + ")"); //Debug used to test coordinate system (WARNING: LAGS GAME REAL BAD)
    
    //Foor loop goes through section of array that fits on screen plus one if in case half tiles fit on screen
    for (int j=0;j<visibleHeight+1;j++){
      for (int i=0;i<visibleWidth+1;i++) {
        
        findArrayPos(j,i); //Find where on the array the camera is located
        
        //Determine the mineral on array and set its coresponding colour
        if (map[arrayY][arrayX] instanceof Bronzium){
          g.setColor(new Color(204, 153, 0));
        }else if (map[arrayY][arrayX] instanceof Ironium){
          g.setColor(Color.GRAY);
        }else if (map[arrayY][arrayX] instanceof Silverium){
          g.setColor(new Color(179, 179, 179));
        }else if (map[arrayY][arrayX] instanceof Goldium){
          g.setColor(Color.YELLOW);
        }else if (map[arrayY][arrayX] instanceof Platinium){
          g.setColor(new Color(204, 255, 255));
        }else if (map[arrayY][arrayX] instanceof Emerald){
          g.setColor(Color.GREEN);
        }else if (map[arrayY][arrayX] instanceof Ruby){
          g.setColor(Color.RED);
        }else if (map[arrayY][arrayX] instanceof Diamond){
          g.setColor(Color.BLUE);
        }else if (map[arrayY][arrayX] instanceof Amazonite){
          g.setColor(new Color(102, 204, 255));
        }else if (map[arrayY][arrayX] instanceof Electronium){
          g.setColor(new Color(255, 204, 0));
        }else if (map[arrayY][arrayX] instanceof Unobtanium){
          g.setColor(new Color(102, 0, 102));  
        }else if (map[arrayY][arrayX] instanceof Dirt){
          g.setColor(new Color(102, 51, 0));
        }else if (map[arrayY][arrayX] instanceof Rock){
          g.setColor(Color.BLACK);
        }else {
          g.setColor(Color.WHITE);
        }
        
        checkBoxEdge(j,i); //Make correction to the edge of screen so that part tiles can be drawn
        
        //When drawing, if a tile fits exactly (on X of Y) the correction must not be applied
        //To determine if the correction should be applied on either X and Y the mod is found 
        //If the mod is 0 then no correction is applied for that axis
        if ((posY % 60 == 0) && (posX % 60 == 0)){
          g.fillRect((i*boxWidth), (j*boxHeight), boxWidth, boxHeight);  //draw box on edge
        }else if (posY % 60 == 0){
          g.fillRect((i*boxWidth-edgeFixX), (j*boxHeight), boxWidth, boxHeight);  //draw box on edge
        }else if (posX % 60 == 0){
          g.fillRect((i*boxWidth), (j*boxHeight)-edgeFixY, boxWidth, boxHeight);  //draw box on edge
        }else{
          g.fillRect((i*boxWidth)-edgeFixX, (j*boxHeight)-edgeFixY, boxWidth, boxHeight);  //draw box on edge
        }
      }
      
    }
  }
  
  /*
   * checkBoxEdge
   * Method used to find how much the box needs to be shifted horizontaly and verticaly when drawn
   * @param integers from for loop
   */
  public void checkBoxEdge(int j, int i){
    //Find the exact cordinates of the top left of the tile by finding its array pos and multiplying by its size
    int boxY = (int)((((posY/boxHeight))+j)*boxHeight);
    int boxX = (int)((((posX/boxWidth))+i)*boxHeight);
    
    //If the camera does not exactly line up with the tile, the amount of pixels it is off by is determined and saved for drawing
    if (posY > boxY){
      edgeFixY = (posY- boxY);
    }
    
    if (posX > boxX){
      edgeFixX = (posX- boxX);
    }
    
  }
  
  /*
   * findArrayPos
   * Method used to find where on the array the camera is located (top left pixel)
   * @param integers from for loop
   */
  public void findArrayPos(int j, int i){
    //By dividing the position by the camera then rounding down you can find what the cordinate on the top left is on the array
    //The values of the for loop are then added so the correct tile can be found
    arrayY = (int)((posY/boxHeight))+j;
    arrayX = (int)((posX/boxWidth))+i;
  }
  
  
}