package testunit;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.makeagame.core.exception.ResourceNotReadyException;
import com.makeagame.core.resource.Resource;
import com.makeagame.core.resource.plugin.LibgdxProcessor;
import com.makeagame.core.resource.plugin.LibgdxResText;
import com.makeagame.core.resource.process.RegisterFinder;

public class TestLibgdxProcess {

    RegisterFinder finder;
    

    @Test
    public void test() {
        /*
         * TEST FINDER
         */
        System.out.println("test finder");
        
        finder = new RegisterFinder();
        try {
            /* IMAGE */
            finder.register("image", packageData("image/bird.png", 0, 0, 128, 128));

            /* ATTRIBUTE */
            finder.register("attribute", packageData("data/bird.txt", "atr"));
    
            /* SOUND */
            finder.register("sound", packageData("sound/button-50.mp3", "snd"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        JSONObject json = finder.find("attribute");
        System.out.println(json.toString());
        
        
        // ???
//        String path = "image/bird.png";
//        Texture texture = new Texture(Gdx.files.classpath("../resource/" + path));
//        LibgdxResImage libgdxImage = new LibgdxResImage(path);
        
        
        /*
         * TEST PROCESSOR
         */
        System.out.println("test processor");
        
        LibgdxProcessor process = new LibgdxProcessor(finder);

        Resource<LibgdxResText> res = new Resource<LibgdxResText>("attribute");
        process.handleResource(res);

        System.out.println("res ID" + res.getID());
        try {
            System.out.println("res context " + res.getPayload().getText());
        } catch (ResourceNotReadyException e) {
            e.printStackTrace();
        }

    }
    
    
    
    static private JSONObject packageData(String path) throws JSONException {
        return new JSONObject().put("path", path);
    }

    static private JSONObject packageData(String path, int x, int y, int w, int h) throws JSONException {
        return packageData(path).put("type", "img").put("x", x).put("y", y).put("w", w).put("h", h);
    }

    static private JSONObject packageData(String path, String type) throws JSONException {
        return packageData(path).put("type", type);
    }

}
