package ca.ualberta.mmcote.mmcote__habiittracker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by mmcote on 2016-09-17.
 * Habit has all the information needed to organize data and keep a history of when the habit was
 * completed.
 */

public class Habit {
    // Habit Description
    private String definition;
    // Habit Date Inserted
    private Date creationDate;
    // Individual Date objects will represent completions, recording when they were completed
    private ArrayList<Date> completionList = new ArrayList<Date>();
    // Which days of the week the Habit will occur on
    private HashMap<Integer, Boolean> weeklyOccurrences = new HashMap<Integer, Boolean>();

    Habit(String habitDefinition, HashMap<Integer, Boolean> weeklyOccurrences, Date inputDate) {
        this.definition = habitDefinition;
        this.creationDate = inputDate;
        this.weeklyOccurrences = weeklyOccurrences;
    }

    // Return the definition of the Habit
    public String getDefinition() {
        return definition;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    /* Input a new completion into the completionList, this corresponding to the input of a
        new date object */
    public void habitComplete() {
        this.completionList.add(new Date());
    }

    // Return a String ArrayList of date objects representing the habits completions
    public ArrayList<String> getHabitCompletions() {
        ArrayList<String> stringHabits = new ArrayList<String>();
        for (int i = 0; i < completionList.size(); ++i) {
            stringHabits.add(completionList.get(i).toString());
        }
        return stringHabits;
    }

    // Remove a completion (Date) from the completionList
    public void removeCompletion(String dateString) {
        for (int i = 0; i < completionList.size(); ++i) {
            if (completionList.get(i).toString().equals(dateString)) {
                completionList.remove(i);
                break;
            }
        }
    }

    //Return the number of completions since the addition of the habit
    public Integer numberOfCompletions() {
        return this.completionList.size();
    }

    public Integer numberOfTodaysCompletions() {
        Integer numCompletions = 0;
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = formatter.format(today);
        for (Date date : completionList) {
            String temp = formatter.format(date);
            if (temp.equals(todayString)) {
                numCompletions += 1;
            }
        }
        return numCompletions;
    }

    public void setWeeklyOccurrences(HashMap<Integer, Boolean> weeklyOccurrences) {
        this.weeklyOccurrences = weeklyOccurrences;
    }

    public Boolean getDayBoolean(Integer i) {
        return weeklyOccurrences.get(i);
    }
}
