import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.fontFamily
import androidx.compose.ui.text.platform.font
import androidx.compose.ui.unit.sp

val JosefinSans = fontFamily(
    font(alias = "JosefinSans-Light", path = "JosefinSans-Light.ttf", weight = FontWeight.W300)
)

val colorPalette = lightColors(
    background = Color(0xFFFCEAD4),
    surface = Color.White,
    secondary = Color(0xFF1D87DA),
    secondaryVariant = Color(0xFFD1E7F7)
)

// BUG: Letter spacing doesn't work https://github.com/JetBrains/compose-jb/issues/82
val typography = Typography(
    subtitle1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        fontFamily = JosefinSans,
        letterSpacing = 0.2.sp
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 14.sp,
        fontFamily = JosefinSans,
        letterSpacing = 2.sp
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        fontFamily = JosefinSans,
        letterSpacing = 0.sp
    )
)

@Composable
fun WeatherCardsTheme(content: @Composable () -> Unit) {
    DesktopMaterialTheme(
        colors = colorPalette,
        typography = typography,
        content = content
    )
}