package com.example.howlstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null
    var googleSignInClient : GoogleSignInClient? = null
    var GOOGLE_LOGIN_CODE = 9001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        email_login_button.setOnClickListener {
            signinAndSignup()
        }
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestIdToken("851786780198-1isihfjbg7b6brdaothmk3n99flpgn7m.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }


       fun signinAndSignup() {
            auth?.createUserWithEmailAndPassword(email_edittext.text.toString(), password_edittext.text.toString())
                ?.addOnCompleteListener {
                    task ->
                        if(task.isSuccessful) {
                            //아이디가 생성 되었을 때 필요한 코드
                            moveMainPage(task.result!!.user)
                        } else if(task.exception?.message.isNullOrEmpty()) {
                            //로그인 실패 에러 메세지
                            Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                        } else {
                            //로그인으로 빠지도록
                            signinEmail()
                        }
                }
        }
    fun signinEmail() {
        auth?.signInWithEmailAndPassword(email_edittext.text.toString(), password_edittext.text.toString())
            ?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful) {
                    //아이디와 패스워드가 맞을 때
                    moveMainPage(task.result!!.user)
                }  else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    fun moveMainPage(user:FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}