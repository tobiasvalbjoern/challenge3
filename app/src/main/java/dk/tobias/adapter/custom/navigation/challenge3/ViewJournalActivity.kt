package dk.tobias.adapter.custom.navigation.challenge3

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_view_journal.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ViewJournalActivity : AppCompatActivity() {
    private val RC_SECOND_ACTIVITY=1000

    private var title : String? = ""
    private var description : String? = ""
    private var timestamp : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_journal)

        val toolbar = findViewById<Toolbar>(R.id.view_journal_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        toolbar.setNavigationOnClickListener { view ->
            val returnIntent = Intent()
            //title and description can be empty, but
            //if the timestamp is empty, the user has not
            //edited an entry.
            if(timestamp == ""){
                finish()
            }
            returnIntent.putExtra("title", title)
            returnIntent.putExtra("description", description)
            returnIntent.putExtra("timestamp",timestamp)
            setResult(Activity.RESULT_OK,returnIntent)
            finish()
        }


        val extras = intent.extras ?: return
        val title = extras.get("viewTitle")
        val description = extras.get("viewDescription")
        val timeStamp=extras.get("viewTimeStamp")
        view_journal_title.text = title?.toString()
        view_journal_description.text = description?.toString()
        view_journal_time_stamp.text=timeStamp?.toString()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.view_journal_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.view_journal_menu_edit -> {
                val i = Intent(this, NewJournalActivity::class.java)
                i.putExtra("viewTitle", view_journal_title.text.toString())
                i.putExtra("viewDescription", view_journal_description.text.toString())
                //startActivity(i)
                startActivityForResult(i,RC_SECOND_ACTIVITY)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==RC_SECOND_ACTIVITY){
            if(resultCode== Activity.RESULT_OK){
                title = data?.getStringExtra("title")
                description= data?.getStringExtra("description")
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                timestamp = current.format(formatter)

                view_journal_title.text = title
                view_journal_description.text = description
                view_journal_time_stamp.text=timestamp
            }
        }
}
}