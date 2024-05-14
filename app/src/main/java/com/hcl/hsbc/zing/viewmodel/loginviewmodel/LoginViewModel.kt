package com.hcl.hsbc.zing.viewmodel.loginviewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hcl.hsbc.zing.model.login.LoginTableModel
import com.hcl.hsbc.zing.repository.loginrepository.LoginRepository

class LoginViewModel : ViewModel() {

    var liveDataLogin: LiveData<LoginTableModel>? = null

    fun insertData(context: Context, username: String, password: String) {
        Log.d("LoginViewModel","username = $username, Password = $password")
       LoginRepository.insertData(context, username, password)
    }

    fun getLoginDetails(context: Context, username: String, strPassword: String) : LiveData<LoginTableModel>? {
        liveDataLogin = LoginRepository.getLoginDetails(context, username, strPassword)
        return liveDataLogin
    }


}