package com.example.mapas.Fragments


import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.mapas.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class MapsFragment : Fragment(), OnMapReadyCallback {
    var miVista: View? = null
    var geoCoder: Geocoder? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this
        miVista = inflater.inflate(R.layout.fragment_maps, container, false)
        return miVista
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapView: MapView?
        mapView = miVista!!.findViewById(R.id.map) as MapView
        mapView.onCreate(null)
        mapView.onResume()
        mapView.getMapAsync(this)
        verSenialGPS()
    }

    override fun onResume() {
        super.onResume()
        verSenialGPS()
    }

    fun verSenialGPS() {
        try {
            val gps =
                Settings.Secure.getInt(activity!!.contentResolver, Settings.Secure.LOCATION_MODE)

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
        val gMap: GoogleMap? = googleMap
        val gt = LatLng(14.599935, -90.51634)
        //val zoom = CameraUpdateFactory.zoomTo(10f)
        gMap!!.addMarker(MarkerOptions().position(gt).draggable(true))
        //gMap!!.animateCamera(zoom)


        gMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragEnd(marker: Marker?) {
                var direccion: List<Address>? = null
                val latitud = marker!!.position.latitude
                val longitud = marker.position.longitude

                try {
                    direccion = geoCoder!!.getFromLocation(latitud, longitud, 1)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                val pais = direccion!![0].countryName
                val estado = direccion[0].adminArea
                val calle = direccion[0].getAddressLine(0)
                val codigoPostal = direccion[0].postalCode
                val ciudad = direccion[0].locality

                marker.title = ciudad
                marker.snippet = calle


                /*Toast.makeText(
                    context,
                    "La locacion es: $pais , $ciudad \n $estado , $calle $codigoPostal",
                    Toast.LENGTH_LONG
                ).show()*/
            }

            override fun onMarkerDragStart(p0: Marker?) {
            }

            override fun onMarkerDrag(p0: Marker?) {
            }

        })

        geoCoder = Geocoder(context, Locale.getDefault())
    }

}
