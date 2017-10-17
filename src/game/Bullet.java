/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Владимир
 */
public class Bullet extends Entity{
    
    private float speed;
    
    protected Bullet(float x, float y, int r, Color color, float speed){
        super(EntityType.Bullet,x,y,r,color);
        this.speed = speed;
    }
    
    @Override
    public void update(){
        if(!removeFlag()){
            y-=speed;
        }
        
    }
    @Override
    public void render(Graphics2D g){
        if(!removeFlag()){
            g.setColor(color);
            g.fillOval((int)x, (int)y, r, 2*r);
            //System.out.println("пиу пиу");
        }
    }
    
    public boolean removeFlag(){
        if(y<0 || y>Game.HEIGHT || x<0 || x>Game.WIDTH)
           return true;
        return false;
    }
}
