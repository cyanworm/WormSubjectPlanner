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

    public void writeToFile(ArrayList<String> tasks){
        try{
            File file = new File(context.getFilesDir(),taskfile);
            file.delete();
            file = new File(context.getFilesDir(),taskfile);
            file.createNewFile();

            int n = tasks.size();
            fout = context.openFileOutput(taskfile, Context.MODE_WORLD_READABLE | Context.MODE_APPEND);
            for (int i = 0; i < n; i++) {
                fout.write((tasks.get(i)).getBytes());
            }
            fout.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
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
                System.out.println("Load is " + load);
            }
            fin.close();

        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return tasks;
    }

    public void addToFile(String task){
        System.out.println("Task is " + task);
        try {

            fout.write((task + "\n").getBytes());
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void deleteTask(String task){
        ArrayList<String> tasks = new ArrayList<String>();
        tasks = loadFile();

        int n = tasks.size();
        for(int i=0; i<n; i++){
            if(task.equals(tasks.get(i))){
                tasks.remove(i);
                break;
            }
        }
        writeToFile(tasks);
    }

    public void editTask(String oldTask, String newTask){
        ArrayList<String> tasks = new ArrayList<String>();
        tasks = loadFile();

        int n = tasks.size();
        for(int i=0; i<n; i++){
            if(oldTask.equals(tasks.get(i))){
                tasks.remove(i);
                tasks.set(i, newTask);
                break;
            }
        }
        writeToFile(tasks);
    }
}
