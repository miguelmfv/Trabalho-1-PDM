package com.example.testtrabalho1pdm.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtrabalho1pdm.ClientAdapter
import com.example.testtrabalho1pdm.database.ClientsDatabaseHelper
import com.example.testtrabalho1pdm.databinding.ViewlistClientsBinding

class ListClients : AppCompatActivity() {
    private lateinit var binding: ViewlistClientsBinding
    private lateinit var dbHelper: ClientsDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewlistClientsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = ClientsDatabaseHelper(this)

        val clientList = dbHelper.getAllClients()

        binding.recyclerViewClientes.layoutManager = LinearLayoutManager(this)
        val clientAdapter = ClientAdapter(clientList) {
            client -> val intent = Intent(this, FormClients::class.java).apply {
                putExtra("clientId", client.id)
            }
            Toast.makeText(this, "Cliente do ID: ${client.id} foi passado!", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
        binding.recyclerViewClientes.adapter = clientAdapter

    }
}