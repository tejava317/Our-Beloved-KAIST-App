package com.example.ourbelovedkaist

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.*
import android.os.Bundle
import android.view.Surface
import android.view.TextureView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class OpenCapsuleActivity : AppCompatActivity() {

    private lateinit var textureView: TextureView
    private lateinit var cameraManager: CameraManager
    private lateinit var cameraDevice: CameraDevice
    private lateinit var captureRequestBuilder: CaptureRequest.Builder
    private lateinit var cameraCaptureSession: CameraCaptureSession

    private val CAMERA_PERMISSION_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_capsule)

        textureView = findViewById(R.id.open_capsule_camera)

        cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager

        // Check camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            setupTextureView()
        } else {
            requestCameraPermission()
        }
    }

    private fun setupTextureView() {
        textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(surfaceTexture: android.graphics.SurfaceTexture, width: Int, height: Int) {
                startCameraPreview()
            }

            override fun onSurfaceTextureSizeChanged(surface: android.graphics.SurfaceTexture, width: Int, height: Int) {}
            override fun onSurfaceTextureDestroyed(surface: android.graphics.SurfaceTexture): Boolean = true
            override fun onSurfaceTextureUpdated(surface: android.graphics.SurfaceTexture) {}
        }
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupTextureView()
            } else {
                Toast.makeText(this, "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun startCameraPreview() {
        val cameraId = cameraManager.cameraIdList[0] // 후면 카메라 ID
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                cameraManager.openCamera(cameraId, object : CameraDevice.StateCallback() {
                    override fun onOpened(camera: CameraDevice) {
                        cameraDevice = camera
                        val surfaceTexture = textureView.surfaceTexture
                        surfaceTexture?.setDefaultBufferSize(textureView.width, textureView.height)
                        val surface = Surface(surfaceTexture)

                        captureRequestBuilder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                        captureRequestBuilder.addTarget(surface)

                        camera.createCaptureSession(listOf(surface), object : CameraCaptureSession.StateCallback() {
                            override fun onConfigured(session: CameraCaptureSession) {
                                cameraCaptureSession = session
                                captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
                                cameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(), null, null)
                            }

                            override fun onConfigureFailed(session: CameraCaptureSession) {
                                Toast.makeText(this@OpenCapsuleActivity, "카메라 설정 실패", Toast.LENGTH_SHORT).show()
                            }
                        }, null)
                    }

                    override fun onDisconnected(camera: CameraDevice) {
                        camera.close()
                    }

                    override fun onError(camera: CameraDevice, error: Int) {
                        camera.close()
                    }
                }, null)
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
            Toast.makeText(this, "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::cameraDevice.isInitialized) {
            cameraDevice.close()
        }
    }
}
