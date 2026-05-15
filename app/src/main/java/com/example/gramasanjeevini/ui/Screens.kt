package com.example.gramasanjeevini.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gramasanjeevini.data.InventoryItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: MainViewModel = viewModel()) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.searchMedicine(it) },
            label = { Text("Search Medicine") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(searchResults) { item ->
                MedicineCard(item = item)
            }
        }
    }
}

@Composable
fun EmergencyScreen(viewModel: MainViewModel = viewModel()) {
    val emergencyStock by viewModel.emergencyStock.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Life Saving Drugs", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(emergencyStock) { item ->
                MedicineCard(item = item)
            }
        }
    }
}

@Composable
fun PharmacistScreen(viewModel: MainViewModel = viewModel()) {
    val expiryAlerts by viewModel.expiryAlerts.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Pharmacist Portal", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("Logged in as: Village B Meds", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        
        Text("Expiry Watch (Next 30 Days)", style = MaterialTheme.typography.titleMedium, color = Color.Red)
        Spacer(modifier = Modifier.height(8.dp))

        if (expiryAlerts.isEmpty()) {
            Text("No near-expiry medicines.", modifier = Modifier.padding(8.dp))
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(expiryAlerts) { item ->
                    MedicineCard(item = item, isPharmacistView = true)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineCard(item: InventoryItem, isPharmacistView: Boolean = false) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.medicine.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                if (item.medicine.isLifeSaving) {
                    Badge(containerColor = Color.Red, contentColor = Color.White) {
                        Text("LIFE SAVING", modifier = Modifier.padding(4.dp), fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = item.medicine.description, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Shop: ${item.shop.name}", fontWeight = FontWeight.Medium)
                Text(text = "${item.shop.distanceKm} km away", color = MaterialTheme.colorScheme.primary)
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = if (item.stockCount > 0) "In Stock: ${item.stockCount}" else "Out of Stock",
                    color = if (item.stockCount > 0) Color(0xFF2E7D32) else Color.Red,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "₹${item.price}")
            }

            if (isPharmacistView && item.daysToExpiry <= 30) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(Color(0xFFFFEBEE), RoundedCornerShape(8.dp)).padding(8.dp).fillMaxWidth()) {
                    Icon(Icons.Default.Warning, contentDescription = "Warning", tint = Color.Red)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Expires in ${item.daysToExpiry} days. Consider Discount!", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
