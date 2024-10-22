package com.example.praktikumpapb2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setContent {
            LoginScreen(
                onLogin = { email, password -> login(email, password) },
                onGithubClick = {
                    // Navigasi ke GithubProfileActivity dengan username contoh
                    startActivity(
                        Intent(this, GithubProfileActivity::class.java).apply {
                            putExtra("username", "octocat")
                        }
                    )
                }
            )
        }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login sukses, navigasi ke ListActivity
                    startActivity(Intent(this, ListActivity::class.java))
                    finish() // Menutup MainActivity agar tidak bisa kembali
                } else {
                    // Tampilkan pesan kesalahan, misalnya menggunakan Toast
                    // Toast.makeText(this, "Login gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    @Composable
    fun LoginScreen(onLogin: (String, String) -> Unit, onGithubClick: () -> Unit) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Scaffold(
            topBar = { AppTopBar(onGithubClick) },
            floatingActionButton = { GithubFab(onGithubClick) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onLogin(email, password) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppTopBar(onGithubClick: () -> Unit) {
        TopAppBar(
            title = { Text("Login") },
            actions = {
                IconButton(onClick = onGithubClick) {
                    Icon(Icons.Default.AccountCircle, contentDescription = "GitHub Profile")
                }
            }
        )
    }

    @Composable
    fun GithubFab(onClick: () -> Unit) {
        FloatingActionButton(onClick = onClick) {
            Icon(Icons.Default.Person, contentDescription = "GitHub Profile")
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        LoginScreen(
            onLogin = { _, _ -> },
            onGithubClick = {}
        )
    }
}
