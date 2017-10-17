/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import io.Input;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Владимир
 */
public abstract class Entity {
    
    public final EntityType type;
    
    protected float x;
    protected float y;
    protected int r;
    
    protected Color color;
    
    protected Entity(EntityType type, float x, float y, int r, Color color){
        this.type = type;
        this.x = x;
        this.y = y;
        this.r = r;
        this.color = color;
    }
    
    public abstract void update();//Input input
    public abstract void render(Graphics2D g);
    
}