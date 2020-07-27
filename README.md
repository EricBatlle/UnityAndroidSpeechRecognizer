# UnityAndroidSpeechRecognizer 🗣️
*Created by Eric Batlle Clavero*

 A simple **Android App** made with **Unity** that implements a **Speech Recognizer** using Android's native recognizer. 

**Without** the annoying **pop-up** and the option to keep the app **listening indefinitely**, not just once.

This repository is divided in 2 parts:

- Unity Project (**C#**)
- Android Plugin (**Java**)

## Example 🎬

<p>
  <img src="SpeechRecognizerImage.jpeg" alt="simple video gif" width="246" height="512"/>
</p>

*In this image it is possible to see 4 results appearing on the panel after speaking to the device "continuous text speech".*


## How to Use ⚙️

If you want to test the app, you can download the APK from [here](https://github.com/EricBatlle/UnityAndroidSpeechRecognizer/releases/download/v1.1/SpeechRecognizer.apk).

If you want to open the project and check the code, you need to have **Unity** and **AndroidStudio** installed and updated.

If you want to scratch the code:

- If you want to check **Unity
**'s project, **open the project**, select **SpeechRecognizer** scene.
Either inside the unity project or simply dragging the **.cs** classes on your editor, you have to watch on to the classes located on ``UnitySpeechRecognizer/Assets/Scripts``. 
- If you want to check the **Android Plugin** you can do it opening the solution with **AndroidStudio** or just drag the ``MainActivity.java`` class located on ``UnitySpeechRecognizerPlugin\app\src\main\java\com\example\eric\unityspeechrecognizerplugin``


#### Upgrading dependencies 📜

Keep in mind that the actual release ([v1.1](https://github.com/EricBatlle/UnityAndroidSpeechRecognizer/releases/tag/v1.1)) of the plugin works with **AndroidX** dependencies. That means that is targeting **Android 9** (API level 28).

If you want to target older versions I recommend to use the old version of the plugin ([v1.0]((https://github.com/EricBatlle/UnityAndroidSpeechRecognizer/releases/tag/v1.0))) that works with **Android Support v4**. You can download the old plugin source code and APK from [here](https://github.com/EricBatlle/UnityAndroidSpeechRecognizer/releases/tag/v1.0).