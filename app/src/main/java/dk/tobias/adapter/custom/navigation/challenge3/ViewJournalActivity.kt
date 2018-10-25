package dk.tobias.adapter.custom.navigation.challenge3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import kotlinx.android.synthetic.main.activity_view_journal.*

class ViewJournalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_journal)

        val toolbar = findViewById<Toolbar>(R.id.view_journal_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener { view ->
            finish()
        }

        val extras = intent.extras ?: return
        val title = extras.get("viewTitle")
        val description = extras.get("viewDescription")

        view_journal_title.text = title?.toString()
        view_journel_description.text = description?.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.view_journal_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}