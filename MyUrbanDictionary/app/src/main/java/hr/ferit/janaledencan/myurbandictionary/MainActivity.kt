package hr.ferit.janaledencan.myurbandictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(),Communicator {
    private val db = Firebase.firestore
    private lateinit var recyclerAdapter: DictionaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val firstFragment = MainFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, firstFragment).commit()
    }

    override fun moveToNewWord(){
        val secondFragment = WordFragment()

        val fragmentTransaction = this.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, secondFragment)
        fragmentTransaction.commit()
    }

    override fun moveToFirstF() {

        val nextFragment = MainFragment()

        val fragmentTransaction: FragmentTransaction=
            this.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, nextFragment)
        fragmentTransaction.commit()
    }

}