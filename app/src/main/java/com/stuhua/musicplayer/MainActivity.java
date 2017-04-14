package com.stuhua.musicplayer;

import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static android.media.AudioManager.AUDIOFOCUS_GAIN;
import static android.media.AudioManager.AUDIOFOCUS_LOSS;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

public class MainActivity extends AppCompatActivity {
    private AudioManager mAudioManager;
    private ComponentName mComponentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        //获取音频焦点，音频流为STREAM_MUSIC，并设置支持Ducking
        int result = mAudioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);
        mComponentName = new ComponentName(getPackageName(), RemoteControlReceiver.class.getName());
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            //获取焦点成功，开始播放音乐
            mAudioManager.registerMediaButtonEventReceiver(mComponentName);
        }
    }

    //    短暂的焦点锁定：当计划播放一个短暂的音频时使用（比如播放导航指示）,短暂的（Transient）
//    永久的焦点锁定：当计划播放一个较长但时长可预期的音频时使用（比如播放音乐）,永久的（Permanent）
    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                //短暂失去焦点
                case AUDIOFOCUS_LOSS_TRANSIENT:
                    break;
                //重新获得焦点
                case AUDIOFOCUS_GAIN:
                    break;
                //失去焦点
                case AUDIOFOCUS_LOSS:
                    mAudioManager.unregisterMediaButtonEventReceiver(mComponentName);
                    mAudioManager.abandonAudioFocus(afChangeListener);
                    break;
                case AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    break;
                default:
                    break;
            }
        }
    };
}
