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
2/13/15 - Jannieca Camba. Added a pop-up window for creation of subject. Implemented dynamic addition of buttons in screen.

File Creation Date:
Development Group: Cyan Worm
Client Group: Blue Navy
Purpose of software: WORM Subject Planner is a mobile platform application made to help
                     students organize their plans and notes according to subjects or
                     categories. It will contain a scheduler, calendar, notes pad, and doodles
                     pad specially designed for the user's convenience.

*/


package com.example.wormsubjectplanner;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.view.LayoutInflater;
import android.content.Context;
import android.widget.Toast;
import android.widget.LinearLayout;
import java.util.List;
import java.util.ArrayList;

public class SubjectsWindow extends ActionBarActivity {

     LinearLayout ll;
     String subjectName;
     SubjectsDAO subjectsDAO;

     String temp = "";
     int a;
     List<Subjects> newList = new ArrayList<Subjects>();
//    NotesDAO notes = new NotesDAO();

     int buttonId;
     int buttonIdCount = 0;
     String buttonName;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.subjects);

          ll = (LinearLayout) findViewById(R.id.notes_subject_list);
          subjectsDAO = new SubjectsDAO();

          /*Button home = (Button)findViewById(R.id.notes_home);
          //home button to HomeWindow
          home.setOnClickListener(new View.OnClickListener() {
               public void onClick(View arg0) {
                    Intent home_screen = new Intent(SubjectsWindow.this, HomeWindow.class);
                    startActivity(home_screen);
               }
          });*/

          /*
     	<Button
        android:id="@+id/notes_home"
        android:layout_width="200dp"
        android:layout_height="30dp"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:layout_marginBottom="20dp"
        android:background="@drawable/home" />
           */

          //button to add subjects pop-up
          Button add_notes = (Button) findViewById(R.id.notes_add_subject);
          add_notes.setOnClickListener(new View.OnClickListener() {
               public void onClick(View arg0) {
                    openPopup();
               }
          });

          viewSubjects();

     }

     public void openPopup() {
          LayoutInflater inflater = LayoutInflater.from(this);
          View layout = inflater.inflate(R.layout.add_subject, null);

          final EditText addsubj = (EditText)layout.findViewById(R.id.add_subject_value);

          final AlertDialog.Builder popupbd = new AlertDialog.Builder(this);
          popupbd.setTitle("Add Subject");
          popupbd.setCancelable(true);

          popupbd.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int data) {
               }
          });

          popupbd.setPositiveButton("Save", new DialogInterface.OnClickListener() {
               @TargetApi(Build.VERSION_CODES.GINGERBREAD)
               public void onClick(DialogInterface dialog, int data) {
                    int time = Toast.LENGTH_SHORT;
                    Context context = getApplicationContext();
                    if (addsubj.getText().toString().isEmpty()) {
                         String text = "Invalid subject";
                         Toast.makeText(context, text, time).show();

                    }

                    else {
                         subjectName = addsubj.getText().toString();
                         subjectsDAO.createSubject(subjectName);
                         Toast.makeText(context, "Subject created", time).show();
                         viewSubjects();
                    }
               }
          });

          AlertDialog popup = popupbd.create();
          popup.setView(layout);
          popup.show();
     }

     public void viewSubjects() {
          newList = subjectsDAO.getSubjectsList();
          ll.removeAllViews();
          int n = newList.size();
          for(a =0; a < n; a++){
               temp = newList.get(a).getSubjectName();
               Button sub = new Button(this);
               sub.setText(temp);
               sub.setTag(newList.get(a));
               sub.setId(buttonIdCount);
               sub.setTextColor(getResources().getColor(R.color.white));
               ll.addView(sub);
               buttonIdCount++;
               sub.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                         //                   Toast.makeText(getBaseContext(),"Have set On Click",Toast.LENGTH_SHORT).show();
                         Intent notes_screen = new Intent(SubjectsWindow.this, NotesWindow.class);

                         Button button = (Button)v;
                         notes_screen.putExtra("subname", button.getText().toString());

                         Bundle bundle = new Bundle();
                         bundle.putParcelable("com.subjects",(Subjects)((Button)v).getTag());

//                    notes_screen.putExtra("subname",temp);
                         notes_screen.putExtras(bundle);
                         startActivity(notes_screen);
                    }
               });

               sub.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View vButtonLong) {
                         buttonId = vButtonLong.getId();
                         buttonName = ((Button)vButtonLong).getText().toString();

                         openDelete();

                         return true;
                    }
               });
          }
     }

     public void openDelete() {
          LayoutInflater inflaterdel = LayoutInflater.from(this);
          View layoutdel = inflaterdel.inflate(R.layout.empty, null);

          final AlertDialog.Builder popupbddel = new AlertDialog.Builder(this);
          popupbddel.setTitle("Delete?");
          popupbddel.setCancelable(true);

          popupbddel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int data) {
                    Button buttonDel = (Button)findViewById(buttonId);
                    buttonDel.setVisibility(View.GONE);
                    subjectsDAO.deleteSubject(buttonName);
               }
          });

          popupbddel.setNegativeButton("No", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int data) {
               }
          });

          AlertDialog popupdel = popupbddel.create();
          //popupdel.setView(layoutdel);
          popupdel.show();
     }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
          // Inflate the menu; this adds items to the action bar if it is present.
          getMenuInflater().inflate(R.menu.notes, menu);
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
     }

}
