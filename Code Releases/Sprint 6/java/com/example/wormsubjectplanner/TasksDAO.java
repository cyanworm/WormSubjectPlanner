package com.example.wormsubjectplanner;

import android.content.Context;
import android.widget.Toast;

import com.example.wormsubjectplanner.TasksWindow;

import java.io.*;
import java.util.*;


/**
 * Created by JoniMarie on 5/4/15.
 */
public class TasksDAO {

    String taskfile = "tasks.txt";

    private Context context;

    FileInputStream fin;
    InputStreamReader isr;
    BufferedReader br;

    FileOutputStream fout;

    public TasksDAO(){
         context = HomeWindow.getAppContext();

         try{
              fout = context.openFileOutput(taskfile, Context.MODE_WORLD_READABLE | Context.MODE_APPEND);
         }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public boolean writeToFile(ArrayList<String> tasks){
        try{
            File file = new File(context.getFilesDir(),taskfile);
            file.delete();
            file = new File(context.getFilesDir(),taskfile);
            file.createNewFile();

            int n = tasks.size();
            fout = context.openFileOutput(taskfile, Context.MODE_WORLD_READABLE | Context.MODE_APPEND);
            for (int i = 0; i < n; i++) {
                fout.write((tasks.get(i) + "\n").getBytes());
            }
            fout.close();
             return true;
        }catch(IOException ioe){
            ioe.printStackTrace();
             return false;
        }
    }

    public ArrayList<String> loadFile(){
        String load;
        ArrayList<String> tasks = new ArrayList<String>();

        try{
            File crfile = context.getFileStreamPath(taskfile);
            if(crfile == null || !crfile.exists()) {
                crfile.createNewFile();
            }

            fin = context.openFileInput(taskfile);
            isr = new InputStreamReader(fin);
            br = new BufferedReader(isr);

            while ((load = br.readLine()) != null) {
                tasks.add(load);
            }
            fin.close();

        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return tasks;
    }

    public boolean addToFile(String task){
        try {
             fout = context.openFileOutput(taskfile, Context.MODE_WORLD_READABLE | Context.MODE_APPEND);
            fout.write((task + "\n").getBytes());
            fout.close();
            return true;
        }
        catch(IOException ioe){
            ioe.printStackTrace();
            return false;
        }
    }

    public void deleteTask(String task){
         ArrayList<String> tasks = new ArrayList<String>();
         tasks = loadFile();
         for(int i = 0; i < tasks.size() ; i++){
              if(task.equals(tasks.get(i))){
                   tasks.remove(i);
                   break;
              }
         }
         boolean finished = writeToFile(tasks);
         if(finished == true) Toast.makeText(context, "Successfully deleted!",Toast.LENGTH_SHORT).show();
         else Toast.makeText(context, "Error occured! Failed to delete task. Sorry!", Toast.LENGTH_SHORT).show();
    }


    public void editTask(String oldTask, String newTask){
        ArrayList<String> tasks = new ArrayList<String>();
        tasks = loadFile();

        int n = tasks.size();
        for(int i=0; i<n; i++){
            if(oldTask.equals(tasks.get(i))){
                tasks.set(i, newTask);
                break;
            }
        }
        boolean finished = writeToFile(tasks);
        if(finished == true) Toast.makeText(context, "Successfully edited task!",Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "Error occured! Failed to edit task. Sorry!", Toast.LENGTH_SHORT).show();
    }
}
