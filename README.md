# Grama-Sanjeevini (Healthcare) 🏥

Grama-Sanjeevini is an Android application designed as a "Rural Pharmacy Network." It addresses the critical issue of remote villages lacking access to real-time medicine availability by connecting 5-10 village medical stores into a single searchable pool.

## The Problem
In remote villages, the "Local Medical Store" is often the only source of advice and medicine. If a medicine is out of stock, a villager might have to travel 20km to the city without knowing if a shop in the neighboring village has it.

## Features
- 🔍 **Medicine Search**: Villagers can search for medicine availability across nearby shops and view the distance (e.g., "Available in Village B — 3km away").
- 🚨 **Emergency Stock**: A dedicated view for identifying the availability of "Life Saving Drugs" (e.g., Insulin, Snake Venom) prominently highlighted with a red badge.
- 👨‍⚕️ **Pharmacist Portal**: A portal for pharmacists to monitor inventory.
- ⏳ **Expiry Watch**: Pharmacists receive alerts to sell near-expiry stock at a discount to prevent medicine wastage.

## Technical Implementation
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with Repository Pattern
- **Data**: Configured to connect to Firebase Firestore (currently utilizing a Mock Repository with 3 mock shops to demonstrate real-time capability and location radius filtering).

## Getting Started

### Prerequisites
- [Android Studio](https://developer.android.com/studio) installed.

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/PreethamVA/Grama-Sanjeevini.git
   ```
2. Open Android Studio and select **Open**.
3. Select the cloned project folder.
4. Android Studio will automatically sync the Gradle files. 
5. Build and run the app on an emulator or physical device.

## Impact Goals
* **Health Accessibility:** Ensuring critical medicines are found with minimal travel.
* **Supply Chain Efficiency:** Reducing medicine wastage due to expiry.
* **Pharmacist Collaboration:** Turning individual shops into a unified "Health Network".
