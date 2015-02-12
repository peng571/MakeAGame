package com.makeagame.core.resource.process;

import com.makeagame.core.resource.InternalResource;

/**
 * 取代Reader和Decoder的概念 (在還沒有要Memory Control的時候先這樣)
 * 根據 Path 取得實際檔案並轉換成 Resource
 * 需對應 Driver 來實作
 */
@Processor(isLoader = true)
public interface Loader extends LoadProcessor {

    // 根據 Path 取得實際檔案並轉換成 Resource
    public <T extends InternalResource> T load(String path, T type);
    
    // 該路徑的資源是否能轉型成所指定的 Type (暫時無實作的必要)
    public <T extends InternalResource> boolean match(String path, T type);
    
}
