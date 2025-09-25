package com.google.ai.edge.gallery.chatlog

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index
import java.util.UUID

@Entity(
  tableName = "messages",
  indices = [Index("threadId"), Index("createdAt")]
)
data class MessageEntity(
  @PrimaryKey val id: String = UUID.randomUUID().toString(),
  val threadId: String,
  val role: String,
  val content: String,
  val createdAt: Long = System.currentTimeMillis()
)
