package com.android.modularmvi.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.modularmvi.R
import com.android.modularmvi.domain.model.Quote
import com.android.modularmvi.ui.common.component.CustomProgressIndicator
import com.android.modularmvi.ui.common.component.ErrorMessageCard
import com.android.modularmvi.ui.navigation.Destinations
import com.android.modularmvi.ui.navigation.Navigation
import com.android.modularmvi.ui.screens.home.component.ListItem
import com.android.modularmvi.ui.theme.ApplicationTheme
import com.android.modularmvi.util.MODE_LIGHT
import com.android.modularmvi.util.MODE_NIGHT

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToDetail: (Destinations) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    HandleSideEffects(viewModel, onNavigateToDetail)

    ScreenUiContent(
        uiState = uiState,
        onItemClick = { itemId ->
            viewModel.handleIntent(HomeIntent.OnItemClick(itemId))
        },
        onFetchOnlineQuotes = {
            viewModel.handleIntent(HomeIntent.FetchLiveQuotes)
        }
    )
}

/**
 * Observes and handles side effects such as navigation and displaying messages.
 */
@Composable
fun HandleSideEffects(
    viewModel: HomeViewModel,
    onNavigateToDetail: (Destinations) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is HomeEffect.NavigateToItemDetail -> {
                    onNavigateToDetail(Destinations.Detail(effect.itemId))
                }
                is HomeEffect.ShowMessage -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenUiContent(
    uiState: HomeUiState,
    onItemClick: (String) -> Unit,
    onFetchOnlineQuotes: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = Navigation.Routes.HOME.uppercase(),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
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
            Column {
                AppHeader(onFetchOnlineQuotes)
                ItemList(uiState.quotes, onItemClick)
            }

            when {
                uiState.isLoading -> CustomProgressIndicator()
                uiState.error.isNotBlank() -> ErrorMessageCard(uiState.error)
            }
        }
    }
}

/**
 * Displays the app header with a refresh button.
 */
@Composable
fun AppHeader(
    onFetchOnlineQuotes: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Daily Quotes",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displayMedium.copy(fontSize = 28.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            onClick = onFetchOnlineQuotes,
            modifier = Modifier
                .padding(horizontal = 36.dp)
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_autorenew),
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = "Refresh Quotes"
            )
            Text(
                text = "Refresh Quotes",
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

/**
 * Displays a list of quotes with click support.
 */
@Composable
fun ItemList(items: List<Quote>, onItemClick: (String) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items) { quote ->
            ListItem(quote, onItemClick)
        }
    }
}

@Preview(
    name = MODE_LIGHT,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    name = MODE_NIGHT,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun HomePreview() {

    val quotes = listOf(
        Quote(
            id = "pNnzE7wpM0W",
            author = "Dee Hock",
            content = "An organization, no matter how well designed, is only as good as the people who live and work in it.",
            tags = arrayListOf("Business"),
            authorSlug = "dee-hock",
            length = 100,
            dateAdded = "2022-07-06",
            dateModified = "2023-04-14"
        ),
        Quote(
            id = "Vs-4YEGn",
            author = "Simone Weil",
            content = "I can, therefore I am.",
            tags = arrayListOf("Inspirational"),
            authorSlug = "simone-weil",
            length = 22,
            dateAdded = "2020-03-11",
            dateModified = "2023-04-14"
        ),
    )

    val uiState = HomeUiState(
        isLoading = false,
        quotes = quotes,
        error = ""
    )

    ApplicationTheme {
        ScreenUiContent(
            uiState = uiState,
            onItemClick = {},
            onFetchOnlineQuotes = {},
        )
    }
}
