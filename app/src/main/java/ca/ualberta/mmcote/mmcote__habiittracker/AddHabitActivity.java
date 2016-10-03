package ca.ualberta.mmcote.mmcote__habiittracker;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by mmcote on 2016-09-18.
 * The purpose of this class is to allow the user to easily add a habit through the interface.
 */

public class AddHabitActivity extends AppCompatActivity {
    private EditText descriptionEditText; // habit description EditText
    private HabitListController hlc;
    private HashMap<Integer, Boolean> habitOccurences;
    private EditText startDateEditText;
    private Date inputDate;
    private SimpleDateFormat formatter;
    private enum days {
        Sunday(0),
        Monday(1),
        Tuesday(2),
        Wednesday(3),
        Thursday(4),
        Friday(5),
        Saturday(6);

        private final int numberRepresentation;

        days(int numberRepresentation) {
            this.numberRepresentation = numberRepresentation;
        }

        public int numberRepresentation() {
            return numberRepresentation;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        // Initialize most habitListController
        hlc = new HabitListController(getApplicationContext());

        // Initialize the habitOccurences
        habitOccurences = hlc.sethabitOccurencesInit();

        // Initialize EditText so we can utilize this later on
        descriptionEditText = (EditText) findViewById(R.id.addHabitEditText);

        startDateEditText = (EditText) findViewById(R.id.addHabitStartDate);
        startDateEditText.setFocusable(false);

        inputDate = new Date();

        formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = formatter.format(inputDate);

        startDateEditText.setText(todayString);
        startDateEditText.setOnClickListener( new View.OnClickListener() {
            Calendar currentHabitDate = Calendar.getInstance();
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddHabitActivity.this, startDateDialog,
                        currentHabitDate.get(Calendar.YEAR), currentHabitDate.get(Calendar.MONTH),
                        currentHabitDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        // Initialize Switches, for now they are just repeating the text of the button
        // must be connected to variable which will be changed
        CompoundButton.OnCheckedChangeListener switchListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    habitOccurences.put(days.valueOf(buttonView.getText().toString()).numberRepresentation(), Boolean.TRUE);
                } else {
                    habitOccurences.put(days.valueOf(buttonView.getText().toString()).numberRepresentation(), Boolean.FALSE);
                }
            }
        };

        ((Switch) findViewById(R.id.addHabitMonday)).setOnCheckedChangeListener(switchListener);
        ((Switch) findViewById(R.id.addHabitTuesday)).setOnCheckedChangeListener(switchListener);
        ((Switch) findViewById(R.id.addHabitWednesday)).setOnCheckedChangeListener(switchListener);
        ((Switch) findViewById(R.id.addHabitThursday)).setOnCheckedChangeListener(switchListener);
        ((Switch) findViewById(R.id.addHabitFriday)).setOnCheckedChangeListener(switchListener);
        ((Switch) findViewById(R.id.addHabitSaturday)).setOnCheckedChangeListener(switchListener);
        ((Switch) findViewById(R.id.addHabitSunday)).setOnCheckedChangeListener(switchListener);


        final Button saveHabitButton = (Button) findViewById(R.id.addHabitButton);
        saveHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = descriptionEditText.getText().toString();
                if (text.equals("")) {
                    return;
                } else if (hlc.getHabitListDefinitions().contains(text)) {
                    Toast.makeText(AddHabitActivity.this, "Habit is already made. Although, we have updated when you would like the habit to occur.", Toast.LENGTH_LONG).show();
                    hlc.setWeeklyOccurences(text, habitOccurences);
                    finish();
                }
                Habit tempHabit = new Habit(text, habitOccurences, inputDate);
                hlc.addToHabitList(tempHabit);
                finish();

            }
        });
    }

    DatePickerDialog.OnDateSetListener startDateDialog = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            startDateEditText.setText(year+"-"+(month + 1)+"-"+dayOfMonth);
            try {
                inputDate = formatter.parse(year+"-"+(month+1)+"-"+dayOfMonth);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };
}
