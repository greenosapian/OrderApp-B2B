package com.example.greenosapian.history

import com.example.greenosapian.database.Dao

class HistoryRepository(dao: Dao) {
    private val historyList = dao.getHistory()

    fun getHistoryList() = historyList
}