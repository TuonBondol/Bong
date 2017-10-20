package com.dnkilic.bong.speaker;

public interface SpeakerUtteranceListener {
    void onStartTTS(String announce);
    void onDoneTTS();
    void onErrorTTS();
}