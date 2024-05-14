package com.hcl.hsbc.zing.repository.favouritepayeerepository

import android.content.Context
import androidx.lifecycle.LiveData
import com.hcl.hsbc.zing.model.favouritepayee.FavouritePayeeSummaryModel
import com.hcl.hsbc.zing.room.core.CoreDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouritePayeeRepository {

    companion object {

        var coreDatabase: CoreDatabase? = null

        var favouritePayeeSummaryModel: LiveData<List<FavouritePayeeSummaryModel>>? = null

        fun initializeDB(context: Context): CoreDatabase {
            return CoreDatabase.getDataseClient(context)
        }

        fun insertData(
            context: Context,
            accountNumber: String,
            payeeName: String,
            bankName: String,
            numberOfTimes: Int
        ) {

            coreDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                val loginDetails =
                    FavouritePayeeSummaryModel(accountNumber, payeeName, bankName, numberOfTimes)
                coreDatabase!!.favouritePayeeDao().InsertData(loginDetails)
            }

        }

        fun getFavouritePayee(
            context: Context
        ): LiveData<List<FavouritePayeeSummaryModel>>? {

            coreDatabase = initializeDB(context)

            favouritePayeeSummaryModel = coreDatabase!!.favouritePayeeDao().getFavouritePayee()

            return favouritePayeeSummaryModel
        }

        fun deleteFavouritePayee(context: Context, accountNumber: String?) {
            coreDatabase = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                coreDatabase!!.favouritePayeeDao().deleteFavouritepayeerow(accountNumber)
            }
        }

        fun editfavouritepayee(context: Context, loginTableModel: FavouritePayeeSummaryModel) {
            coreDatabase = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                coreDatabase!!.favouritePayeeDao().updateData(loginTableModel)
            }
        }
    }
}