package com.example.updatetimehander;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tvTimer;
    private Button btnStart;
    private int seconds = 0;
    private boolean isRunning = false;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTimer = findViewById(R.id.tvTimer);
        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    isRunning = true;
                    startTimer();
                    btnStart.setEnabled(false); // Không cho bấm nhiều lần
                }
            }
        });
    }

    private void startTimer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    try {
                        Thread.sleep(1000); // Đợi 1 giây
                        seconds++; // Tăng biến đếm giây

                        // Gửi Runnable lên UI Thread để cập nhật TextView
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                tvTimer.setText(String.valueOf(seconds));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
