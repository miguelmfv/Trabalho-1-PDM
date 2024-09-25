package com.example.testtrabalho1pdm.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.testtrabalho1pdm.database.ClientsDatabaseHelper
import com.example.testtrabalho1pdm.databinding.FormClientsBinding

class FormClients : AppCompatActivity() {
    private lateinit var binding: FormClientsBinding
    private lateinit var dbHelper: ClientsDatabaseHelper
    private var clientId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FormClientsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = ClientsDatabaseHelper(this)
        clientId = intent.getIntExtra("clientId", -1)
        Toast.makeText(this, "Cliente do ID: $clientId foi recebido!", Toast.LENGTH_SHORT).show()

        if (clientId != null && clientId != -1) {
            val client = dbHelper.getClientByID(clientId!!)
            Toast.makeText(this, "var client pegou as informações: $client do $clientId", Toast.LENGTH_SHORT).show()

            if (client != null) {
                binding.fieldClientNome.setText(client.nome)
                binding.fieldClientEmail.setText(client.email)
                binding.fieldClientTelefone.setText(client.telefone)
                binding.fieldClientEndereco.setText(client.endereco)
                binding.fieldClientDataNasc.setText(client.dataNasc)
            } else {
                Toast.makeText(this, "Cliente não encontrado!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "ID do cliente inválido!", Toast.LENGTH_SHORT).show()
        }


        binding.btnClientCadastrar.setOnClickListener {
            val cNome = binding.fieldClientNome.text.toString()
            val cEmail = binding.fieldClientEmail.text.toString()
            val cFone = binding.fieldClientTelefone.text.toString()
            val cEnder = binding.fieldClientEndereco.text.toString()
            val cNasc = binding.fieldClientDataNasc.text.toString()

            try {
                if (cNome.isNotEmpty() && cEmail.isNotEmpty()) {
                    val clienCreated = dbHelper.addClient(cNome, cEmail, cFone, cEnder, cNasc)
                    if (clienCreated) {
                        binding.fieldClientNome.text.clear()
                        binding.fieldClientEmail.text.clear()
                        binding.fieldClientTelefone.text.clear()
                        binding.fieldClientEndereco.text.clear()
                        binding.fieldClientDataNasc.text.clear()
                        Toast.makeText(
                            this,
                            "Cliente Criado com Sucesso!",
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

        binding.btnClientCancelar.setOnClickListener {
            binding.fieldClientNome.text.clear()
            binding.fieldClientEmail.text.clear()
            binding.fieldClientTelefone.text.clear()
            binding.fieldClientEndereco.text.clear()
            binding.fieldClientDataNasc.text.clear()
            val intent = Intent(this, MainPageActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnClientSave.setOnClickListener {
            val cNome = binding.fieldClientNome.text.toString()
            val cEmail = binding.fieldClientEmail.text.toString()
            val cFone = binding.fieldClientTelefone.text.toString()
            val cEnder = binding.fieldClientEndereco.text.toString()
            val cNasc = binding.fieldClientDataNasc.text.toString()
            try {
                if (cNome.isNotEmpty() && cEmail.isNotEmpty()) {
                    val clienCreated = dbHelper.atualizaClient(clientId!!, cNome, cEmail, cFone, cEnder, cNasc)
                    if (clienCreated) {
                        binding.fieldClientNome.text.clear()
                        binding.fieldClientEmail.text.clear()
                        binding.fieldClientTelefone.text.clear()
                        binding.fieldClientEndereco.text.clear()
                        binding.fieldClientDataNasc.text.clear()
                        Toast.makeText(
                            this,
                            "Cliente Criado com Sucesso!",
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

        binding.btnDelete.setOnClickListener {
            dbHelper.deleteId(clientId!!)
            binding.fieldClientNome.text.clear()
            binding.fieldClientEmail.text.clear()
            binding.fieldClientTelefone.text.clear()
            binding.fieldClientEndereco.text.clear()
            binding.fieldClientDataNasc.text.clear()
            Toast.makeText(
                this,
                "Cliente DELETADO com Sucesso!",
                Toast.LENGTH_LONG
            ).show()
            val intent = Intent(this, MainPageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}