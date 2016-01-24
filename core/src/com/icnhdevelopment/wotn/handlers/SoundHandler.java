package com.icnhdevelopment.wotn.handlers;

import com.badlogic.gdx.audio.Sound;

/**
 * Created by kyle on 1/20/16.
 */
public class SoundHandler {

    Sound currentSound;

    public long PlaySound(Sound sound){
        if (currentSound!=null)
            currentSound.stop();
        currentSound = sound;
        return currentSound.play();
    }

    public long PlaySound(Sound sound, float volume){
        long l = PlaySound(sound);
        currentSound.setVolume(l, volume);
        return l;
    }

    public long PlayShortSound(Sound sound){
        return sound.play();
    }

    public long PlatShortSound(Sound sound, float volume){
        long l = PlayShortSound(sound);
        sound.setVolume(l, volume);
        return l;
    }

    public long PlaySoundLooping(Sound sound){
        if (currentSound!=null)
            currentSound.stop();
        currentSound = sound;
        return currentSound.loop();
    }

    public long PlaySoundLooping(Sound sound, float volume){
        long l = PlaySoundLooping(sound);
        currentSound.setVolume(l, volume);
        return l;
    }
}
