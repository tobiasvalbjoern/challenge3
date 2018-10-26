package dk.tobias.adapter.custom.navigation.challenge3

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_new_journal.*

class NewJournalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_journal)
        val toolbar = findViewById<Toolbar>(R.id.new_journal_toolbar)
        setSupportActionBar(toolbar)
        //You can come back to home from here.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //If we got information from View Journal, we want to add them to the
        //title and description fields. So the user can edit his journal.
        val extras = intent.extras ?: return
        activity_new_journal_title.setText(extras.getString("viewTitle"))
        activity_new_journal_description.setText(extras.getString("viewDescription"))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.new_journal_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            //Send the information back to either the main activity or the view journal.
            R.id.new_journal_menu_save -> {
                val returnIntent = Intent()
                returnIntent.putExtra("title", activity_new_journal_title.text.toString())
                returnIntent.putExtra("description", activity_new_journal_description.text.toString())
                setResult(Activity.RESULT_OK,returnIntent)
                Toast.makeText(this, "Save Clicked", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
