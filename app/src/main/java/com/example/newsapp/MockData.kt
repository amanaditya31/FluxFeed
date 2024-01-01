package com.example.newsapp

import android.os.Build
import android.os.Build.VERSION.SDK
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object MockData {

    val topNewsList= listOf<NewsData>(
        NewsData(
            1,
            author = "Raja Razek, CNN",
            title = " Tiger King Says he ahs beeen diagonised with severe......",
            description = " Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam." +
                    "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                    "\n" +
                    "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.",
            published = "2021-11-04T05:35:21Z"
        ),
        NewsData(
            2,
            R.drawable.this_is_fine,
            author = "Raja Razek, CNN",
            title = " Tiger King Says he ahs beeen diagonised with severe......",
            description = " Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam",
            published = "2021-11-04T05:35:21Z"
        ),NewsData(
            3,
            R.drawable.images,
            author = "Raja Razek, CNN",
            title = " Tiger King Says he ahs beeen diagonised with severe......",
            description = " Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam",
            published = "2021-11-04T05:35:21Z"
        ),NewsData(
            4,
            R.drawable.images__1_,
            author = "Raja Razek, CNN",
            title = " Tiger King Says he ahs beeen diagonised with severe......",
            description = " Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam",
            published = "2021-11-04T05:35:21Z"
        ),NewsData(
            5,
            R.drawable.images__2_,
            author = "Raja Razek, CNN",
            title = " Tiger King Says he ahs beeen diagonised with severe......",
            description = " Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam",
            published = "2021-11-04T05:35:21Z"
        ),NewsData(
            6,
            R.drawable.images__2_,
            author = "Raja Razek, CNN",
            title = " Tiger King Says he ahs beeen diagonised with severe......",
            description = " Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam",
            published = "2021-11-04T05:35:21Z"
        ),NewsData(
            7,
            R.drawable.images__3_,
            author = "Raja Razek, CNN",
            title = " Tiger King Says he ahs beeen diagonised with severe......",
            description = " Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam",
            published = "2021-11-04T05:35:21Z"
        ),NewsData(
            8,
            R.drawable.images__4_,
            author = "Raja Razek, CNN",
            title = " Tiger King Says he ahs beeen diagonised with severe......",
            description = " Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam",
            published = "2021-11-04T05:35:21Z"
        ),

    )

    fun getNews(newsId: Int?):NewsData{
        return topNewsList.first{it.id==newsId}
    }

    fun Date.getTimeAgo(): String{
        val calendar= Calendar.getInstance()
        calendar.time=this

        val year=calendar.get(Calendar.YEAR)
        val month=calendar.get(Calendar.MONTH)
        val day=calendar.get(Calendar.DAY_OF_MONTH)
        val hour=calendar.get(Calendar.HOUR_OF_DAY)
        val minute=calendar.get(Calendar.MINUTE)
        Log.d("cyear", "$year")


        val currentCalendar=Calendar.getInstance()

        val currentyear=calendar.get(Calendar.YEAR)
        val currentmonth=calendar.get(Calendar.MONTH)
        val currentday=calendar.get(Calendar.DAY_OF_MONTH)
        val currenthour=calendar.get(Calendar.HOUR_OF_DAY)
        val currentminute=calendar.get(Calendar.MINUTE)

        Log.d("year", "$currentyear")

        return if(year<currentyear){
            val interval=currentyear- year
            if(interval==1)"$interval year ago" else "$interval years ago"
        }else if(month<currentmonth){
            val interval=currentmonth- month
            if(interval==1)"$interval month ago" else "$interval months ago"
        }else if(day<currentday){
            val interval=currentday- day
            if(interval==1)"$interval day ago" else "$interval days ago"
        }else if(hour<currenthour){
            val interval=currenthour- hour
            if(interval==1)"$interval hour ago" else "$interval hours ago"
        }else if(minute<currentminute){
            val interval=currentminute- minute
            if(interval==1)"$interval minute ago" else "$interval minutes ago"
        }else{
            " a moment ago"
        }
    }
    fun stringToDate(publishedAt: String): Date
    {
        val date=
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss",
                    Locale.ENGLISH).parse(publishedAt)
            }else{
                SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss",
                    Locale.ENGLISH).parse(publishedAt)
            }
        Log.d("published", "$date")

        return date
    }
}