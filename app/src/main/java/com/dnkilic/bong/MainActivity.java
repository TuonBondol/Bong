package com.dnkilic.bong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dnkilic.bong.speaker.Speaker;
import com.dnkilic.bong.speaker.SpeakerUtteranceListener;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;

public class MainActivity extends AppCompatActivity implements AIListener,
        SpeakerUtteranceListener {

    private TextView resultTextView;
    private AIService aiService;
    private Speaker mSpeaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button listenButton = (Button) findViewById(R.id.listenButton);
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

        mSpeaker = new Speaker(this);
    }

    private void startListening() {
        aiService.startListening();
    }

    @Override
    public void onResult(AIResponse result) {
        resultTextView.setText("result");

        if(result.getStatus().getCode() == 200) {

            String whatISay = result.getResult().getResolvedQuery();
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
    public void onStartTTS(String announce) {

    }

    @Override
    public void onDoneTTS() {

    }

    @Override
    public void onErrorTTS() {

    }
}
