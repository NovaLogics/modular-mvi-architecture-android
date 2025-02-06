package com.android.modularmvi.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.modularmvi.ui.common.component.ErrorMessageCard
import com.android.modularmvi.ui.navigation.Destinations
import com.android.modularmvi.ui.navigation.Routes
import com.android.modularmvi.ui.screens.home.component.ListItem
import com.android.modularmvi.ui.theme.ApplicationTheme
import com.android.modularmvi.util.Constants

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToDetail: (Destinations) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    EventListener(viewModel, onNavigateToDetail)

    ScreenUiContent(
        uiState = uiState,
        onItemClick = { item ->
            viewModel.handleIntent(HomeIntent.OnItemClick(item))
        }
    )
}

/**
 * Handles side effects like navigation and showing messages.
 */
@Composable
fun EventListener(
    viewModel: HomeViewModel,
    onNavigateToDetail: (Destinations) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is HomeEvent.NavigateToItemDetail -> {
                    onNavigateToDetail(Destinations.Detail(event.itemId))
                }

                is HomeEvent.ShowMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenUiContent(
    uiState: HomeUiState,
    onItemClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = Routes.HOME.uppercase(),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }

                uiState.error.isNotBlank() -> {
                    ErrorMessageCard(uiState.error)
                }

                else -> {
                    ItemList(uiState.items, onItemClick)
                }
            }
        }
    }
}

/**
 * Displays a list of items with click support.
 */
@Composable
fun ItemList(items: List<String>, onItemClick: (String) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items) { item ->
            ListItem(item, onItemClick)
        }
    }
}

@Preview(
    name = Constants.MODE_LIGHT,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    name = Constants.MODE_NIGHT,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun HomePreview() {
    val uiState = HomeUiState(
        isLoading = false,
        items = listOf("Item 1", "Item 2", "Item 3"),
        error = ""
    )

    ApplicationTheme {
        ScreenUiContent(
            uiState = uiState,
            onItemClick = {}
        )
    }
}
