/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import io.Input;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author Владимир
 */
public class Enemy extends Entity{
    private static final byte ENEMY_SPEED_SCALE = 2;
    private static final byte ENEMY_RADIUS_SCALE = 40;
    private static final byte ENEMY_STROKE = 3;
    private static final byte ENEMY_HEALTH_SCALE = 2;
    
    private float speed;
    //private byte Stroke;
    private int rank;
    private int type;
    
    private float dx;
    private float dy;
    
    private float health;
    private boolean removeFlag;
    
    protected Enemy(int type, int rank){
        super(EntityType.Enemy,0,0,0,Color.RED);
        //this.speed = speed;
        this.speed = (float)Math.random()*ENEMY_SPEED_SCALE+rank;
        
        this.r = (int)(Math.random()*ENEMY_RADIUS_SCALE+rank);
        System.out.println(r);
        //this.Stroke = Stroke;
        this.rank = rank;
        this.type = type;
        this.removeFlag = false;
        this.health = rank*ENEMY_HEALTH_SCALE;
        
        switch(type){
            case(1): switch(rank){
                case(1):
                    
                    x=(float)Math.random()*Game.WIDTH;
                    y=0;
                    this.color = Color.GREEN;
                    double angle = Math.toRadians(Math.random()*360);
                    dx = (float)Math.sin(angle)*speed;
                    dy = (float)Math.cos(angle)*speed;
                    
            }
        }
    }
    
    @Override
    public void update(){
        x+=dx;
        y+=dy;
        removeFlag();
        if(x<0 && dx <0) dx = - dx;
        if(x>Game.WIDTH && dx > 0) dx = -dx;
        if(y<0 && dy <0) dy = - dy;
        if(y>Game.HEIGHT && dy > 0) dy = -dy;
        
    }
    
    @Override
    public void render(Graphics2D g){
        g.setColor(color);
        g.fillOval((int)(x-r), (int)(y-r), 2*r, 2*r);
        g.setStroke(new BasicStroke(ENEMY_STROKE));
        g.setColor(color.darker());
        g.drawOval((int)(x-r), (int)(y-r), 2*(r), 2*(r));
        g.setStroke(new BasicStroke(1));
        
    }
    
    public void hit(){
        health--;
    }
    
    public void removeFlag(){
        if(health <=0) removeFlag = true;
    }
    
    public boolean GetRemoveFlag(){
        return removeFlag;
    }
    
    public void SetRemoveFlag(Boolean val){
        removeFlag = val;
    }
    
    
    
}
