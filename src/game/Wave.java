/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import java.awt.Graphics2D;
import utils.Time;

/**
 *
 * @author Владимир
 */
public class Wave {
    
    private static final String WAVE_TEXT = " В О Л Н А -";
    private static final long WAVE_DELAY = 5000;
    private static final  byte WAVE_MULTIPLIER = 5;
    private static final double TIMER_DIVIDER = WAVE_DELAY / 180;
    
    
    
    private byte waveNum;
    
    private long waveTimer;
    //private long waveDelay;
    private long waveTimerDiff;
    
    public Wave(){
        this.waveTimer = 0;
        this.waveNum = 1;
        //this.waveDelay = WAVE_DELAY;
        this.waveTimerDiff = 0;
        
        
    }
    
    public void update(){
        if(Game.enemies.size()==0 && waveTimer == 0){
            waveTimer = Time.get();
        }
        if(waveTimer>0){
            waveTimerDiff += (Time.get()-waveTimer)/1000000;
            waveTimer = Time.get();
        }
        if(waveTimerDiff>WAVE_DELAY){
            createEnemies();
            waveTimer = 0;
            waveTimerDiff = 0;
        }
    }
    
    public boolean showWave(){
        if(waveTimer!=0) 
            return true;
         else           
            return false;
    }    
    
    public void render(Graphics2D g){
        
        double alpha = waveTimerDiff / TIMER_DIVIDER;
        alpha = 255 * Math.sin(Math.toRadians(alpha));
        if(alpha< 0) alpha = 0;
        if(alpha>255) alpha = 255;
        g.setColor(new Color(255,255,255,(int)alpha));
        g.drawString(WAVE_TEXT+waveNum, Game.WIDTH/2, Game.HEIGHT/2);
    }
    
    public void createEnemies(){
        
        int enemyCount = waveNum*WAVE_MULTIPLIER;
        if(waveNum <= 10){
            while(enemyCount>0){
                System.out.println(waveNum);
                int type = 1;
                int rank = 1;
                Game.enemies.add(new Enemy(type,rank));
                enemyCount -=type*rank;
            }
            waveNum++;
        }
    }
}
