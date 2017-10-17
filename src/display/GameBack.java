/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import java.awt.Color;
import java.util.Arrays;

/**
 *
 * @author Владимир
 */
public class GameBack {
    
    //fields
    private int color;
    
    //Constructor
    public GameBack(){
        color = 0xff0000f0;
    }
    
    public void update(){
        
    
    }
    
    public void render(int[] bufferData){
        Arrays.fill(bufferData, color);
    }
}
