package com.example.praktikumpapb2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.praktikumpapb2.ui.theme.Papb2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Papb2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyScreen()
                }
            }
        }
    }
}

@Composable
fun MyScreen() {
    var text by remember { mutableStateOf("") }
    var inputNama by remember { mutableStateOf("") }
    var inputNIM by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Animasi warna teks hasil input
        val animatedTextColor by animateColorAsState(
            targetValue = if (text.isNotEmpty()) Color.Magenta else Color.Gray, label = ""
        )

        Text(
            text = text.ifEmpty { "Belum ada input" },
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = animatedTextColor,
            textAlign = TextAlign.Center,
            style = androidx.compose.ui.text.TextStyle(
                fontFamily = FontFamily.Cursive,
                textDecoration = TextDecoration.Underline // Teks dengan garis bawah
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Input Nama
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.AccountBox,
                contentDescription = "Icon Profile",
                tint = Color.Blue,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))

            OutlinedTextField(
                value = inputNama,
                onValueChange = { inputNama = it },
                label = { Text("Masukkan nama", color = Color.Black) },
                placeholder = { Text("Contoh: John Doe", color = Color.Gray) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(12.dp),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontFamily = FontFamily.Cursive,
                    fontSize = 20.sp,
                    color = Color.DarkGray
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Input NIM
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.AccountBox,
                contentDescription = "Icon NIM",
                tint = Color.Green,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))

            OutlinedTextField(
                value = inputNIM,
                onValueChange = { if (it.all { char -> char.isDigit() }) { inputNIM = it } },
                label = { Text("Masukkan NIM", color = Color.Black) },
                placeholder = { Text("Contoh: 12345678", color = Color.Gray) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(12.dp),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontFamily = FontFamily.Cursive,
                    fontSize = 20.sp,
                    color = Color.DarkGray
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Warna tombol yang berubah
        val isInputValid = inputNama.isNotEmpty() && inputNIM.isNotEmpty()
        val buttonColor by animateColorAsState(
            targetValue = if (isInputValid) Color.Blue else Color.Gray, label = ""
        )

        Button(
            onClick = {
                text = "Nama: $inputNama\nNIM: $inputNIM"
            },
            enabled = isInputValid, // Tombol hanya aktif jika input valid
            shape = CircleShape,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor, // Warna tombol dinamis
                contentColor = Color.White
            ),
        ) {
            Text(
                text = "Submit",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White, // Teks tetap berwarna putih
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Papb2Theme {
        MyScreen()
    }
}
