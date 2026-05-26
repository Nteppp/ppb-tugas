package com.example.registrasisiswa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.registrasisiswa.data.Siswa
import com.example.registrasisiswa.data.SiswaDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StudentViewModel(private val siswaDao: SiswaDao) : ViewModel() {
    val daftarSiswa: StateFlow<List<Siswa>> = siswaDao.getAllSiswa()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun tambahSiswa(nama: String, kelas: String, jurusan: String, noHp: String) {
        viewModelScope.launch {
            siswaDao.insertSiswa(
                Siswa(
                    nama = nama.trim(),
                    kelas = kelas.trim(),
                    jurusan = jurusan.trim(),
                    noHp = noHp.trim()
                )
            )
        }
    }

    fun ubahSiswa(siswa: Siswa, nama: String, kelas: String, jurusan: String, noHp: String) {
        viewModelScope.launch {
            siswaDao.updateSiswa(
                siswa.copy(
                    nama = nama.trim(),
                    kelas = kelas.trim(),
                    jurusan = jurusan.trim(),
                    noHp = noHp.trim()
                )
            )
        }
    }

    fun hapusSiswa(siswa: Siswa) {
        viewModelScope.launch {
            siswaDao.deleteSiswa(siswa)
        }
    }
}

class StudentViewModelFactory(private val siswaDao: SiswaDao) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            return StudentViewModel(siswaDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
