package com.google.ai.edge.gallery.chatlog

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class MessageDaoTest {
  @Test
  fun insertAndQueryByThread() = runBlocking {
    val context = ApplicationProvider.getApplicationContext<android.content.Context>()
    val db = Room.inMemoryDatabaseBuilder(context, ChatDatabase::class.java).build()
    val dao = db.messageDao()

    val t = "thread-1"
    dao.insertAll(
      MessageEntity(threadId = t, role = "user", content = "hi"),
      MessageEntity(threadId = t, role = "assistant", content = "hello")
    )
    val list = dao.streamByThread(t).first()
    assertEquals(2, list.size)
    assertEquals("user", list[0].role)
    assertEquals("assistant", list[1].role)
  }
}
