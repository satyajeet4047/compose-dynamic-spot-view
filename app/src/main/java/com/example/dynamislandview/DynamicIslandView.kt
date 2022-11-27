package com.example.dynamislandview

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.dynamislandview.ui.theme.Typography

@OptIn(ExperimentalMotionApi::class)
@Composable
fun DynamicView() {

    val context = LocalContext.current
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.dynamic_island_motion)
            .readBytes()
            .decodeToString()
    }

    val shouldExpand = remember { mutableStateOf(false) }
    val motionProgress by animateFloatAsState(
        targetValue = if (shouldExpand.value) 1f else 0f,
        tween(250, easing = LinearEasing)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.TopCenter
    ) {
        MotionLayout(
            motionScene = MotionScene(content = motionScene),
            progress = motionProgress,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            CollapsedMusicView(shouldExpand)
            ExpandedMusicView(shouldExpand)

        }
    }
}

@Composable
fun CollapsedMusicView(shouldExpand: MutableState<Boolean>) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.music_playing))
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = true,
        restartOnPlay = true,
        iterations = Integer.MAX_VALUE
    )
    Card(
        Modifier
            .layoutId("collapsed_card")
            .wrapContentWidth()
            .wrapContentHeight()
            .clickable {
                shouldExpand.value = true
            },
        backgroundColor = Color.Black,
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(R.drawable.music_image),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,            // crop the image if it's not a square
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)                       // clip to the circle shape
                    .border(2.dp, Color.LightGray, CircleShape)   // add a border (optional)
            )
            Text(
                text = "Gunehgar",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 8.dp),
                maxLines = 1

            )
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier
                    .size(28.dp)
                    .padding(start = 12.dp)
            )
        }
    }
}

@Composable
fun ExpandedMusicView(shouldExpand: MutableState<Boolean>) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.music_playing))
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = true,
        restartOnPlay = true,
        iterations = Integer.MAX_VALUE
    )
    Card(
        Modifier
            .layoutId("expanded_card")
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
            .clickable {
                shouldExpand.value = false
            },
        backgroundColor = Color.Black,
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(R.drawable.music_image),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,            // crop the image if it's not a square
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)                       // clip to the circle shape
                        .border(2.dp, Color.LightGray, CircleShape)   // add a border (optional)
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append("Gunehgar")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal
                            )
                        ) {
                            append("\nBy Divine")
                        }
                    },
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(end = 102.dp)
                )
                LottieAnimation(
                    composition = composition,
                    progress = progress,
                    modifier = Modifier.size(36.dp),
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
            )
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "01:31",
                    style = Typography.body1,
                    color = Color.White,
                    modifier = Modifier.weight(0.125f)
                )
                LinearProgressIndicator(
                    progress = 0.5f,
                    color = Color.White,
                    backgroundColor = Color.Gray,
                    modifier = Modifier
                        .weight(0.65f)
                        .height(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Text(
                    text = "03:05",
                    style = Typography.body1,
                    color = Color.White,
                    modifier = Modifier
                        .weight(0.125f)
                        .padding(start = 4.dp)
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "share icon",
                    tint = Color.White
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_rewind),
                    tint = Color.White,
                    modifier = Modifier.padding(start = 48.dp),
                    contentDescription = "rewind icon"
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_pause),
                    tint = Color.White,
                    contentDescription = "play icon"
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_forward),
                    tint = Color.White,
                    modifier = Modifier.padding(end = 48.dp),
                    contentDescription = "forward icon"
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    tint = Color.White,
                    contentDescription = "close icon"
                )
            }
        }
    }
}