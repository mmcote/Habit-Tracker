package ca.ualberta.mmcote.mmcote__habiittracker;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by mmcote on 2016-09-25.
 */

public class HabitListController extends Observable {
    private static final String FILENAME = "file.sav";
    private Context currentContext;
    private HabitList habitList;

    HabitListController(Context context) {
        this.currentContext = context;
        retrieveHabitList();
    }

    HabitListController(Observer observer, Context context) {
        this.currentContext = context;
        retrieveHabitList();
        addObserver(observer);
    }

    public HabitList getHabitList() {
        return habitList;
    }

    public ArrayList<String> getHabitListDefinitions() {
        return habitList.getHabitDefinitions();
    }

    public void addToHabitList(Habit newHabit) {
        habitList.addHabit(newHabit);
        saveInFile();
    }

    public void deleteFromHabitList(String habitDefinition) {
        habitList.removeHabit(habitDefinition);
        saveInFile();
    }

    public Integer getNumCompletions(String habitDefinition) {
        return habitList.getHabit(habitDefinition).numberOfCompletions();
    }

    public Integer getNumCompletionsToday(String habitDefinition) {
        return habitList.getHabit(habitDefinition).numberOfTodaysCompletions();
    }

    public ArrayList<String> getHabitCompletions(String habitDefinition) {
        return habitList.getHabit(habitDefinition).getHabitCompletions();
    }

    public void addCompletion(String habitDefinition) {
        habitList.getHabit(habitDefinition).habitComplete();
        saveInFile();
    }

    public void deleteCompletion(String habitDefinition, String dateString) {
        habitList.getHabit(habitDefinition).removeCompletion(dateString);
        saveInFile();
    }

    public HashMap<Integer, Boolean> sethabitOccurencesInit(){
        HashMap<Integer, Boolean> habitOccurences = new HashMap<Integer, Boolean>();
        for (int i = 0; i <= 6; i++) {
            habitOccurences.put(i, Boolean.FALSE);
        }
        return habitOccurences;
    }

    public void setWeeklyOccurences(String habitDefinition, HashMap<Integer,Boolean> weeklyOccurences) {
        habitList.getHabit(habitDefinition).setWeeklyOccurrences(weeklyOccurences);
        saveInFile();
    }

    public void checkHabitList() {
        HabitList inFile = loadFromFile();
        if (!habitList.equals(inFile)){
            habitList = inFile;
            setChanged();
            notifyObservers();
        }
    }

    /*  function to get and return the shared HabitList meaning
    it will either update the callee or return a new HabitList
    to build off of */
    private void retrieveHabitList() {
        if (fileExists()) {
            habitList = loadFromFile();
        } else {
            habitList = new HabitList();
        }
    }

    /* http://stackoverflow.com/questions/8867334/check-if-a-file-exists-before-calling-openfileinput */
    private Boolean fileExists() {
        File file = currentContext.getFileStreamPath(FILENAME);
        if(file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    private void saveInFile() {
        try {
            // https://developer.android.com/reference/android/content/Context.html#openFileOutput(java.lang.String,%20int)
            // openFileOutput(/*The name of the file to open (String)*/, /*Operating mode (Int)*/);
            FileOutputStream fos = currentContext.openFileOutput(FILENAME, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(habitList, out);
            out.flush();

            fos.close();

            setChanged();
            notifyObservers();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    private HabitList loadFromFile() {
        try {
            FileInputStream fis = currentContext.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listtype = new TypeToken<HabitList>(){}.getType();

            return gson.fromJson(in, listtype);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            return new HabitList();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
