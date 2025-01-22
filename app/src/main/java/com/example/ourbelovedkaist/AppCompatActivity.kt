package com.example.ourbelovedkaist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MapActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        mapView = findViewById(R.id.map_view)
        loadLocations()
    }

    private fun loadLocations() {
        db.collection("locations")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val location = document.toObject<Location>()
                    // 마커 추가
                    val marker = MapPOIItem().apply {
                        itemName = location.name
                        mapPoint = MapPoint.mapPointWithGeoCoord(
                            location.latitude,
                            location.longitude
                        )
                        markerType = MapPOIItem.MarkerType.BluePin
                    }
                    mapView.addPOIItem(marker)
                }
            }
            .addOnFailureListener { exception ->
                // 에러 처리
            }
    }

    private fun saveLocation(location: Location) {
        db.collection("locations")
            .add(location)
            .addOnSuccessListener { documentReference ->
                // 성공 처리
            }
            .addOnFailureListener { e ->
                // 에러 처리
            }
    }
}