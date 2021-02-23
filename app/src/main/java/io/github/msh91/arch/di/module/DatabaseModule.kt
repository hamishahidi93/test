package io.github.msh91.arch.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.github.msh91.arch.app.ArchApplication
import io.github.msh91.arch.data.source.db.AppDataBase
import io.github.msh91.arch.data.source.db.ServerDAO
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(context: ArchApplication): AppDataBase {
        return Room
            .databaseBuilder(context.applicationContext, AppDataBase::class.java, AppDataBase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideServerDao(db: AppDataBase): ServerDAO {
        return db.serverDAO()
    }

}
