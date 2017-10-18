package com.example.dmiuser.bong;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;

public class MainActivity extends AppCompatActivity implements AIListener{

    private Button listenButton;
    private TextView resultTextView;
    private AIService aiService;

    private static final int RC_HANDLE_RECORD_AUDIO_PERM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String maymun = "";

        listenButton = (Button) findViewById(R.id.listenButton);
        listenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startListening();
            }
        });
        resultTextView = (TextView) findViewById(R.id.resultTextView);

        final AIConfiguration config = new AIConfiguration("c8116198fe514854a7fd1b0e43c4e4bf",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(this, config);
        aiService.setListener(this);
    }

    private void startListening() {
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            aiService.startListening();
        } else {
            requestRecordAudioPermission();
        }
    }

    private void requestRecordAudioPermission() {

        final String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECORD_AUDIO)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_RECORD_AUDIO_PERM);
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name))
                .setMessage("Permission necessary")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this, permissions,
                                RC_HANDLE_RECORD_AUDIO_PERM);
                    }
                })
                .show();
    }

    @Override
    public void onResult(AIResponse result) {
        resultTextView.setText("result");

        if(result.getStatus().getCode() == 200) {

            String whatISay = result.getResult().getResolvedQuery();
            String whatAISay = result.getResult().getFulfillment();
        }

        String maymun = "";

    }

    @Override
    public void onError(AIError error) {
        resultTextView.setText("error");
        String maymun = "";

    }

    @Override
    public void onAudioLevel(float level) {
        String maymun = "";

    }

    @Override
    public void onListeningStarted() {
        String maymun = "";

    }

    @Override
    public void onListeningCanceled() {
        String maymun = "";

    }

    @Override
    public void onListeningFinished() {
        String maymun = "";

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != RC_HANDLE_RECORD_AUDIO_PERM) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            aiService.startListening();
            return;
        }

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name))
                .setMessage("Permission necessary")
                .setPositiveButton(android.R.string.ok, listener)
                .show();
    }
}
