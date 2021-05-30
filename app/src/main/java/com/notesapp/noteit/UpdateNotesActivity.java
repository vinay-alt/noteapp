package com.notesapp.noteit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateNotesActivity extends AppCompatActivity {

    EditText title, desc;
    Button updateNotes;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notes);

        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);

        updateNotes = findViewById(R.id.UpdateNote);

        Intent i = getIntent();
        title.setText(i.getStringExtra("title"));
        desc.setText(i.getStringExtra("desc"));
        id = i.getStringExtra("id");

        updateNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(desc.getText().toString()) )
                {
                    DatabaseClass db = new DatabaseClass(UpdateNotesActivity.this);
                    db.updateNotesdata(title.getText().toString(), desc.getText().toString(), id);

                    Intent i = new Intent(UpdateNotesActivity.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(UpdateNotesActivity.this, "Both Fields are Required", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
