package hr.ferit.janaledencan.myurbandictionary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList


class MainFragment : Fragment() {

    private lateinit var communicator: Communicator
    private val db = Firebase.firestore
    private lateinit var recyclerAdapter: DictionaryAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val shownData = ArrayList<RDictionary>()

        view.findViewById<ImageButton>(R.id.btnSearch)
        .setOnClickListener {
            val term = view.findViewById<EditText>(R.id.editTextSearch).text.toString()
            val request = ServiceBuilder.buildService(DictionaryAPI::class.java)

            val key = context?.getString(R.string.hiddenAPIkey).toString()
            val host = "mashape-community-urban-dictionary.p.rapidapi.com"
            val call = request.getDescription(key, host, term)

            call.enqueue(object : Callback<DictionaryR> {

                override fun onResponse(
                    call: Call<DictionaryR>,
                    response: Response<DictionaryR>
                ) {

                    if (response.isSuccessful) {
                        shownData.clear()
                        shownData.addAll(response.body()!!.list)
                        view.findViewById<RecyclerView>(R.id.recyclerView).apply {
                            layoutManager = LinearLayoutManager(this.context)
                            adapter = DictionaryAdapter(shownData)

                        }
                    }
                }

                override fun onFailure(call: Call<DictionaryR>, t: Throwable) {
                    Log.d(
                        "FAIL", t.message.toString()
                    )
                }
            })

        }

        view.findViewById<ImageButton>(R.id.imageButtonNW)
            .setOnClickListener() {
                communicator = activity as Communicator
                communicator.moveToNewWord()
            }

        view.findViewById<Button>(R.id.myDefBtn)
            .setOnClickListener {
                val term=view.findViewById<EditText>(R.id.editTextSearch).text.toString()
                if(term == ""){
                    Toast.makeText(this.context, "You have to enter word to get result.", Toast.LENGTH_SHORT).show()
                }else{
                    db.collection("newWords").document(term).collection(term[0].toString())
                        .get()
                        .addOnSuccessListener { result ->
                            val words =ArrayList<RDictionary>()
                            for (data in result.documents){
                                val word = data.toObject(RDictionary::class.java)
                                if (word != null){
                                    word.defid=data.id
                                    words.add (word)
                                }
                            }
                            shownData.addAll(words)

                            recyclerAdapter = DictionaryAdapter(shownData)
                            view.findViewById<RecyclerView>(R.id.recyclerView).apply {
                                layoutManager = LinearLayoutManager(this.context)
                                adapter = recyclerAdapter
                            }
                        }
                }
            }
        return view
    }

}

