package com.example.softwareproject.data.nosql_entity


import com.google.firebase.Timestamp

data class UserBattleLog(
    var userId: String = "",
    var match: Int = 0,
    var win: Int = 0,
    var lose: Int = 0,
    var rate: Double = 0.0,
)