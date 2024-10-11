package com.filip.cryptoViewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.filip.cryptoViewer.presentation.navigation.BottomNavBar
import com.filip.cryptoViewer.presentation.navigation.NavGraph
import com.filip.cryptoViewer.presentation.ui.theme.CryptoViewerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoViewerTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    Scaffold(
                        modifier = Modifier.navigationBarsPadding(),
                        bottomBar = { BottomNavBar(navController) }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()-32.dp, top = 16.dp)) {
                            NavGraph(navController = navController)
                        }
                    }
                //    CoinTickerListScreen()
                }
            }
        }
    }
}

