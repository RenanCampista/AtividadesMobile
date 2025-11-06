package com.renan.aplicativo2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.renan.aplicativo2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var catSelected : Boolean = false
    private  var dogSelected : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        try {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            // Pegar as informações salvas
            val sp = getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)
            val username = sp.getString("USERNAME", "usuário")
            binding.textName.text = "Olá, $username"

            // Gato selecionado
            binding.catImg.setOnClickListener {
                binding.catImg.setColorFilter(ContextCompat.getColor(this, R.color.yellow))
                binding.dogImg.setColorFilter(ContextCompat.getColor(this, R.color.white))
                catSelected = true
                dogSelected = false
            }

            // Cachorro selecionado
            binding.dogImg.setOnClickListener {
                binding.catImg.setColorFilter(ContextCompat.getColor(this, R.color.white))
                binding.dogImg.setColorFilter(ContextCompat.getColor(this, R.color.yellow))
                catSelected = false
                dogSelected = true
            }

            binding.btnGerarFrase.setOnClickListener {
                var randomMessage: String = ""
                if (catSelected) {
                    randomMessage = Facts.catFacts.random()
                } else if (dogSelected) {
                    randomMessage = Facts.dogFacts.random()
                } else {
                    Toast.makeText(this, "Escolha um animal primeiro! 🐶🐱", Toast.LENGTH_SHORT).show()
                }

                binding.randomMessage.text = randomMessage
            }

            binding.btnSair.setOnClickListener {
                sp.edit().remove("USERNAME").apply()

               val intent = Intent(this, SessionActivity::class.java)
                startActivity(intent)
                finish()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}