package com.epicodus.magic8ball.ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.epicodus.magic8ball.R;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecordActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Bind(R.id.spinner) Spinner mResponseSpinner;
    @Bind(R.id.recordButton) Button mRecordButton;
    @Bind(R.id.btnHome) Button mHomeButton;
    @Bind(R.id.btnListen) Button mListenButton;
    @Bind(R.id.btnStop) Button mStopButton;
    private ArrayAdapter<CharSequence> mAdapter;
    private String mSelectedResponse;
    private String mOutputFile;
    private MediaRecorder recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ButterKnife.bind(this);

        mAdapter = ArrayAdapter.createFromResource(this, R.array.responses, android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mResponseSpinner.setAdapter(mAdapter);
        mResponseSpinner.setOnItemSelectedListener(this);

        mRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOutputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+ mSelectedResponse +".3gp";

                recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                recorder.setOutputFile(mOutputFile);
                try {
                    recorder.prepare();
                    recorder.start();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("HELP", mOutputFile);
                Log.d("HELP", mSelectedResponse);
                mRecordButton.setVisibility(View.INVISIBLE);
                mStopButton.setVisibility(View.VISIBLE);
                mHomeButton.setVisibility(View.INVISIBLE);
                mListenButton.setVisibility(View.INVISIBLE);
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recorder.stop();
                mStopButton.setVisibility(View.INVISIBLE);
                mRecordButton.setVisibility(View.VISIBLE);
                mListenButton.setVisibility(View.VISIBLE);
                mHomeButton.setVisibility(View.VISIBLE);
            }
        });

        mListenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException,SecurityException,IllegalStateException {
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
        });

        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recorder.reset();
                recorder.release();

                mHomeButton.setVisibility(View.INVISIBLE);
                mListenButton.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(RecordActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mSelectedResponse = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
