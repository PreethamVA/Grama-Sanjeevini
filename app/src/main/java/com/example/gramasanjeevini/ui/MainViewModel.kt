package com.example.gramasanjeevini.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gramasanjeevini.data.InventoryItem
import com.example.gramasanjeevini.data.MockPharmacyRepositoryImpl
import com.example.gramasanjeevini.data.PharmacyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository: PharmacyRepository = MockPharmacyRepositoryImpl()

    private val _searchResults = MutableStateFlow<List<InventoryItem>>(emptyList())
    val searchResults: StateFlow<List<InventoryItem>> = _searchResults.asStateFlow()

    private val _emergencyStock = MutableStateFlow<List<InventoryItem>>(emptyList())
    val emergencyStock: StateFlow<List<InventoryItem>> = _emergencyStock.asStateFlow()

    private val _expiryAlerts = MutableStateFlow<List<InventoryItem>>(emptyList())
    val expiryAlerts: StateFlow<List<InventoryItem>> = _expiryAlerts.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        searchMedicine("") // Load all initially
        loadEmergencyStock()
        // Mocking login as shop "s2" (Village B Meds) to see expiry alerts
        loadExpiryAlerts("s2")
    }

    fun searchMedicine(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            repository.searchMedicine(query).collect { results ->
                _searchResults.value = results
            }
        }
    }

    private fun loadEmergencyStock() {
        viewModelScope.launch {
            repository.getEmergencyStock().collect { results ->
                _emergencyStock.value = results
            }
        }
    }

    private fun loadExpiryAlerts(shopId: String) {
        viewModelScope.launch {
            repository.getNearExpiryStock(shopId).collect { results ->
                _expiryAlerts.value = results
            }
        }
    }
}
