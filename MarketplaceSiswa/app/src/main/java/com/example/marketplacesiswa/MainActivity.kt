package com.example.marketplacesiswa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.marketplacesiswa.ui.theme.MarketplaceSiswaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarketplaceSiswaTheme {
                MarketplaceSiswaApp()
            }
        }
    }
}

private data class Product(
    val name: String,
    val category: String,
    val seller: String,
    val price: String,
    val description: String,
    val color: Color
)

private val sampleProducts = listOf(
    Product(
        name = "Brownies Lumer",
        category = "Makanan",
        seller = "Naya XI RPL",
        price = "Rp 15.000",
        description = "Cokelat melimpah, cocok untuk camilan sore.",
        color = Color(0xFFFFE1D2)
    ),
    Product(
        name = "Totebag Custom",
        category = "Fashion",
        seller = "Dimas X DKV",
        price = "Rp 35.000",
        description = "Tas kanvas tebal dengan ilustrasi sesuai request.",
        color = Color(0xFFDDE7FF)
    ),
    Product(
        name = "Stiker Planner",
        category = "Alat Tulis",
        seller = "Rara XII IPA",
        price = "Rp 8.000",
        description = "Set stiker lucu untuk jurnal dan catatan sekolah.",
        color = Color(0xFFE6F6DF)
    ),
    Product(
        name = "Jasa Desain Poster",
        category = "Jasa",
        seller = "Tim Kreatif OSIS",
        price = "Rp 20.000",
        description = "Poster acara sekolah siap unggah ke media sosial.",
        color = Color(0xFFFFF0C7)
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketplaceSiswaApp() {
    val products = remember { mutableStateListOf<Product>().apply { addAll(sampleProducts) } }
    var selectedCategory by remember { mutableStateOf("Semua") }
    var searchKeyword by remember { mutableStateOf("") }
    var showAddProductDialog by remember { mutableStateOf(false) }
    val displayedProducts = products.filter { product ->
        val matchesCategory = selectedCategory == "Semua" || product.category == selectedCategory
        val matchesSearch = searchKeyword.isBlank() ||
            product.name.contains(searchKeyword, ignoreCase = true) ||
            product.category.contains(searchKeyword, ignoreCase = true) ||
            product.seller.contains(searchKeyword, ignoreCase = true) ||
            product.description.contains(searchKeyword, ignoreCase = true) ||
            product.price.contains(searchKeyword, ignoreCase = true)

        matchesCategory && matchesSearch
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "MS",
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "MarketSiswa",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    Surface(
                        modifier = Modifier
                            .padding(end = 18.dp)
                            .size(36.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        tonalElevation = 2.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "JS",
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddProductDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                PlusIcon(modifier = Modifier.size(28.dp))
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                HeroSection()
            }
            item {
                SearchField(
                    keyword = searchKeyword,
                    onKeywordChange = { searchKeyword = it }
                )
            }
            item {
                CategoryRow(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { category -> selectedCategory = category }
                )
            }
            items(displayedProducts) { product ->
                ProductCard(product = product)
            }
        }
    }

    if (showAddProductDialog) {
        AddProductDialog(
            onDismiss = { showAddProductDialog = false },
            onAddProduct = { product ->
                products.add(0, product)
                selectedCategory = product.category
                searchKeyword = ""
                showAddProductDialog = false
            }
        )
    }
}

@Composable
private fun HeroSection() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        shape = RoundedCornerShape(28.dp),
        color = Color.Transparent,
        shadowElevation = 0.dp
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.tertiaryContainer
                        )
                    )
                )
                .padding(22.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth(0.74f)) {
                Text(
                    text = "Halo, Siswa!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Temukan produk kreatif dari teman-temanmu.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.74f)
                )
            }
            Surface(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(58.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.72f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = "Hi", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}

@Composable
private fun SearchField(
    keyword: String,
    onKeywordChange: (String) -> Unit
) {
    TextField(
        value = keyword,
        onValueChange = onKeywordChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = "Cari makanan, fashion, jasa...")
        },
        leadingIcon = {
            SearchIcon(
                modifier = Modifier.size(20.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(28.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
private fun AddProductDialog(
    onDismiss: () -> Unit,
    onAddProduct: (Product) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Makanan") }
    var seller by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val categories = listOf("Makanan", "Fashion", "Alat Tulis", "Jasa")
    val canSave = name.isNotBlank() && seller.isNotBlank() && price.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Tambah Produk",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Nama produk") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(categories) { item ->
                        val isSelected = item == category

                        AssistChip(
                            onClick = { category = item },
                            label = { Text(text = item) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = if (isSelected) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.surface
                                },
                                labelColor = if (isSelected) {
                                    MaterialTheme.colorScheme.onPrimary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                }
                            ),
                            border = null
                        )
                    }
                }
                OutlinedTextField(
                    value = seller,
                    onValueChange = { seller = it },
                    label = { Text(text = "Nama penjual") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text(text = "Harga") },
                    placeholder = { Text(text = "Rp 10.000") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(text = "Deskripsi") },
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onAddProduct(
                        Product(
                            name = name.trim(),
                            category = category,
                            seller = seller.trim(),
                            price = price.trim(),
                            description = description.trim().ifBlank {
                                "Produk kreatif siswa siap dipesan."
                            },
                            color = categoryColor(category)
                        )
                    )
                },
                enabled = canSave
            ) {
                Text(text = "Simpan")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Text(text = "Batal")
            }
        }
    )
}

