import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.awt.image.*;
import javax.imageio.*;

public class PlayerDrill{
  
  private double posX, posY; //Position of the player
  double xdirection, ydirection; //Sets direction and speed of player
  boolean gravity; //Toggles effects of gravity on player
  
  
  boolean buildingUse; //Used to check if a building has already been used to avoid using the same building more than once in a sigle colision
  
  private int money; //Money variable
  private int health, healthMax, healthTier; //Health Variables
  private int fuel, fuelMax, fuelTier; //Fuel Variables
  private int heat, heatMax, heatTier; //Heat Variables
  private Tile[] cargo; //Array containing the players cargo
  private int cargoMax, cargoTier, cargoNum; //Cargo Variables (Contents of cargo is stored as an array of the game tiles)
  private int smeltTier; //Used to check if smelter is high enough level to smelt minerals
  private Tile lastMined; //Used to keep track of what mineral was last mined for the smelter
  private int drawX, drawY; //Variables used to determine where on the screen to draw the player
  Rectangle playerBoundingBox; //Players hitbox
  
  //Variables are automaticaly set to the current screen size
  int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
  int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
  
  //Constructor
  PlayerDrill(){
    
    //Set movement and draw values to starting position
    posX = 0;
    posY = 850;
    drawX = 0;
    drawY = 0;
    gravity = true;
    
    //Set starting money, health, fuel ,heat and cargo
    money = 300;
    health = 100;
    fuel = 100;
    heat = 0;
    cargoNum =0;
    cargo = new Tile[50];
    
    //Set max values and teirs for upgrading
    healthMax = 100;
    healthTier = 1;
    fuelMax = 100;
    fuelTier = 1;
    heatMax = 50;
    heatTier = 1;
    cargoMax = 10;
    cargoTier = 1;
    smeltTier = 1;
    
    //Set so that buildings can be used
    buildingUse = true;
    
    //Set the players hit box
    playerBoundingBox = new Rectangle ((int)posX,(int)posY, 40, 40);
    
  }
  

  /*
   * update
   * This method is called by the game loop to update the status of the player
   * @param A 2D Tile array containing map, 3 boolean values to determine direction of drilling
   * @param A clock used for movement speed and cunsumption rates
   * @param A building array containing all of the buildings on the map
   */
  public void update(Tile [][] map, boolean digLeft, boolean digRight, boolean digDown, Clock clock, Building[] buildings){
    useFuel(clock); //Check to consume players fuel
    checkHeat(clock); //Check player heat
    collision(map, digLeft, digRight, digDown, buildings); //Perform collision checks on player
    
    clock.update(); //Update the clock for movement
    //System.out.println(clock.getElapsedTime());
    
    if (gravity == true) { //If collision has determined that gravity is true, gravity is applied to player
      //this.posY=this.posY+1;
      //ydirection=1;
      posY=posY+1*clock.getElapsedTime()*600; //Calculation to move player at set speed 
    }
    
    posX=posX+xdirection*clock.getElapsedTime()*500; //Move the player in the direction set by the keylistener on the X axis
    posY=posY+ydirection*clock.getElapsedTime()*500; //Move the player in the direction set by the keylistener on the Y axis
    
    //Calculations used to determine if player is on boundries and keeps them inside the map
    if (posX < 0){ //Check for leftmost side
      posX = 0; //Corect for left
    }else if (posX > 3000-40){ //Check for right most side
      posX = 3000-41; //Correct for right
    }
    
    if (posY < 0){ //Check for top
      posY = 0; //Correct for top
    }else if (posY > 60000-40){ //Check for bottom
      posY = 60000-41; //Correct for bottom
    }
    
    //Updates bounding box to new player coordinates
    playerBoundingBox.x = (int)posX;
    playerBoundingBox.y = (int)posY;
    
  }

