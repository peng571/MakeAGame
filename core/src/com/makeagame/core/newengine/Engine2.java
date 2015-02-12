package com.makeagame.core.newengine;

public class Engine2 {

    Driver2 driver;
    
    Engine2(Driver2 driver){
        this.driver = driver;
        this.driver.setEngine(this);
        
    }
    
    
    
    
    void mainLoop(){
        
        driver.signal();
        
        driver.render();
        
    }
    
}


