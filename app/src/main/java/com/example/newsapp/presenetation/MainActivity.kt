package com.example.newsapp.presenetation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.newsapp.presenetation.navigation.NavGraph
import com.example.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    @Inject
//    lateinit var appEntryUseCases: AppEntryUseCases
    
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        installSplashScreen().apply { 
            setKeepOnScreenCondition(condition = { viewModel.splashCondition.value })
        }

//        lifecycleScope.launch {
//            appEntryUseCases.readAppEntry().collect{
//                Log.d("Test", it.toString())
//            }
//        }

        setContent {
            NewsAppTheme {

                Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
//                    OnBoardingScreen()
                    NavGraph(startDestination = viewModel.startDestination.value)
                }

            }
        }
    }
}
