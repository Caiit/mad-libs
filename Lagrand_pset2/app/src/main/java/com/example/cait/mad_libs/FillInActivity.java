package com.example.cait.mad_libs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class FillInActivity extends AppCompatActivity {

    private TextView wordsLeftTV;
    private EditText userInputET;
    private Story story;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in);

        Bundle extras = getIntent().getExtras();
        String fileName = (String) extras.get("File");

        // Create story object with input file
        InputStream file = null;
        try {
            file = this.getAssets().open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        story = new Story(file);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Create text view and user input with corresponding words
        wordsLeftTV = (TextView) findViewById(R.id.wordsLeftText);
        wordsLeftTV.setText(story.getPlaceholderRemainingCount() + " words left");
        userInputET = (EditText) findViewById(R.id.wordUser);
        userInputET.setHint(story.getNextPlaceholder());
    }

    public void nextWord(View view) {
        // Check if edit text consists text
        if (userInputET.getText().length() > 0) {
            // Save word
            story.fillInPlaceholder(userInputET.getText().toString());
            // Words left after this new word is submitted
            int wordsLeft = story.getPlaceholderRemainingCount();
            if (wordsLeft > 1) {
                wordsLeftTV.setText(wordsLeft + " words left");
                // Set edit text to empty and show new hint
                userInputET.setText("");
                userInputET.setHint(story.getNextPlaceholder());
            }
            else if (wordsLeft == 1) {
                wordsLeftTV.setText(wordsLeft + " word left");
                userInputET.setText("");
                userInputET.setHint(story.getNextPlaceholder());
                Button button = (Button) findViewById(R.id.okButton);
                button.setText("To Story");
            }
            // Go to next intent when all words are filled in
            else {
                // All words are filled in
                if (story.isFilledIn()) {
                    Intent goToStory = new Intent(this, StoryActivity.class);
                    goToStory.putExtra("Story", story);
                    startActivity(goToStory);
                    finish();
                }
                else {
                    // Something went wrong, but should not be possible to be here
                    wordsLeftTV.setText("WOOOOW NOOOH YOU KILLED THE PROGRAM!");
                }
            }
        }
        // Show toast that no text is filled in
        else {
            Toast toast = Toast.makeText(this, "Please fill in a word", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    // Override save instances to save the story after rotation
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putSerializable("story", story);
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        story = (Story) savedInstanceState.getSerializable("story");
    }
}
