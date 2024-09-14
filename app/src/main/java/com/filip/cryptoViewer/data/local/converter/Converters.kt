package com.filip.cryptoViewer.data.local.converter

import androidx.room.TypeConverter
import com.filip.cryptoViewer.data.remote.dto.TeamMember
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }
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
}