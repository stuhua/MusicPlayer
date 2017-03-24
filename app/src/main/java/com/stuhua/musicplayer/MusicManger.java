package com.stuhua.musicplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Created by liulh on 2017/3/24.
 */
public class MusicManger implements IMusic {
    private MusicService mMusicService;
    private static MusicManger ourInstance = new MusicManger();

    public static MusicManger getInstance() {
        return ourInstance;
    }

    private MusicManger() {
    }

    public void bindService(Context ctx) {
        Intent intent = new Intent(ctx, MusicService.class);
        ctx.bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    public void unBindService(Context ctx) {
        ctx.unbindService(conn);
    }

    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicService = ((MusicService.MyBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMusicService = null;
        }
    };

    @Override
    public void play() {
        mMusicService.play();
    }

    @Override
    public void pause() {
        mMusicService.pause();
    }

    @Override
    public void stop() {
        mMusicService.pause();
    }
}
