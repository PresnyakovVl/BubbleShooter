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
    
    protected Enemy(float x, float y,int r, byte Stroke, Color color, byte rank, float speed){
        super(EntityType.Enemy,x,y,r,color);
        this.speed = speed;
        this.Stroke = Stroke;
        this.rank = rank;
        
        switch(type){
            //case
        }
    }
    
    @Override
    public void update(){
        System.out.println("Неправильный вызов Player.update");
    }
    
    public void update(Input input){
        
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
}
