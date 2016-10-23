package com.example.lucky.alarmclock;

/**
 * Created by lucky on 28/09/16.
 */

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    private static Ringtone ringtone;
    private static MediaPlayer mediaPlayer;

    @Override
    public void onReceive(final Context context, Intent intent) {


        if(MainActivity.getSongTheme() != null){
            String filePath = "file://"+setSong(MainActivity.getSongTheme());
            Uri myUri = Uri.parse(filePath);
            Log.i("APPLI", "myURI : "+myUri.toString());

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            try {
                mediaPlayer.setDataSource(context, myUri);
                mediaPlayer.setLooping(true);
                mediaPlayer.prepare();
            } catch (IllegalArgumentException e) {
                Toast.makeText(context, "You might not set the URI correctly! 1", Toast.LENGTH_LONG).show();
            } catch (SecurityException e) {
                Toast.makeText(context, "You might not set the URI correctly! 2", Toast.LENGTH_LONG).show();
            } catch (IllegalStateException e) {
                Toast.makeText(context, "You might not set the URI correctly! 3", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

            setMediaPlayer();
        } else {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            ringtone = RingtoneManager.getRingtone(context, uri);

            setRingtone();
        }
    }

    public static void setRingtone(){
        if(ringtone.isPlaying()){
            MainActivity.getTextView2().setText("Nice =)");
            ringtone.stop();
        } else {
            MainActivity.getTextView2().setText("Enough Rest. Do Work Now!");
            ringtone.play();
        }
    }

    public static void setMediaPlayer(){
        if(mediaPlayer.isPlaying()){
            MainActivity.getTextView2().setText("Nice =)");
            mediaPlayer.stop();
        } else {
            MainActivity.getTextView2().setText("Enough Rest. Do Work Now!");
            mediaPlayer.start();
        }
    }

    public String setSong(String s) {
        String theme = Environment.getExternalStorageDirectory()+"/Music/AlarmClock/" + s;
        Log.i("APPLI", "path : "+theme);

        ArrayList<File> listFile = new ArrayList<File>();
        File repertoire = new File(theme);
        if(repertoire.isDirectory()){
            Log.i("APPLI", "Repertoire : OK");
            for (File file : repertoire.listFiles()) {
                listFile.add(file);
                Log.i("APPLI", "file : "+file.getName());
            }
        } else {
            Log.i("APPLI", "Repertoire : NOK");
        }

        SecureRandom random = new SecureRandom();
        String song = listFile.get(random.nextInt(listFile.size())).toString();

        Log.i("APPLI", "Nb Song : "+listFile.size());
        Log.i("APPLI", "Song choisit : "+song);

        return song;
    }

}