class Clock { //Class to check the amount of time that has passed
  
  //Initialize variables
  long elapsedTime; //Time that has elapsed
  long lastTimeCheck; //Last time the time was checked
  long lastTimeCheckFuel; //Last time the fuel was checked
  long lastTimeCheckHeat; //Last time the heat was checked

  //Constructor for the Clock class
  public Clock() { 
    //Take the last time checked from the computer and set these values into the variables
    lastTimeCheck=System.nanoTime(); 
    lastTimeCheckFuel=System.nanoTime();
    lastTimeCheckHeat=System.nanoTime();
    elapsedTime=0; //Reset the elapsed time
  }
  
  /*
   * update 
   * This method updates the time check and calcuates how much time has passed since the last check. Returns nothing
   */
  public void update() {
  long currentTime = System.nanoTime(); //Take the current time from the computer
  elapsedTime=currentTime - lastTimeCheck; //Subtract the current time from the last time to see how much time has passed
  lastTimeCheck=currentTime; //Set the current time as the last time checked for the next check
  }
  
  /*
   * update 
   * This method returns the elapsed time in miliseconds
   * @return the elapsed time in miliseconds
   */
  public double getElapsedTime() {
    return elapsedTime/1.0E9;
  }
  
  /*
   * checkFuel 
   * This method checks how much fuel has been consumed
   * @return boolean
   */
  public boolean checkFuel() {
    long currentTime = System.nanoTime(); //Take the current time from the computer
    if ((currentTime - lastTimeCheckFuel) >= 500000000){ //If more than 500000000 nano seconds passed
      lastTimeCheckFuel = System.nanoTime(); //Set the current time as the last time checked for the next check
      return true; //Return true and deduct fuel
    }
    return false; //Return false
  }

  /*
   * checkHeat 
   * This method checks how much damage is taken if the player is over their heat resistance limit
   * @return boolean 
   */
  public boolean checkHeat() {
    long currentTime = System.nanoTime(); //Take the current time from the computer
    if ((currentTime - lastTimeCheckHeat) >= 100000000){ //If more than 100000000 nano seconds passed
      lastTimeCheckHeat = System.nanoTime(); //Set the current time as the last time checked for the next check
      return true; //Return true and take damage
    }
    return false; //Return false
  }
  
}