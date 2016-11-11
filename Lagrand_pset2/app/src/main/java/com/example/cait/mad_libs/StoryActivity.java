package com.example.cait.mad_libs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StoryActivity extends AppCompatActivity {

    private Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        // Get the story and show it to the user
        Bundle extras = getIntent().getExtras();
        story = (Story) extras.get("Story");
        TextView storyText = (TextView) findViewById(R.id.storyText);
        storyText.setText(story.toString());
    }

    public void playAgain(View view) {
        finish();
    }
}
