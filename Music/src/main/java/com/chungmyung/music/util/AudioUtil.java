package com.chungmyung.music.util;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;

/**
 * Created by user on 2017-10-14.
 */

public class AudioUtil {

    public static MediaPlayer newInstance() {
        MediaPlayer mediaPlayer = new MediaPlayer() ;

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build();
            mediaPlayer.setAudioAttributes(attributes);
        } else {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        return  mediaPlayer;
    }
}
