package com.hcl.hsbc.zing.view.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hcl.hsbc.zing.R
import com.hcl.hsbc.zing.viewmodel.loginviewmodel.LoginViewModel
import com.hcl.hsbc.zing.view.favouritepayee.Favouritepayee
import com.hcl.hsbc.zing.view.register.Register

class LoginActivity : AppCompatActivity() {

    lateinit var loginViewModel: LoginViewModel
    lateinit var context: Context
    lateinit var strUsername: String
    lateinit var strPassword: String
    lateinit var editTextEmail: EditText
    lateinit var editTextPassword: EditText
    lateinit var buttonLogin: Button
    lateinit var register: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        LoginActivityinit()
    }

    private fun LoginActivityinit() {
        context = this@LoginActivity
        getIds()
        listeners()
    }

    private fun listeners() {
        register.setOnClickListener {
            navigareToRegisterActivity()
        }
        buttonLogin.setOnClickListener {
            strUsername = editTextEmail.text.toString().trim()
            strPassword = editTextPassword.text.toString().trim()
            processLogin(strUsername, strPassword)

        }
    }

    private fun getIds() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        register = findViewById(R.id.textViewForgotPassword)
    }

    private fun processLogin(strUsername: String, strPassword: String) {
        if (strPassword.isEmpty()) {
            Toast.makeText(
                context, "Please enter the username",
                Toast.LENGTH_LONG
            ).show();
        } else if (strPassword.isEmpty()) {
            Toast.makeText(
                context, "Please enter the Password",
                Toast.LENGTH_LONG
            ).show();
        } else {
            loginViewModel.getLoginDetails(context, strUsername, strPassword)!!
                .observe(this, Observer {
                    if (it != null) {
                        val intent = Intent(this, Favouritepayee::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            context,
                            "Sorry user details not exist please register",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }
    }

    private fun navigareToRegisterActivity() {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }
}