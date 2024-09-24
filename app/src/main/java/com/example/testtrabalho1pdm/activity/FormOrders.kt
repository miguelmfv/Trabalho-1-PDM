package com.example.testtrabalho1pdm.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testtrabalho1pdm.R
import com.example.testtrabalho1pdm.database.ClientsDatabaseHelper
import com.example.testtrabalho1pdm.database.OrdersDatabaseHelper
import com.example.testtrabalho1pdm.databinding.FormClientsBinding
import com.example.testtrabalho1pdm.databinding.FormOrdersBinding

class FormOrders : AppCompatActivity() {
    private lateinit var binding: FormOrdersBinding
    private lateinit var dbHelper: OrdersDatabaseHelper
    private var orderId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FormOrdersBinding.inflate(layoutInflater)
        setContentView(R.layout.form_orders)
        setContentView(binding.root)

        dbHelper = OrdersDatabaseHelper(this)
        orderId = intent.getIntExtra("orderId", -1)
        Toast.makeText(this, "OS do ID: $orderId foi recebido!", Toast.LENGTH_SHORT).show()

        if (orderId != null && orderId != -1) {
            val order = dbHelper.getOrderByID(orderId!!)
            Toast.makeText(this, "var order pegou as informações: $order do $orderId", Toast.LENGTH_SHORT).show()

            if (order != null) {
                binding.fieldOrderData.setText(order.dataPedido)
                binding.fieldOrderValorTotal.setText(order.valorTot.toString())
                binding.fieldOrderStatus.setText(order.status)
                binding.fieldOrderObservacao.setText(order.observacao)
            } else {
                Toast.makeText(this, "OS não encontrada!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "ID da OS inválida!", Toast.LENGTH_SHORT).show()
        }


        binding.btnOrderCadastrar.setOnClickListener {
            val data = binding.fieldOrderData.text.toString()
            val valTot = binding.fieldOrderValorTotal.text.toString()
            val status = binding.fieldOrderStatus.text.toString()
            val obs = binding.fieldOrderObservacao.text.toString()

            try {
                if (data.isNotEmpty() && valTot.isNotEmpty() && status.isNotEmpty()) {
                    val orderCreated = dbHelper.addOrder(data, valTot,status,obs)
                    if (orderCreated) {
                        binding.fieldOrderData.text.clear()
                        binding.fieldOrderValorTotal.text.clear()
                        binding.fieldOrderStatus.text.clear()
                        binding.fieldOrderObservacao.text.clear()
                        Toast.makeText(
                            this,
                            "OS Criada com Sucesso!",
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

        binding.btnOrderCancelar.setOnClickListener {
            binding.fieldOrderData.text.clear()
            binding.fieldOrderValorTotal.text.clear()
            binding.fieldOrderStatus.text.clear()
            binding.fieldOrderObservacao.text.clear()
            val intent = Intent(this, MainPageActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnOrderSave.setOnClickListener {
            val data = binding.fieldOrderData.text.toString()
            val valTot = binding.fieldOrderValorTotal.text.toString()
            val status = binding.fieldOrderStatus.text.toString()
            val obs = binding.fieldOrderObservacao.text.toString()
            try {
                if (data.isNotEmpty() && valTot.isNotEmpty() && status.isNotEmpty()) {
                    val orderCreated = dbHelper.atualizaOrders(orderId!!, data, valTot,status,obs)
                    if (orderCreated) {
                        binding.fieldOrderData.text.clear()
                        binding.fieldOrderValorTotal.text.clear()
                        binding.fieldOrderStatus.text.clear()
                        binding.fieldOrderObservacao.text.clear()
                        Toast.makeText(
                            this,
                            "OS Criada com Sucesso!",
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

        binding.btnOrderDelete.setOnClickListener {
            dbHelper.deleteId(orderId!!)
            binding.fieldOrderData.text.clear()
            binding.fieldOrderValorTotal.text.clear()
            binding.fieldOrderStatus.text.clear()
            binding.fieldOrderObservacao.text.clear()
            Toast.makeText(
                this,
                "OS DELETADA com Sucesso!",
                Toast.LENGTH_LONG
            ).show()
            val intent = Intent(this, MainPageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}