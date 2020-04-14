package com.example.mask;

// Import Standard Android Libraries
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

// Standard library for HTTP requests
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.speech.RecognizerIntent.EXTRA_PREFER_OFFLINE;

// Standard App Activity
public class MainActivity extends AppCompatActivity {

    // Set speech code request, so that message can be filtered by code (but I do not use it in this code)
    private static final int SPEECH_REQUEST_CODE = 0;

    // Constant with IP number
    private static final String ESP_CONTROLLER_IP = "192.168.4.1";

    // Queue for sending requests to the server (ESP controller)
    private RequestQueue queue;

    //On action create (when MainActivity is started)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call super method which should be done on creation of any activity
        super.onCreate(savedInstanceState);

        // Set Activity View (which an App page with pictures)
        setContentView(R.layout.activity_main);

        // Initialize queue for requests
        queue = Volley.newRequestQueue(this);

        // Register button events
        initEmotionButtons();

        // Initialize speech button as a constant, find corresponding button on the view
        final FloatingActionButton speechButton = findViewById(R.id.speech_button_id);

        // On button click, call display speech organizer
        speechButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                displaySpeechRecognizer();
            }
        });

        // Makes sure screen does not go to sleep mode
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    // For each image button: find it in the view, set on click event to send request
    // to the server with corresponding emotion name
    private void initEmotionButtons() {
        final ImageButton angryButton = findViewById(R.id.angry_button_id);
        angryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.add(getRequest(Emotion.ANGRY));
            }
        });

        final ImageButton coffeeButton = findViewById(R.id.coffee_button_id);
        coffeeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.add(getRequest(Emotion.COFFEE));
            }
        });

        final ImageButton cozyButton = findViewById(R.id.cozy_button_id);
        cozyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.add(getRequest(Emotion.COZY));
            }
        });

        final ImageButton cryingButton = findViewById(R.id.crying_button_id);
        cryingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.add(getRequest(Emotion.CRYING));
            }
        });

        final ImageButton excitingButton = findViewById(R.id.exciting_button_id);
        excitingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.add(getRequest(Emotion.EXCITING));
            }
        });

        final ImageButton greatButton = findViewById(R.id.great_button_id);
        greatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.add(getRequest(Emotion.GREAT));
            }
        });

        final ImageButton grumpyButton = findViewById(R.id.grumpy_button_id);
        grumpyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.add(getRequest(Emotion.GRUMPY));
            }
        });

        final ImageButton happyButton = findViewById(R.id.happy_button_id);
        happyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.add(getRequest(Emotion.HAPPY));
            }
        });

        final ImageButton kissButton = findViewById(R.id.kiss_button_id);
        kissButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.add(getRequest(Emotion.KISS));
            }
        });

        final ImageButton loveItButton = findViewById(R.id.love_it_button_id);
        loveItButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.add(getRequest(Emotion.LOVE_IT));
            }
        });

        final ImageButton loveYouBearButton = findViewById(R.id.love_you_bear_button_id);
        loveYouBearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.add(getRequest(Emotion.LOVE_YOU_BEAR));
            }
        });

        final ImageButton maskButton = findViewById(R.id.mask_button_id);
        maskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.add(getRequest(Emotion.MASK));
            }
        });

        final ImageButton noButton = findViewById(R.id.no_button_id);
        noButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.add(getRequest(Emotion.NO));
            }
        });

        final ImageButton pleaseButton = findViewById(R.id.please_button_id);
        pleaseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.add(getRequest(Emotion.PLEASE));
            }
        });

        final ImageButton poopButton = findViewById(R.id.poop_button_id);
        poopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.add(getRequest(Emotion.POOP));
            }
        });

        final ImageButton sleepyButton = findViewById(R.id.sleepy_button_id);
        sleepyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.add(getRequest(Emotion.SLEEPY));
            }
        });

        final ImageButton veryFunnyButton = findViewById(R.id.very_funny_button_id);
        veryFunnyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.add(getRequest(Emotion.VERY_FUNNY));
            }
        });

        final ImageButton veryGoodButton = findViewById(R.id.very_good_button_id);
        veryGoodButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue.add(getRequest(Emotion.VERY_GOOD));
            }
        });
    }

    // Preparing to make a request
    // Create request object for selected emotion
    private StringRequest getRequest(Emotion emotion) {
        return new StringRequest(Request.Method.GET, "http://" + ESP_CONTROLLER_IP + "/emotion/" + emotion,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        });
    }

    // Create an intent that can start the Speech Recognizer activity
    // When user click speech recognition button we start waiting for results from speech recognizer
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(EXTRA_PREFER_OFFLINE, true);
        // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    // This callback is invoked when the Speech Recognizer returns.
    // This is where the intent is processed and extraction of the speech text from the intent is happened.
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        // Make sure that result that we got came from speech recognizer and it is successful
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            // Data that we need can be found in extra response list
            // It has a sentence which user said
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            // Assign this variable with a text that user has spoken
            String spokenText = results.get(0);

            // Try to match emotion with words used in the sentence
            // If found send request to server to display a picture
            Emotion emotion = Emotion.getEmotionFromText(spokenText);
            if (emotion != null) {
                queue.add(getRequest(emotion));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

// References:
// 1) Send simple request from android: https://developer.android.com/training/volley/simple
// 2) General Developer guide: https://developer.android.com/guide
// 3) Remove space in grid layout https://stackoverflow.com/questions/42381386/how-to-remove-space-in-between-android-grid-layout
