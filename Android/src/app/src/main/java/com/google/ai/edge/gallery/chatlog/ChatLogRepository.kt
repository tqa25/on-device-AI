package com.google.ai.edge.gallery.chatlog

import androidx.room.Room
import android.content.Context

class ChatLogRepository private constructor(
  private val dao: MessageDao
) {
  companion object {
    @Volatile private var instance: ChatLogRepository? = null
    fun get(context: Context): ChatLogRepository {
      return instance ?: synchronized(this) {
        val db = Room.databaseBuilder(
          context.applicationContext,
          ChatDatabase::class.java,
          "chat.db"
        ).fallbackToDestructiveMigration()
         .build()
        ChatLogRepository(db.messageDao()).also { instance = it }
      }
    }
  }

  suspend fun logUser(threadId: String, text: String) =
    dao.insertAll(MessageEntity(threadId = threadId, role = "user", content = text))

  suspend fun logAssistant(threadId: String, text: String) =
    dao.insertAll(MessageEntity(threadId = threadId, role = "assistant", content = text))
}
