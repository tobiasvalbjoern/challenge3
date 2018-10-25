package dk.tobias.adapter.custom.navigation.challenge3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class JournalListAdapter(context: Context?, resource: Int, objects: MutableList<JournalEntry>?)
    : ArrayAdapter<JournalEntry>(context, resource, objects) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var itemView = convertView

        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.journal_list_item, parent, false)
        }

        val titleTextView: TextView? = itemView?.findViewById(R.id.journal_list_item_title)
        val descriptionTextView = itemView?.findViewById<TextView>(R.id.journal_list_item_description)

        val currentJournalEntry = getItem(position)

        titleTextView?.text = currentJournalEntry?.title
        descriptionTextView?.text = currentJournalEntry?.description

        return itemView as View
    }
}