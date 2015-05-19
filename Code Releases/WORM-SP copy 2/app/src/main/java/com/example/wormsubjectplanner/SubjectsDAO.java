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
2/13/15 - Patrick Domingo. Subjects DAO overhaul.
02/18/15 - Joni Jimenez. Added populate.
03/04/15 - Patrick Domingo. Added delete.
File Creation Date:
Development Group: Cyan Worm
Client Group: Blue Navy
Purpose of software: WORM Subject Planner is a mobile platform application made to help
                     students organize their plans and notes according to subjects or
                     categories. It will contain a scheduler, calendar, and otes pad
                     specially designed for the user's convenience.

*/

package com.example.wormsubjectplanner;

import android.content.Context;
import android.widget.Toast;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//manipulator for the subjects class- class with list of subjects
public class SubjectsDAO {
     List<Subjects> subjectsList;
     List<Subjects> forDelete;
     private Context context;
     private String sb = "Subjects.txt";

     FileOutputStream fOut;
     FileOutputStream ffOut;
     FileInputStream fIn;
     InputStreamReader isr;
     BufferedReader br;
//    BufferedReader brr;
      NotesDAO notesDAO = new NotesDAO();

     public SubjectsDAO(){
          context = HomeWindow.getAppContext();
          subjectsList = new ArrayList<Subjects>();
          forDelete = new ArrayList<Subjects>();

          //file for subjects.txt
          try {
        /*    File ss = new File(sb);
            if(sb == null || !ss.exists()){
                ss.createNewFile();
            }
        */
               fOut = context.openFileOutput(sb, Context.MODE_WORLD_READABLE | Context.MODE_APPEND);
               //    ffOut = context.openFileOutput(sb, Context.MODE_WORLD_READABLE);
               fIn = context.openFileInput(sb);
               isr = new InputStreamReader(fIn);
               br = new BufferedReader(isr);
//            brr = new BufferedReader(isr);
          }
          catch(Exception e){
               // Toast.makeText(HomeWindow.getAppContext(), "Opening SubjectSSSS EERROORR "+e, Toast.LENGTH_LONG).show();
          }
          populateList();
     }

     /*create a subjects class
       append the subject in the file subjects.txt
       append subject in the arraylist of subjects*/
     public void createSubject(String subjectName) {
          try {
               File crfile = new File(context.getFilesDir(), subjectName + ".txt" );
             // crfile.createNewFile();
               //    ffOut = context.openFileOutput(subjectName + ".txt", Context.MODE_WORLD_READABLE | Context.MODE_APPEND);
               Subjects newSubject = new Subjects(subjectName);

               fOut.write((subjectName + "\n").getBytes());
               //   fOut.close();

               subjectsList.add(newSubject);
          } catch (Exception e) {
               //Toast.makeText(HomeWindow.getAppContext(), "SubjectDAO error EERORROR "+ e, Toast.LENGTH_LONG).show();
          }
     }

