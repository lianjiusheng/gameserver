package com.ljs.gameserver.cache;

import org.ehcache.Cache;

public abstract  class DBCache<K,V> {

    private Cache<K,V> cache;

    public DBCache(){

    }

}
