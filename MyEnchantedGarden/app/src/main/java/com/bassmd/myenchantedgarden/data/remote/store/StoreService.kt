package com.bassmd.myenchantedgarden.data.remote.store

import com.bassmd.myenchantedgarden.dto.EventModel
import com.bassmd.myenchantedgarden.dto.EventResponse
import com.bassmd.myenchantedgarden.dto.StoreRequest
import com.bassmd.myenchantedgarden.dto.StoreResponse

interface StoreService {
    suspend fun getEvents(): Result<EventResponse>
    suspend fun getStore(): Result<StoreResponse>
    suspend fun buyItem(storeRequest: StoreRequest): Result<StoreResponse>
}