package com.example.gramasanjeevini.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface PharmacyRepository {
    fun searchMedicine(query: String): Flow<List<InventoryItem>>
    fun getEmergencyStock(): Flow<List<InventoryItem>>
    fun getNearExpiryStock(shopId: String): Flow<List<InventoryItem>>
}

class MockPharmacyRepositoryImpl : PharmacyRepository {

    // Mock Shops
    private val shopA = Shop("s1", "Village A Pharmacy", 1.2, "Village A")
    private val shopB = Shop("s2", "Village B Meds", 3.0, "Village B")
    private val shopC = Shop("s3", "City Center Health", 15.5, "City Center")

    // Mock Medicines
    private val paracetamol = Medicine("m1", "Paracetamol", "Pain reliever and a fever reducer", false, "PharmaCorp")
    private val amoxicillin = Medicine("m2", "Amoxicillin", "Antibiotic", false, "HealthPlus")
    private val insulin = Medicine("m3", "Insulin", "Hormone used to treat diabetes", true, "LifeMeds")
    private val snakeVenomAntidote = Medicine("m4", "Snake Venom Antiserum", "Used for snake bites", true, "GovMeds")
    private val cetirizine = Medicine("m5", "Cetirizine", "Antihistamine for allergies", false, "Allergo")

    // Mock Inventory
    private val inventory = listOf(
        InventoryItem("i1", shopA, paracetamol, 50, 20.0, 365),
        InventoryItem("i2", shopB, paracetamol, 10, 22.0, 30), // Near expiry
        InventoryItem("i3", shopC, paracetamol, 100, 18.0, 700),
        
        InventoryItem("i4", shopA, amoxicillin, 20, 50.0, 180),
        InventoryItem("i5", shopC, amoxicillin, 0, 45.0, 0), // Out of stock
        
        InventoryItem("i6", shopA, insulin, 5, 250.0, 60),
        InventoryItem("i7", shopB, insulin, 2, 260.0, 15), // Near expiry, emergency
        
        InventoryItem("i8", shopC, snakeVenomAntidote, 10, 1000.0, 365), // Only available in city
        
        InventoryItem("i9", shopA, cetirizine, 30, 15.0, 200),
        InventoryItem("i10", shopB, cetirizine, 40, 14.0, 300)
    )

    override fun searchMedicine(query: String): Flow<List<InventoryItem>> = flow {
        if (query.isBlank()) {
            emit(inventory)
        } else {
            val lowerQuery = query.lowercase()
            emit(inventory.filter { 
                it.medicine.name.lowercase().contains(lowerQuery) || 
                it.medicine.description.lowercase().contains(lowerQuery)
            }.sortedBy { it.shop.distanceKm })
        }
    }

    override fun getEmergencyStock(): Flow<List<InventoryItem>> = flow {
        emit(inventory.filter { it.medicine.isLifeSaving }.sortedBy { it.shop.distanceKm })
    }

    override fun getNearExpiryStock(shopId: String): Flow<List<InventoryItem>> = flow {
        emit(inventory.filter { it.shop.id == shopId && it.daysToExpiry <= 30 && it.stockCount > 0 })
    }
}
