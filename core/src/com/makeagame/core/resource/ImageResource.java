//package com.makeagame.core.resource;
//
//import org.json.JSONObject;
//
//import com.makeagame.core.view.RenderEvent;
//
//public class ImageResource extends Resource {
//
//    ImageResource(String id) {
//        super(id);
//        this.type = RenderEvent.IMAGE;
//    }
//
//    int centerX, centerY;
//    int srcX, srcY, srcW, srcH;
//    String file;
//    
//    public ImageResource src(int srcX, int srcY, int srcW, int srcH) {
//        this.srcX = srcX;
//        this.srcY = srcY;
//        this.srcW = srcW;
//        this.srcH = srcH;
//        return this;
//    }
//
//    public ImageResource src(int srcW, int srcH) {
//        return src(0, 0, srcW, srcH);
//    }
//    
//    public int[] getWH() {
//        return new int[] {srcW, srcH};
//    }
//    
//    public int[] getSrcDim() {
//        return new int[] {srcX, srcY, srcW, srcH};
//    }
//
////    @Override
////    public void setAttr(JSONObject json){
////        this.srcX = json.optInt("x");
////        this.srcY = json.optInt("y");
////        this.srcW = json.optInt("w", -1);
////        this.srcH = json.optInt("h", -1);
////        
////        // TODO other...
////        
////    }
//
//
////
////        public Resource dst(int dstX, int dstY, int dstW, int dstH) {
////            this.x = dstX;
////            this.y = dstY;
////            this.w = dstW;
////            this.h = dstH;
////            return this;
////        }
////
////        public Resource center(int x, int y) {
////            this.centerX = x;
////            this.centerY = y;
////            return this;
////        }
//        
//}
