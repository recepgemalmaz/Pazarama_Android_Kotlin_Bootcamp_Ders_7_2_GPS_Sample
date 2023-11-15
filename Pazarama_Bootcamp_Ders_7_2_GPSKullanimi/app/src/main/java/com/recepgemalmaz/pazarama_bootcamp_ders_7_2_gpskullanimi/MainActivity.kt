package com.recepgemalmaz.pazarama_bootcamp_ders_7_2_gpskullanimi

import android.content.Context
import android.graphics.Color
import android.location.Criteria
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import dalvik.annotation.optimization.CriticalNative
import java.util.Locale

class MainActivity : AppCompatActivity(),LocationListener {

    lateinit var mng: LocationManager
    var curLoc : Location? = null

    override fun onLocationChanged(p0: Location) {

        if (curLoc != null)
        {
            curLoc = p0

            var gc = Geocoder(this, Locale.getDefault())
            var eslesmeler = gc.getFromLocation(curLoc!!.latitude,curLoc!!.longitude,5)
            var adres = ""


            adres += eslesmeler!![0].countryName +" -- "+eslesmeler!![0].postalCode


            setContentView(Compose(curLoc!!, adres))
        }
    }

    override fun onFlushComplete(requestCode: Int) {
        super.onFlushComplete(requestCode)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        super.onStatusChanged(provider, status, extras)
    }

    override fun onProviderEnabled(provider: String) {
        super.onProviderEnabled(provider)
        this.title = "GPS Açık"
    }

    override fun onProviderDisabled(provider: String) {
        super.onProviderDisabled(provider)
        this.title = "GPS Kapalı"
    }








    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mng =  this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        var crt = Criteria()
        crt.accuracy = Criteria.ACCURACY_FINE
        crt.powerRequirement = Criteria.POWER_MEDIUM
        crt.isSpeedRequired = false
        crt.isAltitudeRequired = false



        mng.requestLocationUpdates(mng.getBestProvider(crt,true)!!, 1000L, 1.0F, this)

        if (curLoc == null)
        {
            curLoc = Location(LocationManager.GPS_PROVIDER)

        }

        setContentView(Compose(curLoc!!, ""))



    }


    private fun Compose(loc: Location,addr : String) : LinearLayout
    {


        var lay = LinearLayout(this)
        lay.orientation = LinearLayout.VERTICAL

        var t1 = TextView(this)
        t1.setTextColor(Color.BLUE)
        t1.text = "Enlem: ${loc.latitude}"
        lay.addView(t1)

        var t2 = TextView(this)
        t2.setTextColor(Color.RED)
        t2.text = "Boylam: ${loc.longitude}"
        lay.addView(t2)

        var t3 = TextView(this)
        t3.setTextColor(Color.GREEN)
        t3.text = "Yükseklik: ${loc.altitude}"
        lay.addView(t3)

        var t4 = TextView(this)
        t4.setTextColor(Color.MAGENTA   )
        t4.text = "Adres: \n ${addr}"
        lay.addView(t4)

        return lay
    }


}