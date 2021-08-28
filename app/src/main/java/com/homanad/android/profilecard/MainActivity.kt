package com.homanad.android.profilecard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.homanad.android.profilecard.ui.theme.ProfileCardTheme
import com.homanad.android.profilecard.ui.theme.lightGreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileCardTheme {
                UserListScreen()
            }
        }
    }
}

@Composable
fun UserListScreen(users: List<User> = userList) {
    Scaffold(topBar = { AppBar() }) {
        Surface(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                items(users) {
                    ProfileCard(it)
                }
            }
        }
    }
}

@Composable
fun AppBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                Icons.Default.ArrowBack,
                "Content description",
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        },
        title = { Text(text = "Home") })
}

@Composable
fun ProfileCard(user: User) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top),
        elevation = 8.dp,
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ProfilePicture(user.pictureUrl, user.status, 72.dp)
            ProfileContent(user.name, user.status, Alignment.Start)
        }
    }
}

@Composable
fun ProfilePicture(pictureUrl: String, isOnline: Boolean, imageSize: Dp) {
    Card(
        shape = CircleShape,
        border = BorderStroke(
            width = 2.dp,
            color = if (isOnline) MaterialTheme.colors.lightGreen else Color.Red
        ),
        modifier = Modifier.padding(16.dp),
        elevation = 4.dp
    ) {
        Image(
            painter = rememberImagePainter(
                data = pictureUrl,
                builder = { CircleCropTransformation() }
            ),
            modifier = Modifier.size(imageSize),
            contentDescription = "Profile picture description",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ProfileContent(name: String, isOnline: Boolean, alignment: Alignment.Horizontal) {
    Column(
        modifier = Modifier
            .padding(5.dp),
        horizontalAlignment = alignment
    ) {
        CompositionLocalProvider(LocalContentAlpha provides if (isOnline) 1f else ContentAlpha.medium) {
            Text(text = name, style = MaterialTheme.typography.h5)
        }

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = if (isOnline) "Active now" else "Offline",
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
fun UserDetailsScreen(user: User = userList[0]) {
    Scaffold(topBar = { AppBar() }) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                ProfilePicture(user.pictureUrl, user.status, 240.dp)
                ProfileContent(user.name, user.status, Alignment.CenterHorizontally)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserDetailsPreview() {
    ProfileCardTheme {
        UserDetailsScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun UserListPreview() {
    ProfileCardTheme {
        UserListScreen()
    }
}