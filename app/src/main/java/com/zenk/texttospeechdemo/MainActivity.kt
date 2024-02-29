package com.zenk.texttospeechdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.zenk.texttospeechdemo.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding : ActivityMainBinding? = null
    private var tts : TextToSpeech? = null



    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)

        tts = TextToSpeech(this, this)

        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        binding?.btnSpeak?.setOnClickListener(){

            speak()

        }
    }

    private fun speak()
    {
        if(binding?.etSpokenText?.text!!.isEmpty())
        {
            Toast.makeText(this@MainActivity,
            "Please enter text for text to speech",
            Toast.LENGTH_SHORT)
                .show()
        }
        else
        {
            speakOut(binding?.etSpokenText?.text.toString())
        }
    }

    override fun onInit(status : Int) {
        if(status == TextToSpeech.SUCCESS)
        {
            val result = tts!!.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("TTS", "The language specified is not supported!")
            }
        }
        else
        {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun speakOut(text : String)
    {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null," ")
    }

    override fun onDestroy() {
        super.onDestroy()

        if(tts != null)
        {
            tts?.stop()
            tts?.shutdown()
        }

        binding = null
    }
}