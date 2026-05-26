package com.example.registrasisiswa.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun FormInput(
    nama: String,
    kelas: String,
    jurusan: String,
    noHp: String,
    sedangEdit: Boolean,
    onNamaChange: (String) -> Unit,
    onKelasChange: (String) -> Unit,
    onJurusanChange: (String) -> Unit,
    onNoHpChange: (String) -> Unit,
    onSimpan: () -> Unit,
    onBatal: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isFormValid = nama.isNotBlank() && kelas.isNotBlank() && jurusan.isNotBlank()

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = nama,
            onValueChange = onNamaChange,
            label = { Text("Nama siswa") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = kelas,
            onValueChange = onKelasChange,
            label = { Text("Kelas") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = jurusan,
            onValueChange = onJurusanChange,
            label = { Text("Jurusan") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = noHp,
            onValueChange = onNoHpChange,
            label = { Text("Nomor HP") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onSimpan,
                enabled = isFormValid,
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 48.dp)
            ) {
                Text(if (sedangEdit) "Simpan" else "Tambah")
            }
            if (sedangEdit) {
                OutlinedButton(
                    onClick = onBatal,
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 48.dp)
                ) {
                    Text("Batal")
                }
            }
        }
    }
}
