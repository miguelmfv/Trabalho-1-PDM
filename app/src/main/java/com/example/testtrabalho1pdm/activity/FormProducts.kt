package com.example.testtrabalho1pdm.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testtrabalho1pdm.R
import com.example.testtrabalho1pdm.database.ProductsDatabaseHelper
import com.example.testtrabalho1pdm.databinding.FormProductsBinding

class FormProducts : AppCompatActivity() {
    private lateinit var binding: FormProductsBinding
    private lateinit var dbHelper: ProductsDatabaseHelper
    private var productId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FormProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = ProductsDatabaseHelper(this)
        productId = intent.getIntExtra("productId", -1)
        Toast.makeText(this, "Produto do ID: $productId foi recebido!", Toast.LENGTH_SHORT).show()

        if (productId != null && productId != -1) {
            val product = dbHelper.getProductByID(productId!!)
            Toast.makeText(this, "product pegou as informações: $product do $productId", Toast.LENGTH_SHORT).show()

            if (product != null) {
                binding.fieldProductName.setText(product.nome)

            } else {
                Toast.makeText(this, "Produto não encontrado!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "ID do Produto inválido!", Toast.LENGTH_SHORT).show()
        }


        binding.btnProductCadastrar.setOnClickListener {
            val nome = binding.fieldProductName.text.toString()
            val descricao = binding.fieldProductDesc.text.toString()
            val preco = binding.fieldProductPreco.text.toString()
            val estoque = binding.fieldProductEstoque.text.toString()

            try {
                if (nome.isNotEmpty() && preco.isNotEmpty() && estoque.isNotEmpty()) {
                    val productCreated = dbHelper.addProduct(nome, descricao, preco, estoque)
                    if (productCreated) {
                        binding.fieldProductName.text.clear()
                        binding.fieldProductDesc.text.clear()
                        binding.fieldProductPreco.text.clear()
                        binding.fieldProductEstoque.text.clear()
                        Toast.makeText(
                            this,
                            "Produto Criado com Sucesso!",
                            Toast.LENGTH_LONG
                        ).show()
                        val intent = Intent(this, MainPageActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
                println(e.message)
            }
        }

        binding.btnProductCancelar.setOnClickListener {
            binding.fieldProductName.text.clear()
            binding.fieldProductDesc.text.clear()
            binding.fieldProductPreco.text.clear()
            binding.fieldProductEstoque.text.clear()
            val intent = Intent(this, MainPageActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnProductSave.setOnClickListener {
            val nome = binding.fieldProductName.text.toString()
            val desc = binding.fieldProductDesc.text.toString()
            val preco = binding.fieldProductPreco.text.toString()
            val estoque = binding.fieldProductEstoque.text.toString()
            try {
                if (nome.isNotEmpty() && preco.isNotEmpty() && estoque.isNotEmpty()) {
                    val productCreated = dbHelper.atualizaProduto(productId!!, nome, desc, preco, estoque)
                    if (productCreated) {
                        binding.fieldProductName.text.clear()
                        binding.fieldProductDesc.text.clear()
                        binding.fieldProductPreco.text.clear()
                        binding.fieldProductEstoque.text.clear()
                        Toast.makeText(
                            this,
                            "Produto Atualizado com Sucesso!",
                            Toast.LENGTH_LONG
                        ).show()
                        val intent = Intent(this, MainPageActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
                println(e.message)
            }
        }

        binding.btnProductDelete.setOnClickListener {
            dbHelper.deleteId(productId!!)
            binding.fieldProductName.text.clear()
            binding.fieldProductDesc.text.clear()
            binding.fieldProductPreco.text.clear()
            binding.fieldProductEstoque.text.clear()
            Toast.makeText(
                this,
                "Produto DELETADO com Sucesso!",
                Toast.LENGTH_LONG
            ).show()
            val intent = Intent(this, MainPageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}