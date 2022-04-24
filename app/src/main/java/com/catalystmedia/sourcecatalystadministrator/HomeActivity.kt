package com.catalystmedia.sourcecatalystadministrator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initUserData()
        initDatabase()

        cv_registered_home.setOnClickListener {
            val intent = Intent(this, RecyclerActivity::class.java)
            intent.putExtra("type", "registrations")
            startActivity(intent)
        }

    }



    private fun initUserData() {
     tv_name_home.text =  FirebaseAuth.getInstance().currentUser?.displayName.toString()
    }

    private fun initDatabase() {

    //get number of active accounts
        FirebaseDatabase.getInstance().reference.child("Users").addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               if(snapshot.exists()){
                   val currentAccounts = snapshot.childrenCount.toString()
                   tv_active_home.text = "Total Active Accounts:$currentAccounts"
               }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    //get number of registered accounts
    FirebaseDatabase.getInstance().reference.child("Registrations").addListenerForSingleValueEvent(object: ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            if(snapshot.exists()){
                val currentAccounts = snapshot.childrenCount.toString()
                tv_registered_home.text = "Total Registered Accounts:$currentAccounts"
            }
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    })
}




}