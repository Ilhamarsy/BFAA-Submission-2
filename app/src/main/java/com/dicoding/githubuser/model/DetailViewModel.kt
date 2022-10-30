package com.dicoding.githubuser.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.response.DetailUserResponse
import com.dicoding.githubuser.api.ApiConfig
import com.dicoding.githubuser.event.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val _data = MutableLiveData<DetailUserResponse>()
    val data: LiveData<DetailUserResponse> = _data

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Event<Boolean>>()
    val isError: LiveData<Event<Boolean>> = _isError

    fun setData(user: String) {
        _isLoading.value = true
        _isError.value = Event(false)
        val client = ApiConfig.getApiService().getDetail(user)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _data.postValue(response.body())
                } else  {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _isError.value = Event(true)
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _isError.value = Event(true)
                _isLoading.value = false
            }
        })
    }

    companion object{
        private const val TAG = "DetailViewModel"
    }
}