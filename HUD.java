import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Font;
import java.awt.Color;

class HUD{ //Class used to draw the hud

  /*
   * drawHud
   * Method called by the game loop used to draw the heads up display seen by the player
   * @param Graphics object
   * @param Plyaer object to get data from
   */
  public void drawHud(Graphics g,PlayerDrill player){
    
    //Draw health bar
    double healthLength = (double)player.getHealth()/(double)player.getHealthMax(); //Length of health bar determined
    g.clearRect(10,20,200,20); //Blank rectangle drawn as background
    g.setColor(Color.RED); //Colour set to red for health
    g.fillRect(10,20,(int)(200*healthLength),20); //Red rectangle is drawn to the correct length to represent health
    g.setColor(Color.BLACK); //Colour is set to black for text and outline
    g.drawRect(10,20,200,20); //Outline is drawn
    g.drawString("Health",20 ,35); //Bar is labeled
    
    //Draw Fuel bar
    double fuelLength = (double)player.getFuel()/(double)player.getFuelMax(); //Length of fuel bar determined
    g.clearRect(10,40,200,20); //Blank rectangle drawn as background
    g.setColor(Color.GREEN); //Colour set to red for fuel (looks nicer than brown)
    g.fillRect(10,40,(int)(200*fuelLength),20); //green rectangle is drawn to the correct length to represent fuel
    g.setColor(Color.BLACK); //Colour is set to black for text and outline
    g.drawRect(10,40,200,20); //Outline is drawn
    g.drawString("Fuel",20 ,55); //Bar is labeled

    //Draw heat bar
    double heatLength = (double)player.getHeat()/(double)player.getHeatMax(); //Length of heat bar determined
    if (heatLength>1){ //Quick check to keep the heat bar from going over max
      heatLength = 1; 
    }
    g.clearRect(10,60,200,20); //Blank rectangle drawn as background
    g.setColor(Color.ORANGE); //Colour set to orange for heat
    g.fillRect(10,60,(int)(200*heatLength),20); //orange rectangle is drawn to the correct length to represent heat
    g.setColor(Color.BLACK); //Colour is set to black for text and outline
    g.drawRect(10,60,200,20); //Outline is drawn
    g.drawString("Heat",20 ,75); //Bar is labeled
    
        
    //Draw money and cargo
    g.setFont(new Font("Times New Roman", Font.BOLD, 24)); //A new font is created and set
    g.setColor(Color.BLACK); //The colour is set to black for the text
    g.drawString("Money: $" + Integer.toString(player.getMoney()),240,40); //The players money is displayed
    g.drawString("Cargo: " + Integer.toString(player.getCargo()) + "/" + Integer.toString(player.getCargoMax()), 240 , 70); //The players current cargo and capacity is displayed
    
    //Draw last mined cargo
    if (player.getCargoLast() != null){    //Check to ensure the player has mined something
      //The object is converted to a string, leaving a memory address so it is removed using a substring indexing the start of the adress, always begining with a @
      g.drawString((player.getCargoLast().toString()).substring(0,(player.getCargoLast().toString()).indexOf("@")), 60 , 100); //Mineral last mined is displayed
    }
    
  }
}