package com.eb.obd2.repositories.source.persistent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import java.time.LocalDateTime

@Dao
interface RecordDao {
    @Insert
    suspend fun insert(record: RecordEntity) : Long

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("""
        SELECT r.recordId, r.command, r.value, r.unit, r.time,
               s.speedId, s.speed, s.acceleration, s.deltaTime
        FROM record r
        LEFT JOIN speed s ON r.recordId = s.recordId
        WHERE r.time BETWEEN :start AND :end
        ORDER BY r.time DESC
    """)
    suspend fun getRecordsInRange(start: LocalDateTime, end: LocalDateTime): List<DynamicRecord>
}