@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.newsapp.ui.screen

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.MockData
import com.example.newsapp.MockData.getTimeAgo
import com.example.newsapp.NewsData
import com.example.newsapp.R
import com.example.newsapp.models.TopNewsArticle
import com.skydoves.landscapist.coil.CoilImage


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(article: TopNewsArticle, scrollState: ScrollState,navController: NavController){

    Scaffold(topBar = {
           DetailTopAppBar(onBackPressed = {navController.popBackStack()})
    }){
        Column(modifier= Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally ) {
            Text( text= "Detail Screen", fontWeight= FontWeight.SemiBold)
            CoilImage(
                imageModel=article.urlToImage,
                contentScale= ContentScale.Crop,
                error= ImageBitmap.imageResource(id=R.drawable.breaking_news),
                placeHolder= ImageBitmap.imageResource(id=R.drawable.breaking_news)
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                InfoWithIcon(Icons.Default.Edit, info =article.author?: "Not Available" )
                InfoWithIcon(Icons.Default.DateRange, info =MockData.stringToDate(article.publishedAt!! ).getTimeAgo())
            }
            Text(text = article.title?:"Not Available", fontWeight = FontWeight.Bold)
            Text(text = article.description?:"Not Available", modifier=Modifier.padding(top=16.dp))
        }
    }


}

@Composable
fun DetailTopAppBar(onBackPressed: ()-> Unit={}){
    TopAppBar(title=
    {
        Text("Detail Screen", modifier = Modifier, fontWeight=FontWeight.SemiBold)
    },
        navigationIcon = {
            IconButton(onClick = {onBackPressed()}) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        }
        )
}




@Composable
fun InfoWithIcon(icon: ImageVector, info: String){
    Row {
        Icon(icon,"Author", modifier=Modifier.padding(end=8.dp),
            colorResource(id = R.color.purple_500))
        Text(text = info)
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview(){
    DetailScreen(
        TopNewsArticle(
            author = "Raja Razek, CNN",
            title = " Tiger King Says he ahs beeen diagonised with severe......",
            description = " Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam",
            publishedAt = "2021-11-04T05: 35:21Z"
        )
       ,rememberScrollState(), rememberNavController())
}