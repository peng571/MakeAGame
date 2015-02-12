package com.makeagame.core.resource.process;

import com.makeagame.core.resource.InternalResource;

public interface MemoryManager {

    void add(String id, InternalResource res);

    void unwanted(String id);

    void reduce();
}
