package com.epicodus.magic8ball.ui;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.epicodus.magic8ball.R;
import com.epicodus.magic8ball.models.Magic8Ball;
import com.epicodus.magic8ball.models.ShakeListener;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn8) Button m8Button;
    @Bind(R.id.responseText) TextView mResponseText;
    private Magic8Ball m8Ball;
    private ShakeListener mShaker;
    private String mOutputFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        m8Ball = new Magic8Ball(getResources().getStringArray(R.array.responses));

        m8Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playResponse();
            }
        });

        final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        mShaker = new ShakeListener(this);
        mShaker.setOnShakeListener(new ShakeListener.OnShakeListener () {
            public void onShake() {
                vibe.vibrate(100);
                playResponse();
            }
        });
    }

    private void playResponse() {
        String response = m8Ball.getResponse();
        mResponseText.setText(response);
        mOutputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+response+".3gp";

        MediaPlayer m = new MediaPlayer();

        try {
            m.setDataSource(mOutputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            m.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        m.start();
    }

    @Override
    public void onResume()
    {
        mShaker.resume();
        super.onResume();
    }
    @Override
    public void onPause()
    {
        mShaker.pause();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_record) {
            Intent intent = new Intent(MainActivity.this, RecordActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}