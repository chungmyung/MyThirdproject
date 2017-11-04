package com.chungmyung.ttl;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 1000 ;
    private TextToSpeech mTts ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    mTts.speak("안녕하세요", TextToSpeech.QUEUE_FLUSH,null);  // 추가는 QUEUE_ADD로
                }
            }
        });
        promptSpeechInput();
    }

    /**
     * 음성 인식 인텐트 수행
     */
    public void promptSpeechInput( ) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.KOREAN);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
               "아무 말이나 해 주세요");

        if(intent.resolveActivity(getBaseContext().getPackageManager()) != null){
            startActivityForResult(intent,REQ_CODE_SPEECH_INPUT);
        }
    }
}
