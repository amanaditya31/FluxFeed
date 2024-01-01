@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.newsapp.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.BottomMenuScreen
import com.example.newsapp.MockData
import com.example.newsapp.components.BottomMenu
import com.example.newsapp.models.TopNewsArticle
import com.example.newsapp.network.NewsManager
import com.example.newsapp.ui.screen.Categories
import com.example.newsapp.ui.screen.DetailScreen
import com.example.newsapp.ui.screen.Sources
import com.example.newsapp.ui.screen.TopNews

@Composable
fun NewsApp(){
    val scrollState= rememberScrollState()
    val navController= rememberNavController()
    MainScreen(navController , scrollState)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState){
    Scaffold(bottomBar = {
                         BottomMenu(navController)
    },

    ) {
        Navigation(navController,scrollState, paddingValues = it)

    }
}

@Composable
fun Navigation(navController: NavHostController,
               scrollState: ScrollState,
               newsManager: NewsManager=NewsManager(),
               paddingValues: PaddingValues
){
    val articles=newsManager.newsResponse.value.articles
    Log.d("news","$articles")
    articles?.let {

        NavHost(navController = navController, startDestination =
        BottomMenuScreen.TopNews.route, modifier= Modifier.padding(paddingValues= paddingValues)

        ) {
            bottomNavigation(navController = navController, articles, newsManager)

            composable("Detail/{index}",
                arguments = listOf(navArgument("index") { type = NavType.IntType })
            ) { navBackStackEntry ->
                val index= navBackStackEntry.arguments?.getInt("index")
                index?.let{
                    val article =articles[index]
                    DetailScreen(article, scrollState, navController)
                }


            }

        }
    }

    val catarticle=newsManager.getArticleByCategory.value.articles
    Log.d("news","$catarticle")
    catarticle?.let {

        NavHost(navController = navController, startDestination =
        BottomMenuScreen.Categories.route, modifier= Modifier.padding(paddingValues= paddingValues)

        ) {
            bottomNavigation(navController = navController, catarticle, newsManager)

            composable("Details/{index}",
                arguments = listOf(navArgument("index") { type = NavType.IntType })
            ) { navBackStackEntry ->
                val index= navBackStackEntry.arguments?.getInt("index")
                index?.let{
                    val article =catarticle[index]
                    DetailScreen(article, scrollState, navController)
                }


            }
        }
    }

}


fun NavGraphBuilder.bottomNavigation(navController: NavController,
                                     articles: List<TopNewsArticle>,
                                     newsManager: NewsManager){
    composable(BottomMenuScreen.TopNews.route){
        TopNews(navController = navController, articles)
    }
    composable(BottomMenuScreen.Categories.route){
        newsManager.getArticlesByCategory("general")
        newsManager.onSelectedCategoryChanged("general")
        Categories(newsManager=newsManager, onFetchCategory = {
            newsManager.onSelectedCategoryChanged(it)
            newsManager.getArticlesByCategory(it)
        },navController = navController)
    }
    composable(BottomMenuScreen.Sources.route){
        Sources(newsManager=newsManager)
    }
}