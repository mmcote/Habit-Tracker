package ca.ualberta.mmcote.mmcote__habiittracker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mmcote on 2016-09-18.
 * The purpose of this class is to hold all of the habits created and still of interest.
 */

public class HabitList {
    /* habits: given the habit definition we use this a the key to access the Habit object
        this representation was choosen as the ListView will only have the habit definition
        to access the rest of the habit data */
    private HashMap<String,Habit> habits;

    HabitList() {
        habits = new HashMap<String, Habit>();
    }

    /* Used to retrieve individual Habit objects from the HashMap provided with the Habit
        definition */
    public Habit getHabit(String habitDefinition) {
        if (habits.containsKey(habitDefinition)) {
            return habits.get(habitDefinition);
        } else {
            return null;
        }
    }

    // Add a Habit object to the habits HashMap
    public void addHabit(Habit habit) {
        this.habits.put(habit.getDefinition(), habit);
    }

    // Remove a habit object from the habit lsit given the habit description from the ListView
    // element selected
    public void removeHabit(String habitDefinition) {
        this.habits.remove(habitDefinition);
    }

    /* This function will allow the users of this model to easily request all of the HashMap Keys
        (habit definitions) */
    public ArrayList<String> getHabitDefinitions() {
        ArrayList<String> stringHabits = new ArrayList<String>(this.habits.keySet());
        return stringHabits;
    }
}

