package com.example.notesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesapp.model.UserProfile

class ProfileViewModel : ViewModel() {

    // Backing property for LiveData
    private val _userProfile = MutableLiveData<UserProfile>()

    // Exposed LiveData
    val userProfile: LiveData<UserProfile> get() = _userProfile

    // Function to set the user's profile data
    fun setUserProfile(name: String, email: String) {
        _userProfile.value = UserProfile(name, email)
    }

    // Function to update user's name and email
    fun updateProfile(name: String, email: String) {
        _userProfile.value = _userProfile.value?.copy(name = name, email = email)
    }
}