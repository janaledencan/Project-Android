package hr.ferit.janaledencan.myurbandictionary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.DateTime
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class WordFragment : Fragment() {

    private lateinit var communicator: Communicator
    private val db = Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_word, container, false)


        val btnExit = view.findViewById<ImageButton>(R.id.imageButton)
            .setOnClickListener() {
                communicator = activity as Communicator
                communicator.moveToFirstF()
            }


        val wordList = ArrayList<RDictionary>()
        val dictionaryAdapter = DictionaryAdapter(wordList)

        val nWord = view.findViewById<EditText>(R.id.editTextWord)
        val nDefinition = view.findViewById<EditText>(R.id.editTextDefinition)
        val nExample = view.findViewById<EditText>(R.id.editTextExample)

        val btnSubmit = view.findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {

            val newWord = RDictionary(
                "Me",
                "0",
                "1",
                nDefinition.text.toString(),
                nExample.text.toString(),
                "abc",
                0,
                0,
                nWord.text.toString(),
                SimpleDateFormat("yyyy-MM-dd").format(Date()).toString()
            )

            val firstCollectionRef = db.collection("newWords")
            val firstDocRef = firstCollectionRef.document(nWord.text.toString())
            val secondCollectionRef = firstDocRef.collection(nWord.text[0].toString())
            val newDocRef = secondCollectionRef.document()

            newDocRef.set(newWord, SetOptions.merge())

        }
        return view
    }
}