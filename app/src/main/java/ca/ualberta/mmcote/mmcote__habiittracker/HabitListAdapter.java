package ca.ualberta.mmcote.mmcote__habiittracker;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

/**
 * Created by mmcote on 2016-09-30.
 *
 * The purpose of this is to properly map view elements with the proper data they should be given.
 * Also customizing the layout to portray information such as whether or not the habit should be done
 * today or not, and whether it has been completed today or not.
 *
 */

public class HabitListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private ArrayList<String> habitDefinitions;
    private HabitList habits;

    public HabitListAdapter(Context context, ArrayList<String> habitDefinitions, HabitList habits) {
        super(context, R.layout.custom_row, habitDefinitions);
        this.context = context;
        this.habitDefinitions = habitDefinitions;
        this.habits = habits;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater habitListInflater = LayoutInflater.from(getContext());
        View customView = habitListInflater.inflate(R.layout.custom_row, parent, false);

        String singleHabitDefinition = getItem(position);
        TextView habitDefinitionTextView = (TextView) customView.findViewById(R.id.customRowHabitDefinition);
        habitDefinitionTextView.setText(singleHabitDefinition);
        ImageView habitStatusImage = (ImageView) customView.findViewById(R.id.customRowImageView);
        Boolean today = false;
        if (checkIfToday(singleHabitDefinition)) {
            today = true;
            habitDefinitionTextView.setBackgroundColor(Color.WHITE);
            habitDefinitionTextView.setTextColor(Color.BLACK);
            habitStatusImage.setBackgroundColor(Color.WHITE);
        } else {
            habitDefinitionTextView.setBackgroundColor(Color.BLACK);
            habitDefinitionTextView.setTextColor(Color.WHITE);
            habitStatusImage.setBackgroundColor(Color.BLACK);
        }

        switch (habits.getHabit(singleHabitDefinition).numberOfTodaysCompletions()) {
            case 0:
                if (today) {
                    habitStatusImage.setImageResource(R.drawable.ic_clear_black_36dp);
                } else {
                    habitStatusImage.setImageResource(R.drawable.ic_clear_white_36dp);
                }
                break;
            case 1:
                if (today) {
                    habitStatusImage.setImageResource(R.drawable.ic_done_black_36dp);
                } else {
                    habitStatusImage.setImageResource(R.drawable.ic_done_white_36dp);
                }
                break;
            default:
                if (today) {
                    habitStatusImage.setImageResource(R.drawable.ic_done_all_black_36dp);
                } else {
                    habitStatusImage.setImageResource(R.drawable.ic_done_all_white_36dp);
                }
                break;
        }
        return customView;
    }

    public void refreshHabitDefinitions(ArrayList<String> habitDefinitions, final HabitList habits) {
        this.habitDefinitions.clear();
        this.habitDefinitions.addAll(habitDefinitions);
        this.habits = habits;
        sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return habits.getHabit(o1).getCreationDate().compareTo(habits.getHabit(o2).getCreationDate());
            }
        });
        checkWhatsRelevantToday();
        notifyDataSetChanged();
    }

    public void checkWhatsRelevantToday() {
        ArrayList<String> happeningToday = new ArrayList<String>();
        ArrayList<String> happeningNotToday = new ArrayList<String>();

        for (String def : this.habitDefinitions) {
            if (checkIfToday(def)) {
                happeningToday.add(def);
            } else {
                happeningNotToday.add(def);
            }
        }
        happeningToday.addAll(happeningNotToday);
        this.habitDefinitions.clear();
        this.habitDefinitions.addAll(happeningToday);
    }

    private Boolean checkIfToday(String habitDefinition) {
        Calendar day = Calendar.getInstance();
        Integer dayOfWeek = day.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 7) {
            dayOfWeek = 0;
        } else {
            dayOfWeek = dayOfWeek - 1;
        }
        return this.habits.getHabit(habitDefinition).getDayBoolean(dayOfWeek);
    }
}
