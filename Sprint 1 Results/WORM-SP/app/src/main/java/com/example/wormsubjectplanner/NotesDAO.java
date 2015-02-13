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
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class NotesDAO {


    List<Notes> notesList = new ArrayList<Notes>();
    private Context context;
    private Notes currentNote;
    private String noteTitle;
    private String noteContent;
    private String noteSubject;
//     private String noteCreationDate;

    /* NotesDao: 2/12/15: Patrick Leiniel H. Domingo: instantiates NotesDAO and gets the context of the app */
    public NotesDAO() {
        context = HomeWindow.getAppContext();
    }

    /* createNote: 2/12/15: Patrick Leiniel H. Domingo: called when creating a new Note */
    public void createNote(String title, String content, String subject) {
        File crfile = new File(title + ".txt");
        try {
            FileOutputStream fOut = context.openFileOutput(title, Context.MODE_WORLD_READABLE | Context.MODE_APPEND);
            fOut.write((title + "\n").getBytes());
            fOut.write(content.getBytes());
            fOut.close();
            Notes newNote = new Notes(subject, title, content);
            notesList.add(newNote);
        } catch (Exception e) {
            Toast.makeText(HomeWindow.getAppContext(), (CharSequence) e, Toast.LENGTH_SHORT).show();
        }
    }

}
