package com.materiapps.gloom.ui.viewmodels.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.materiapps.gloom.UserProfileQuery
import kotlinx.coroutines.launch

class UserProfileViewModel(
    client: ApolloClient,
    username: String
) : ViewModel() {

    var isLoading by mutableStateOf(true)
    var user: UserProfileQuery.User? by mutableStateOf(null)
    var hasErrors by mutableStateOf(false)

    init {
        viewModelScope.launch {
            if (username.isNotEmpty()) {
                client.query(UserProfileQuery(username)).execute().also {
                    user = it.data?.user
                    hasErrors = it.hasErrors()
                }
            }
        }
    }

}