package ca.ualberta.mmcote.mmcote__habiittracker;

import org.junit.Test;

import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by mmcote on 2016-10-02.
 */
public class HabitTest {
    @Test
    public void getDefinition() throws Exception {
        String def = "habit definition test";
        HashMap<Integer, Boolean> days = new HashMap<Integer, Boolean>();
        Date creationDate = new Date();
        Habit habit = new Habit(def, days, creationDate);
        assertFalse(habit.getDefinition() == "This is not the definition");
        assertTrue(habit.getDefinition() == def);
    }

    @Test
    public void getCreationDate() throws Exception {
        String def = "habit definition test";
        HashMap<Integer, Boolean> days = new HashMap<Integer, Boolean>();
        Date creationDate = new Date();
        Habit habit = new Habit(def, days, creationDate);
        assertFalse(habit.getCreationDate() == new Date());
        assertTrue(habit.getCreationDate() == creationDate);
    }

    @Test
    public void habitComplete() throws Exception {
        String def = "habit definition test";
        HashMap<Integer, Boolean> days = new HashMap<Integer, Boolean>();
        Date creationDate = new Date();
        Habit habit = new Habit(def, days, creationDate);
        assertFalse(habit.numberOfCompletions() == 1);
        habit.habitComplete();
        assertTrue(habit.numberOfCompletions() == 1);
    }

    @Test
    public void getHabitCompletions() throws Exception {
        String def = "habit definition test";
        HashMap<Integer, Boolean> days = new HashMap<Integer, Boolean>();
        Date creationDate = new Date();
        Habit habit = new Habit(def, days, creationDate);
        assertFalse(habit.numberOfCompletions() == 1);
        habit.habitComplete();
        assertTrue(habit.numberOfCompletions() == 1);
        habit.habitComplete();
        assertTrue(habit.numberOfCompletions() == 2);
        habit.habitComplete();
        assertTrue(habit.numberOfCompletions() == 3);
        habit.habitComplete();
        assertTrue(habit.numberOfCompletions() == 4);
    }

    @Test
    public void removeCompletion() throws Exception {
        String def = "habit definition test";
        HashMap<Integer, Boolean> days = new HashMap<Integer, Boolean>();
        Date creationDate = new Date();
        Habit habit = new Habit(def, days, creationDate);
        habit.habitComplete();
        assertTrue(habit.numberOfCompletions() == 1);
        String habitDate = habit.getHabitCompletions().get(0);
        habit.removeCompletion(habitDate);
        assertTrue(habit.numberOfCompletions() == 0);
    }

    @Test
    public void numberOfCompletions() throws Exception {
        String def = "habit definition test";
        HashMap<Integer, Boolean> days = new HashMap<Integer, Boolean>();
        Date creationDate = new Date();
        Habit habit = new Habit(def, days, creationDate);
        habit.habitComplete();
        assertTrue(habit.numberOfCompletions() == 1);
        String habitDate = habit.getHabitCompletions().get(0);
        habit.removeCompletion(habitDate);
        assertTrue(habit.numberOfCompletions() == 0);
        habit.habitComplete();
        habit.habitComplete();
        assertTrue(habit.numberOfCompletions() == 2);
    }

    @Test
    public void setWeeklyOccurrences() throws Exception {
        String def = "habit definition test";
        HashMap<Integer, Boolean> days = new HashMap<Integer, Boolean>();
        Date creationDate = new Date();
        Habit habit = new Habit(def, days, creationDate);
        days.put(0, true);
        days.put(1,false);
        days.put(2, true);
        days.put(3,false);
        days.put(4,false);
        days.put(5, true);
        days.put(6, false);
        habit.setWeeklyOccurrences(days);
        assertTrue(habit.getDayBoolean(0));
        assertFalse(habit.getDayBoolean(1));
        assertTrue(habit.getDayBoolean(2));
        assertFalse(habit.getDayBoolean(3));
        assertFalse(habit.getDayBoolean(4));
        assertTrue(habit.getDayBoolean(5));
        assertFalse(habit.getDayBoolean(6));
    }
}