package dk.tobias.adapter.custom.navigation.challenge3

import android.content.Context
import com.google.gson.Gson

class JournalPreferenceManager(context: Context) {

    private val MY_PREF = "MySharedJournalPreference"
    private val PREFS_KEY_JOURNAL_SET = "MyJournalSetKey"

    private var preference = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)

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
/*
    fun removeJournal(journal : JournalEntry){
        val editor=preference.edit()
        editor.remove(journal.title)
        editor.apply()
    }
*/
    fun getSavedJournals() : ArrayList<JournalEntry> {
        val myJournalList = getHashSet()

        val journalArrayList = ArrayList<JournalEntry>()

        val gson = Gson()

        for (jsonEntry in myJournalList) {
            val currentEntry = gson.fromJson(jsonEntry, JournalEntry::class.java)
            journalArrayList.add(currentEntry)
        }
        return journalArrayList
    }

    private fun getHashSet() : HashSet<String> = HashSet(preference.getStringSet(PREFS_KEY_JOURNAL_SET, HashSet<String>()))
}