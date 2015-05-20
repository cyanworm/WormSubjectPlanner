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
        //if(finished == true) Toast.makeText(context, "Successfully deleted!",Toast.LENGTH_SHORT).show();
         //else Toast.makeText(context, "Error occured! Failed to delete task. Sorry!", Toast.LENGTH_SHORT).show();
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
        //if(finished == true) Toast.makeText(context, "Successfully edited task!",Toast.LENGTH_SHORT).show();
        //else Toast.makeText(context, "Error occured! Failed to edit task. Sorry!", Toast.LENGTH_SHORT).show();
    }
}
