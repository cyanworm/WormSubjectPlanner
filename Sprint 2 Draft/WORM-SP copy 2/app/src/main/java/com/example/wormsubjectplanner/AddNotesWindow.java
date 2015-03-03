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
2/13/15 - Jannieca Camba. Some minor changes.

File Creation Date:
Development Group: Cyan Worm
Client Group: Blue Navy
Purpose of software: WORM Subject Planner is a mobile platform application made to help
                     students organize their plans and notes according to subjects or
                     categories. It will contain a scheduler, calendar, notes pad, and doodles
                     pad specially designed for the user's convenience.

*/


package com.example.wormsubjectplanner;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class AddNotesWindow extends ActionBarActivity {

	EditText addnotes_title_field, addnotes_date_field, addnotes_content_field;
	String title = "", content= "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_note);
		
        /*Button addnotes_save = (Button)findViewById(R.id.addnotes_save);
        addnotes_title_field = (EditText) findViewById(R.id.addnotes_title_field);
		addnotes_date_field = (EditText) findViewById(R.id.addnotes_date_field);
		addnotes_content_field = (EditText) findViewById(R.id.addnotes_content_field);

        //save button to SampleViewNoteWindow
        addnotes_save.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View arg0)
        	{
        		title = addnotes_title_field.getText().toString().trim();
	        	content = addnotes_date_field.getText().toString().trim();
	        	if(title.length() != 0){
	        		Toast.makeText(getApplicationContext(), "Saving Note", 					                  
							Toast.LENGTH_SHORT).show();
	        		SaveNotesController snc = new SaveNotesController(title,content);
	        		snc.callFileHandler();
	        	}
	        	else{
	        		
	        		Toast.makeText(getApplicationContext(), "Invalid title",
							Toast.LENGTH_SHORT).show();
	        	}
        		Intent viewnotes_screen = new Intent(AddNotesWindow.this, SampleViewNoteWindow.class);
        		startActivity(viewnotes_screen);
        		
        	}
        });*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_notes, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}