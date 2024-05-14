package com.hcl.hsbc.zing.repository.loginrepository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.hcl.hsbc.zing.model.login.LoginTableModel
import com.hcl.hsbc.zing.room.core.CoreDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class LoginRepository {

    companion object {

        var coreDatabase: CoreDatabase? = null

        var loginTableModel: LiveData<LoginTableModel>? = null

        fun initializeDB(context: Context) : CoreDatabase {
            return CoreDatabase.getDataseClient(context)
        }

        fun insertData(context: Context, username: String, password: String) {
            Log.d("LoginRepository","username = $username, Password = $password")
            coreDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                val loginDetails = LoginTableModel(username, password)
                coreDatabase!!.loginDao().InsertData(loginDetails)
            }
        }

        fun getLoginDetails(context: Context, username: String, strPassword: String) : LiveData<LoginTableModel>? {
            coreDatabase = initializeDB(context)
            loginTableModel = coreDatabase!!.loginDao().getLoginDetails(username, strPassword)
            return loginTableModel
        }

    }
}