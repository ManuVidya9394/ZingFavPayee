package com.hcl.hsbc.zing.room.logindao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hcl.hsbc.zing.model.login.LoginTableModel

@Dao
interface DAOAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertData(loginTableModel: LoginTableModel)

    @Query("SELECT * FROM Login WHERE Username LIKE :username AND password LIKE :password")
    fun getLoginDetails(username: String?, password: String?) : LiveData<LoginTableModel>

}