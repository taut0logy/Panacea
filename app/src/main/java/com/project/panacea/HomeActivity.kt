package com.project.panacea

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import com.project.panacea.UserUtility.OnUserRetrievedListener

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        val recordsCardView = findViewById<CardView>(R.id.records)

        UserUtility.getInstance().getUser(FirebaseAuth.getInstance().currentUser!!.uid,    object : OnUserRetrievedListener{

            override fun onSuccess(user: com.project.panacea.User?) {
                if (user != null) {
                    Log.d("UserClass", user.toJSON().toString())
                };
            }

            override fun onError(message: String?) {
                Toast.makeText(this@HomeActivity, message, Toast.LENGTH_SHORT).show()
            }
        })
        recordsCardView.setOnClickListener {
            val intent = Intent(this, RecordActivity::class.java)
            startActivity(intent);
        }
    }
}