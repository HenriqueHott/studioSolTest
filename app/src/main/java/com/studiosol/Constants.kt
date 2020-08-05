package com.studiosol

class Constants {
    companion object{
        private const val API_URL = "https://us-central1-ss-devops.cloudfunctions.net"
        const val RAND_ENDPOINT = "$API_URL/rand";
        const val MAX_ATTEMPTS = 3;
    }
}