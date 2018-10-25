package dk.tobias.adapter.custom.navigation.challenge3

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var dataArray : ArrayList<JournalEntry>
    private lateinit var journalAdapter: JournalListAdapter
    private lateinit var preferenceManager : JournalPreferenceManager

    private val RC_SECOND_ACTIVITY=1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            val i = Intent(this, NewJournalActivity::class.java)
            startActivityForResult(i,RC_SECOND_ACTIVITY)
        }

        //Click to view list entry
        main_list_view.setOnItemClickListener {
                _: AdapterView<*>?, _: View?, position: Int, _: Long ->
            val extraTitle = dataArray[position].title
            val extraDescription = dataArray[position].description
            val i = Intent(this, ViewJournalActivity::class.java)
            i.putExtra("viewTitle", extraTitle)
            i.putExtra("viewDescription", extraDescription)
            startActivity(i)
        }

        //Long click to remove list entry. Uses AlertDialog for confirmation
        main_list_view.setOnItemLongClickListener { _, _, position, _->
            val alert = AlertDialog.Builder(this)
            alert.setTitle(getString(R.string.alertDeleteConfirmationTitle))
            alert.setMessage(getString(R.string.alertDeleteConfirmationText))
            alert.setPositiveButton(getString(R.string.alertSetPositiveButtonText)) { _, _->
                //Delete list entry
                //preferenceManager.removeJournal(dataArray[position])
                dataArray.remove(dataArray[position])
                journalAdapter.notifyDataSetChanged()
            }
            alert.setNegativeButton(getString(R.string.alertSetNegativeButtonText)){ dialog, which ->
                //Do nothing
            }
            val dialog: AlertDialog = alert.create()
            dialog.show()
            true
        }

        preferenceManager = JournalPreferenceManager(this)
        dataArray = preferenceManager.getSavedJournals()
        journalAdapter = JournalListAdapter(this, 0, dataArray)
        main_list_view.adapter = journalAdapter
    }

    private fun updateListView() {
        dataArray = preferenceManager.getSavedJournals()
        journalAdapter.clear()
        journalAdapter.addAll(dataArray)
        journalAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if(requestCode==RC_SECOND_ACTIVITY){
        if(resultCode== Activity.RESULT_OK){
            val title = data?.getStringExtra("title")
            val description= data?.getStringExtra("description")
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            val timestamp = current.format(formatter)
            val newEntry = JournalEntry(title.toString(),description.toString(),timestamp)
            preferenceManager.saveJournal(newEntry)
            updateListView()
        }
    }
}
}


