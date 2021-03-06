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
2/12/15: Patrick Leiniel H. Domingo: Notes DAO overhaul
2/18/15: Joni Jimenez: Added load notes.
2/23/15: Patrick Domingo: Fixed load notes.
3/02/15: Patrick Domingo: Added delete notes.
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
//import java.io.File;
//import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class NotesDAO {
     private Context context;

     FileOutputStream fOut;
     //    FileOutputStream subfOut;
     FileInputStream fIn;
     InputStreamReader isr;
     BufferedReader br;
     int kk;
     SubjectsDAO subjectsDAO = Globals.subjectsDAO;
     Subjects currentSubj;
     String currentSubjName;
     List<Subjects> subjectsList;
     List<Notes> nnotes;

     /* NotesDao: 2/12/15: Patrick Leiniel H. Domingo: instantiates NotesDAO and gets the context of the app */
     public NotesDAO() {
          context = HomeWindow.getAppContext();
          nnotes = new ArrayList<Notes>();
     }
     public boolean checkDuplicate(String subjectName){
          String temp;
          String filename = subjectName + ".txt";
          for(int i = 0; i < nnotes.size();i++){
               temp = nnotes.get(i).getTitle();
               temp = temp.toLowerCase();
               if((subjectName.toLowerCase()).equals(temp)){
                    return true;
               }
          }
          File f4 = context.getFilesDir();
          File f = new File(f4, filename);
          return f.exists();
     }
     /* createNote: 2/12/15: Patrick Leiniel H. Domingo: called when creating a new Note */
    /* create a notes class
      append notes in the arraylist of notes
      create a File notes.txt
      write to file notes.txt
      append the note in the file subject.txt*/
     public void createNote(String subject, String title, String content) {
          Notes newNote = new Notes(subject,title,content);
//        sub.getNotesList().add(newNote); //append notes to subject arraylist
          //Toast.makeText(context,"Have gotten to notesDAO",Toast.LENGTH_SHORT).show();


          try{
               File crfile = new File(title + ".txt");
       /*    if(crfile == null || !crfile.exists()) {
               crfile.createNewFile();
           }
       */
               fOut = context.openFileOutput(title + ".txt", Context.MODE_WORLD_READABLE | Context.MODE_APPEND);
            //   fOut.write((title + "\n").getBytes());
               fOut.write(content.getBytes());
               fOut.close();

               fOut = context.openFileOutput(subject + ".txt", Context.MODE_WORLD_READABLE | Context.MODE_APPEND);
               fOut.write((title + "\n").getBytes());
               //     fOut.write(content.getBytes());
               fOut.close();

          } catch (Exception e) {
               //Toast.makeText(HomeWindow.getAppContext(), "ADDING NOTES ERROR "+e, Toast.LENGTH_LONG).show();
          }
          try{
               subjectsList = subjectsDAO.subjectsList;
               for(int i = 0; i < subjectsList.size();i++){
                    currentSubj = subjectsList.get(i);
                    currentSubjName = currentSubj.getSubjectName();
                    if(currentSubjName.equals(subject)){
                         currentSubj.getNotesList().add(newNote);
                    }
               }
          } catch (Exception e){
               //Toast.makeText(HomeWindow.getAppContext(), (CharSequence) e, Toast.LENGTH_SHORT).show();
          }
     }

     public void editNote(String subject, String prevTitle, String newTitle, String newContent){
         Notes temp;
         String temp2;
         List<Notes> newList = new ArrayList<Notes>();
         for(int i = 0; i < nnotes.size();i++){
             temp = nnotes.get(i);
             if(temp.getTitle().equals(prevTitle)){
                 temp.setTitle(newTitle);
                 temp.setContent(newContent);
             }
             newList.add(temp);
         }
         File thisFile = new File (context.getFilesDir(),prevTitle + ".txt");
         thisFile.delete();

         File ffile = new File(newTitle + ".txt");
         try{
            /* fOut = context.openFileOutput(subject + ".txt", Context.MODE_WORLD_READABLE | Context.MODE_APPEND);
             fOut.write(newTitle.getBytes());
             fOut.close();
            */
             fOut = context.openFileOutput(newTitle + ".txt", Context.MODE_WORLD_READABLE | Context.MODE_APPEND);
             fOut.write(newContent.getBytes());
             fOut.close();
         }catch(Exception e){
             //Toast.makeText(context, (CharSequence) e, Toast.LENGTH_SHORT).show();
         }


         thisFile = new File(context.getFilesDir(), subject + ".txt");
         thisFile.delete();
         File file = new File(subject + ".txt");
         for(int i = 0;  i < newList.size();i++){
             temp = newList.get(i);
             temp2 = temp.getTitle();
             temp2 = temp2 +"\n";
             try{
                 fOut = context.openFileOutput(subject + ".txt", Context.MODE_WORLD_READABLE | Context.MODE_APPEND);
                 fOut.write(temp2.getBytes());
                 fOut.close();
             }catch(Exception e){
                 //Toast.makeText(context, (CharSequence) e, Toast.LENGTH_SHORT).show();
             }
         }
         nnotes = newList;

     }

     /*
          called when deleting a note
          @param subject - subject of the note
          @param note - title of the note
      */
     public void deleteNote(String subject,String note){
          Notes temp;
          String temp2;
          List<Notes> newList = new ArrayList<Notes>();
          for(int i = 0; i < nnotes.size();i++){
               temp = nnotes.get(i);
               if(temp.getTitle().equals(note)){}
               else{
                    newList.add(temp);
               }
          }
          File thisFile = new File (context.getFilesDir(),note + ".txt");
          thisFile.delete();
          thisFile = new File(context.getFilesDir(), subject + ".txt");
          thisFile.delete();
          File file = new File(subject + ".txt");
          for(int i = 0;  i < newList.size();i++){
               temp = newList.get(i);
               temp2 = temp.getTitle();
               temp2 = temp2 +"\n";
               try{
                    fOut = context.openFileOutput(subject + ".txt", Context.MODE_WORLD_READABLE | Context.MODE_APPEND);
                    fOut.write(temp2.getBytes());
                    fOut.close();
                    Toast.makeText(context,"Successfully deleted note!",Toast.LENGTH_SHORT).show();
               }catch(Exception e){
                    //Toast.makeText(context, (CharSequence) e, Toast.LENGTH_SHORT).show();
               }
          }
          nnotes = newList;
     }

     /*
          called when loading the notes
          @param subject - subject of the notes
      */
     public List<Notes> loadNotes(String subject){
          try {
               Notes temp;
               File file = context.getFileStreamPath(subject + ".txt");
               if(file == null || !file.exists()){
                    //Toast.makeText(context,"File Not Found ",Toast.LENGTH_SHORT).show();
                    file.createNewFile();
               }
               else {
                    fIn = context.openFileInput(subject + ".txt");
                    isr = new InputStreamReader(fIn);
                    br = new BufferedReader(isr);

                    nnotes = new ArrayList<Notes>();
                    //    nnotes.clear();

                    if(nnotes==null){
                         //Toast.makeText(context,"Nnotes is null",Toast.LENGTH_SHORT).show();
                    }
                    else {

//                    Toast.makeText(context,"Nnotes is NOT null " + nnotes.size(),Toast.LENGTH_SHORT).show();
                         int kk = 0;
                         String str = "";
                         while ((str = br.readLine()) != null) {
                              //only get the notes title for the mean time
//                    Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
                              Notes newNote = new Notes();
                              newNote.setTitle(str);
//                  subject.getNotesList().add(newNote);

//                        nnotes.add(newNote);
                              nnotes.add(newNote);
//                    Toast.makeText(context,nnotes.get(kk).getTitle() + " " + kk,Toast.LENGTH_SHORT).show();

//                    Toast.makeText(context,kk + " is the number",Toast.LENGTH_SHORT).show();
                              kk++;
                         }
                         fIn.close();
                         for(int i = 0; i < nnotes.size(); i++){
                              temp = nnotes.get(i);
                              File file2 = context.getFileStreamPath(temp.getTitle() + ".txt" );
                              if(file2 == null || !file2.exists()){
                                   //Toast.makeText(context,"File Not Found ",Toast.LENGTH_SHORT).show();
                                   file2.createNewFile();
                              }
                              else{
                                   fIn = context.openFileInput(temp.getTitle() + ".txt");
                                   isr = new InputStreamReader(fIn);
                                   br = new BufferedReader(isr);
                                   StringBuilder sb = new StringBuilder();
                                   while((str = br.readLine()) != null) {
                                         sb.append(str);
                                         sb.append("\n");
                                   }
                                   temp.setContent(sb.toString());
                                   temp.setSubject(subject);
                                   fIn.close();
                              }
                         }
//               Toast.makeText(context,nnotes.size() + "",Toast.LENGTH_SHORT).show();
                         return nnotes;
                    }
               }

          }
          catch(Exception e){
               //Toast.makeText(HomeWindow.getAppContext(), "EERRORR GETTING LIST NOTES "+ e, Toast.LENGTH_SHORT).show();
          }
          return nnotes;

     }
}
