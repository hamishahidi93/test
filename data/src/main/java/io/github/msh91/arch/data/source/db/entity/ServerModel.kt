package io.github.msh91.arch.data.source.db.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.github.msh91.arch.data.util.DateConverter
import io.github.msh91.arch.data.util.ListConverter
import java.util.*

@Entity(tableName = "InquiryServer")
data class ServerModel(

    @ColumnInfo(name = "serverId")
    var ServerId: String,

    @ColumnInfo(name = "hashKey")
    var HashKey: String,

    @ColumnInfo(name = "ip")
    var Ip: String,

    @TypeConverters(ListConverter::class)
    @ColumnInfo(name = "ports")
    var Ports: List<String>,

    @ColumnInfo(name = "connectionStatus")
    var ConnectionStatus: String,

    @ColumnInfo(name = "isActive")
    var IsActive: String,

    @TypeConverters(DateConverter::class)
    @ColumnInfo(name = "date")
    var Date: Date

) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null

}
