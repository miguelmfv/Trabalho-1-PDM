package com.example.testtrabalho1pdm.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testtrabalho1pdm.R
import com.example.testtrabalho1pdm.database.UsersDatabaseHelper
import com.example.testtrabalho1pdm.databinding.ActivityMainBinding
import com.example.testtrabalho1pdm.activity.MainPageActivity
import com.example.testtrabalho1pdm.model.Users

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: UsersDatabaseHelper
    private lateinit var userList: MutableList<Users>
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)

        dbHelper = UsersDatabaseHelper(this)
        userList = mutableListOf()



        binding.btnRegister.setOnClickListener {
            val username = binding.fieldUsername.text.toString()
            val password = binding.fieldPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val userCreated = dbHelper.addUser(username, password)
                if (userCreated) {
                    binding.fieldUsername.text.clear()
                    binding.fieldPassword.text.clear()
                    Toast.makeText(
                        this,
                        "Usuário Criado com Sucesso! Faça o Login.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(this, "Erro ao Criar Usuário.", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.fieldUsername.text.toString()
            val password = binding.fieldPassword.text.toString()
            try {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    val user: Users? = dbHelper.getUserByUName(username)
                    if (user != null) {
                        if (username == user.userName && password == user.password) {
                            Toast.makeText(this, "Logando!", Toast.LENGTH_SHORT).show()
                            println("log")
                            val intent = Intent(this, MainPageActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Usuário ou senha incorretos!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Por favor, preencha os campos ou faça um registro!", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
                println(e.message)
            }
        }
    }
}