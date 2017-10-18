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
    private float speed;
    private byte Stroke;
    private byte rank;
    private byte type;
    
    private float dx;
    private float dy;
    
    private float health;
    
    protected Enemy(int r, byte Stroke, Color color, byte rank, byte type, float speed){
        super(EntityType.Enemy,0,0,r,color);
        this.speed = speed;
        this.Stroke = Stroke;
        this.rank = rank;
        
        switch(type){
            case(1): switch(rank){
                case(1):
                    x=(float)Math.random()*Game.WIDTH;
                    y=0;
                    
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
        
        if(x<0 && dx <0) dx = - dx;
        if(x>Game.WIDTH && dx > 0) dx = -dx;
        if(y<0 && dy <0) dy = - dy;
        if(y>Game.HEIGHT && dy > 0) dy = -dy;
    }
    
    @Override
    public void render(Graphics2D g){
        g.setColor(color);
        g.fillOval((int)(x-r), (int)(y-r), 2*r, 2*r);
        g.setStroke(new BasicStroke(Stroke));
        g.setColor(color.darker());
        g.drawOval((int)(x-r), (int)(y-r), 2*(r), 2*(r));
        g.setStroke(new BasicStroke(1));
        
    }
    
    public void hit(){
        health--;
    }
    
    public boolean removeFlag(){
        if(health <=0) return true;
        return false;
    }
    
    
    
}
