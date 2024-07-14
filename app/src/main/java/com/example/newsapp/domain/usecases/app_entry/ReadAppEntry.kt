package com.example.newsapp.domain.usecases.app_entry

import com.example.newsapp.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localUSerManager: LocalUserManager
) {

    operator fun invoke(): Flow<Boolean> {
        return localUSerManager.readAppEntry()
    }
}