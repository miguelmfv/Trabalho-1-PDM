package com.example.testtrabalho1pdm.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtrabalho1pdm.ProductAdapter
import com.example.testtrabalho1pdm.database.ProductsDatabaseHelper
import com.example.testtrabalho1pdm.databinding.ViewlistProductsBinding

class ListProducts : AppCompatActivity() {
    private lateinit var binding: ViewlistProductsBinding
    private lateinit var dbHelper: ProductsDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewlistProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = ProductsDatabaseHelper(this)

        val productList = dbHelper.getAllProducts()

        binding.recyclerViewProdutos.layoutManager = LinearLayoutManager(this)
        try {
            val productAdapter = ProductAdapter(productList) { product ->
                val intent = Intent(this, FormProducts::class.java).apply {
                    putExtra("productId", product.id)
                }
                Toast.makeText(this, "Produto do ID: ${product.id} foi passado!", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
            binding.recyclerViewProdutos.adapter = productAdapter
        } catch (e: Exception) {
            Toast.makeText(this, "Erro ao passar o produto: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}