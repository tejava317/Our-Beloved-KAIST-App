package com.example.ourbelovedkaist

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsViewActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_view)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun loadTimeCapsuleLocations(): List<LatLng> {
        // TODO: DB에서 저장된 위치들을 불러오는 로직 구현
        // 임시로 하드코딩된 위치 반환
        return listOf(
            LatLng(36.374, 127.365),  // KAIST 위치
            LatLng(36.375, 127.366)
        )
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        //현재 위치로 이동
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
            googleMap.isMyLocationEnabled = true

            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    googleMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                    )
                }
            }
        }

        // 저장된 타임캡슐 위치들을 가져와서 마커 표시
        val timeCapsuleLocations = loadTimeCapsuleLocations()// DB에서 저장된 위치 가져오기

            timeCapsuleLocations.forEach { location ->
                val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_time_capsule)
                val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false)

                googleMap.addMarker(
                    MarkerOptions()
                        .position(location)
                        .icon(BitmapDescriptorFactory.fromBitmap(scaledBitmap))
                )
            }

        // 마커 클릭 리스너
        googleMap.setOnMarkerClickListener { marker ->
            val intent = Intent(this, ShowMemoryActivity::class.java)
            startActivity(intent)
            true
        }
    }
}