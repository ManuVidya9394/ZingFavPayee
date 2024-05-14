package com.hcl.hsbc.zing.view.register

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hcl.hsbc.zing.R
import com.hcl.hsbc.zing.view.login.LoginActivity
import com.hcl.hsbc.zing.viewmodel.loginviewmodel.LoginViewModel

class Register : AppCompatActivity() {
    lateinit var emailid: EditText
    lateinit var password: EditText
    lateinit var conformpassword: EditText
    lateinit var buttonLogin: Button
    lateinit var loginViewModel: LoginViewModel
    lateinit var context: Context

    companion object{
        val TAG = "Register"
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        registerActivityInit()
     }

    private fun registerActivityInit() {
        context = this@Register
        getIds()
        listeners()
    }

    private fun listeners() {
        buttonLogin.setOnClickListener {
            if (password.text.toString().equals(conformpassword.text.toString()) && !emailid.text.isEmpty()) {
                    loginViewModel.insertData(
                        context,
                        emailid.text.toString().trim(),
                        password.text.toString().trim()
                    )
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
            } else {
                Toast.makeText(context, "Password mismatching or some fields are empty", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getIds() {
        emailid = findViewById(R.id.emailid)
        password = findViewById(R.id.password)
        conformpassword = findViewById(R.id.conformpassword)
        buttonLogin = findViewById(R.id.buttonLogin)
    }
}