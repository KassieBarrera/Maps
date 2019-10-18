package com.example.mapas.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mapas.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val marcador: MarkerOptions?

        // Add a marker in Sydney and move the camera
        val gt = LatLng(14.599935, -90.51634)
      //  mMap.addMarker(MarkerOptions().position(gt).title("Marcado en Guatemala").draggable(true))

        mMap.setMinZoomPreference(6.0f)
        mMap.setMaxZoomPreference(14.0f)

        marcador = MarkerOptions()
        marcador.position(gt)
        marcador.title("Guatemala")
        marcador.snippet("ciudad")
        marcador.icon(BitmapDescriptorFactory.fromResource(android.R.drawable.arrow_up_float))
        //marcador.draggable(true)

        mMap.addMarker(marcador)

        val camara = CameraPosition.Builder()
            .target(gt)
            //.zoom(10f)
            .bearing(0f)
            .tilt(0f)
            .build()

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camara))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(gt))

        mMap.setOnMapClickListener { latLng ->
            Toast.makeText(
                this, "las coordenadas son \n" +
                        ": Latitud: " + latLng.latitude + "\n:" +
                        "Longitud: " + latLng.longitude,
                Toast.LENGTH_SHORT
            ).show()
        }

        mMap.setOnMapLongClickListener {  latLng ->
            Toast.makeText(
                this, "**las coordenadas son \n" +
                        ": Latitud: " + latLng.latitude + "\n:" +
                        "Longitud: " + latLng.longitude,
                Toast.LENGTH_SHORT
            ).show()
        }

        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener{
            override fun onMarkerDragEnd(marker: Marker?) {
                Toast.makeText(
                    this@MapsActivity, "Drag: de las coordenadas \n" +
                            ": Latitud: " + marker!!.position.latitude + "\n:" +
                            "Longitud: " + marker.position.longitude,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onMarkerDragStart(p0: Marker?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onMarkerDrag(p0: Marker?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}
