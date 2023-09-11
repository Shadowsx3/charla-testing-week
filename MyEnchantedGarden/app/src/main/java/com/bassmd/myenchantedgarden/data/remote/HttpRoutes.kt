package com.bassmd.myenchantedgarden.data.remote

object HttpRoutes {
    private const val BASE_URL = "http://10.0.2.2:8080/api"
    const val AUTH = "$BASE_URL/auth"
    const val USER = "$BASE_URL/user"
    const val PLANTS = "$BASE_URL/plants"
    const val STORE = "$BASE_URL/store"
    const val EVENTS = "$STORE/events"
    const val ACHIEVEMENTS = "$BASE_URL/achievements"

}