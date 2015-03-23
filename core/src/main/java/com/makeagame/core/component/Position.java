package com.makeagame.core.component;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    
    public double getDistance(Position otherP){
        return getDistance(this, otherP);
    }
    
    public static double getDistance(Position p1, Position p2){
        if(p1.equals(p2)){ return 0;}
        
        if(p1.x == p2.x){ return Math.abs(p1.y - p2.y); } 
        
        if(p1.y == p2.y){ return Math.abs(p1.x - p2.x); }
        
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }
    
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Position){
            Position p2 = (Position)obj;
            if(this.x == p2.x && this.y == p2.y){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
        return "{ x=" + x + ", y=" + y + "}";
    }
}
