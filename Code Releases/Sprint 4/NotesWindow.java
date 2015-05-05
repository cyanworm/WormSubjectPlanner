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
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;

import java.util.ArrayList;
import java.util.List;

public class NotesWindow extends ActionBarActivity {

     LinearLayout ll;
     NotesDAO notesDAO;
     List<Notes> listNotes;
     List<Notes> subjNotes;
     Notes notes = new Notes();
     Context context;
     Subjects subs;
     String sub;
     String note;
     String notesName;
     String notesContent;
     String buttonName;
     String content;
     String prevtitle;
     String prevsubject;
     int buttonId;
     int buttonIdCount = 0;
     int a,j;

     SubjectsDAO subjectDAO = Globals.subjectsDAO;
     List<Subjects> subjectList;
     Subjects noteSubject;
     Subjects currentSubj;

     String[] options = {"Edit", "Delete"};

     Button.OnClickListener btnclick = new Button.OnClickListener() {
          @Override
          public void onClick(View v) {
               Button button = (Button)v;
               StringBuilder titleStr = new StringBuilder();
               StringBuilder contentStr = new StringBuilder();
               String name = button.getText().toString();
               //subjNotes = currentSubj.getNotesList();
               subjNotes = notesDAO.loadNotes(currentSubj.getSubjectName());

               for(int i = 0; i < subjNotes.size();i++){
                    if((subjNotes.get(i).getTitle()).equals(name)){
                         //sb.append("Title: ");
                         titleStr.append(subjNotes.get(i).getTitle());
                         contentStr.append(subjNotes.get(i).getContent());
                         break;
                    }
               }

               Intent view_note_screen = new Intent(NotesWindow.this, ViewNoteWindow.class);
               view_note_screen.putExtra("note_title", titleStr.toString());
               view_note_screen.putExtra("note_content", contentStr.toString());
               startActivity(view_note_screen);
          }
     };

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.notes);

          context = HomeWindow.getAppContext();
          listNotes = new ArrayList<Notes>();

          notesDAO = new NotesDAO();

          ll = (LinearLayout) findViewById(R.id.subjectnotes_note_list);

          subjectList = subjectDAO.getSubjectsList();

          for(int i = 0; i < subjectList.size();i++){
               currentSubj = subjectList.get(i);
               if(currentSubj.getSubjectName().equals(getIntent().getExtras().getString("subname"))){
                    noteSubject = currentSubj;
                    break;
               }
          }

//        intent = getIntent();
//        sub = intent.getExtras().getString("subname");

          Bundle data = getIntent().getExtras();
          if (data != null) {
               subs = (Subjects) data.getParcelable("com.subjects");
               sub = subs.getSubjectName();
//            Toast.makeText(HomeWindow.getAppContext(),sub, Toast.LENGTH_SHORT).show();

          } else {
               //Toast.makeText(HomeWindow.getAppContext(), "Have NOT gotten to noteswindows", Toast.LENGTH_SHORT).show();
          }

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

          final EditText noteTitle = (EditText) layout.findViewById(R.id.addnotes_title_field);
          final EditText noteContent = (EditText) layout.findViewById(R.id.addnotes_content_field);

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
                        // toast.show();
                    } else {
                         //    createButton(noteTitle.getText().toString());
                         //    createButton(noteTitle.getText().toString());
                         notesContent = noteContent.getText().toString();
                         notesName = noteTitle.getText().toString();
                         //Toast.makeText(context, "Note created", Toast.LENGTH_SHORT).show();
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
          sub.setId(buttonIdCount);
          sub.setTextColor(getResources().getColor(R.color.white));
          ll.addView(sub);
          buttonIdCount++;

          sub.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View vButtonLong) {
                    buttonId = vButtonLong.getId();
                    buttonName = ((Button)vButtonLong).getText().toString();
                    //openDelete();
                    openOptions();
                    return true;
               }
          });

          sub.setOnClickListener(btnclick);
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
               notee.setId(buttonIdCount);
               notee.setTextColor(getResources().getColor(R.color.white));
               ll.addView(notee);
               buttonIdCount++;

               notee.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View vButtonLong) {
                         buttonId = vButtonLong.getId();
                         Button buttonDel = (Button)findViewById(buttonId);
                         buttonName = ((Button)vButtonLong).getText().toString();
                         //openDelete();
                         openOptions();
                         return true;
                    }
               });

               notee.setOnClickListener(btnclick);
          }

     }

     public void openDelete() {
          final AlertDialog.Builder popupbddel = new AlertDialog.Builder(this);
          popupbddel.setTitle("Delete?");
          popupbddel.setCancelable(true);

          popupbddel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int data) {
                    //sb.setVisibility(View.GONE); //sb = button; pass the id etc
                    Button buttonDel = (Button) findViewById(buttonId);
                    buttonDel.setVisibility(View.GONE);
                    notesDAO.deleteNote(sub,buttonName);
                    //or
                    //layoutdel.removeView(sb); //sb = button; pass id etc
               }
          });

          popupbddel.setNegativeButton("No", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int data) {
               }
          });

          AlertDialog popupdel = popupbddel.create();
          popupdel.show();
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

     public void openEdit() {
          LayoutInflater inflater = LayoutInflater.from(this);
          View layout = inflater.inflate(R.layout.add_note, null);

          final EditText editNoteTitle = (EditText) layout.findViewById(R.id.addnotes_title_field);
          final EditText editNoteContent = (EditText) layout.findViewById(R.id.addnotes_content_field);

          prevtitle = buttonName;
          editNoteTitle.setText(buttonName);

          subjNotes = notesDAO.loadNotes(currentSubj.getSubjectName());
          for(j = 0; j < subjNotes.size();j++){
               if((subjNotes.get(j).getTitle()).equals(buttonName)){
                    content = (subjNotes.get(j).getContent());
                    break;
              }
          }

          editNoteContent.setText(content);

          final AlertDialog.Builder popupbd = new AlertDialog.Builder(this);
          popupbd.setTitle("Edit Note");
          popupbd.setCancelable(true);

          popupbd.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int data) {
               }
          });

          popupbd.setPositiveButton("Save", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int data) {
                    if (editNoteTitle.getText().toString().isEmpty()) {
                         Context context = getApplicationContext();
                         String text = "Invalid title";
                         int time = Toast.LENGTH_SHORT;

                         Toast toast = Toast.makeText(context, text, time);
                         // toast.show();
                    } else {
                         notesContent = editNoteContent.getText().toString();
                         notesName = editNoteTitle.getText().toString();

                        notesDAO.editNote(sub, prevtitle, notesName, notesContent);



                         //notesDAO.createNote(sub, notesName, notesContent);
                    }
                    viewNotes();
               }
          });

          AlertDialog popup = popupbd.create();
          popup.setView(layout);
          popup.show();
     }

     public void openOptions() {
          final AlertDialog.Builder popupbddel = new AlertDialog.Builder(this);
          popupbddel.setTitle("Note");
          popupbddel.setCancelable(true);
          popupbddel.setItems(options, new DialogInterface.OnClickListener() {
               @Override
               public void onClick (DialogInterface dialog, int optionId) {
                    if (optionId == 0)
                         openEdit();
                    if (optionId == 1)
                         openDelete();
               }
          });

          AlertDialog popupdel = popupbddel.create();
          popupdel.show();
     }
}