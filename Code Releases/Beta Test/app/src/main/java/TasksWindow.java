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
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import com.example.wormsubjectplanner.R;


public class TasksWindow extends ActionBarActivity {

    TasksDAO taskDAO;
    LinearLayout ll;
    private static Context context = HomeWindow.getAppContext();
    int buttonId, buttonIdC, buttonIdCount = 0;
    String buttonName, buttonNameC;
    String[] options = {"Mark as Completed", "Edit", "Delete"};
    String[] options2 = {"Delete"};

    ArrayList<String> tasks = new ArrayList<String>();


    @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.tasks);

          Button add_task = (Button)findViewById(R.id.tasks_add);
          add_task.setOnClickListener(new View.OnClickListener() {
               public void onClick(View arg0) {
                    openAdd();
               }
          });

          taskDAO = new TasksDAO();
          ll = (LinearLayout) findViewById(R.id.tasks_list);

          viewTasks();
     }


     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
          // Inflate the menu; this adds items to the action bar if it is present.
          getMenuInflater().inflate(R.menu.tasks, menu);
          return true;
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
          // Handle action bar item clicks here. The action bar will
          // automatically handle clicks on the Home/Up button, so long
          // as you specify a parent activity in AndroidManifest.xml.
          int id = item.getItemId();

          //noinspection SimplifiableIfStatement
          if (id == R.id.action_settings) {
               return true;
          }

          return super.onOptionsItemSelected(item);
     }

     public void openAdd() {
          LayoutInflater inflater = LayoutInflater.from(this);
          View layout = inflater.inflate(R.layout.add_task, null);

          final EditText task = (EditText) layout.findViewById(R.id.addtask_content);

          final AlertDialog.Builder popupbd = new AlertDialog.Builder(this);
          popupbd.setTitle("Add Task");
          popupbd.setCancelable(true);

          popupbd.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int data) {
               }
          });

          popupbd.setPositiveButton("Save", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int data) {
                    String temp = task.getText().toString();
                    Context context = getApplicationContext();
                    if (temp.isEmpty()) Toast.makeText(context,"Invalid task!", Toast.LENGTH_SHORT).show();
                    else{
                        if (tasks.contains(temp)) {
                            Toast.makeText(context,"Task already exists!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            boolean finished = taskDAO.addToFile(temp);
                            //if(finished == true) Toast.makeText(context,"Successfully added task!", Toast.LENGTH_SHORT).show();
                            //else Toast.makeText(context,"Error occured! Failed to add task. Sorry!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    viewTasks();
               }
          });

          AlertDialog popup = popupbd.create();
          popup.setView(layout);
          popup.show();
     }

     public void viewTasks(){

         tasks = taskDAO.loadFile();

         //if(tasks.isEmpty() == false) {
             int n = tasks.size();
             //Toast toast = Toast.makeText(getApplicationContext(), "Task size is " + tasks.size(), Toast.LENGTH_SHORT);
             //toast.show();
             ll.removeAllViews();
             for (int i = 0; i < n; i++) {
                 Button newButton = new Button(this);

                 if (tasks.get(i).length() > 1) {
                      if (tasks.get(i).charAt(0) == '(' && tasks.get(i).charAt(tasks.get(i).length() - 1) == ')') {
                           newButton.setText(tasks.get(i));
                           newButton.setId(buttonIdCount);
                           newButton.setTextColor(getResources().getColor(R.color.background_floating_material_dark));
                           ll.addView(newButton);
                           buttonIdCount++;

                           newButton.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View vButtonLong) {
                                     buttonId = vButtonLong.getId();
                                     //Button buttonDel = (Button)findViewById(buttonId);
                                     buttonName = ((Button) vButtonLong).getText().toString();
                                     buttonIdC = buttonId;
                                     buttonNameC = buttonName;
                                     //openDelete();
                                     openOptions2();
                                     return true;
                                }
                           });

                           newButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View vButtonLong) {
                                     buttonName = ((Button)vButtonLong).getText().toString();
                                     openView();
                                     //return true;
                                }
                           });
                      }

                      else {
                           newButton.setText(tasks.get(i));
                           newButton.setId(buttonIdCount);
                           newButton.setTextColor(getResources().getColor(R.color.white));
                           ll.addView(newButton);
                           buttonIdCount++;

                           newButton.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View vButtonLong) {
                                     buttonId = vButtonLong.getId();
                                     //Button buttonDel = (Button)findViewById(buttonId);
                                     buttonName = ((Button) vButtonLong).getText().toString();
                                     buttonIdC = buttonId;
                                     buttonNameC = buttonName;
                                     //openDelete();
                                     openOptions();
                                     return true;
                                }
                           });

                           newButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View vButtonLong) {
                                     buttonName = ((Button)vButtonLong).getText().toString();
                                     openView();
                                     //return true;
                                }
                           });
                      }
                 }

                 else {
                      newButton.setText(tasks.get(i));
                      newButton.setId(buttonIdCount);
                      newButton.setTextColor(getResources().getColor(R.color.white));
                      ll.addView(newButton);
                      buttonIdCount++;

                      newButton.setOnLongClickListener(new View.OnLongClickListener() {
                           @Override
                           public boolean onLongClick(View vButtonLong) {
                                buttonId = vButtonLong.getId();
                                //Button buttonDel = (Button)findViewById(buttonId);
                                buttonName = ((Button) vButtonLong).getText().toString();
                                buttonIdC = buttonId;
                                buttonNameC = buttonName;
                                //openDelete();
                                openOptions();
                                return true;
                           }
                      });

                      newButton.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View vButtonLong) {
                                buttonName = ((Button)vButtonLong).getText().toString();
                                openView();
                                //return true;
                           }
                      });
                 }
             }
     }

     public static Context getAppContext() {
        return TasksWindow.context;
     }

     public void openEdit() {
          LayoutInflater inflater = LayoutInflater.from(this);
          View layout = inflater.inflate(R.layout.add_task, null);

          final EditText taskEdit = (EditText) layout.findViewById(R.id.addtask_content);
          taskEdit.setText(buttonName);

          final AlertDialog.Builder popupbd = new AlertDialog.Builder(this);
          popupbd.setTitle("Edit Task");
          popupbd.setCancelable(true);

          popupbd.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int data) {
               }
          });

          popupbd.setPositiveButton("Save", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int data) {
                    String newtask = taskEdit.getText().toString();
                    Context context = getApplicationContext();
                    if (newtask.isEmpty()) Toast.makeText(context, "Invalid task!",  Toast.LENGTH_SHORT).show();
                    else {
                        if(tasks.contains(newtask))
                        {
                            Toast.makeText(context,"Task already exists!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            taskDAO.editTask(buttonName, newtask);
                        }
                    }
                    viewTasks();
               }
          });

          AlertDialog popup = popupbd.create();
          popup.setView(layout);
          popup.show();
     }

     public void openDelete() {
          final AlertDialog.Builder popupbddel = new AlertDialog.Builder(this);
          popupbddel.setTitle("Delete task?");
          popupbddel.setCancelable(true);

          popupbddel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int data) {
                    taskDAO.deleteTask(buttonName);
                    /*Button buttonDel = (Button) findViewById(buttonId);
                    buttonDel.setVisibility(View.GONE);*/
                    viewTasks();
               }
          });

          popupbddel.setNegativeButton("No", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int data) {
               }
          });
          AlertDialog popupdel = popupbddel.create();
          popupdel.show();
     }

     public void openCompleted() {
          final AlertDialog.Builder popupbddel = new AlertDialog.Builder(this);
          popupbddel.setTitle("Mark task as completed?");
          popupbddel.setCancelable(true);

          popupbddel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int data) {
                    Button buttonC = (Button)findViewById(buttonId);
                    buttonC.setTextColor(getResources().getColor(R.color.background_floating_material_dark));
                    taskDAO.editTask(buttonName, "(" + buttonName + ")");
                    viewTasks();
               }
          });

          popupbddel.setNegativeButton("No", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int data) {
               }
          });
          AlertDialog popupdel = popupbddel.create();
          popupdel.show();
     }

     public void openView() {
          final AlertDialog.Builder popupbddel = new AlertDialog.Builder(this);
          popupbddel.setTitle(buttonName);
          popupbddel.setCancelable(true);

          AlertDialog popupdel = popupbddel.create();
          popupdel.show();
     }

     public void openOptions() {
          final AlertDialog.Builder popupbddel = new AlertDialog.Builder(this);
          popupbddel.setTitle("Task");
          popupbddel.setCancelable(true);
          popupbddel.setItems(options, new DialogInterface.OnClickListener() {
               @Override
               public void onClick (DialogInterface dialog, int optionId) {
                    if (optionId == 0)
                         openCompleted();
                    if (optionId == 1)
                         openEdit();
                    if (optionId == 2)
                         openDelete();
               }
          });

          AlertDialog popupdel = popupbddel.create();
          popupdel.show();
     }

     public void openOptions2() {
          final AlertDialog.Builder popupbddel = new AlertDialog.Builder(this);
          popupbddel.setTitle("Completed Task");
          popupbddel.setCancelable(true);
          popupbddel.setItems(options2, new DialogInterface.OnClickListener() {
               @Override
               public void onClick (DialogInterface dialog, int optionId2) {
                    if (optionId2 == 0)
                         openDelete();
               }
          });

          AlertDialog popupdel = popupbddel.create();
          popupdel.show();
     }
}
