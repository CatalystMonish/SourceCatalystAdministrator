package com.catalystmedia.sourcecatalystadministrator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.catalystmedia.sourcecatalystadministrator.adapters.RegistrationsAdapter
import com.catalystmedia.sourcecatalystadministrator.models.Registrations
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

class RecyclerActivity : AppCompatActivity() {

    var globalIntentPref:String = ""


    val user = FirebaseAuth.getInstance().currentUser
    private var registrationsAdapter: RegistrationsAdapter? = null
    private var itemList: MutableList<Registrations>? = null
    private var firebaseUser: FirebaseUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        globalIntentPref = intent.getStringExtra("type").toString()

        if(globalIntentPref == "registrations"){
            getRegistrations()
            var recyclerView: RecyclerView? = null
            recyclerView = findViewById(R.id.rv_view)
            val linearLayoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = linearLayoutManager
            itemList = ArrayList()
            registrationsAdapter = RegistrationsAdapter(this, itemList as ArrayList<Registrations>)
            recyclerView.adapter = registrationsAdapter
        }




    }

    private fun getRegistrations() {
        FirebaseDatabase.getInstance().reference.child("Registrations").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    itemList!!.clear()
                    for (snapshot in dataSnapshot.children) {
                        Log.d("ITEM", snapshot.toString())

                        val item = snapshot.getValue(Registrations::class.java)
                        val userEmail = snapshot.getKey().toString()
                        item!!.setUserEmail(userEmail)
                        itemList!!.add(item!!)
                    }
                    registrationsAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}
