package com.example.registrasisiswa.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.registrasisiswa.data.Siswa
import com.example.registrasisiswa.viewmodel.StudentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: StudentViewModel) {
    val daftarSiswa by viewModel.daftarSiswa.collectAsState()
    var siswaDipilih by remember { mutableStateOf<Siswa?>(null) }
    var nama by remember { mutableStateOf("") }
    var kelas by remember { mutableStateOf("") }
    var jurusan by remember { mutableStateOf("") }
    var noHp by remember { mutableStateOf("") }

    fun resetForm() {
        siswaDipilih = null
        nama = ""
        kelas = ""
        jurusan = ""
        noHp = ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrasi Siswa") }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = if (siswaDipilih == null) "Tambah Data Siswa" else "Edit Data Siswa",
                        style = MaterialTheme.typography.titleLarge
                    )
                    FormInput(
                        nama = nama,
                        kelas = kelas,
                        jurusan = jurusan,
                        noHp = noHp,
                        sedangEdit = siswaDipilih != null,
                        onNamaChange = { nama = it },
                        onKelasChange = { kelas = it },
                        onJurusanChange = { jurusan = it },
                        onNoHpChange = { noHp = it },
                        onSimpan = {
                            val siswa = siswaDipilih
                            if (siswa == null) {
                                viewModel.tambahSiswa(nama, kelas, jurusan, noHp)
                            } else {
                                viewModel.ubahSiswa(siswa, nama, kelas, jurusan, noHp)
                            }
                            resetForm()
                        },
                        onBatal = { resetForm() }
                    )
                }
            }

            item {
                HorizontalDivider()
            }

            item {
                Text(
                    text = "Daftar Siswa (${daftarSiswa.size})",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (daftarSiswa.isEmpty()) {
                item {
                    Text(
                        text = "Belum ada data siswa.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                items(
                    items = daftarSiswa,
                    key = { it.id }
                ) { siswa ->
                    StudentItem(
                        siswa = siswa,
                        onEdit = {
                            siswaDipilih = it
                            nama = it.nama
                            kelas = it.kelas
                            jurusan = it.jurusan
                            noHp = it.noHp
                        },
                        onDelete = {
                            if (siswaDipilih?.id == it.id) {
                                resetForm()
                            }
                            viewModel.hapusSiswa(it)
                        }
                    )
                }
            }
        }
    }
}
