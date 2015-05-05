
package com.example.wormsubjectplanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
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

import com.example.wormsubjectplanner.R;


public class TasksWindow extends ActionBarActivity {

    TasksDAO taskDAO;
    LinearLayout ll;
    private static Context context;
    int buttonId, buttonIdCount = 0;
    String buttonName;
    String[] options = {"Edit", "Delete"};

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
                  String newtask = "";
                  if (task.getText().toString().isEmpty()) {
                         Context context = getApplicationContext();
                         String text = "Invalid task";
                         int time = Toast.LENGTH_SHORT;
                    }
                  else{
                        newtask = task.getText().toString();
                        taskDAO.addToFile(newtask);
                    }
                   viewTasks();
               }
          });

          AlertDialog popup = popupbd.create();
          popup.setView(layout);
          popup.show();
     }

     public void viewTasks(){
         ArrayList<String> tasks = new ArrayList<String>();

         tasks = taskDAO.loadFile();

         //if(tasks.isEmpty() == false) {
             int n = tasks.size();
             //Toast toast = Toast.makeText(getApplicationContext(), "Task size is " + tasks.size(), Toast.LENGTH_SHORT);
             //toast.show();
             ll.removeAllViews();
             for (int i = 0; i < n; i++) {
                 Button newButton = new Button(this);
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
                            buttonName = ((Button)vButtonLong).getText().toString();
                            //openDelete();
                            openOptions();
                            return true;
                       }
                  });
             }
        // }
         /*else{
             Toast toast = Toast.makeText(getApplicationContext(), "Task size is empty", Toast.LENGTH_SHORT);
             toast.show();
         }*/
     }

     public static Context getAppContext() {
        return TasksWindow.context;
     }

     public void openEdit() {
          LayoutInflater inflater = LayoutInflater.from(this);
          View layout = inflater.inflate(R.layout.add_task, null);

          final EditText taskEdit = (EditText) layout.findViewById(R.id.addtask_content);

          final AlertDialog.Builder popupbd = new AlertDialog.Builder(this);
          popupbd.setTitle("Edit Task");
          popupbd.setCancelable(true);

          popupbd.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int data) {
               }
          });

          popupbd.setPositiveButton("Save", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int data) {
                    String newtask = "";
                    if (taskEdit.getText().toString().isEmpty()) {
                         Context context = getApplicationContext();
                         String text = "Invalid task";
                         int time = Toast.LENGTH_SHORT;
                    }
                    else{
                         newtask = taskEdit.getText().toString();
                         taskDAO.addToFile(newtask);
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
          popupbddel.setTitle("Delete?");
          popupbddel.setCancelable(true);

          popupbddel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int data) {
                    //sb.setVisibility(View.GONE); //sb = button; pass the id etc
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
