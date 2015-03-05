/*
Copyright (c) 2015, Cyan Worm - cyan-worm.blogspot.com
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
1. Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution.
3. All advertising materials mentioning features or use of this software
must display the following acknowledgement:
This product includes software developed by the Cyan Worm Development Team.
4. Neither the name of the Cyan Worm Development Team nor the
names of its contributors may be used to endorse or promote products
derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY CYAN WORM ''AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL CYAN WORM BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

Author/s: Jannieca Camba
          Patrick Leiniel Domingo
          Joni Marie Jimenez

“This is a course requirement for CS 192 Software Engineering II
under the supervision of Asst. Prof. Ma. Rowena C. Solamo of the
Department of Computer Science, College of Engineering, University of the Philippines, Diliman
for the AY 2014-2015”.

Code History:
02/13/15 - Jannieca Camba. Added a pop-up window for inputting new note.

File Creation Date:
Development Group: Cyan Worm
Client Group: Blue Navy
Purpose of software: WORM Subject Planner is a mobile platform application made to help
                     students organize their plans and notes according to subjects or
                     categories. It will contain a scheduler, calendar, notes pad, and doodles
                     pad specially designed for the user's convenience.

*/


package com.example.wormsubjectplanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NotesWindow extends ActionBarActivity {

    LinearLayout ll;
    String notesName;
    String notesContent;
    NotesDAO notesDAO;
    Context context;

    Subjects subs;
    Intent intent;
    String sub;
    int a;
    List<Notes> listNotes;
    Notes notes = new Notes();
    String note;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notes);

        context = HomeWindow.getAppContext();
        listNotes = new ArrayList<Notes>();

        notesDAO = new NotesDAO();

        ll = (LinearLayout) findViewById(R.id.subjectnotes_note_list);


//        intent = getIntent();
//        sub = intent.getExtras().getString("subname");

        Bundle data = getIntent().getExtras();
        if(data!=null){
            subs = (Subjects) data.getParcelable("com.subjects");
            sub = subs.getSubjectName();
//            Toast.makeText(HomeWindow.getAppContext(),sub, Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(HomeWindow.getAppContext(), "Have NOT gotten to noteswindows", Toast.LENGTH_SHORT).show();
        }

        Button home = (Button)findViewById(R.id.subjectnotes_home);
        //home button to HomeWindow
        home.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0)
            {
                Intent home_screen = new Intent(NotesWindow.this, HomeWindow.class);
                startActivity(home_screen);
            }
        });

        //button to add notes pop-up
        Button add_notes = (Button) findViewById(R.id.subjectnotes_add_note);
        add_notes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                openPopup();
            }
        });

        viewNotes();
	}


    public void openPopup() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View layout = inflater.inflate(R.layout.add_note, null);

        final EditText noteTitle = (EditText)layout.findViewById(R.id.addnotes_title_field);
        final EditText noteContent = (EditText)layout.findViewById(R.id.addnotes_content_field);

        final AlertDialog.Builder popupbd = new AlertDialog.Builder(this);
        popupbd.setTitle("Add Note");
        popupbd.setCancelable(true);

        popupbd.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int data) {
            }
        });

        popupbd.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int data) {

                if (noteTitle.getText().toString().isEmpty()) {
                    Context context = getApplicationContext();
                    String text = "Invalid title";
                    int time = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, time);
                    toast.show();
                }

                else {
                //    createButton(noteTitle.getText().toString());
                    notesContent = noteContent.getText().toString();
                    notesName = noteTitle.getText().toString();
                    Toast.makeText(context, subs.getSubjectName(), Toast.LENGTH_SHORT).show();
                    notesDAO.createNote(sub, notesName, notesContent);
                }
                viewNotes();
            }
        });

        AlertDialog popup = popupbd.create();
        popup.setView(layout);
        popup.show();
    }

    public void createButton(String value) {
        Button sub = new Button(this);
        sub.setText(value);
        ll.addView(sub);

        sub.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent notes_screen = new Intent(NotesWindow.this, SampleViewNoteWindow.class);
                startActivity(notes_screen);
            }
        });
    }

    public void viewNotes() {
//      String string;
        ll.removeAllViews();

        listNotes = notesDAO.loadNotes(sub);
        int n = listNotes.size();
//     Toast.makeText(context,n+ "",Toast.LENGTH_SHORT).show();
        for (a = 0; a < n; a++) {
                //        notes = (Notes)(subs.getNotesList()).get(a);
                notes = listNotes.get(a);
                note = notes.getTitle();
                Button notee = new Button(this);
                notee.setText(note);
                ll.addView(notee);
                notee.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        Intent notes_screen = new Intent(NotesWindow.this, SampleViewNoteWindow.class);
                        startActivity(notes_screen);
                    }
                });
            }

        }
}
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subject_notes, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/