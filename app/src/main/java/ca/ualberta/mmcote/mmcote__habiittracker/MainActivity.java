package ca.ualberta.mmcote.mmcote__habiittracker;
/*
    HabitTracker: Posts habits and track habit completions
    Copyright (C) 2016 Michael Cote mmcote@ualberta.ca

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;


public class MainActivity extends AppCompatActivity implements Observer {
    private HabitListController hlc;
    private ListView habitListView;
    private HabitListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hlc = new HabitListController(this, MainActivity.this);
        habitListView = (ListView) findViewById(R.id.habitListView);
        final Button addHabitButton = (Button) findViewById(R.id.habitListButton);
        addHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addHabitIntent = new Intent(MainActivity.this, AddHabitActivity.class);
                startActivity(addHabitIntent);
            }
        });
        adapter = new HabitListAdapter(MainActivity.this, hlc.getHabitListDefinitions(), hlc.getHabitList());
        habitListView.setAdapter(adapter);

        habitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewHabitIntent = new Intent(MainActivity.this, ViewHabitActivity.class);
                viewHabitIntent.putExtra("HABIT_KEY", String.valueOf(parent.getItemAtPosition(position)));
                startActivity(viewHabitIntent);
            }
        });

        habitListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"Great job you completed the habit!", Toast.LENGTH_LONG).show();
                hlc.addCompletion(String.valueOf(parent.getItemAtPosition(position)));
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        hlc.checkHabitList();
    }

    @Override
    public void update(Observable o, Object arg) {
        adapter.refreshHabitDefinitions(hlc.getHabitListDefinitions(), hlc.getHabitList());
    }
}
