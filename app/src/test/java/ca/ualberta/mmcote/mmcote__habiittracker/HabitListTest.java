package ca.ualberta.mmcote.mmcote__habiittracker;

import org.junit.Test;

import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by mmcote on 2016-10-02.
 */
public class HabitListTest {
    @Test
    public void getHabit() throws Exception {
        HabitList habitList = new HabitList();

        String def = "habit definition test";
        HashMap<Integer, Boolean> days = new HashMap<Integer, Boolean>();
        Date creationDate = new Date();
        Habit habit = new Habit(def, days, creationDate);

        String def2 = "habit definition test 2";
        Habit habit2 = new Habit(def2, days, creationDate);

        habitList.addHabit(habit);
        habitList.addHabit(habit2);

        assertFalse(habitList.getHabit(def) == habit2);
        assertFalse(habitList.getHabit(def2) == habit);
        assertTrue(habitList.getHabit(def) == habit);
        assertTrue(habitList.getHabit(def2) == habit2);
        assertTrue(habitList.getHabit("fake def") == null);
    }

    @Test
    public void addHabit() throws Exception {
        HabitList habitList = new HabitList();

        String def = "habit definition test";
        HashMap<Integer, Boolean> days = new HashMap<Integer, Boolean>();
        Date creationDate = new Date();
        Habit habit = new Habit(def, days, creationDate);

        String def2 = "habit definition test 2";
        Habit habit2 = new Habit(def2, days, creationDate);

        habitList.addHabit(habit);
        habitList.addHabit(habit2);

        assertFalse(habitList.getHabitDefinitions().size() == 0);
        assertFalse(habitList.getHabit(def) == habit2);
        assertFalse(habitList.getHabit(def2) == habit);
        assertTrue(habitList.getHabitDefinitions().size() == 2);
        assertTrue(habitList.getHabit(def) == habit);
        assertTrue(habitList.getHabit(def2) == habit2);
    }

    @Test
    public void removeHabit() throws Exception {
        HabitList habitList = new HabitList();

        String def = "habit definition test";
        HashMap<Integer, Boolean> days = new HashMap<Integer, Boolean>();
        Date creationDate = new Date();
        Habit habit = new Habit(def, days, creationDate);

        String def2 = "habit definition test 2";
        Habit habit2 = new Habit(def2, days, creationDate);

        habitList.addHabit(habit);
        habitList.addHabit(habit2);

        assertFalse(habitList.getHabit(def) == habit2);
        assertFalse(habitList.getHabit(def2) == habit);
        assertTrue(habitList.getHabit(def) == habit);
        assertTrue(habitList.getHabit(def2) == habit2);

        habitList.removeHabit(def);
        habitList.removeHabit(def2);

        assertTrue(habitList.getHabit(def) == null);
        assertTrue(habitList.getHabit(def2) == null);
    }

    @Test
    public void getHabitDefinitions() throws Exception {
        HabitList habitList = new HabitList();

        String def = "habit definition test";
        HashMap<Integer, Boolean> days = new HashMap<Integer, Boolean>();
        Date creationDate = new Date();
        Habit habit = new Habit(def, days, creationDate);

        String def2 = "habit definition test 2";
        Habit habit2 = new Habit(def2, days, creationDate);

        habitList.addHabit(habit);
        habitList.addHabit(habit2);

        assertFalse(habitList.getHabitDefinitions().get(0) == def2);
        assertFalse(habitList.getHabitDefinitions().get(1) == def);
        assertTrue(habitList.getHabitDefinitions().get(0) == def);
        assertTrue(habitList.getHabitDefinitions().get(1) == def2);
        assertTrue(habitList.getHabitDefinitions().size() == 2);
    }

}