  /*
   * draw
   * This method draws the player on the correct area of the screen using sprites
   * @param Graphics class for drawing graphics
   */
  public void draw(Graphics g){
    
    //Create buffered images to be used as sprites for the player
    BufferedImage drillRight;
    BufferedImage drillDown;
    BufferedImage drillLeft;
    BufferedImage drillFly;
    BufferedImage drillIdle;
    
    //Set draw values as the integer values of the players posision
    drawX = (int)posX; 
    drawY = (int)posY;
    
    //Check to find where on the screen the player should be drawn based on position
    //When the player reaches the middle of the screen it is kept in the same possision and the camera moves instead
    if (posX > (screenWidth/2)){ //Keep player drawn in centre
      drawX = screenWidth/2;
    }
    if (posX > 3000-screenWidth/2){ //Draw player to the right if the camera stops
      drawX =(int) (posX - (3000 - (screenWidth)));
    }
    
    if (posY > (screenHeight/2)){ //Keep player drawn in the centre
      drawY = screenHeight/2;
    }
    
    if (posY > 60000-screenHeight/2){ //Draw player bellow middle if the camera stops
      drawY =(int) (posY - (60000 - (screenHeight)));
    }
    
    //Try catch block used to load and draw sprites
    try {
      //Load sprites
      drillRight = ImageIO.read(new File("Drill Right.png"));
      drillDown = ImageIO.read(new File("Drill Down.png"));
      drillLeft = ImageIO.read(new File("Drill Left.png"));
      drillFly = ImageIO.read(new File("Drill Fly.png"));
      drillIdle = ImageIO.read(new File("Drill Idle.png"));
      
      //Determine the direction of the player and draw the correct sprite
      if (gravity == true || ydirection == -1){ //Flying
        g.drawImage(drillFly, drawX, drawY, null);
      }else if ((xdirection == 1 || xdirection == 0.1) && ydirection == 0) { //Right
        g.drawImage(drillRight, drawX, drawY, null);
      }else if ((xdirection == -1  || xdirection == -0.1) && ydirection == 0) { //Left
        g.drawImage(drillLeft, drawX, drawY, null);
      }else if (ydirection == 0.1){ //Down
        g.drawImage(drillDown, drawX, drawY, null);
      }else{ //Idle
        g.drawImage(drillIdle, drawX, drawY, null);
      }
      
    } catch(Exception e) { //If any sprites are missing they are replaced by a simple blue square as placeholder
      g.setColor(Color.BLUE);
      g.fillRect(drawX,drawY, 40, 40);
    }
    
    
    
  }
  
  /*
   * getPosX
   * retruns players X posision for camera calculation
   * @return integer value of player X cordinate
   */
  public int getPosX(){
    return (int)posX;
  }
  
  /*
   * getPosY
   * retruns players Y posision for camera calculation
   * @return integer value of player Y cordinate
   */
  public int getPosY(){
    return (int)posY;
  }
  
  
  
  /*
   * getMoney
   * retruns players current amount of money
   * @return integer value representing money
   */
  public int getMoney(){
    return money;
  }
  
  /*
   * checkMoney
   * Method used by the upgrade methods to check for corect amouts of money and charges that amount  
   * @param Integer representing the current tier of the part requesting upgrade
   * @return boolean true if player has the money required, otherwise false
   */
  public boolean checkMoney(int tier){
    if (tier == 1){ //Tier 1 --> Tier 2 money check 
      if (money >= 1500){
        money = money - 1500;
        return true;
      }else{
        return false;
      }    
    }else if (tier == 2){  //Tier 2 --> Tier 3 money check 
      if (money >= 6000){
        money = money - 6000;
        return true;
      }else{
        return false;
      }
    }else if (tier == 3){  //Tier 3 --> Tier 4 money check 
      if (money >= 25000){
        money = money - 25000;
        return true;
      }else{
        return false;
      } 
    }else if (tier == 4){  //Tier 4 --> Tier 5 money check 
      if (money >= 100000){
        money = money - 100000;
        return true;
      }else{
        return false;
      }
    }
    return false;
    
  }

  
  /*
   * getHealth
   * retruns players current amount health
   * @return integer value representing health
   */
  public int getHealth(){
    return health;
  }
  
  /*
   * getHealthTier
   * retruns players current health tier
   * @return integer value representing health tier
   */
  public int getHealthTier(){
    return healthTier;
  }
  
