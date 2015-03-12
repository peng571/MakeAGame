package com.makeagame.core.model;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONArray;
//import java.lang.reflect.Method;
//import java.lang.reflect.Constructor;
import java.lang.reflect.*;

public class ModuleLoader {
    
    
    
    private ArrayList<Object> modules;
    
    
    // 傳回第一個符合該介面的 Module
    // 通常用來跨 Module 溝通
    // 例如取得存放在別的 Module 的資料
    public <T> T getModule(Class<T> inter) throws Exception {
        ArrayList<T> results = new ArrayList<T>();
        for (Object m : modules) {
            Class<?>[] inters = m.getClass().getInterfaces();
            for (Class<?> other : inters) {
                if (inter.isAssignableFrom(other)) {
                    return (T) m;
                }
            }
        }
        throw new Exception("Module with Interface not found");
    }
    
    // 傳回所有符合該介面的Module
    // 通常用在群播
    public <T> ArrayList<T> getModules(Class<T> inter) {
        System.out.println(inter.getCanonicalName());
        ArrayList<T> results = new ArrayList<T>();
        for (Object m : modules) {
            Class<?>[] inters = m.getClass().getInterfaces();
            for (Class<?> other : inters) {
                if (inter.isAssignableFrom(other)) {
                    //System.out.println(m);
                    results.add((T) m);
                }
            }
        }
        return results;
        //System.out.println(results);
        //return (T[]) results.toArray(new T[results.size()]);
    }
    
    
    // TODO: 支援更複雜的型別
    // TODO: 必須是某種介面的方法才可以支援send
    public void sendCommand(String name, JSONArray args) throws 
                JSONException, 
                IllegalAccessException, 
                InvocationTargetException {
        // 建構參數型別
        Object[] params = new Object[args.length()];
        for (int i=0; i<args.length(); i++) {
            params[i] = args.get(i);
        }
        
        for (Object m : modules) {
            Method[] methods = m.getClass().getMethods();
            for (Method method : methods) {
                if (method.getName() == name) {
                    method.invoke(m, params);
                }
            }
        }
    }
    
    // 動態載入java類別, 並且初始化 Module
    // Module必須有空引數建構子
    // ex: loadModules(new String[]{
    //  "com.icebreaker.officegame.BattleSystem",
    //  "com.icebreaker.officegame.MoneySystem"
    // });
    public void loadModules(String[] urls) throws 
                ClassNotFoundException, 
                NoSuchMethodException, 
                InstantiationException, 
                IllegalAccessException, 
                InvocationTargetException {
        modules = new ArrayList<Object>();
        for (String url : urls) {
            //try {
                //Class<Module> klass = /*(Class<Module>)*/ Class.forName(url).asSubclass(Module.class)
                Class klass = Class.forName(url);
                Constructor ctor = klass.getDeclaredConstructor(/*Class<?>... parameterTypes*/);
                Object module = ctor.newInstance();
                modules.add(module);
            /*
            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
            } catch (NoSuchMethodException ex) {
                System.out.println(ex);
            } catch (InstantiationException ex) {
                System.out.println(ex);
            } catch (IllegalAccessException ex) {
                System.out.println(ex);
            } catch (InvocationTargetException ex) {
                System.out.println(ex);
            }
            */
        }
    }






}