import androidx.compose.animation.core.FloatPropKey
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.transition
import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradient
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun main() = Window(title = "Weather") {

    WeatherCardsTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            Row {
                sidebar(Modifier.fillMaxHeight())
                weatherCalendar(modifier = Modifier.fillMaxSize().padding(48.dp, 24.dp))
            }
        }
    }
}

@Composable
fun weatherCalendar(modifier: Modifier) {
    Column(modifier = modifier.background(Color.Unspecified)) {
        Text(
            text = "M A R C H",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "< KW 12 >", // < > should be icons but who cares
            style = MaterialTheme.typography.h4,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Row(
            modifier = Modifier.align(Alignment.End),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Day", style = MaterialTheme.typography.subtitle2)
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colors.secondaryVariant, CircleShape)
                    .padding(12.dp, 6.dp)
            ) {
                Text(
                    text = "Week",
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Text(text = "Month", style = MaterialTheme.typography.subtitle2)
        }
        Spacer(Modifier.height(8.dp))
        cardsGrid(
            items = weekWeather,
            numColumns = 3,
            modifier = Modifier.fillMaxSize()
        ) { item, modifier ->
            var rotated by remember { mutableStateOf(false) }
            val transitionState = transition(
                cardRotationDef,
                toState = if (rotated) WeatherCardState.ROTATED else WeatherCardState.IDLE
            )

            weatherCard(
                item = item,
                modifier = modifier
                    .pointerMoveFilter(
                        onExit = { rotated = false; true },
                        onEnter = { rotated = true; true }
                    ).drawLayer(
                        // BUG: 3D rotation doesn't work currently so the rotation looks kinda off
                        // https://github.com/JetBrains/compose-jb/issues/12
                        shadowElevation = 4f,
                        transformOrigin = TransformOrigin(0),
                        scaleX = transitionState[scaleX],
                        rotationX = transitionState[rotationX],
                        rotationY = transitionState[rotationY],
                        rotationZ = transitionState[rotationZ],
                    )
            )
        }
    }
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun <T> cardsGrid(
    items: List<T>,
    numColumns: Int,
    modifier: Modifier,
    itemContent: @Composable (item: T, modifier: Modifier) -> Unit
) {
    val rows = items.chunked(numColumns).map { rowItems ->
        // Fill list with nulls for empty spaces
        rowItems + List<T?>(numColumns - rowItems.size) { null }
    }
    Column(modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        rows.forEach { rowItems ->
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)) {
                rowItems.forEach { item ->
                    when (item) {
                        null -> Spacer(Modifier.weight(1f))
                        else -> itemContent(item, Modifier.size(200.dp, 125.dp))
                    }
                }

//                BUG: https://issuetracker.google.com/issues/172947246
//                val weight = numColumns - items.size
//                if(weight > 0) {
//                    Spacer(Modifier.weight(weight.toFloat()))
//                }
            }
        }
    }
}

@Composable
fun weatherCard(item: Weather, modifier: Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(3.dp),
        elevation = 8.dp
    ) {
        Box(
            modifier = Modifier.weatherCardGradient(item.color).padding(8.dp)
        ) {
            Text(
                "${item.dayOfWeek}\n${item.date}",
                modifier = Modifier.align(Alignment.TopStart).padding(top = 8.dp),
                style = MaterialTheme.typography.subtitle1
            )
            IconButton(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(Color.Black.copy(alpha = 0.4f), shape = CircleShape)
                    .preferredSize(30.dp)
                    .padding(8.dp)
            ) {
                Icon(Icons.Outlined.DateRange, tint = Color.White.copy(0.8f))
            }
            Text(
                "${item.temperature}°",
                modifier = Modifier.align(Alignment.BottomStart),
                style = MaterialTheme.typography.h4
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                    .padding(12.dp, 6.dp)
            ) {
                Text(
                    "Week",
                    style = MaterialTheme.typography.subtitle2.copy(fontSize = 12.sp),
                    color = Color.White.copy(0.8f)
                )
            }
        }
    }
}

@Composable
fun sidebar(modifier: Modifier) {
    Surface(
        modifier,
        color = MaterialTheme.colors.surface,
        shape = CutCornerShape(topRightPercent = 15),
        elevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .background(MaterialTheme.colors.secondaryVariant, shape = CircleShape)
                        .preferredSize(36.dp)
                        .padding(8.dp)
                ) {
                    Icon(Icons.Outlined.DateRange, tint = MaterialTheme.colors.secondary)
                }

                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .preferredSize(36.dp)
                        .padding(8.dp)
                ) {
                    Icon(Icons.Outlined.LocationOn)
                }

                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .preferredSize(36.dp)
                        .padding(8.dp)
                ) {
                    Icon(Icons.Outlined.Search)
                }
            }

            IconButton(
                onClick = {},
                modifier = Modifier
                    .preferredSize(36.dp)
                    .padding(8.dp)
            ) {
                Icon(Icons.Outlined.Settings)
            }
        }
    }
}

enum class WeatherCardState { IDLE, ROTATED }

val rotationX = FloatPropKey()
val rotationY = FloatPropKey()
val rotationZ = FloatPropKey()
val scaleX = FloatPropKey()

val cardRotationDef = transitionDefinition<WeatherCardState> {
    state(WeatherCardState.IDLE) {
        this[rotationX] = 0f
        this[rotationY] = 0f
        this[rotationZ] = 0f
        this[scaleX] = 1f
    }

    state(WeatherCardState.ROTATED) {
        this[rotationX] = -3f
        this[rotationY] = 46f
        this[rotationZ] = -1.5f
        this[scaleX] = 1.3f
    }

    transition(WeatherCardState.IDLE to WeatherCardState.ROTATED) {
        rotationX using spring()
        rotationY using spring()
        rotationZ using spring()
        scaleX using spring()
    }
}

fun Modifier.weatherCardGradient(color: Color) = composed {
    drawBehind {
        drawRect(
            RadialGradient(
                0f to color.copy(alpha = 0.2f),
                0.1f to color.copy(alpha = 0.3f),
                0.3f to color.copy(alpha = 0.4f),
                0.5f to color.copy(alpha = 0.5f),
                0.7f to color.copy(alpha = 0.7f),
                0.8f to color.copy(alpha = 0.8f),
                1.0f to color.copy(0.9f),
                centerX = 20.dp.toPx(),
                centerY = 20.dp.toPx(),
                radius = size.width - 15.dp.toPx(),
                tileMode = TileMode.Clamp
            )
        )
    }
}
