import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyForm()
        }
    }

    @Composable
    fun MyForm() {
        var inputNama by remember { mutableStateOf("") }
        var inputNIM by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf("") }
        var text by remember { mutableStateOf("") }

        val isInputValid = inputNama.isNotEmpty() && inputNIM.all { it.isDigit() }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Animasi warna teks hasil input
            val animatedTextColor by animateColorAsState(
                targetValue = if (text.isNotEmpty()) Color.Magenta else Color.Gray
            )

            Text(
                text = text.ifEmpty { "Silakan Masukkan Nama dan NIM" },
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = animatedTextColor,
                textAlign = TextAlign.Center,
                style = androidx.compose.ui.text.TextStyle(
                    fontFamily = FontFamily.Cursive,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Input Nama
            Row {
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = "Icon Nama",
                    tint = Color.Blue,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))

                OutlinedTextField(
                    value = inputNama,
                    onValueChange = {
                        inputNama = it
                        errorMessage = "" // Reset error jika ada input baru
                    },
                    label = { Text("Masukkan Nama", color = Color.Black) },
                    placeholder = { Text("Contoh: Jane Doe", color = Color.Gray) },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 18.sp,
                        color = Color.DarkGray
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Input NIM
            OutlinedTextField(
                value = inputNIM,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) {
                        inputNIM = it
                        errorMessage = "" // Reset error jika input valid
                    }
                },
                label = { Text("Masukkan NIM", color = Color.Black) },
                placeholder = { Text("Contoh: 12345678", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(12.dp),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 18.sp,
                    color = Color.DarkGray
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Pesan error jika input tidak valid
            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tombol Submit
            Button(
                onClick = {
                    if (isInputValid) {
                        performLogin(inputNama, inputNIM,
                            onSuccess = {
                                val context = LocalContext.current
                                val intent = Intent(context, ListActivity::class.java)
                                context.startActivity(intent)
                            },
                            onError = {
                                errorMessage = it
                            }
                        )
                    } else {
                        errorMessage = "NIM harus berupa angka."
                    }
                },
                enabled = isInputValid,
                shape = CircleShape,
                modifier = Modifier
                    .padding(horizontal = 48.dp)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isInputValid) Color.Blue else Color.Gray,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Submit",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }

    private fun performLogin(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    task.exception?.message?.let {
                        val it
                        onError(it)
                    }
                }
            }
    }
}