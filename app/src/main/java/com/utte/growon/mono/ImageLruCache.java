package com.utte.growon.mono;

import android.graphics.drawable.BitmapDrawable;
import android.support.v4.util.LruCache;

public class ImageLruCache {

    // 缓存已经下好的图片 在内存达到一定值时会将最近最少使用的图片遗弃
    private LruCache<String, BitmapDrawable> mMemoryCache;

    public ImageLruCache() {
        initCache();
    }

    private void initCache() {
        // 应用可用最大内存的一部分
        int cacheSize = (int) Runtime.getRuntime().maxMemory() / 2;
        mMemoryCache = new LruCache<String, BitmapDrawable>(cacheSize) {
            @Override
            protected int sizeOf(String key, BitmapDrawable drawable) {
                return drawable.getBitmap().getByteCount();
            }
        };
    }

    // 放缓存
    public void addCache(String key, BitmapDrawable drawable) {
        if (getCache(key) == null) {
            mMemoryCache.put(key, drawable);
        }
    }

    // 拿缓存
    public BitmapDrawable getCache(String key) {
        return mMemoryCache.get(key);
    }

    // 删缓存
    public BitmapDrawable delCache(String key) {
        return mMemoryCache.remove(key);
    }

}
