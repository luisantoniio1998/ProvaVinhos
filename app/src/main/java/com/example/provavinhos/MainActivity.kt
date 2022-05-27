package com.example.provavinhos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonEntrar = findViewById<Button>(R.id.buttonEntrar)
        buttonEntrar.setOnClickListener{entrar()}
    }

    private fun entrar() {

        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)

        val username = editTextUsername.text.toString()
        if (username.isBlank()) {
            editTextUsername.error = getString(R.string.field_mandatory)
            editTextUsername.requestFocus()
            return
        }

        if (username != "admin") {
            editTextUsername.error = getString(R.string.usr_incorrect)
            editTextUsername.requestFocus()
            return
        }

        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)

        val password = editTextPassword.text.toString()

        if (password.isBlank()) {
            editTextPassword.error = getString(R.string.field_mandatory)
            editTextPassword.requestFocus()
            return
        }
        if (password != "admin") {
            editTextPassword.error = getString(R.string.pw_incorrect)
            editTextPassword.requestFocus()
            return
        }


        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)

    }
}