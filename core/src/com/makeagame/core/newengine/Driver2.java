package com.makeagame.core.newengine;

public class Driver2 {

    Engine2 engine;
    
    Driver2(){
    }
    
    void setEngine(Engine2 engine){
        this.engine = engine;
    }
    
    void signal(){
        
    }
    
    void render(){
        
    }
    
    void callLoop(){
        engine.mainLoop();
    }
    
    
}