  /*
   * getHealthMax
   * retruns players maximum health
   * @return integer value representing the max health
   */
  public int getHealthMax(){
    return healthMax;
  }
  
  /*
   * damageHealth
   * When called it damages the player for the value input
   * It also checks to see if the player has died and displays a dialoge box if they do the exits the game
   * @param integer that represents the damage to be dealt to the player
   */
  public void damageHealth(int damage){
    health = health - damage;
    if (health <= 0){
      JOptionPane.showMessageDialog(null, "GAME OVER");
      System.exit(0);
    }
  }
  
  /*
   * repairHealth
   * Fully repairs the player and charges the player for the repair
   */
  public void repairHealth(){
    if (health != healthMax){
      money = money - (healthMax - health);
      health = healthMax;
    }
  }
  
  /*
   * upgradeHealth
   * Upgrades the players maximum health based on its current tier
   */
  public void upgradeHealth(){
    if (checkMoney(healthTier)){
      if (healthTier == 1){
        healthTier ++;
        healthMax = 200;
      }else if (healthTier == 2){
        healthTier ++;
        healthMax = 300;
      }else if (healthTier == 3){
        healthTier ++;
        healthMax = 400;
      }else if (healthTier == 4){
        healthTier ++;
        healthMax = 500;
      }
    }
  }
  
  
  /*
   * getFuel
   * retruns players current amount of fuel
   * @return integer value representing fuel
   */
  public int getFuel(){
    return fuel;
  }
  
    /*
   * getFuelTier
   * retruns players current fuel tier
   * @return integer value representing fuel tier
   */
  public int getFuelTier(){
    return fuelTier;
  }
  
  /*
   * getFuelMax
   * retruns players maximum amount of fuel
   * @return integer value representing maximum capacity
   */
  public int getFuelMax(){
    return fuelMax;
  }
  
  /*
   * useFuel
   * Method that traks when fuel should be consumed and consumes it when it should
   * @param A clock that is used to determine the last time fuel was used so that it is always consumed at a constaint rate
   */
  public void useFuel(Clock clock){
    if ((clock.checkFuel()) && (((int)((posY + 25)/60)) > 16)){ //Checks how much time is passed and consumes fuel if it should
      fuel--;
      if (fuel <= 0){
        damageHealth(healthMax);
      }
    }
  }
  
  /*
   * reFuel
   * Fully refuels the player and charges based on how much fuel was used
   */
  public void reFuel(){
    if (fuel != fuelMax){
      money = money - (fuelMax - fuel);
      fuel = fuelMax;
    }
  }
  
  /*
   * upgradeFuel
   * Upgrades the players maximum fuel based on its current tier
   */
  public void upgradeFuel(){
    if (checkMoney(fuelTier)){
      if (fuelTier == 1){
        fuelTier ++;
        fuelMax = 200;
      }else if (fuelTier == 2){
        fuelTier ++;
        fuelMax = 400;
      }else if (fuelTier == 3){
        fuelTier ++;
        fuelMax = 800;
      }else if (fuelTier == 4){
        fuelTier ++;
        fuelMax = 1500;
      }
    }
  }
 
  /*
   * getHeat
   * retruns players current heat
   * @return integer value representing heat
   */
  public int getHeat(){
    return heat;
  }
  
  /*
   * getHeatTier
   * retruns players current heat resistaince tier
   * @return integer value representing heat resistaince tier
   */
  public int getHeatTier(){
    return heatTier;
  }
  
  /*
   * getHeatMax
   * retruns players current maximum heat
   * @return integer value representing the max heat resistaince
   */
  public int getHeatMax(){
    return heatMax;
  }
  /* 
   * checkHeat
   * Method that checks the heat of the drill and removes health at set interval if health is over the max
   * @param Clock used to determine last time damage was dealth so that it is done at a set rate
   */
  public void checkHeat(Clock clock){
    heat = (int)(((int)((posY + 25)/60))/2); 
    if (heat > heatMax && clock.checkHeat()){
      damageHealth(1);
    } 
  }
  
