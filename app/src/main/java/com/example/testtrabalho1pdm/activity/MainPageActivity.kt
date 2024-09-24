package com.example.testtrabalho1pdm.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testtrabalho1pdm.R
import com.example.testtrabalho1pdm.databinding.ActivityMainPageBinding

class MainPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOpenClientForms.setOnClickListener {
            val intent = Intent(this, FormClients::class.java)
            startActivity(intent)
        }
        binding.btnListarClientes.setOnClickListener {
            val intent = Intent(this, ListClients::class.java)
            startActivity(intent)
        }

        binding.btnOpenProductForms.setOnClickListener {
            val intent = Intent(this, FormProducts::class.java)
            startActivity(intent)
        }

        binding.btnListarProduto.setOnClickListener {
            val intent = Intent(this, ListProducts::class.java)
            startActivity(intent)
        }

        binding.btnOpenOSForms.setOnClickListener {
            val intent = Intent(this, FormOrders::class.java)
            startActivity(intent)
        }

        binding.btnListarOS.setOnClickListener {
            val intent = Intent(this, ListOrders::class.java)
            startActivity(intent)
        }
    }
}