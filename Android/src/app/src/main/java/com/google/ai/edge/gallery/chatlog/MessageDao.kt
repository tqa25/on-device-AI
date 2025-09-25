package com.google.ai.edge.gallery.chatlog

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
  @Query("SELECT * FROM messages WHERE threadId = :threadId ORDER BY createdAt ASC")
  fun streamByThread(threadId: String): Flow<List<MessageEntity>>

  @Insert
  suspend fun insertAll(vararg msg: MessageEntity)

  @Query("DELETE FROM messages WHERE threadId = :threadId")
  suspend fun clearThread(threadId: String)
}
