package edu.ucne.franniel_arias_ap2_p2.data.remote

import androidx.room.compiler.processing.util.Resource

sealed class Resource <T>(val data: T? = null, val message: String? = null){
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String, data: T? = null ): Resource<T>(data,message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
