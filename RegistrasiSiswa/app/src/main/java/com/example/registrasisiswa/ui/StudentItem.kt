package com.example.registrasisiswa.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.registrasisiswa.data.Siswa

@Composable
fun StudentItem(
    siswa: Siswa,
    onEdit: (Siswa) -> Unit,
    onDelete: (Siswa) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = siswa.nama,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "${siswa.kelas} - ${siswa.jurusan}",
                style = MaterialTheme.typography.bodyMedium
            )
            if (siswa.noHp.isNotBlank()) {
                Text(
                    text = siswa.noHp,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = { onDelete(siswa) },
                    modifier = Modifier.heightIn(min = 44.dp)
                ) {
                    Text("Hapus")
                }
                OutlinedButton(
                    onClick = { onEdit(siswa) },
                    modifier = Modifier.heightIn(min = 44.dp)
                ) {
                    Text("Edit")
                }
            }
        }
    }
}
