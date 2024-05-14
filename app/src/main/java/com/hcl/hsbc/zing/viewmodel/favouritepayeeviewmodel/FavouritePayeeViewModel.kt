package com.hcl.hsbc.zing.viewmodel.favouritepayeeviewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hcl.hsbc.zing.model.favouritepayee.FavouritePayeeSummaryModel
import com.hcl.hsbc.zing.repository.favouritepayeerepository.FavouritePayeeRepository

class FavouritePayeeViewModel : ViewModel() {

    var favouritePayeeSummaryModel: LiveData<List<FavouritePayeeSummaryModel>>? = null

    fun insertData(
        context: Context,
        accountNumber: String,
        payeeName: String,
        bankName: String,
        numberOfTimes: Int = 0
    ) {
        FavouritePayeeRepository.insertData(
            context,
            accountNumber,
            payeeName,
            bankName,
            numberOfTimes
        )
    }

    fun getFavouritePayee(context: Context): LiveData<List<FavouritePayeeSummaryModel>>? {
        favouritePayeeSummaryModel = FavouritePayeeRepository.getFavouritePayee(context)
        return favouritePayeeSummaryModel
    }

    fun editFavPayee(
        context: Context,
        favouritePayeeSummaryModel: FavouritePayeeSummaryModel,
        Id: Int
    ){
        favouritePayeeSummaryModel.Id = Id
        FavouritePayeeRepository.editfavouritepayee(context,favouritePayeeSummaryModel)

    }

    fun deleteFavPayee(
        context: Context,
        accountNumber: String?
    ){
        FavouritePayeeRepository.deleteFavouritePayee(context,accountNumber)

    }
}