package com.hgshkt.todolistfirebase.data.auth

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class FirebaseAuthHelper {

    var account: GoogleSignInAccount? = null

    private val clientId = "716224848849-gk083su0e1r6hkit91g5d2a3vemnajqd.apps.googleusercontent.com"

    fun getLastSignedInAccount(context: Context): GoogleSignInAccount? {
        account = GoogleSignIn.getLastSignedInAccount(context)
        return account
    }

    fun createSignInIntent(context: Context): Intent {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)

        return googleSignInClient.signInIntent
    }

    fun signInWithCredential(
        data: Intent,
        afterSignIn: () -> Unit,
        errorHandling: (ApiException) -> Unit
    ) {
        val googleTask = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val result = googleTask.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(result.idToken, null)
            val auth = FirebaseAuth.getInstance()
            auth.signInWithCredential(credential)
            afterSignIn()
        } catch (e: ApiException) {
            errorHandling(e)
        }
    }
}