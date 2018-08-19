package com.example.andrewforwork.timem8.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.andrewforwork.timem8.Models.dailyFrog

val TABLE_DAILY_NAME="frogs"
val COL_DAILY_NAME="name"
val COL_DAILY_COUNT="count"
val COL_DAILY_ISDONE="isdone"
val COL_DAILY_DATE="date"
val COL_DAILY_DESC="desc"
val COL_DAILY_COLOR_1="color1"
val COL_DAILY_COLOR_2="color2"
val COL_DAILY_TASKS="tasks"

class DBdaily(var contex: Context): SQLiteOpenHelper(contex, DATABASE_NAME,null , DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_DAILY_NAME + " (" + COL_ID + " INTEGER PRIMARY KEY," +
                COL_DAILY_NAME + " text," +
                COL_DAILY_COUNT + " int," +
                COL_DAILY_ISDONE + " text," +
                COL_DAILY_COLOR_1 + " int," +
                COL_DAILY_COLOR_2 + " int," +
                COL_DAILY_DESC + " text," +
                COL_DAILY_TASKS + " text," +
                COL_DAILY_DATE + " text)"
        db!!.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("drop table if exists $TABLE_DAILY_NAME")
        onCreate(db)
    }

    fun test(): String {
        return "OK"
    }

    //TODO решить че с этим делать
//    val allSub:List<SubDetail>
////        get()
////        {
////            val lstSubs = ArrayList<SubDetail>()
////            val selectQuery = "SELECT * FROM "+ TABLE_DETAIL_NAME+" ORDER BY $COL_DAY , $COL_CNT"
////            val db = this.writableDatabase
////            val cursor = db.rawQuery(selectQuery,null)
////            if(cursor.moveToFirst()){
////                do{
////                    val sub = SubDetail()
////                    sub.id = cursor.getInt(cursor.getColumnIndex(COL_ID))
////                    sub.name = cursor.getString(cursor.getColumnIndex(COL_NAME))
////                    sub.day = cursor.getInt(cursor.getColumnIndex(COL_DAY))
////                    sub.importance = cursor.getInt(cursor.getColumnIndex(COL_IMP))
////                    sub.count = cursor.getInt(cursor.getColumnIndex(COL_CNT))
////                    sub.time = cursor.getString(cursor.getColumnIndex(COL_TIME))
////
////                    lstSubs.add(sub)
////                }while (cursor.moveToNext())
////            }
////            db.close()
////            return lstSubs
////        }
    fun allFrogByDay(date: String): List<dailyFrog> {
        val lstFrogs = ArrayList<dailyFrog>()
        val selectQuery = "SELECT * FROM $TABLE_DAILY_NAME WHERE $COL_DAILY_DATE = '$date'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val frog = dailyFrog()
                frog.id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                frog.name = cursor.getString(cursor.getColumnIndex(COL_DAILY_NAME))
                frog.count = cursor.getInt(cursor.getColumnIndex(COL_DAILY_COUNT))
                frog.date = cursor.getString(cursor.getColumnIndex(COL_DAILY_DATE))
                frog.tasks = cursor.getString(cursor.getColumnIndex(COL_DAILY_TASKS))
                var tmp = cursor.getString(cursor.getColumnIndex(COL_DAILY_ISDONE))
                frog.isDone = ArrayList<Boolean>()
                for(i in tmp){
                    if(i == '0'){
                        frog.isDone.add(false)
                    } else {
                        frog.isDone.add(true)
                    }
                }
                frog.description = cursor.getString(cursor.getColumnIndex(COL_DAILY_DESC))
                frog.colorId1 = cursor.getInt(cursor.getColumnIndex(COL_DAILY_COLOR_1))
                frog.colorId2 = cursor.getInt(cursor.getColumnIndex(COL_DAILY_COLOR_2))
                lstFrogs.add(frog)
            } while (cursor.moveToNext())
        }
        db.close()
        return lstFrogs
    }

    fun addFrog(frog: dailyFrog) {
        val db = this.writableDatabase
        onCreate(db)
        var res: String = ""
        for (i in frog.isDone) {
            if (i) {
                res += "1"
            } else {
                res += "0"
            }
        }
        var cv = ContentValues()
        cv.put(COL_ID, frog.id)
        cv.put(COL_DAILY_TASKS,frog.tasks)
        cv.put(COL_DAILY_NAME,frog.name)
        cv.put(COL_DAILY_COUNT, frog.count)
        cv.put(COL_DAILY_DATE, frog.date)
        cv.put(COL_DAILY_ISDONE, res)
        cv.put(COL_DAILY_COLOR_1, frog.colorId1)
        cv.put(COL_DAILY_COLOR_2, frog.colorId2)
        cv.put(COL_DAILY_DESC, frog.description)

        db.insertOrThrow(TABLE_DAILY_NAME, null, cv)
        db.close()
    }

    fun updateFrog(frog: dailyFrog): Int {
        val db = this.writableDatabase
        onCreate(db)
        var res: String = ""
        for (i in frog.isDone) {
            if (i) {
                res += "1"
            } else {
                res += "0"
            }
        }
        var cv = ContentValues()
        cv.put(COL_ID, frog.id)
        cv.put(COL_DAILY_TASKS,frog.tasks)
        cv.put(COL_DAILY_NAME,frog.name)
        cv.put(COL_DAILY_COUNT, frog.count)
        cv.put(COL_DAILY_DATE, frog.date)
        cv.put(COL_DAILY_ISDONE, res)
        cv.put(COL_DAILY_COLOR_1, frog.colorId1)
        cv.put(COL_DAILY_COLOR_2, frog.colorId2)
        cv.put(COL_DAILY_DESC, frog.description)
        return db.update(TABLE_DAILY_NAME, cv, COL_ID + "=?", arrayOf(frog.id.toString()))

    }

    fun deleteFrog(frog: dailyFrog) {
        val db = this.writableDatabase
        db.delete(TABLE_DAILY_NAME, COL_ID + "=?", arrayOf(frog.id.toString()))
        db.close()
    }

    fun deleteAllData() {
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DAILY_NAME")
        onCreate(db)
        db.close()
    }
}