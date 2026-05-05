package ru.makoto.fefustore.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import ru.makoto.fefustore.Entity.Size
import ru.makoto.fefustore.components.CategoryItem
import ru.makoto.fefustore.ui.theme.AppColors
import ru.makoto.fefustore.viewmodels.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardScreen(id: String, navController: NavController, viewModel: ViewModel) {
    val uiState by (viewModel as ProductsViewModel).uiState.collectAsState()

    val item = uiState.clothes.find { it.id == id }
    val activeSize = remember { mutableStateOf<Size?>(null) }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, "icon")
                }
            })
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                ) {
                    item?.sizes?.forEach {
                        CategoryItem(

                            it.name.toString(),
                            isActive = (activeSize.value?.id ?: "") != it.id,
                            onClick = { activeSize.value = it }
                        )
                    }
                }
                Button(
                    onClick = {}, shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = "Добавить в корзину - ${item?.price ?: "Undefined Price"}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                AsyncImage(
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth(),
                    model = item?.img,
                    contentDescription = "Picture"
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item?.title ?: "Undefined Title",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "icon",
                        tint = AppColors.GrayLight,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Text(
                    modifier = Modifier.padding(5.dp),
                    text = item?.longDescription ?: "Undefined Description",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }


        }
    }
}


