package io.github.msh91.arch.data.source.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.*
import io.github.msh91.arch.data.source.db.entity.ServerModel
import io.reactivex.Observable

@Dao
interface ServerDAO {

    @Query("SELECT * FROM InquiryServer WHERE ServerId =:serverId")
    fun getServerDetail(serverId: String): LiveData<ServerModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertServerToDb(serverModel: ServerModel?)

    @Update
    suspend fun updateServer(serverModel: ServerModel)

    @Query("UPDATE InquiryServer SET IsActive = :isActive  WHERE ServerId LIKE :serverId ")
    suspend fun updateServer(isActive: String, serverId: String?)

    @Query("DELETE FROM InquiryServer WHERE IsActive = :serverId")
    suspend fun deleteServer(serverId: String?)

    @Query("DELETE FROM InquiryServer")
    suspend fun deleteAll()

    @Query("SELECT * FROM InquiryServer Order By Date DESC")
    fun getServerModels(): PagingSource<Int, ServerModel>
}
