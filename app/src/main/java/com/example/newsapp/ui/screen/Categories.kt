package com.example.newsapp.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newsapp.MockData
import com.example.newsapp.MockData.getTimeAgo
import com.example.newsapp.R
import com.example.newsapp.models.TopNewsArticle
import com.example.newsapp.models.getAllArticleCategory
import com.example.newsapp.network.NewsManager
import com.example.newsapp.ui.theme.Purple40
import com.example.newsapp.ui.theme.Purple80
import com.example.newsapp.ui.theme.PurpleGrey40
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun Categories(onFetchCategory: (String) -> Unit, newsManager: NewsManager,navController: NavController){
    val tabsItems= getAllArticleCategory()
    Column {
        LazyRow{
            items(tabsItems.size){
                val category=tabsItems[it]
                CategoryTab(category=category.categoryName,
                    onFetchCategory=onFetchCategory ,
                    isSelected = newsManager.selectedCategory.value ==category
                    )
            }
        }
        ArticleContent(articles = newsManager.getArticleByCategory.value.articles?: listOf(),modifier= Modifier,navController)
    }
}

@Composable
fun CategoryTab(category: String, isSelected :Boolean=false,
                onFetchCategory:(String)-> Unit){
        val background=if(isSelected)(PurpleGrey40)else Purple40
    Surface(
        modifier= Modifier
            .padding(horizontal = 4.dp, vertical = 16.dp)
            .clickable {
                onFetchCategory(category)
            },
        shape= MaterialTheme.shapes.small,
        color=background,
    ){
        Text(text = category,
            style=MaterialTheme.typography.bodyMedium,
            color=Color.White,
            modifier= Modifier.padding(8.dp)
            )
    }

}


@Composable
fun ArticleContent(articles: List<TopNewsArticle>, modifier: Modifier=Modifier,navController: NavController){
    LazyColumn{
        items(articles.size){
            index->
            NewsItem(modifier, article = articles[index],onNewsClick={
                navController.navigate("Details/$index")
            })

        }
    }
}

@Composable
fun NewsItem(modifier:Modifier, article: TopNewsArticle, onNewsClick:()->Unit={}){
    Card(modifier= Modifier
        .padding(8.dp)
        .clickable {
            onNewsClick()
        },
        border= BorderStroke(2.dp, color= Purple40)){
        Row(
            modifier
                .fillMaxWidth()
                .padding(8.dp)){
            CoilImage(
                imageModel = article.urlToImage,
                modifier= Modifier.size(100.dp),
                placeHolder = painterResource(id = R.drawable.breaking_news),
                error = painterResource(id = R.drawable.breaking_news)
            )
            Column(modifier.padding((8.dp))) {
                Text(text=article.title?: "Not Available",
                    fontWeight= FontWeight.Bold,
                    maxLines = 3, overflow= TextOverflow.Ellipsis
                )
                Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                    Text(text=article.author?: "Not Available")
                    Text(text= MockData.stringToDate(article.publishedAt?: "2021-11-04T05:35:21Z").getTimeAgo())
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun ArticleContentPreviewer(){
//    ArticleContent(articles =listOf(
//        TopNewsArticle(
//            author = "Raja Razek, CNN",
//            title = " Tiger King Says he ahs beeen diagonised with severe......",
//            description = " Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam." +
//                    "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
//                    "\n" +
//                    "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.",
//            publishedAt = "2021-11-04T05:35:21Z"
//    )
//    ))
//}