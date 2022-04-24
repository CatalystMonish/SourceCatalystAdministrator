package com.catalystmedia.sourcecatalystadministrator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.catalystmedia.sourcecatalystadministrator.R
import com.catalystmedia.sourcecatalystadministrator.models.Registrations
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class RegistrationsAdapter(private var mContext: Context, private var mList: ArrayList<Registrations>):
RecyclerView.Adapter<RegistrationsAdapter.ViewHolder>() {
    private var mCtx: Context? = null
    private var profileList: MutableList<Registrations>? = null
    private var firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistrationsAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.registrations_recycler,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RegistrationsAdapter.ViewHolder, position: Int) {
        val item = mList[position]

        val rawEmail = item!!.getUserEmail()
        val status = item!!.getVerified()

        var email = rawEmail.replace(',', '.')
        holder.userEmail.text = email


        if (status) {
            holder.userBadge.visibility = View.VISIBLE
            holder.userBadge.setImageResource(R.drawable.ic_fill)

        } else if (!status) {
            holder.userBadge.visibility = View.VISIBLE
            holder.userBadge.setImageResource(R.drawable.ic_blank)
        }

        holder.userBadge.setOnClickListener {
            if (status) {
                FirebaseDatabase.getInstance().reference.child("Registrations")
                    .child(item.getUserEmail()).child("verified")
                    .setValue(false)
                holder.userBadge.setImageResource(R.drawable.ic_blank)
            } else if (!status) {
                holder.userBadge.setOnClickListener {
                    FirebaseDatabase.getInstance().reference.child("Registrations")
                        .child(item.getUserEmail()).child("verified")
                        .setValue(true)
                    holder.userBadge.setImageResource(R.drawable.ic_fill)
                }
            }

        }




    }



    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder (@NonNull itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var userEmail: TextView = itemView.findViewById(R.id.rv_tv_email)
        var userBadge: ImageView = itemView.findViewById(R.id.rv_iv_verified)

    }


}