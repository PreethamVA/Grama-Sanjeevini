package com.example.gramasanjeevini.data

data class Shop(
    val id: String,
    val name: String,
    val distanceKm: Double,
    val villageName: String
)

data class Medicine(
    val id: String,
    val name: String,
    val description: String,
    val isLifeSaving: Boolean,
    val manufacturer: String
)

data class InventoryItem(
    val id: String,
    val shop: Shop,
    val medicine: Medicine,
    val stockCount: Int,
    val price: Double,
    val daysToExpiry: Int
)
