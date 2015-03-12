package com.makeagame.core.model;

import com.makeagame.core.model.ModuleLoader;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

/*
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
*/
import org.junit.Test;


import java.lang.reflect.*;

import com.makeagame.core.model.MyModule.MyListener;
import com.makeagame.core.model.MyModule.MyModuleAccessor;


public class TestModuleLoader {

    
    @Test
    public void testAll () throws 
                ClassNotFoundException, 
                NoSuchMethodException, 
                InstantiationException, 
                IllegalAccessException, 
                InvocationTargetException,
                Exception {
        ModuleLoader ml = new ModuleLoader();
        
        ml.loadModules(new String[]{
            "com.makeagame.core.model.MyModule.MyModule",
        });
        
        
        // 找到事件接收者們後, 依序發送事件給她們
        ArrayList<MyListener> listeners = ml.getModules(MyListener.class);
        assertEquals(1, listeners.size());
        for (MyListener target: listeners) {
            target.CallMe(10);
        }
        
        // 從別的 Module 取得資料的方式
        MyModuleAccessor accessor = ml.getModule(MyModuleAccessor.class);
        assertEquals(100 ,accessor.getSqrt());
    }

}