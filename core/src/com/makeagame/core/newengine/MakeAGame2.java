package com.makeagame.core.newengine;

public class MakeAGame2 {

    
    Driver2 driver;
    Engine2 engine;
    
    ResourceSystem2 resource = ResourceSystem2.get();
    
    MakeAGame2(){
    
        driver = new Driver2();
        engine = new Engine2(driver);
        
        resource.addProcessor(new Process2());
        
        
    }
    
    Driver2 getDriver(){
        return driver;
    }
    
}
