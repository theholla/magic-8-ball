package com.epicodus.magic8ball.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.epicodus.magic8ball.R;
import com.epicodus.magic8ball.models.Magic8Ball;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn8) Button m8Button;
    @Bind(R.id.responseText) TextView mResponseText;
    private Magic8Ball m8Ball;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        m8Ball = new Magic8Ball();

        m8Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResponseText.setText(m8Ball.getResponse());
            }
        });
    }
}
