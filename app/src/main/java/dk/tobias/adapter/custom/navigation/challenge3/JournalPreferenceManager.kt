package dk.tobias.adapter.custom.navigation.challenge3

import android.content.Context
import com.google.gson.Gson

class JournalPreferenceManager(context: Context) {

    private val MY_PREF = "MySharedJournalPreference"
    private val PREFS_KEY_JOURNAL_SET = "MyJournalSetKey"

    private val preference = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)

    fun saveJournal(journal : JournalEntry) {
        val myJournalList = getHashSet()

        //Initialize Gson
        val gson = Gson()
        val journalJsonString = gson.toJson(journal)

        myJournalList.add(journalJsonString)

        //Save list to preferences
        val editor = preference.edit()
        editor.putStringSet(PREFS_KEY_JOURNAL_SET, myJournalList)
        editor.apply()
    }

    fun removeJournal(journal : JournalEntry, journalComplete:ArrayList<JournalEntry> ){
        val editor=preference.edit()
        //The shared preferences are cleared, and we built it up again
        //without the journal entry, that needs to be deleted.
        editor.remove(PREFS_KEY_JOURNAL_SET)
        editor.apply()

        //Get the empty HastSet
        val myJournalList = getHashSet()

        //Initialize Gson
        val gson = Gson()

        //browse through the complete journal
        for (entry in journalComplete) {
            //the entry to be removed
            val journalJsonString = gson.toJson(journal)

            //the iterator
            val entryJsonString = gson.toJson(entry)

            //don't save the entry to be deleted
            if (entryJsonString == journalJsonString){}
            else{
                //Add and save the rest
                myJournalList.add(entryJsonString)
                //Save list to preferences
                editor.putStringSet(PREFS_KEY_JOURNAL_SET, myJournalList)
                editor.apply()
            }
        }
    }

    fun getSavedJournals() : ArrayList<JournalEntry> {
        val myJournalList = getHashSet()

        val journalArrayList = ArrayList<JournalEntry>()

        val gson = Gson()

        for (jsonEntry in myJournalList) {
            val currentEntry = gson.fromJson(jsonEntry, JournalEntry::class.java)
            journalArrayList.add(currentEntry)

        }
        //sort by timestamp
        val sortedList=journalArrayList.sortedByDescending { it.timestamp }
        return ArrayList(sortedList)
    }

    private fun getHashSet() : HashSet<String> = HashSet(preference.getStringSet(PREFS_KEY_JOURNAL_SET, HashSet<String>()))
}