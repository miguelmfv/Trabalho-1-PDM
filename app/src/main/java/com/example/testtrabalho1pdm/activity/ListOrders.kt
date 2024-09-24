package com.example.testtrabalho1pdm.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtrabalho1pdm.OrdersAdapter
import com.example.testtrabalho1pdm.database.OrdersDatabaseHelper
import com.example.testtrabalho1pdm.databinding.ViewlistOrdersBinding

class ListOrders : AppCompatActivity() {
    private lateinit var binding: ViewlistOrdersBinding
    private lateinit var dbHelper: OrdersDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewlistOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = OrdersDatabaseHelper(this)

        val orderList = dbHelper.getAllOrders()

        binding.recyclerViewPedidos.layoutManager = LinearLayoutManager(this)
        val orderAdapter = OrdersAdapter(orderList) {
                order -> val intent = Intent(this, FormOrders::class.java).apply {
            putExtra("orderId", order.id)
        }
            Toast.makeText(this, "OS do ID: ${order.id} foi passado!", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
        binding.recyclerViewPedidos.adapter = orderAdapter

    }
}