  /*
   * upgradeHeat
   * Upgrades the players maximum heat based on its current tier
   */
  public void upgradeHeat(){
    if (checkMoney(heatTier)){
      if (heatTier == 1){
        heatTier ++;
        heatMax = 100;
      }else if (heatTier == 2){
        heatTier ++;
        heatMax = 200;
      }else if (heatTier == 3){
        heatTier ++;
        heatMax = 300;
      }else if (heatTier == 4){
        heatTier ++;
        heatMax = 500;
      }
    }
  } 
  
  /*
   * getHeatTier
   * retruns players current cargo tier
   * @return integer value representing cargo tier
   */
  public int getCargoTier(){
    return cargoTier;
  }
  
  /*
   * getSmelterTier
   * retruns players current smelter tier
   * @return integer value representing smelter tier
   */
  public int getSmelterTier(){
    return smeltTier;
  }
  
  /*
   * getCargo
   * retruns what slot in the cargo array the player is currently on
   * @return integer value representing slot on the cargo array
   */
  public int getCargo(){
    return cargoNum;
  }
  
  /*
   * getCargoMax
   * retruns players current maximum cargo size
   * @return integer value representing the max cargo size
   */
  public int getCargoMax(){
    return cargoMax;
  }
  
  /*
   * getCargoLast
   * retruns the last mineral mined by the player using cargo num to check
   * @return Tile object that was last added to cargo array
   */
  public Tile getCargoLast(){
    if (cargoNum-1 > -1){
      return cargo[cargoNum-1];
    }else{ 
      return null;
    }
  }
  /*
   * addCargo
   * Method used to add cargo to cargo array and keep track of how many minerals are in inventory
   * @param Tile that was last dug by player
   */
  public void addCargo(Tile newCargo){
    newCargo = checkSmelt(newCargo); //Check for possible smelting combinations before adding the cargo
    if (cargoNum < cargoMax){
      cargo[cargoNum] = newCargo;
      //System.out.println(newCargo); //Test output
      cargoNum++;
    }
  }
  
  /*
   * checkSmelt
   * Method used to check the last mined mineral and the mineral just mined by the player for possible combinations
   * What combinations are considerd valid depend on the current smelter tier
   * @return Tile that is the new mineral after combining or the same tile put in if no combination was succsessful
   * @param Tile that was last dug by player
   */
  public Tile checkSmelt(Tile newCargo){
    //Check for tier 1 combonations
    if (smeltTier >= 2){
      if ((lastMined instanceof Bronzium && newCargo instanceof Silverium) || (lastMined instanceof Silverium && newCargo instanceof Bronzium)){
        newCargo = new SterlingSilver(0,0);
        cargoNum--;
      }else if ((lastMined instanceof Bronzium && newCargo instanceof Goldium) || (lastMined instanceof Goldium && newCargo instanceof Bronzium)){
        newCargo = new KatanaGold(0,0);
        cargoNum--;
      }else if (lastMined instanceof SterlingSilver && newCargo instanceof Goldium){
        newCargo = new WhiteGold(0,0);
        cargoNum--;
      }else if ((lastMined instanceof Ironium && newCargo instanceof Platinium) || (lastMined instanceof Platinium && newCargo instanceof Ironium)){
        newCargo = new EarthMagnet(0,0);
        cargoNum--;
      }
    }else{
    }
    //Check for tier 2 combonations
    if (smeltTier >= 3){
      if ((lastMined instanceof Emerald && newCargo instanceof Diamond) || (lastMined instanceof Diamond && newCargo instanceof Emerald)){
        newCargo = new GreenDiamond(0,0);
        cargoNum--;
      }else if ((lastMined instanceof Ruby && newCargo instanceof Diamond) || (lastMined instanceof Diamond && newCargo instanceof Ruby)){
        newCargo = new RedDiamond(0,0);
        cargoNum--;
      }else if ((lastMined instanceof Amazonite && newCargo instanceof Diamond) || (lastMined instanceof Diamond && newCargo instanceof Amazonite)){
        newCargo = new BlueDiamond(0,0);
        cargoNum--;
      }
    }
    //Check for tier 3 combonations
    if (smeltTier >= 4){
      if ((lastMined instanceof Amazonite && newCargo instanceof Emerald) || (lastMined instanceof Emerald && newCargo instanceof Amazonite)){
        newCargo = new ForestStone(0,0);
        cargoNum--;
      }else if ((lastMined instanceof Amazonite && newCargo instanceof Ruby) || (lastMined instanceof Ruby && newCargo instanceof Amazonite)){
        newCargo = new FireStone(0,0);
        cargoNum--;
      }else if ((lastMined instanceof Amazonite && newCargo instanceof Electronium) || (lastMined instanceof Electronium && newCargo instanceof Amazonite)){
        newCargo = new LightningStone(0,0);
        cargoNum--;
      }
    }
    //Check for tier 4 combonations
    if (smeltTier >= 5){
      if (lastMined instanceof LightningStone && newCargo instanceof Unobtanium){
        newCargo = new AetherStone(0,0);
        cargoNum--;
      }
    }
    lastMined = newCargo;
    return newCargo;
  }
  
