package ru.makoto.fefustore.Data.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tag")
data class TagEntity (
    @PrimaryKey
    val id: String,
    val tagName: String
)

fun TagEntity.toTagString() : String = this.tagName;