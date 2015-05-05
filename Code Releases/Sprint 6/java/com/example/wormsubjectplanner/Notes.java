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
2/12/15: Patrick Leiniel H. Domingo: Notes Entity overhaul
2/26/15: Joni Jimenez: Implemented parcelable

File Creation Date:
Development Group: Cyan Worm
Client Group: Blue Navy
Purpose of software: WORM Subject Planner is a mobile platform application made to help
                     students organize their plans and notes according to subjects or
                     categories. It will contain a scheduler, calendar, and otes pad
                     specially designed for the user's convenience.

 */

package com.example.wormsubjectplanner;

import java.util.Date;
import java.io.*;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

//a class for each individual notes
public class Notes implements Parcelable{
     private String subject;
     private String title;
     private String content;


     /*Notes: 2/12/15: Instantiates a Note entity and instantly calls the setters for a first time creation of a note*/
     public Notes(String subject,String title,String content) {

          setTitle(title);
          setContent(content);
          setSubject(subject);
     }

     public Notes(Parcel in){
          subject = in.readString();
          title = in.readString();
          content = in.readString();

     }

     public Notes(){

     }

     /*setTitle: 2/12/15: sets the title of the Note by the given parameter */
     public void setTitle(String title){

          this.title = title;
     }
     /*setContent: 2/12/15: sets the title of the Note by the given parameter */
     public void setContent(String content){
          this.content = content;
     }
     /*setSubject: 2/12/15: sets the title of the Note by the given parameter */
     public void setSubject(String subject){
          this.subject = subject;
     }
     /*getTitle: 2/12/15: returns the title of the Note*/
     public String getTitle(){
          return title;
     }
     /*getTitle: 2/12/15: returns the content of the Note*/
     public String getContent(){
          return content;
     }
     /*getTitle: 2/12/15: returns the subject of the Note*/
     public String getSubject(){
          return subject;
     }


     @Override
     public int describeContents() {
          return 0;
     }

     @Override
     public void writeToParcel(Parcel dest, int flags) {
          dest.writeString(subject);
          dest.writeString(content);
          dest.writeString(title);
     }

     public static final Parcelable.Creator CREATOR =
               new Parcelable.Creator(){
                    public Notes createFromParcel(Parcel in){
                         return new Notes(in);
                    }

                    public Notes[] newArray(int size){
                         return new Notes[size];
                    }
               };
}