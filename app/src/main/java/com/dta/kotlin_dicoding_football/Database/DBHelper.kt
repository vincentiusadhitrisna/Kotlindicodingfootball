package com.dta.kotlin_dicoding_football.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DBHelper(ctx: Context): ManagedSQLiteOpenHelper(ctx, "FavSchedule.db", null ) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(TABLE_FAVORITE, true,
                ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                EVENT_ID to TEXT + UNIQUE,
                EVENT_NAME to TEXT,
                EVENT_DATE to TEXT,
                HOME_TEAM_ID to TEXT + UNIQUE,
                HOME_TEAM_NAME to TEXT,
                HOME_TEAM_SCORE to TEXT,
                AWAY_TEAM_ID to TEXT + UNIQUE,
                AWAY_TEAM_NAME to TEXT,
                AWAY_TEAM_SCORE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(TABLE_FAVORITE, true)
    }

    companion object {
        const val TABLE_FAVORITE = "TABLE_FAVORITE"
        const val ID = "ID_"
        const val EVENT_ID = "EVENT_ID"
        const val EVENT_NAME = "EVENT_NAME"
        const val EVENT_DATE = "EVENT_DATE"
        const val HOME_TEAM_ID = "HOME_TEAM_ID"
        const val HOME_TEAM_NAME = "HOME_TEAM_NAME"
        const val HOME_TEAM_SCORE = "HOME_TEAM_SCORE"
        const val AWAY_TEAM_ID = "AWAY_TEAM_ID"
        const val AWAY_TEAM_NAME = "AWAY_TEAM_NAME"
        const val AWAY_TEAM_SCORE = "AWAY_TEAM_SCORE"

        private var instance: DBHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DBHelper{
            if (instance==null){
                instance = DBHelper(ctx.applicationContext)

            }
            return  instance as DBHelper
        }
    }
}
val Context.database: DBHelper
    get() = DBHelper.getInstance(applicationContext)