package com.ardakazanci.dynamicalignmentbutton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ardakazanci.dynamicalignmentbutton.ui.theme.DynamicAlignmentButtonTheme
import com.ardakazanci.dynamicalignmentbutton.ui.theme.WhiteGray

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(WhiteGray.toArgb(), WhiteGray.toArgb()),
            navigationBarStyle = SystemBarStyle.auto(WhiteGray.toArgb(), WhiteGray.toArgb())
        )
        setContent {
            DynamicAlignmentButtonTheme {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    AlignmentMenuDemo(
                        modifier = Modifier
                            .background(WhiteGray)
                    )
                }
            }
        }
    }
}

@Composable
fun AlignmentMenuDemo(modifier: Modifier = Modifier) {
    var selectedAlignment by remember { mutableStateOf(AlignmentType.LEFT) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AlignmentClickableContent(
            alignment = selectedAlignment,
            onAlignmentChange = { selectedAlignment = it }
        )
    }
}

@Composable
fun AlignmentClickableContent(
    alignment: AlignmentType,
    onAlignmentChange: (AlignmentType) -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        val scope = this
        val cardPadding = 8.dp
        val cardExtra = 20.dp
        val availableWidth = (scope.maxWidth / 2.5f) + cardExtra - (cardPadding * 2)
        val transition = updateTransition(targetState = alignment, label = "alignmentTransition")

        val line1Width by transition.animateDp(
            transitionSpec = {
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            },
            label = "line1DummyWidth"
        ) { state ->
            when (state) {
                AlignmentType.LEFT -> 30.dp
                AlignmentType.CENTER -> 30.dp
                AlignmentType.RIGHT -> 30.dp
            }

        }
        val line2Width by transition.animateDp(
            transitionSpec = {
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            },
            label = "line2DummyWidth"
        ) { state ->
            when (state) {
                AlignmentType.LEFT -> 20.dp
                AlignmentType.CENTER -> 30.dp
                AlignmentType.RIGHT -> 20.dp
            }
        }
        val line3Width by transition.animateDp(
            transitionSpec = {
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            },
            label = "line3DummyLabel"
        ) { state ->
            when (state) {
                AlignmentType.LEFT -> 10.dp
                AlignmentType.CENTER -> 15.dp
                AlignmentType.RIGHT -> 10.dp
            }
        }

        val line1Offset by transition.animateDp(
            transitionSpec = {
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            },
            label = "line1DummyLabel"
        ) { state ->
            when (state) {
                AlignmentType.LEFT -> 0.dp
                AlignmentType.CENTER -> (availableWidth - line1Width) / 2
                AlignmentType.RIGHT -> availableWidth - line1Width
            }
        }
        val line2Offset by transition.animateDp(
            transitionSpec = {
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            },
            label = "line2DummyLabel"
        ) { state ->
            when (state) {
                AlignmentType.LEFT -> 0.dp
                AlignmentType.CENTER -> (availableWidth - 30.dp) / 2
                AlignmentType.RIGHT -> availableWidth - 20.dp
            }
        }
        val line3Offset by transition.animateDp(
            transitionSpec = {
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            },
            label = "line3DummyLabel"
        ) { state ->
            when (state) {
                AlignmentType.LEFT -> 0.dp
                AlignmentType.CENTER -> (availableWidth - 15.dp) / 2
                AlignmentType.RIGHT -> availableWidth - 10.dp
            }
        }


        val ghostData = remember(availableWidth) {
            listOf(
                GhostLineData(
                    widths = listOf(30.dp, 20.dp, 10.dp),
                    offsets = listOf(0.dp, 0.dp, 0.dp)
                ),
                GhostLineData(
                    widths = listOf(30.dp, 30.dp, 15.dp),
                    offsets = listOf(
                        (availableWidth - 30.dp) / 2,
                        (availableWidth - 30.dp) / 2,
                        (availableWidth - 15.dp) / 2
                    )
                ),
                GhostLineData(
                    widths = listOf(30.dp, 20.dp, 10.dp),
                    offsets = listOf(
                        availableWidth - 30.dp,
                        availableWidth - 20.dp,
                        availableWidth - 10.dp
                    )
                )
            )
        }

        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.elevatedCardElevation(8.dp),
            colors = CardDefaults.cardColors(Color.White),
            modifier = Modifier
                .width(availableWidth + 32.dp) // for padding * 2
                .height(100.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.CenterStart
            ) {
                GhostLinesBackground(ghostData = ghostData)

                Column {
                    AnimatedLine(
                        offset = line1Offset,
                        width = line1Width,
                        height = 4.dp,
                        color = Color.Black,
                        verticalPadding = 2.dp
                    )
                    AnimatedLine(
                        offset = line2Offset,
                        width = line2Width,
                        height = 4.dp,
                        color = Color.Black,
                        verticalPadding = 2.dp
                    )
                    AnimatedLine(
                        offset = line3Offset,
                        width = line3Width,
                        height = 4.dp,
                        color = Color.Black,
                        verticalPadding = 2.dp
                    )
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    AlignmentClickableBox(
                        onClick = { onAlignmentChange(AlignmentType.LEFT) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("leftBox")
                    )
                    AlignmentClickableBox(
                        onClick = { onAlignmentChange(AlignmentType.CENTER) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("centerBox")
                    )
                    AlignmentClickableBox(
                        onClick = { onAlignmentChange(AlignmentType.RIGHT) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("rightBox")
                    )
                }
            }
        }

    }
}

@Composable
fun AnimatedLine(
    offset: Dp,
    width: Dp,
    height: Dp,
    color: Color,
    verticalPadding: Dp
) {
    Box(
        modifier = Modifier
            .offset(x = offset)
            .padding(vertical = verticalPadding, horizontal = 16.dp)
            .width(width)
            .height(height)
            .background(color)
    )
}

@Composable
fun AlignmentClickableBox(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.clickable { onClick() }
    )
}

@Composable
fun GhostLinesBackground(ghostData: List<GhostLineData>) {
    ghostData.forEach { data ->
        GhostLines(widths = data.widths, offsets = data.offsets)
    }
}

@Composable
fun GhostLines(widths: List<Dp>, offsets: List<Dp>) {
    Column {
        widths.indices.forEach { i ->
            Box(
                modifier = Modifier
                    .offset(x = offsets[i])
                    .padding(vertical = 2.dp, horizontal = 16.dp)
                    .width(widths[i])
                    .height(4.dp)
                    .background(Color.Black.copy(alpha = 0.2f))
            )
        }
    }
}

