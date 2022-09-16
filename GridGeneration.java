import java.util.Scanner;
import java.io.*;

class GridGeneration { //Class for grid generation
  
  GridGeneration(boolean load){ //Stores a previously loaded grid, if applicable 
    
    Tile [][] mapBoard = new Tile [1000][50]; //Initialize the Board
    
    if (!load){ //If there is no previous map loaded
      for (int i = 0; i < 1000; i++) { //Height of the map
        for (int j = 0; j < 50; j++) { //Width  of the map 
          if (i < 16){ //Make the first 16 blocks null, as a sky
            mapBoard[i][j] = null;
          }else if (i == 16){ //Always make the first layer dirt
            mapBoard[i][j] = new Dirt(i,j);
          }else{ //Random generation of minerals based on spawn rates   
            int randSpawn = (int)(Math.random()*14); //Pick from 1 out of 14 possible tiles
            
            //Randomly decide by chance if the mineral is to be spawned
            if (randSpawn == 1 && Math.random() < 0.65 - i*0.00055) { //Ironium spawn rate
              mapBoard[i][j] = new Ironium(i, j);
            }else if (randSpawn == 2 && Math.random() < 0.35 - i*0.00025) { //Bronzium spawn rate
              mapBoard[i][j] = new Bronzium(i, j);
            }else if (randSpawn == 3 && Math.random() < 0.25 - i*0.00015) { //Silverium spawn rate
              mapBoard[i][j] = new Silverium(i, j);
            }else if (randSpawn == 4 && Math.random() < i*0.00045) { //Goldium spawn rate
              mapBoard[i][j] = new Goldium(i, j);
            }else if (randSpawn == 5 && Math.random() < i*0.00040) { //Platinium spawn rate
              mapBoard[i][j] = new Platinium(i, j);
            }else if (randSpawn == 6 && Math.random() < i*0.00035) { //Emerald spawn rate
              mapBoard[i][j] = new Emerald(i, j);
            }else if (randSpawn == 7 && Math.random() < i*0.00030) { //Ruby spawn rate
              mapBoard[i][j] = new Ruby(i, j);
            }else if (randSpawn == 8 && Math.random() < i*0.00025) { //Diamond spawn rate
              mapBoard[i][j] = new Diamond(i, j);
            }else if (randSpawn == 9 && Math.random() < i*0.00020) { //Amazonite spawn rate
              mapBoard[i][j] = new Amazonite(i, j);
            }else if (randSpawn == 10 && Math.random() < i*0.00015) { //Electronium spawn rate
              mapBoard[i][j] = new Electronium(i, j);
            }else if (randSpawn == 11 && Math.random() < i*0.00001) { //Unobtanium spawn rate
              mapBoard[i][j] = new Unobtanium(i, j);
            }else if (randSpawn == 12 && Math.random() < i*0.00050) { //Rock spawn rate
              mapBoard[i][j] = new Rock(i, j);
            }else if (randSpawn == 13) { //Blank space spawn rate
            }else{
              mapBoard[i][j] = new Dirt(i, j); //If a block is not spawned, spawn dirt insead
            }
          }
        }
      }
    }else{ //If there is a previous map loaded
      try{
        File mapFile = new File("DigMap.txt"); //Open up the file it is saved in
        Scanner read = new Scanner(mapFile); //Initialize the scanner
        int readTile;
        
        for (int i = 0; i < 1000; i++) { //Length of the map
          for (int j = 0; j < 50; j++) { //Width of the map
           
            readTile = read.nextInt(); //Read the file
            //System.out.println(readTile); //Debug
            
            //Spawn tiles based on what was saved
            if (readTile == 1) {
              mapBoard[i][j] = new Ironium(i, j); //If the coordinates land on an Ironium
            }else if (readTile == 2) {
              mapBoard[i][j] = new Bronzium(i, j); //If the coordinates land on a Bronzium
            }else if (readTile == 3) {
              mapBoard[i][j] = new Silverium(i, j); //If the coordinates land on a Silverium
            }else if (readTile == 4) {
              mapBoard[i][j] = new Goldium(i, j); //If the coordinates land on a Goldium
            }else if (readTile == 5) {
              mapBoard[i][j] = new Platinium(i, j); //If the coordinates land on a Platinium
            }else if (readTile == 6) {
              mapBoard[i][j] = new Emerald(i, j); //If the coordinates land on an Emerald
            }else if (readTile == 7) {
              mapBoard[i][j] = new Ruby(i, j); //If the coordinates land on a Ruby
            }else if (readTile == 8) {
              mapBoard[i][j] = new Diamond(i, j); //If the coordinates land on a Diamond
            }else if (readTile == 9) {
              mapBoard[i][j] = new Amazonite(i, j); //If the coordinates land on an Amazonite
            }else if (readTile == 10) { 
              mapBoard[i][j] = new Electronium(i, j); //If the coordinates land on an Electronium
            }else if (readTile == 11) {
              mapBoard[i][j] = new Unobtanium(i, j); //If the coordinates land on an Unobtanium
            }else if (readTile == 12) {
              mapBoard[i][j] = new Rock(i, j); //If the coordinates land on a Rock
            }else if (readTile == 0) {
              mapBoard[i][j] = new Dirt(i, j); //If the coordinates land on a Dirt
            }
          }
        }        
        read.close(); //Close the scanner
      }catch (IOException e) { 
        System.out.println("Could not find save file"); //Error message
      }
      
    }
    
    saveGrid(mapBoard);//TEST SAVE MEHTOD
    
    DigGameLoop game = new DigGameLoop(mapBoard); //Start the game loop
  }
  
