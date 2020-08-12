using UnityEngine;

public abstract class SpeechRecognizerPlugin
{
    protected string gameObjectName = "SpeechRecognizer";

    protected SpeechRecognizerPlugin(string gameObjectName = null)
    {
        this.gameObjectName = gameObjectName;
        this.SetUp();
    }
    public static SpeechRecognizerPlugin GetPlatformPluginVersion(string gameObjectName = null)
    {
        if (Application.isEditor)
            return new SpeechRecognizerPlugin_Editor(gameObjectName);
        else
        {
            #if UNITY_ANDROID
            return new SpeechRecognizerPlugin_Android(gameObjectName);
            #endif
        }
    }

    public interface ISpeechRecognizerPlugin
    {
        void OnResult(string recognizedResult);
    }

    //Features
    protected abstract void SetUp();
    public abstract void StartListening();
    public abstract void SetContinuousListening(bool isContinuous);
}