    public void editSubject(String prevSub, String newSub){
        List<Subjects> gget = getSubjectsList();
        List<Notes> ggetnotes = notesDAO.loadNotes(prevSub);
        for(int i = 0; i < gget.size();i++){
            if(gget.get(i).getSubjectName().equals(prevSub)){
                gget.get(i).setSubjectName(newSub);
                break;
            }
        }

        File thisFile = new File(context.getFilesDir(), sb);
        thisFile.delete();
        File file = new File(sb);
        for(int i = 0;  i < gget.size();i++){
            Subjects temp = gget.get(i);
            String temp2 = temp.getSubjectName();
            temp2 = temp2 +"\n";
            try{
                fOut = context.openFileOutput(sb, Context.MODE_WORLD_READABLE | Context.MODE_APPEND);
                fOut.write(temp2.getBytes());
                fOut.close();
            }catch(Exception e){
                Toast.makeText(context, (CharSequence) e, Toast.LENGTH_SHORT).show();
            }
        }

        File file1 = new File(prevSub+".txt");
        File file2 = new File(newSub+".txt");

        file1.renameTo(file2);
    }
     /*
          called for deleting a subject
     */
     public void deleteSubject(String subjectName){
          Subjects temp;
          String name;
          String ss;

          /*delete each note inside the subject*/
          try{
               int j = 0;
               for(int i = 0; i < subjectsList.size();i++){
                    temp = subjectsList.get(i);
                    if(temp.getSubjectName().equals(subjectName)){
                         File crfile = new File(temp.getSubjectName() + ".txt" );
                         fIn = context.openFileInput(temp.getSubjectName() + ".txt");
                         isr = new InputStreamReader(fIn);
                         br = new BufferedReader(isr);
                         File dir = context.getFilesDir();
                        // File dir = getFilesDir();
                         while((ss= br.readLine()) != null ){
                              if(j!= 0){
                                  File thisFile = new File(dir, ss + ".txt");
                                  thisFile.delete();
                              }
                              j++;
                         }
                         File thisFile = new File(dir,temp.getSubjectName() + ".txt" );
                         thisFile.delete();
                         fIn.close();
                         break;
                    }
               }


          }catch(Exception e){
               //Toast.makeText(context,"SDAO DELETELIST ERROR " +e,Toast.LENGTH_LONG).show();
          }
          try {

               forDelete.clear();
               fIn = context.openFileInput(sb);
               isr = new InputStreamReader(fIn);
               br = new BufferedReader(isr);
               while ((ss = br.readLine()) != null) {
                    if((ss.equals(subjectName))==false) {

                         Subjects newSub = new Subjects(ss);
                         forDelete.add(newSub);
                    }

               }
               writeToSubject(forDelete);
          }
          catch(Exception e){
               //Toast.makeText(context,"SDAO DELETELIST ERROR " +e,Toast.LENGTH_LONG).show();
          }
     }

     /*
          returns all the subjects
      */
     public List getSubjectsList(){
          try {
               String ss;
               subjectsList.clear();
               fIn = context.openFileInput(sb);
               isr = new InputStreamReader(fIn);
               br = new BufferedReader(isr);
               while ((ss = br.readLine()) != null) {
                    Subjects newSub = new Subjects(ss);
                    subjectsList.add(newSub);

               }
          }
          catch(Exception e){
               //Toast.makeText(context,"SDAO GETLIST ERROR " +e,Toast.LENGTH_LONG).show();
          }

          int n = subjectsList.size();
          //    Toast.makeText(HomeWindow.getAppContext(),n + " subjectList.size",Toast.LENGTH_LONG).show();
          return subjectsList;
     }

     /*
          initial load of the subjects
      */
     public void populateList(){
          String temp;
          try {
               while((temp= br.readLine()) != null){
                    Subjects newSub = new Subjects(temp);
                    //    notesDAO.loadNotes(newSub);
                    subjectsList.add(newSub);
               }
          }catch(Exception e){
               Toast.makeText(HomeWindow.getAppContext(), "populateList EERROORR "+e, Toast.LENGTH_SHORT).show();
          }
     }

     /*
          rewrite all subject in the file after deleting a subject
      */
     public void writeToSubject(List<Subjects> subLists){
          try {

               fOut = context.openFileOutput(sb, Context.MODE_WORLD_READABLE);
               fOut.write("".getBytes());
               fOut = context.openFileOutput(sb, Context.MODE_WORLD_READABLE | Context.MODE_APPEND);
               int n = subjectsList.size();
               //    ffOut.write("".getBytes());

               for(int a=0; a<n;a++){
                    fOut.write((subLists.get(a).getSubjectName() + "\n").getBytes());
               }

          }
          catch(Exception e){
               //Toast.makeText(context,"SDAO DELETELIST ERROR " +e,Toast.LENGTH_LONG).show();
          }
     }


}