  /*
   * saveGrid 
   * This method accepts the parameters of a grid of Tile and saves the grid in a text file. Returns nothing
   * @param A 2D Tile array holding objects of the Tile superclass
   */
  public void saveGrid(Tile[][] mapBoard){
    
    try{ //Try to see if there is a file that can be written on
      File mapFile = new File("DigMap.txt");
      PrintWriter write = new PrintWriter(mapFile); //Initialize the PrintWriter
      
      for (int i = 0; i < 1000; i++) { //Length of the array
        for (int j = 0; j < 50; j++) { //Width of the array
          
          //Write on the file the exact tiles on the grid
          if (mapBoard[i][j] instanceof Ironium){ //If the coordinates land on an Ironium
            write.print("1 ");
          }else if (mapBoard[i][j] instanceof Bronzium){ //If the coordinates land on a Bronzium
            write.print("2 ");
          }else if (mapBoard[i][j] instanceof Silverium){ //If the coordinates land on a Silverium
            write.print("3 ");
          }else if (mapBoard[i][j] instanceof Goldium){ //If the coordinates land on a Goldium
            write.print("4 ");
          }else if (mapBoard[i][j] instanceof Platinium){ //If the coordinates land on a Platinium
            write.print("5 ");
          }else if (mapBoard[i][j] instanceof Emerald){ //If the coordinates land on an Emerald
            write.print("6 ");
          }else if (mapBoard[i][j] instanceof Ruby){ //If the coordinates land on a Ruby
            write.print("7 ");
          }else if (mapBoard[i][j] instanceof Diamond){ //If the coordinates land on a Diamond
            write.print("8 ");
          }else if (mapBoard[i][j] instanceof Amazonite){ //If the coordinates land on an Amazonite
            write.print("9 ");
          }else if (mapBoard[i][j] instanceof Electronium){ //If the coordinates land on an Electronium
            write.print("10 ");
          }else if (mapBoard[i][j] instanceof Unobtanium){ //If the coordinates land on an Unobtanium
            write.print("11 ");
          }else if (mapBoard[i][j] instanceof Rock){ //If the coordinates land on a Rock
            write.print("12 ");
          }else if (mapBoard[i][j] instanceof Dirt){ //If the coordinates land on a Dirt
            write.print("0 ");
          }else{
            write.print("13 "); //If the coordinates land on a blank space
          }
        }
        write.println(); //Space
      }
      write.close(); //Close Print Writer
    } catch (IOException e) {
      System.out.println("Could not find save file"); //Error message
    }
  }
}

