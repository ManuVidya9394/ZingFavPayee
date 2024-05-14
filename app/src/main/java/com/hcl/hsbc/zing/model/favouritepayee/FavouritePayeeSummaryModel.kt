package com.hcl.hsbc.zing.model.favouritepayee

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavouritePayeeSummary")
data class FavouritePayeeSummaryModel(

    @ColumnInfo(name = "account_number")
    var accountNumber: String,

    @ColumnInfo(name = "payee_name")
    var payeeName: String,

    @ColumnInfo(name = "bank_name")
    var bankName: String,

    @ColumnInfo(name = "number_of_times")
    var numberOfTimes: Int
){

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null
}