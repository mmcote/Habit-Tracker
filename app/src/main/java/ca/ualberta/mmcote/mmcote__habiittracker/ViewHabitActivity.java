package ca.ualberta.mmcote.mmcote__habiittracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by mmcote on 2016-09-18.
 * The purpose of this class is to easily allow users to see the habit and its details and keep an
 * accurate history of when the habit was completed, also to delete habits and completions.
 */

public class ViewHabitActivity extends AppCompatActivity implements Observer {
    /* HabitListController is the controller which the view will communicate with, thereafter
        the controller will ask the model to manipulate the data stored within itself */
    private HabitListController hlc;
    /* habitListKey, is transfered from the prior intent to communicate which habit is to be
        represented in this ViewHabitActivity */
    private String habitListKey;
    /* UI elements:
        -adapter: holder of all data currently presented in the UI, this will be used to instantly
        update data as the user manipulates completions
        -deleteAlertBuilder: this AlertDialog.Builder is used to create both the completion and
        habit delete alerts to confirm their deletions
        -completionListView: this ListView element is used to show the historical data of when
        the habit was completed
        -todayCompletionsTextView: this textview shows the number of times the habit is completed, this is a
        class variable unlike textviewTitle as this may change if the user decides to delete
        completions
     */
    private ArrayAdapter<String> adapter;
    private AlertDialog.Builder deleteAlertBuilder;
    private ListView completionListView;
    private TextView todayCompletionsTextView;
    private TextView completionsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit);

        // This section ensures that the key of the habit has been trasfered and is set
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
        } else {
            habitListKey = extras.getString("HABIT_KEY");
            // TODO: 2016-09-29 Then properly check that the HABIT_KEY is in the habitList, if not return
        }

        // Initialize the HabitListController
        hlc = new HabitListController(this, getApplicationContext());

        // Initialize the title TextView, that represents the habit definition
        TextView textViewTitle = (TextView) findViewById(R.id.viewHabitLargeText);
        textViewTitle.setText(habitListKey);

        // Initialize the TextView which shows the number of completions
        todayCompletionsTextView = (TextView) findViewById(R.id.viewHabitCompletionsNumber);
        todayCompletionsTextView.setText(String.valueOf(hlc.getNumCompletionsToday(habitListKey)));

        completionsTextView = (TextView) findViewById(R.id.viewHabitTotalCompletionsNumber);
        completionsTextView.setText(String.valueOf(hlc.getNumCompletions(habitListKey)));

        TextView sunIcon = (TextView) findViewById(R.id.sunIcon);
        if (hlc.getHabitList().getHabit(habitListKey).getDayBoolean(0)) { sunIcon.setBackgroundColor(Color.GRAY); };
        TextView monIcon = (TextView) findViewById(R.id.monIcon);
        if (hlc.getHabitList().getHabit(habitListKey).getDayBoolean(1)) { monIcon.setBackgroundColor(Color.GRAY); };
        TextView tuesIcon = (TextView) findViewById(R.id.tuesIcon);
        if (hlc.getHabitList().getHabit(habitListKey).getDayBoolean(2)) { tuesIcon.setBackgroundColor(Color.GRAY); };
        TextView wedIcon = (TextView) findViewById(R.id.wedIcon);
        if (hlc.getHabitList().getHabit(habitListKey).getDayBoolean(3)) { wedIcon.setBackgroundColor(Color.GRAY); };
        TextView thursIcon = (TextView) findViewById(R.id.thursIcon);
        if (hlc.getHabitList().getHabit(habitListKey).getDayBoolean(4)) { thursIcon.setBackgroundColor(Color.GRAY); };
        TextView friIcon = (TextView) findViewById(R.id.friIcon);
        if (hlc.getHabitList().getHabit(habitListKey).getDayBoolean(5)) { friIcon.setBackgroundColor(Color.GRAY); };
        TextView satIcon = (TextView) findViewById(R.id.satIcon);
        if (hlc.getHabitList().getHabit(habitListKey).getDayBoolean(6)) { satIcon.setBackgroundColor(Color.GRAY); };


        // Initialize the AlertDialog that will be used to confirm deletions of completions and habits
        deleteAlertBuilder = new AlertDialog.Builder(ViewHabitActivity.this);

        final Button allHabitsButton = (Button) findViewById(R.id.viewHabitButton);
        allHabitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final Button deleteHabitButton = (Button) findViewById(R.id.viewHabitDeleteHabitButton);
        deleteHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAlertBuilder.setMessage("Are you sure you want to delete this habit?");

                deleteAlertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        hlc.deleteObserver(ViewHabitActivity.this);
                        hlc.deleteFromHabitList(habitListKey);
                        finish();
                    }
                });

                deleteAlertBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = deleteAlertBuilder.create();
                alertDialog.show();
            }
        });

        // Initialize the ListView
        completionListView = (ListView) findViewById(R.id.completionListView);

        adapter = new ArrayAdapter<String>(this, R.layout.list_item, hlc.getHabitCompletions(habitListKey));
        completionListView.setAdapter(adapter);

        completionListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                deleteAlertBuilder.setMessage("Are you sure you want to delete this completion?");

                deleteAlertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        hlc.deleteCompletion(habitListKey, ((TextView) view).getText().toString());
                        Toast.makeText(getApplicationContext(), "Completion Deleted" ,Toast.LENGTH_LONG).show();
                    }
                });

                deleteAlertBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = deleteAlertBuilder.create();
                alertDialog.show();

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
        adapter.clear();
        adapter.addAll(hlc.getHabitCompletions(habitListKey));
        todayCompletionsTextView.setText(String.valueOf(hlc.getNumCompletionsToday(habitListKey)));
        completionsTextView.setText(String.valueOf(hlc.getNumCompletions(habitListKey)));
        adapter.notifyDataSetChanged();
    }
}