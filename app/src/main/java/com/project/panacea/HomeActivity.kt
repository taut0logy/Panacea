package com.project.panacea

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.project.panacea.AuthUtility.OnUserSignedOutListener

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val recordsCardView = findViewById<CardView>(R.id.records)
        val healthTipsCardView = findViewById<CardView>(R.id.healthtip)

        recordsCardView.setOnClickListener {
            val intent = Intent(this, RecordActivity::class.java)
            startActivity(intent)
        }

        healthTipsCardView.setOnClickListener {
            val intent = Intent(this, HealthTipsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.view_profile -> {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.edit_profile -> {
                val intent = Intent(this, EditProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.logout -> {
                signOutUser()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun signOutUser() {
        AuthUtility.getInstance().signOut(object : OnUserSignedOutListener {
            override fun onSuccess() {
                Toast.makeText(this@HomeActivity, "Signed out successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                finish()
            }

            override fun onError(error: String) {
                Toast.makeText(this@HomeActivity, "Error signing out: $error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
