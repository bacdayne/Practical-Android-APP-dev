package com.example.simplevideoplayer_7;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.MediaController;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_VIDEO_PICK = 1;
    private static final int REQUEST_PERMISSION = 100;

    private VideoView videoView;
    private EditText edtVideoUrl;
    private Button btnChooseVideo, btnPlayUrl;
    private Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);
        edtVideoUrl = findViewById(R.id.edtVideoUrl);
        btnChooseVideo = findViewById(R.id.btnChooseVideo);
        btnPlayUrl = findViewById(R.id.btnPlayUrl);

        // Kiểm tra quyền truy cập
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            }
        }

        // Xử lý chọn video từ thiết bị
        btnChooseVideo.setOnClickListener(v -> {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                chooseVideoFromGallery();
            } else {
                Toast.makeText(this, "Bạn chưa cấp quyền truy cập thư viện!", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý phát video từ URL
        btnPlayUrl.setOnClickListener(v -> playVideoFromUrl());
    }

    private void chooseVideoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_VIDEO_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VIDEO_PICK && resultCode == RESULT_OK && data != null) {
            videoUri = data.getData();
            playVideo(videoUri);
        }
    }

    private void playVideoFromUrl() {
        String videoUrl = edtVideoUrl.getText().toString().trim();
        if (!videoUrl.isEmpty()) {
            Uri uri = Uri.parse(videoUrl);
            playVideo(uri);
        } else {
            Toast.makeText(this, "Vui lòng nhập URL video!", Toast.LENGTH_SHORT).show();
        }
    }

    private void playVideo(Uri uri) {
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
    }
}
