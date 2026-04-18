//package com.csk.myjpmcandroid.util
//
//import android.content.Context
//import android.location.Address
//import android.location.Geocoder
//import android.os.Build
//import androidx.annotation.RequiresApi
//import java.util.Locale
//
//@RequiresApi(Build.VERSION_CODES.TIRAMISU)
//class AddressFinder(private val context: Context) {
//
//    fun getAddress(latitude: Double?, longitude: Double?): String {
//        val geocoder = Geocoder(context, Locale.getDefault())
//        var address: Address?
//        var fullAddress = ""
//        val map = mutableMapOf<String, String>()
//        geocoder.getFromLocation(latitude ?: 0.0, longitude ?: 0.0, 1,
//            object : Geocoder.GeocodeListener {
//                override fun onGeocode(addresses: MutableList<Address>) {
//                    address = addresses[0]
//                    address?.let { address ->
//                        fullAddress = address.getAddressLine(0).toString()
//                        map[FULL_ADDRESS] = fullAddress
//                        map[CITY] = address.locality ?: ""
//                        map[STATE] = address.adminArea ?: ""
//                        map[COUNTRY] = address.countryName ?: ""
//                        map[POSTAL_CODE] = address.postalCode ?: ""
//                        map[FEATURE_NAME] = address.featureName ?: ""
//                    }
//                }
//
//                override fun onError(errorMessage: String?) {
//                    super.onError(errorMessage)
//                    fullAddress = "$errorMessage"
//                }
//            })
//
//        return fullAddress
//    }
//
//    companion object {
//        const val FULL_ADDRESS = "fullAddress"
//        const val CITY = "city"
//        const val STATE = "state"
//        const val COUNTRY = "country"
//        const val POSTAL_CODE = "postalCode"
//        const val FEATURE_NAME = "featureName"
//    }
//}