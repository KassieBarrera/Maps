package com.example.mapas.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.mapas.Fragments.MainFragment
import com.example.mapas.Fragments.MapsFragment
import com.example.mapas.Fragments.MapsGPS
import com.example.mapas.R
import com.google.android.gms.maps.MapFragment

class MainActivity : AppCompatActivity() {

    var fragmentoActual: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentoActual = MainFragment()
        cambiarFragment(fragmentoActual!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_principal -> fragmentoActual = MainFragment()
            R.id.menu_mapa -> fragmentoActual = MapsFragment()
            R.id.gps -> fragmentoActual = MapsGPS()
        }

        cambiarFragment(fragmentoActual!!)
        return super.onOptionsItemSelected(item)
    }

    fun cambiarFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
