
package vboo.com.givemeweather.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import dagger.hilt.android.qualifiers.ApplicationContext
import vboo.com.weatherlib.data.NetworkUtils
import javax.inject.Inject

/**
 * Checks if a network connection exists.
 */
open class NetworkUtilsImpl @Inject constructor(@ApplicationContext val context: Context):
    NetworkUtils {

    override fun hasNetworkConnection(): Boolean {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}
