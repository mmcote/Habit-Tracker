<h1>CMPUT 301 - Android Habit Tracker</h1>
Michael Cote

<h2>Demo:</h2>
<a href="https://www.youtube.com/watch?v=-kewEkleU3g">Youtube Video</a>

<h2>Setup:</h2>
Android 4.4 (API Level 19)
Android Studio Version 2.14
Android Plugin Version 2.2
Java JDK 1.8.0
GSON 2.7

<h2>Usage:</h2>
<h3>MainActivity (View all habits homepage)</h3>
  
  -Users can see all the habits inputted thus far, habits are separated in black and white on whether they happen on the current weekday or not. White: the habit should be completed today, black: the habit was not planned to be done today but still can be. Then within the two categories they are organized by their creation dates. 
  
  -The user can keep track of if they have completed the habit today or not by pressing and holding the habit, if it is the first time completing the habit today then the X will switch to a checkmark, any time after this will just appear as a multiple checkmark. Saying that you have completed this habit multiple times today. These badges will reset at the end of each day or if you delete completions.

  -Users can also click on the habit to view more details of the habit and delete completions or the habit.
  
<h3>AddHabitActivity (Add a habit page)</h3>

  -Users can add habits by filling in a definition (cannot be empty), giving an optional date other than the default current day, and specifying which days of the week the habit should occur on.
  
<h3>ViewHabitActivity (Detail view of a habit)</h3>

  -Users can see which days the habit will occur on and when past completions happened. They can also see the full amount of completions since the creation of the habit, or just the amount of completions today.
  
  -Users can also edit the habit in this view. They are able to delete false completions or delete the habit entirely.
  
  -If a user wants to change the days the habit occurs on, they need to go back to the add habit page and enter in the habit definition, and specify the new days, this will overwrite the prior habit while maintaining the old completions and creation date.
  
<h3>Sources</h3>

  -<a href="http://stackoverflow.com/questions/8867334/check-if-a-file-exists-before-calling-openfileinput">Check if file exists</a>
  
  -<a href="https://developer.android.com/index.html">Android Docs</a> - Used generally throughout the whole project, especially with the creation of the UI.
  
  -<a href="https://github.com/joshua2ua/lonelyTwitter">lonelyTwitter</a> - Used as a guide to using GSON.
  
  -<a href="http://www.tutorialspoint.com/android/android_datepicker_control.htm">Tutorials Point - Date Picker</a> - Used as a guide to creating the datePickerDialog.
