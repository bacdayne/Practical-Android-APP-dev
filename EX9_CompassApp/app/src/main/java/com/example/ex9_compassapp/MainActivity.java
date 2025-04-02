package com.example.ex9_compassapp;

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
    private Sensor magnetometer;

    private float[] gravity;
    private float[] geomagnetic;

    private ImageView compassImage;
    private TextView angleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compassImage = findViewById(R.id.compassImage);
        angleText = findViewById(R.id.angleText);

        // Khởi tạo SensorManager và các cảm biến
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Đăng ký các cảm biến
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Hủy đăng ký khi không sử dụng
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravity = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = event.values;
        }

        if (gravity != null && geomagnetic != null) {
            // Tính ma trận xoay
            float[] R = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, null, gravity, geomagnetic);
            if (success) {
                // Lấy hướng của la bàn
                float[] orientation = new float[3];
                SensorManager.getOrientation(R, orientation);

                // Hướng Bắc tính từ góc lệch (hướng azimuth)
                float azimuth = orientation[0];  // Azimuth là góc lệch so với hướng Bắc
                float degree = (float) Math.toDegrees(azimuth);  // Chuyển từ radian sang độ

                // Cập nhật góc lệch lên TextView
                angleText.setText("Angle: " + degree + "°");

                // Xoay la bàn theo hướng Bắc
                compassImage.setRotation(-degree);  // Xoay ImageView theo hướng Bắc
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Không cần xử lý khi độ chính xác thay đổi
    }
}
