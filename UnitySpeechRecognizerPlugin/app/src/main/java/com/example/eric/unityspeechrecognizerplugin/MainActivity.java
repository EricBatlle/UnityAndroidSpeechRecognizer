package com.example.eric.unityspeechrecognizerplugin;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Created by Eric on 27/03/2020.
 */

public class MainActivity extends UnityPlayerActivity
{
    private static final String TAG = "SpeechRecognizer";

    //ANDROID BRIDGE
    public static String androidBridgeGameObjectName = "AndroidBridge";

    //SPEECH RECOGNIZER
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private static String gQuestion = "Hello, How can I help you?";
    private static boolean languageNotSet = true;
    private static String glanguage = "en-US";
    private static int gMaxResults = 10;
    public SpeechRecognizer sr;
    public SpeechRecognitionListener speechListener = new SpeechRecognitionListener();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        RequestRecordAudioPermission();
    }
    private void RequestRecordAudioPermission()
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
        {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO))
            {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            }
            else
            {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},20);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else
        {
            // Permission has already been granted
            Log.d(TAG, "Permission has already been granted");
        }
    }

    //ANDROID BRIDGE
    public static void SendUnityResults(String results)
    {
        UnityPlayer.UnitySendMessage(androidBridgeGameObjectName, "OnResult", results);
        Log.d(TAG, results);
    }

    //SPEECH RECOGNIZER
    public void StopListening()
    {
        sr.destroy();
        sr = null;
    }
    public void StartListening()
    {
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(speechListener);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        if (languageNotSet)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        else
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, glanguage);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, gMaxResults);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, gQuestion);

        try
        {
            sr.startListening(intent);
        }
        catch (ActivityNotFoundException a)
        {
            Log.d(TAG, a.toString());
        }
    }
    public void RestartListening()
    {
        StopListening();
        StartListening();
    }

    class SpeechRecognitionListener implements RecognitionListener {
        private ArrayList<String> resultData = new ArrayList<>();
        private List<String> dataList;
        private Boolean keepListening = false;

        @Override
        public void onReadyForSpeech(Bundle params) {
            Log.d(TAG, "onReadyForSpeech");
        }

        public void onBeginningOfSpeech() {Log.d(TAG, "onBeginningOfSpeech");}

        public void onRmsChanged(float rmsdB) {
            //Log.d(TAG, "onRmsChanged");
        }

        public void onBufferReceived(byte[] buffer) {
            Log.d(TAG, "onBufferReceived");
        }

        public void onEndOfSpeech() {
            Log.d(TAG, "onEndofSpeech");
        }

        public void onError(int error)
        {
            if(keepListening)
                RestartListening();
        }

        public void onResults(Bundle results)
        {
            StringBuilder str = new StringBuilder();
            resultData = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (resultData != null)
            {
                for (int i = 0; i < resultData.size(); i++)
                {
                    Log.d(TAG, "result " + resultData.get(i));
                    str.append(resultData.get(i));
                    str.append("~");
                }
            }
            SendUnityResults(str.toString());
            if(keepListening)
                RestartListening();
        }

        public void onPartialResults(Bundle partialResults) {
            Log.d(TAG, "onPartialResults");
        }

        public void onEvent(int eventType, Bundle params) {
            Log.d(TAG, "onEvent " + eventType);
        }
    }

}