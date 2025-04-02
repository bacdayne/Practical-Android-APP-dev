package com.example.ex8_accelerometerapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView accelX, accelY, accelZ;
    private ImageView ball;

    // Các biến để lưu giá trị gia tốc
    private float accelXValue = 0, accelYValue = 0, accelZValue = 0;
    private float ballX = 0, ballY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các đối tượng
        accelX = findViewById(R.id.accelX);
        accelY = findViewById(R.id.accelY);
        accelZ = findViewById(R.id.accelZ);
        ball = findViewById(R.id.ball);

        // Lấy SensorManager và Sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Đăng ký SensorEventListener
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Lấy giá trị gia tốc từ event
            accelXValue = event.values[0];
            accelYValue = event.values[1];
            accelZValue = event.values[2];

            // Cập nhật các TextView để hiển thị gia tốc theo trục x, y, z
            accelX.setText("X: " + accelXValue);
            accelY.setText("Y: " + accelYValue);
            accelZ.setText("Z: " + accelZValue);

            // Tính toán sự thay đổi vị trí của hình ảnh
            ballX += accelXValue / 10; // Chia cho 10 để giảm tốc độ
            ballY -= accelYValue / 10; // Chia cho 10 để giảm tốc độ

            // Cập nhật vị trí của ImageView
            ball.setX(ballX);
            ball.setY(ballY);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Xử lý khi độ chính xác của cảm biến thay đổi, không cần thiết cho bài này
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Đăng ký listener khi Activity được hiển thị
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Hủy đăng ký listener khi Activity bị tạm dừng
        sensorManager.unregisterListener(this);
    }
}
