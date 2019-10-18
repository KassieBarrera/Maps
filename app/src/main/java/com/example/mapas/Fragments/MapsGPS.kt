package com.example.mapas.Fragments


import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.example.mapas.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * A simple [Fragment] subclass.
 */
@Suppress("DEPRECATION")
class MapsGPS : Fragment(), OnMapReadyCallback, LocationListener {
    var miVista: View? = null
    var marcador: Marker? = null
    var gestorLocation: LocationManager? = null
    var gMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        miVista = inflater.inflate(R.layout.fragment_mapsgps, container, false)
        return miVista
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapView: MapView?
        mapView = miVista!!.findViewById(R.id.mapGPS) as MapView
        mapView.onCreate(null)
        mapView.onResume()
        mapView.getMapAsync(this)
        verSenialGPS()
    }

    override fun onResume() {
        super.onResume()
        verSenialGPS()
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun verSenialGPS() {
        try {
            val gps = Settings.Secure.getInt(
                activity!!.contentResolver,
                Settings.Secure.LOCATION_MODE
            )

            if (gps == 0) {
                Toast.makeText(context, "Ubicacion desactivada", Toast.LENGTH_LONG).show()
                val intet = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intet)
            }
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        gMap = googleMap
        gestorLocation = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (context?.let {
                ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION)
            } != PackageManager.PERMISSION_GRANTED
            && context?.let {
                ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_COARSE_LOCATION)
            } != PackageManager.PERMISSION_GRANTED) {
            return
        }
        gMap!!.isMyLocationEnabled = true
        gMap!!.uiSettings.isMyLocationButtonEnabled = false

        gestorLocation!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0f, this)
        gestorLocation!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0f, this)
    }

    override fun onLocationChanged(location: Location?) {
        Toast.makeText(context, "Actualizado" + location!!.provider, Toast.LENGTH_LONG).show()
        if (marcador == null) {
            marcador = gMap!!.addMarker(
                MarkerOptions().position(
                    LatLng(
                        location.latitude,
                        location.longitude
                    )
                ).draggable(true)
            )
        } else {
            marcador!!.position = LatLng(location.latitude, location.longitude)
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