private fun categoryColor(category: String): Color {
    return when (category) {
        "Makanan" -> Color(0xFFFFE1D2)
        "Fashion" -> Color(0xFFDDE7FF)
        "Alat Tulis" -> Color(0xFFE6F6DF)
        else -> Color(0xFFFFF0C7)
    }
}

@Composable
private fun CategoryRow(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf("Semua", "Makanan", "Fashion", "Alat Tulis", "Jasa")

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 1.dp)
    ) {
        items(categories) { category ->
            val isSelected = category == selectedCategory

            AssistChip(
                onClick = { onCategorySelected(category) },
                label = { Text(text = category) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surface
                    },
                    labelColor = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                ),
                border = null
            )
        }
    }
}

@Composable
private fun ProductCard(product: Product) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(86.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(product.color),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = product.name.first().toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.72f)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.category.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = product.price,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = product.seller,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    contentPadding = PaddingValues(vertical = 10.dp)
                ) {
                    Text(text = "Lihat Detail", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun SearchIcon(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier = modifier) {
        drawCircle(
            color = color,
            radius = size.minDimension * 0.34f,
            center = Offset(size.width * 0.43f, size.height * 0.43f),
            style = Stroke(width = 2.6.dp.toPx())
        )
        drawLine(
            color = color,
            start = Offset(size.width * 0.66f, size.height * 0.66f),
            end = Offset(size.width * 0.88f, size.height * 0.88f),
            strokeWidth = 2.6.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun PlusIcon(modifier: Modifier = Modifier) {
    val color = MaterialTheme.colorScheme.onPrimary

    Canvas(modifier = modifier) {
        val center = Offset(size.width / 2f, size.height / 2f)
        drawLine(
            color = color,
            start = Offset(center.x, size.height * 0.16f),
            end = Offset(center.x, size.height * 0.84f),
            strokeWidth = 3.2.dp.toPx(),
            cap = StrokeCap.Round
        )
        drawLine(
            color = color,
            start = Offset(size.width * 0.16f, center.y),
            end = Offset(size.width * 0.84f, center.y),
            strokeWidth = 3.2.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 760)
@Composable
fun MarketplaceSiswaPreview() {
    MarketplaceSiswaTheme(dynamicColor = false) {
        MarketplaceSiswaApp()
    }
}
