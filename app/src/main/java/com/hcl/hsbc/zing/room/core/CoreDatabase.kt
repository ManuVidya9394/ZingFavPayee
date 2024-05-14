package com.hcl.hsbc.zing.room.core

import android.content.Context
import androidx.room.*
import com.hcl.hsbc.zing.model.favouritepayee.FavouritePayeeSummaryModel
import com.hcl.hsbc.zing.model.login.LoginTableModel
import com.hcl.hsbc.zing.room.favoutitepayeedao.FavouritePayeeDao
import com.hcl.hsbc.zing.room.logindao.DAOAccess

@Database(
    entities = arrayOf(LoginTableModel::class, FavouritePayeeSummaryModel::class),
    version = 1,
    exportSchema = false
)
abstract class CoreDatabase : RoomDatabase() {

    abstract fun loginDao(): DAOAccess
    abstract fun favouritePayeeDao(): FavouritePayeeDao

    companion object {

        @Volatile
        private var INSTANCE: CoreDatabase? = null

        fun getDataseClient(context: Context): CoreDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, CoreDatabase::class.java, "LOGIN_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }

    }

}