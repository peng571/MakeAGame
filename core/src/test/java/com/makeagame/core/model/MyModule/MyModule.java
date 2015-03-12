package com.makeagame.core.model.MyModule;

public class MyModule implements MyListener, MyModuleAccessor{

    private int b;
    
    public int getSqrt() {
        return b;
    }
    public void CallMe(int a) {
        b = a*a;
    }
    
}