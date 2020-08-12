using UnityEngine;

public class SpeechRecognizerPlugin_Android : SpeechRecognizerPlugin
{
    public string javaClassPackageName = "com.example.eric.unityspeechrecognizerplugin.SpeechRecognizerFragment";
    private AndroidJavaClass javaClass = null;
    AndroidJavaObject instance = null;

    public SpeechRecognizerPlugin_Android(string gameObjectName) : base(gameObjectName) { }

    public override void StartListening()
    {
        instance.Call("StartListening");
    }

    public override void SetContinuousListening(bool isContinuous)
    {
        instance.Call("SetContinuousListening", isContinuous);
    }

    protected override void SetUp()
    {
        Debug.Log("SetUpAndroid " + gameObjectName);
        javaClass = new AndroidJavaClass(javaClassPackageName);
        javaClass.CallStatic("SetUp", gameObjectName);
        instance = javaClass.GetStatic<AndroidJavaObject>("instance");
    }
}