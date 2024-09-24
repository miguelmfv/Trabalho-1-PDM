package com.example.testtrabalho1pdm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testtrabalho1pdm.ClientAdapter.*
import com.example.testtrabalho1pdm.model.Clients

class ClientAdapter(private val clientList: List<Clients>,
    private val onClientClick: (Clients) -> Unit
    ) : RecyclerView.Adapter<ClientsViewHolder>(){

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ClientsViewHolder {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_layout_client, viewGroup, false)

            return ClientsViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: ClientsViewHolder, position: Int) {
            val client = clientList[position]
            viewHolder.bind(client)
            viewHolder.itemView.setOnClickListener {
                onClientClick(client)
            }
        }

        override fun getItemCount() = clientList.size

        class ClientsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val id: TextView = view.findViewById(R.id.clientID)
            private val nome: TextView = view.findViewById(R.id.clientNAME)
            private val email: TextView = view.findViewById(R.id.clientEMAIL)
            private val telefone: TextView = view.findViewById(R.id.clientFONE)
            private val endereco: TextView = view.findViewById(R.id.clientENDER)
            private val dataNasc: TextView = view.findViewById(R.id.clientNASC)

            fun bind(clients: Clients) {
                id.text = clients.id.toString()
                nome.text = clients.nome
                email.text = clients.email
                telefone.text = clients.telefone
                endereco.text = clients.endereco
                dataNasc.text = clients.dataNasc
            }
        }

        fun getItem(id: Int): Clients {
            return clientList[id]
        }
}