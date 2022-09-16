import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class DigGameLoop extends JFrame{ //Main class that holds all the objects together
  
  static JFrame gameFrame; //Create a new frame to put the game on
  
  DigGameLoop(Tile[][] map){ //Frame constructor
    
    gameFrame = new JFrame("DIG"); //name the frame
    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //set to exit on close
    gameFrame.getContentPane().add( new DigGamePanel(map)); //make and add the panel that contians our game
    
    //Set frame as visable and fullscreen
    gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    gameFrame.setUndecorated(true);
    gameFrame.setVisible(true);
  }
  
  static class DigGamePanel extends JPanel implements KeyListener{ //Game panel
    
    //Declare variables
    FrameRate frameCounter = new FrameRate();
    PlayerDrill player = new PlayerDrill(); //player object
    Camera camera;
    Tile[][] map; //empty map array
    Building[] buildings; //building array
    Clock clock;
    HUD hud;
    
    //booleans used for dig directions
    boolean digLeft = false;
    boolean digRight = false;
    boolean digDown = false;
    
    //Game panel constructor, takes in map genorated earlier and begins game
    DigGamePanel(Tile[][] map){
      
      //Add keylistener and set panel as focused
      addKeyListener(this);
      setFocusable(true);
      requestFocusInWindow();
      
      clock = new Clock(); //Initialize clock
      hud = new HUD(); //Initialize hud
      
      camera = new Camera(map); //Initialize camera
      //Initialize buildings (and set locations)
      buildings = new Building[4]; 
      buildings[0] = new FuelStation(120,840,120,120);
      buildings[1] = new RepairStation(2000,840,120,120);
      buildings[2] = new SellStation(500,840,120,120);
      buildings[3] = new StoreStation(2800,840,120,120);
      
      //load building sprites
      for (int i = 0; i < 4; i++){
        buildings[i].loadSprites();
      }
      
      this.map = map; //set the panels map to what was genorated
    }
    
    
    public void paintComponent(Graphics g) { 
      super.paintComponent(g); //required to ensure the panel is correctly redrawn
      
      //Update methods
      player.update(map, digLeft, digRight, digDown, clock, buildings);
      camera.update(player, map);
      frameCounter.update();
      
      //Draw methods
      //Map is drawn first, the buildings over it, player over both, then the frame counter and HUD
      camera.draw(g);
      for (int i = 0; i < 4; i++){
        buildings[i].draw(g, camera);
      }
      player.draw(g);
      if (true){
        g.setColor(Color.BLACK);
        frameCounter.draw(g,0,10);
      }
      hud.drawHud(g, player); 
      
      //Refresh screen
      repaint();
    }
    
    //Key listener
    public void keyTyped (KeyEvent e){
    }
    
    public void keyPressed(KeyEvent e) {
      if(e.getKeyChar() == 'a' ){    
        player.xdirection=-1; //Move left
        digLeft = true;
      } else if(e.getKeyChar() == 's'){
        player.ydirection=1; //Move down
        digDown = true;
      } else if(e.getKeyChar() == 'd' ){
        player.xdirection=1; //Move right
        digRight = true;
      } else if(e.getKeyChar() == 'w' ){
        player.ydirection= -1; //movce up
        //Do not allow digging while moving up
        digLeft = false;
        digRight = false;
        digDown = false;
        player.gravity = false; //prevent gravity from action on player while moving up
      }  
    }
    
    public void keyReleased(KeyEvent e) {    
      //stop digging
      digLeft = false;
      digRight = false;
      digDown = false;
      if(e.getKeyChar() == 'a' ){    
        player.xdirection=0; //stop move
      } else if(e.getKeyChar() == 's' ){
        player.ydirection=0; //stop move
      } else if(e.getKeyChar() == 'd' ){
        player.xdirection=0; //stop move
      } else if(e.getKeyChar() == 'w' ){
        player.ydirection=0; //stop move
        player.gravity = true; //turn back on gravity
      } 
    }  
  } 
}

