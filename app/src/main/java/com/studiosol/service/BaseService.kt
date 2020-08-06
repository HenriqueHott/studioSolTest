package com.studiosol.service

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.studiosol.utils.SingletonHolder
import org.json.JSONObject

open class BaseService (context: Context) {

    companion object : SingletonHolder<BaseService, Context>(::BaseService)

    private val requestQueue: RequestQueue by lazy {
        // applicationContext is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    private fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }

    protected fun sendJsonObjectRequest(
        url: String,
        method: Int,
        listener: (JSONObject) -> Unit,
        onError: (VolleyError) -> Unit,
        params: Map<String, String>? = null
    ) {
        val formattedUrl = "$url?${params?.map { "${it.key}=${it.value}" }?.joinToString("&")}"
        val jsonObjectRequest = JsonObjectRequest(
            method,
            formattedUrl,
            null,
            Response.Listener(listener),
            Response.ErrorListener(onError)
        )
        addToRequestQueue(jsonObjectRequest)
    }

}

