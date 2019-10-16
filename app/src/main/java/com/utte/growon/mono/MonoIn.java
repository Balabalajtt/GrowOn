package com.utte.growon.mono;

import com.utte.growon.mono.model.Music;

public interface MonoIn {
    void onSuccess(MonoData data);
    void onMusicSuccess(Music data);
}
