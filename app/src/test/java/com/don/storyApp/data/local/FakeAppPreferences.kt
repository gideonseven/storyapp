package com.don.storyApp.data.local


/**
 * Created by gideon on 21 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
class FakeAppPreferences() {
    var mockAccessToken = ""

    fun setupMockAccessToken(accessToken: String) {
        mockAccessToken = accessToken
    }
}