package com.nycschools.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nycschools.data.model.NycSchool

@Database(
    entities = [NycSchool::class],
    version = 1,
    exportSchema = false
)
abstract class NycSchoolDataBase : RoomDatabase() {
    abstract fun getNycSchoolDao(): NycSchoolDAO
}