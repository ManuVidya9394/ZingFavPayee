package com.hcl.hsbc.zing.room.favoutitepayeedao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hcl.hsbc.zing.model.favouritepayee.FavouritePayeeSummaryModel

@Dao
interface FavouritePayeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertData(loginTableModel: FavouritePayeeSummaryModel)

    @Query("SELECT * FROM FavouritePayeeSummary")
    fun getFavouritePayee(): LiveData<List<FavouritePayeeSummaryModel>>

    @Query("DELETE FROM FavouritePayeeSummary WHERE  account_number =:accountNumber")
    fun deleteFavouritepayeerow(accountNumber: String?)

    @Update
    fun updateData(loginTableModel: FavouritePayeeSummaryModel)
}