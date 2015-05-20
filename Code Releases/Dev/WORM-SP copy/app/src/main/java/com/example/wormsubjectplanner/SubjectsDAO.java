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

File Creation Date: 02/03/15
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
     private Context context;
     private String sb = "Subjects.txt";

     FileOutputStream fOut;
     FileInputStream fIn;
     FileInputStream fIn2;
     FileInputStream fIn3;
     InputStreamReader isr;
     InputStreamReader isr2;
     InputStreamReader isr3;
     BufferedReader br;
     BufferedReader br2;
     BufferedReader br3;

     /*
          SubjectsDAO: 2/12/15 - instantiates a DAO for Subject
     */
     public SubjectsDAO(){
         context = HomeWindow.getAppContext();
         subjectsList = new ArrayList<Subjects>();

         //file for subjects.txt
          try {
               fOut = context.openFileOutput(sb, Context.MODE_WORLD_READABLE | Context.MODE_APPEND);
               fIn = context.openFileInput(sb);
               isr = new InputStreamReader(fIn);
               br = new BufferedReader(isr);
          }
          catch(Exception e){
               Toast.makeText(HomeWindow.getAppContext(), (CharSequence) e, Toast.LENGTH_SHORT).show();
          }
          populateList();
     }

     /*
          createSubject: 2/12/15: creates a Subject class, appends the subjects.txt file
     */
     public void createSubject(String subjectName) {
          try {
               fOut = context.openFileOutput(sb, Context.MODE_WORLD_READABLE | Context.MODE_APPEND);
               Subjects newSubject = new Subjects(subjectName);
               fOut.write((subjectName + "\n").getBytes());
               fOut.close();
               subjectsList.add(newSubject);
          }
          catch (Exception e) {
               Toast.makeText(HomeWindow.getAppContext(), (CharSequence) e, Toast.LENGTH_SHORT).show();
          }
     }

     /*
          getSubjectList: 2/15/2015: gets the List of subjects
      */
     public List getSubjectsList(){
        return subjectsList;
     }

     /*
          populateList:
      */
     public void populateList(){
          Subjects currentSubject;
          String temp;
          String temp2;
          String subjectName;
          try {
               while((temp= br.readLine()) != null){
                    Subjects newSub = new Subjects(temp);
                    subjectsList.add(newSub);
               }
          }catch(Exception e){
               Toast.makeText(HomeWindow.getAppContext(), (CharSequence) e, Toast.LENGTH_SHORT).show();
          }
          /*
          try {
               for(int i = 0; i < subjectsList.size();i++){
                    currentSubject = subjectsList.get(i);
                    subjectName= currentSubject.getSubjectName();
                    fIn2 = context.openFileInput(subjectName+".txt");
                    isr2 = new InputStreamReader(fIn2);
                    br2 = new BufferedReader(isr2);
                    while((temp2 = br2.readLine())!=null){
                         Notes newNotes = new Notes(subjectName,temp2);
                         currentSubject.getNotesList().add(newNotes);
                    }
                    //fIn2.close();
               }
          } catch (Exception e){
               Toast.makeText(HomeWindow.getAppContext(), (CharSequence) e, Toast.LENGTH_SHORT).show();
          }*/
     }
}