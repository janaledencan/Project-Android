package hr.ferit.janaledencan.myurbandictionary

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class DictionaryAdapter(private val items:ArrayList<RDictionary>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DictionaryAdapter.DictionaryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.dictionary_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is DictionaryAdapter.DictionaryViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(newWord: RDictionary) {
        items.add(newWord)
        notifyItemRangeChanged(items.indexOf(newWord), items.size)
    }


    class DictionaryViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.word)
        private val definition: TextView = itemView.findViewById(R.id.definition)
        private val example: TextView = itemView.findViewById(R.id.example)
        private val authorDate: TextView = itemView.findViewById(R.id.authorDate)


        fun bind(item: RDictionary) {

            title.setText(item.word)
            definition.setText(item.definition)
            example.setText(item.example)

            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val dateTimeString = item.written_on
            val date = dateFormat.parse(dateTimeString)
            val dateFormatOnlyDate = SimpleDateFormat("yyyy-MM-dd")
            val dateOnly = dateFormatOnlyDate.format(date)
            authorDate.setText("By ${item.author}  ${dateOnly}")

        }
    }
}
