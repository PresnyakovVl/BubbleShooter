/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Владимир
 */
public class Time {
    public static final long SECOND = 1000000000l;
    public static long get(){
        return System.nanoTime();
    }
}
