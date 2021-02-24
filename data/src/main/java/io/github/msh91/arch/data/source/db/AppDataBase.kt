package io.github.msh91.arch.data.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.msh91.arch.data.source.db.entity.RemoteKeys
import io.github.msh91.arch.data.source.db.entity.ServerModel
import io.github.msh91.arch.data.util.DateConverter
import io.github.msh91.arch.data.util.ListConverter


@Database(entities = [ServerModel::class, RemoteKeys::class], version = AppDataBase.VERSION,exportSchema = false )
@TypeConverters(ListConverter::class, DateConverter::class )

abstract class AppDataBase : RoomDatabase() {

    abstract fun serverDAO() : ServerDAO
    abstract fun getRepoDAO(): RemoteKeysDao

    companion object {
        const val DB_NAME = "arch.db"
        const val VERSION = 4
    }
}
