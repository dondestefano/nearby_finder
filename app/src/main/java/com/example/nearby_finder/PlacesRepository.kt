package com.example.nearby_finder

class CacheRepository(private val cacheDao: CacheDao) {
    val allWords: Flow<List<Word>> = wordDao
}