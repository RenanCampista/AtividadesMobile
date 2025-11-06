package com.renan.aplicativo2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.renan.aplicativo2.databinding.ActivitySessionBinding

class SessionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySessionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivitySessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtém referência para SharedPreferences
        val sp = getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)

        // Precisa verificar se já existe esse nome salvo
        val savedName = sp.getString("USERNAME", null)
        if (savedName != null) {
            // Usuário já tem nome salvo vai direto pra tela principal
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Botão de salvar nome
        binding.btnGuardar.setOnClickListener {
            try {
                val username = binding.inputName.text.toString().trim()

                // Se o usuário clicou em salvar, precisamos garantir que ele tenha preenchido o nome
                if (username.isEmpty()) {
                    Toast.makeText(this, "Digite seu nome primeiro!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Salva o nome no SharedPreferences
                sp.edit().putString("USERNAME", username).apply()

                // Mensagem de confirmação
                Toast.makeText(this, "Nome salvo com sucesso!", Toast.LENGTH_SHORT).show()

                // Vai para a próxima Activity
                startActivity(Intent(this, MainActivity::class.java))
                finish()

            } catch (e: Exception) {
                Toast.makeText(this, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
