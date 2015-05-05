
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
    private static Context context = HomeWindow.getAppContext();
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
               String temp = task.getText().toString();
               Context context = getApplicationContext();
               String text;
               int time = Toast.LENGTH_SHORT;
               if (temp.isEmpty()) {
                    text = "Invalid task! Must not be empty!";
                    Toast.makeText(context,text,time).show();
               }
               else{
                    taskDAO.addToFile(temp);
                    text = "Successfully added task!";
                    Toast.makeText(context,text,time).show();
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
                    String newtask = taskEdit.getText().toString();
                    Context context = getApplicationContext();
                    String text;
                    int time = Toast.LENGTH_SHORT;
                    if (newtask.isEmpty()) {
                         text = "Invalid task";
                         Toast.makeText(context, text, time).show();
                    }
                    else{
                        taskDAO.editTask(buttonName,newtask);
                        text = "Successfully edited task";
                        Toast.makeText(context,text,time).show();
                    }
                    viewTasks();
               }
          });

          AlertDialog popup = popupbd.create();
          popup.setView(layout);
          popup.show();
     }

     public void openDelete() {
          LayoutInflater inflaterdel = LayoutInflater.from(this);
          View layoutdel = inflaterdel.inflate(R.layout.empty, null);
          final AlertDialog.Builder popupbddel = new AlertDialog.Builder(this);
          popupbddel.setTitle("Delete task?");
          popupbddel.setCancelable(true);

          popupbddel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int data) {
                    taskDAO.deleteTask(buttonName);
                    Button buttonDel = (Button)findViewById(buttonId);
                    buttonDel.setVisibility(View.GONE);
                    Toast.makeText(context, "Successfully deleted!",Toast.LENGTH_SHORT).show();
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
