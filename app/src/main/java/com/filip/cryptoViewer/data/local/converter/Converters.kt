package com.filip.cryptoViewer.data.local.converter

import androidx.room.TypeConverter
import com.filip.cryptoViewer.data.remote.dto.Tag
import com.filip.cryptoViewer.data.remote.dto.TeamMember
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {


    @TypeConverter
    fun fromTeamMemberList(team: List<TeamMember>): String {
        val namesList = team.map { it.name } // Extract only names from TeamMember
        return Gson().toJson(namesList)
    }

    @TypeConverter
    fun toTeamMemberList(data: String): List<TeamMember> {
        val listType = object : TypeToken<List<String>>() {}.type
        val namesList: List<String> = Gson().fromJson(data, listType)
        // Convert the List<String> back to List<TeamMember> (with name only)
        return namesList.map { TeamMember(name = it, position = "", id = "") } // Assuming position isn't needed
    }

    @TypeConverter
    fun fromTags(tags: List<Tag>?): String? {
        // Convert list of Tag to a comma-separated String
        return tags?.joinToString(",") { it.name }
    }

    @TypeConverter
    fun toTags(tagNames: String?): List<Tag> {
        // Convert comma-separated String back to a list of Tag
        return tagNames?.split(",")?.map { Tag(name = it, id = "", coinCounter = 0, icoCounter = 0) } ?: emptyList()
    }

}