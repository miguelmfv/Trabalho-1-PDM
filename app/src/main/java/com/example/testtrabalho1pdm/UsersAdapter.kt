package com.example.testtrabalho1pdm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testtrabalho1pdm.model.Users


public class usersAdapter(private val userList:List<Users>) : RecyclerView.Adapter<usersAdapter.usersViewHolder>(){

    override fun onBindViewHolder(holder: usersViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): usersViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return usersViewHolder(itemView)
    }

    override fun getItemCount() = userList.size

    class usersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName: TextView = itemView.findViewById(android.R.id.text1)
        private val password:TextView = itemView.findViewById(android.R.id.text2)

        fun bind(user: Users){
            userName.text = user.userName
            password.text = user.password
        }
    }
}
