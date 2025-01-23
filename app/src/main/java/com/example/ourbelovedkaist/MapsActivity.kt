package com.example.ourbelovedkaist

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

import com.example.ourbelovedkaist.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.google.android.gms.maps.CameraUpdateFactory
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private var currentLocation: LatLng? = null
    private val timeCapsuleLocations = mutableListOf<LatLng>()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // 지도 준비
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // FusedLocationProvider 초기화
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // 버튼 클릭 시 현재 위치 저장
        findViewById<Button>(R.id.save_location_button).setOnClickListener {
            saveCurrentLocation()
        }

        // 현재 위치 가져오기
        getCurrentLocation()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // 현재 위치 권한 확인
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // 현재 위치 버튼 활성화
            googleMap.isMyLocationEnabled = true

            // 현재 위치 가져와서 카메라 이동
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    googleMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                    )
                }
            }
        }

        // 마커 클릭 리스너 추가
        googleMap.setOnMarkerClickListener { marker ->
            val intent = Intent(this, ShowMemoryActivity::class.java)
            startActivity(intent)
            true // 이벤트 소비
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1001
            )
            return
        }

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = LatLng(location.latitude, location.longitude)
                Toast.makeText(this, "Location: $currentLocation", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to get current location", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveCurrentLocation() {
        if (currentLocation != null) {
            val location = currentLocation!!
            timeCapsuleLocations.add(location)

            // 이미지 크기 조절
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_time_capsule)
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false)
            val timeCapsuleIcon = BitmapDescriptorFactory.fromBitmap(scaledBitmap)

            googleMap.addMarker(
                MarkerOptions()
                    .position(location)
                    .title("Time Capsule")
                    .icon(timeCapsuleIcon)
            )
        } else {
            Toast.makeText(this, "Current location is not available", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }
}
