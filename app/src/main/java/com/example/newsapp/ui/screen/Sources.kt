package com.example.newsapp.ui.screen

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card

import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

import com.example.newsapp.models.TopNewsArticle
import com.example.newsapp.network.NewsManager
import com.example.newsapp.ui.theme.Purple40
import com.example.newsapp.ui.theme.Purple80
import com.example.newsapp.ui.theme.PurpleGrey40

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Sources(newsManager: NewsManager){


    val items= listOf(
        "TechCrunch" to "techcrunch",
        "TalkSport" to "talksport",
        "Business Insider" to "business-insider",
        "Reuters" to "reuters",
        "Politics" to "politics",
        "TheVerge" to "the-verge"
    )

    Scaffold(topBar={
        TopAppBar(title = { 
            Text(text = "${newsManager.sourceName.value} Source")
        },
         actions={
             var menuExpanded by remember{ mutableStateOf(false)}
             IconButton(onClick = {menuExpanded=true}){
                 Icon(Icons.Default.MoreVert, contentDescription=null)
             }
             MaterialTheme(shapes=MaterialTheme.shapes.copy(
                 medium= RoundedCornerShape(16.dp))) {
                    DropdownMenu(expanded = menuExpanded,
                        onDismissRequest = { menuExpanded=false }) {

                        items.forEach{
                            androidx.compose.material.DropdownMenuItem(onClick = {
                                newsManager.sourceName.value = it.second
                                menuExpanded=false
                            }){
                               Text(it.first)
                            }
                        }
                    }
             }
         }
        )
    }) {
        newsManager.getArticlesBySource()
        val articles=newsManager.getArticleBySource.value
        SourceContent(articles = articles.articles?: listOf())
    }
}


@Composable
fun SourceContent(articles: List<TopNewsArticle>){
    val uriHandler = LocalUriHandler.current

    LazyColumn{
        items(articles){ article->
            val annotatedString= buildAnnotatedString {
                pushStringAnnotation(
                    tag="URL",
                    annotation = article.url ?: "newsapi.org"
                )
                withStyle(style= SpanStyle
                    (color= Purple40,
                    textDecoration= TextDecoration.Underline
                    )){
                    append("Read Full Article")
                }
            }

            Card(backgroundColor= Purple80,
                elevation = 6.dp,
                modifier = Modifier.padding(8.dp)
            ){
                Column(modifier= Modifier
                    .height(200.dp)
                    .padding(end = 8.dp, start = 8.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    ){

                    Text(
                        text = article.title ?: "Not Available",
                        fontWeight = FontWeight.Bold ,
                        maxLines=2 ,
                        overflow=TextOverflow.Ellipsis
                    )
                    Text(
                        text = article.description ?: "Not Available",
                        maxLines=3,
                        overflow=TextOverflow.Ellipsis
                    )

                    Card(backgroundColor= Color.White, elevation = 6.dp){
                        ClickableText(text = annotatedString, modifier = Modifier.padding(8.dp), onClick ={
                            annotatedString.getStringAnnotations(it, it).firstOrNull()?.let{
                                result->
                                if(result.tag=="URL"){
                                    uriHandler.openUri(result.item)
                                }
                            }
                        } )
                    }
                }
            }
        }
    }
}