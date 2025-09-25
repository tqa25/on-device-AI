package com.google.ai.edge.gallery.chatlog

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
  entities = [MessageEntity::class],
  version = 1,
  exportSchema = true
)
abstract class ChatDatabase : RoomDatabase() {
  abstract fun messageDao(): MessageDao
}
