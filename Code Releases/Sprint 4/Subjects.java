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
02/20/15 - Jimenez, Joni - Implemented Parcelable
File Creation Date:
Development Group: Cyan Worm
Client Group: Blue Navy
Purpose of software: WORM Subject Planner is a mobile platform application made to help
                     students organize their plans and notes according to subjects or
                     categories. It will contain a scheduler, calendar, and otes pad
                     specially designed for the user's convenience.

 */

package com.example.wormsubjectplanner;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

//a class per subject
public class Subjects implements Parcelable{
     private String subjectName; //the name of the file containing the list of notes
     private List<Notes> notesList;

     public Subjects(String subjectName){
          this.subjectName = subjectName;
          List<Notes> newNotes = new ArrayList<Notes>();
          notesList = newNotes;
         /*
          Notes notes = new Notes("CS 190","1","First Note");
          Notes notes2 = new Notes("CS 190","2","Second Note");
          newNotes.add(notes);
          newNotes.add(notes2);
          */
     }

     public Subjects(){

     }
     public Subjects(Parcel in){
          notesList = new ArrayList<Notes>();
          in.readTypedList(notesList,Notes.CREATOR);
          subjectName = in.readString();
     }

     //Sets subjectNames
     public void setSubjectName(String subjectName){
          this.subjectName = subjectName;
     }
     //Sets notes list
     public void setNotesList(List notesList){
          this.notesList = notesList;
     }
     //Gets subject name
     public String getSubjectName(){
          return subjectName;
     }
     //Gets notesList
     public List getNotesList(){
          return notesList;
     }


     @Override
     public int describeContents() {
          return 0;
     }

     @Override
     public void writeToParcel(Parcel dest, int flags) {
          dest.writeTypedList(notesList);
          dest.writeString(subjectName);
     }

     public static final Parcelable.Creator CREATOR =
               new Parcelable.Creator(){
                    public Subjects createFromParcel(Parcel in){
                         return new Subjects(in);
                    }
                    public Subjects[] newArray(int size){
                         return new Subjects[size];
                    }

               };
}