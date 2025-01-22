package com.example.ourbelovedkaist

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MapActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    private var capsuleLocation: MapPoint? = null  // 저장된 타임캡슐 위치
    private lateinit var openCapsuleButton: Button

    // 거리 계산을 위한 상수
    companion object {
        const val ACTIVATION_DISTANCE = 50  // 미터 단위, 원하는 거리로 조절 가능
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        mapView = findViewById(R.id.map_view)
        openCapsuleButton = findViewById(R.id.btn_open_capsule)

        // 버튼 초기 상태는 비활성화
        openCapsuleButton.isEnabled = false

        // 현재 위치 트래킹 모드 설정
        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading

        // 위치 트래킹 이벤트 리스너
        mapView.setCurrentLocationEventListener(object : MapView.CurrentLocationEventListener {
            override fun onCurrentLocationUpdate(mapView: MapView, currentLocation: MapPoint, accuracy: Float) {
                // 저장된 위치가 있을 경우 거리 체크
                capsuleLocation?.let { savedLocation ->
                    val distance = calculateDistance(currentLocation, savedLocation)

                    // 설정된 거리 이내일 경우 버튼 활성화
                    openCapsuleButton.isEnabled = distance <= ACTIVATION_DISTANCE
                }
            }

            override fun onCurrentLocationDeviceHeadingUpdate(mapView: MapView, v: Float) {}
            override fun onCurrentLocationUpdateFailed(mapView: MapView) {}
            override fun onCurrentLocationUpdateCancelled(mapView: MapView) {}
        })

        // 타임캡슐 위치 저장 버튼 클릭 리스너
        findViewById<Button>(R.id.btn_save_location).setOnClickListener {
            mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading
            capsuleLocation = mapView.mapCenterPoint

            // 마커 추가
            val marker = MapPOIItem().apply {
                itemName = "타임캡슐 위치"
                mapPoint = capsuleLocation
                markerType = MapPOIItem.MarkerType.BluePin
                selectedMarkerType = MapPOIItem.MarkerType.RedPin
            }
            mapView.addPOIItem(marker)
        }

        // 타임캡슐 열기 버튼 클릭 리스너
        openCapsuleButton.setOnClickListener {
            // 여기서 기존에 구현된 구슬 조회 API 호출
            loadMarbles()
        }
    }

    // 두 지점 간의 거리 계산 (미터 단위)
    private fun calculateDistance(point1: MapPoint, point2: MapPoint): Double {
        val latitude1 = point1.mapPointGeoCoord.latitude
        val longitude1 = point1.mapPointGeoCoord.longitude
        val latitude2 = point2.mapPointGeoCoord.latitude
        val longitude2 = point2.mapPointGeoCoord.longitude

        val R = 6371e3 // 지구의 반지름 (미터)
        val φ1 = latitude1 * Math.PI/180
        val φ2 = latitude2 * Math.PI/180
        val Δφ = (latitude2-latitude1) * Math.PI/180
        val Δλ = (longitude2-longitude1) * Math.PI/180

        val a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
                Math.cos(φ1) * Math.cos(φ2) *
                Math.sin(Δλ/2) * Math.sin(Δλ/2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))

        return R * c // 미터 단위 거리
    }

    // 기존에 구현된 구슬 조회 함수 호출
    private fun loadMarbles() {
        // 여기에 기존 구슬 조회 API 호출 코드 추가
        // 예: Firebase 호출 또는 스프링 백엔드 API 호출
    }
}