  /*
   * sellCargo
   * Method that loops through cargo array and sells all of the cargo for their value
   */
  public void sellCargo(){
    for (int i=0; i < cargoNum-1; i++){
      money = money + cargo[i].getValue();
    }
    lastMined = null;
    cargoNum = 0;
  }
  
  /*
   * upgradeSmelter
   * Unlocks better combinations for the player using the smelter
   */
  public void upgradeSmelter(){
    if (checkMoney(smeltTier)){
      if (smeltTier == 1){
        smeltTier ++;
      }else if (smeltTier == 2){
        smeltTier ++;
      }else if (smeltTier == 3){
        smeltTier ++;
      }else if (smeltTier == 4){
        smeltTier ++;
      }
    }
  }
  
  /*
   * upgradeHeat
   * Upgrades the players maximum cargo capacity
   */
  public void upgradeCargo(){
    if (checkMoney(cargoTier)){
      if (cargoTier == 1){
        cargoTier ++;
        cargoMax = 20;
      }else if (cargoTier == 2){
        cargoTier ++;
        cargoMax = 30;
      }else if (cargoTier == 3){
        cargoTier ++;
        cargoMax = 40;
      }else if (cargoTier == 4){
        cargoTier ++;
        cargoMax = 50;
      }
    }
  }
  /*
   * collision
   * Method used to check if the player is coliding with the map or buildings
   * This method also checks if the player is digging and apropriately sets speed and removes dug tiles
   * @param 2D Tile array representing the map
   * @param 3 Booleans used to determine diging direction
   * @param Building array containing all building objects
   */
  public void collision(Tile [][] map, boolean digLeft, boolean digRight, boolean digDown, Building[] buildings){
    
    //Integers used to determine diferent locations on the map array around and inside the player object
    int arrayLowerY = (int)((posY + 40)/60); //Bellow player
    int arrayHigherY = (int)((posY)/60); //Above player
    int arrayLeftX = (int)((posX)/60); //Left of player
    int arrayRightX = (int)((posX + 40)/60); //Right of player
    int arrayMiddleY = (int)((posY + 20)/60); //Middle of player Y coordinate
    int arrayMiddleX = (int)((posX + 20)/60);  //Middle of player X coordinate
    //The two variables bellow are used to correct an error with hit detection when mining to the right or left
    int arrayMiddleLX = (int)((posX + 18)/60); //2 pixels to the right of the middle of the player 
    int arrayMiddleRX = (int)((posX + 22)/60); //2 pixels to the left of the middle of the player 
    
    //Collision downwards
    if ((map[arrayLowerY][arrayMiddleX] != null && playerBoundingBox.intersects(map[arrayLowerY][arrayMiddleX].boundingBox)) || ydirection == - 1){
      gravity = false; //Gravity false if player is on ground
    }else{
      gravity = true; //True if player is in air
    }
    
    //Collision Up
    if ((map[arrayHigherY][arrayMiddleX] != null && playerBoundingBox.intersects(map[arrayHigherY][arrayMiddleX].boundingBox))){
      if (ydirection != 1){
        ydirection = 0; //Stop the player if they hit a tile above them unless they are moving down
      }
    }
    
    //Collision right
    if ((map[arrayMiddleY][arrayRightX] != null && playerBoundingBox.intersects(map[arrayMiddleY][arrayRightX].boundingBox))){
      if (xdirection !=-1){
        xdirection = 0; //Stop the player if they hit a tile to their right unless they are moving left
      }
    }
    //Collision left
    if ((map[arrayMiddleY][arrayLeftX] != null && playerBoundingBox.intersects(map[arrayMiddleY][arrayLeftX].boundingBox))){
      if (xdirection != 1){
        xdirection = 0; //Stop the player if they hit a tile to their left unless they are moving right
      }
    }
    
    //The diging methods will first avoid null pointer throws with a check
    //They will then slow the players movement speed to simulate drilling
    //Once the player reaches the middle of the tile it removes the tile and adds it to cargo is aplicable
    //To prevent players diging while flying, the left and right methods will ensure the player is on the ground by checking for null
    if (digDown){
      if (map[arrayLowerY][arrayMiddleX] != null){
        ydirection = 0.1; //Set dig speed
        if (map[arrayMiddleY][arrayMiddleX] instanceof Dirt || map[arrayMiddleY][arrayMiddleX] == null) {
        }else{
          addCargo(map[arrayMiddleY][arrayMiddleX]); //Add tile to cargo array
        }
        map[arrayMiddleY][arrayMiddleX] = null; //Remove tile from map array
      }
    }
    
    if (digLeft){
      if (map[arrayLowerY][arrayMiddleX] != null){  //Check player is on ground
        if (map[arrayMiddleY][arrayLeftX] != null){
          xdirection = -0.1; //Set dig speed
          if (map[arrayMiddleY][arrayMiddleLX] instanceof Dirt || map[arrayMiddleY][arrayMiddleLX] == null) {
          }else{
            addCargo(map[arrayMiddleY][arrayMiddleLX]); //Add tile to cargo array
          }
          map[arrayMiddleY][arrayMiddleLX] = null; //Remove tile from map array
        }
      }
    }
    
    if (digRight){
      if (map[arrayLowerY][arrayMiddleX] != null){  //Check player is on ground
        if (map[arrayMiddleY][arrayRightX] != null){
          xdirection = 0.1; //Set dig speed
          if (map[arrayMiddleY][arrayMiddleRX] instanceof Dirt || map[arrayMiddleY][arrayMiddleRX] == null) {
          }else{
            addCargo(map[arrayMiddleY][arrayMiddleRX]); //Add tile to cargo array
          }
          map[arrayMiddleY][arrayMiddleRX] = null; //Remove tile from map array
        }
      }
    }
    
    //The building checks will ensure that the player can use the building using buildingUse
    //Once the player has colided with abuilding, its sets building use to false to prevent the player from using a building more than once in a single collision
    if (playerBoundingBox.intersects(buildings[0].boundingBox) && buildingUse){
      buildings[0].doAction(this);
      buildingUse = false;
    }
    if (playerBoundingBox.intersects(buildings[1].boundingBox) && buildingUse){
      buildings[1].doAction(this);
      buildingUse = false;
    }
    if (playerBoundingBox.intersects(buildings[2].boundingBox) && buildingUse){
      buildings[2].doAction(this);
      buildingUse = false;
    }
    if (playerBoundingBox.intersects(buildings[3].boundingBox) && buildingUse){
      //Stop the player movement when the new frame opens for shop
      xdirection = 0;
      ydirection = 0;
      buildings[3].doAction(this);
      buildingUse = false;
    }
    
    //If the player is clear of all buildings, building use is set back to true so that the player can once again use a building
    if ((!playerBoundingBox.intersects(buildings[3].boundingBox)) && (!playerBoundingBox.intersects(buildings[2].boundingBox)) && (!playerBoundingBox.intersects(buildings[1].boundingBox)) && (!playerBoundingBox.intersects(buildings[0].boundingBox))){
      buildingUse = true; 
    }else{
      buildingUse = false;  
    }
    
  }
}

