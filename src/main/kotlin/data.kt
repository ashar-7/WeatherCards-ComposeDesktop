import androidx.compose.ui.graphics.Color

data class Weather(
    val dayOfWeek: String,
    val date: String,
    val temperature: Int,
    val label: String,
    val color: Color
)

val sunny = Color(0xFFECA161)
val cloudy = Color(0xFF026F6F)
val hot = Color(0xFFF26C69)
val cold = Color(0xFF182780)

val weekWeather = listOf(
    Weather(dayOfWeek = "Monday", date = "16.03", temperature = 2, label = "Be prepared", color = cold),
    Weather(dayOfWeek = "Tuesday", date = "17.03", temperature = 14, label = "90% Take an umbrella", color = cloudy),
    Weather(dayOfWeek = "Wednesday", date = "18.03", temperature = 20, label = "Enjoy the sun", color = sunny),
    Weather(dayOfWeek = "Thursday", date = "19.03", temperature = 21, label = "Enjoy the sun", color = sunny),
    Weather(dayOfWeek = "Friday", date = "20.03", temperature = 25, label = "It's getting hot", color = hot),
    Weather(dayOfWeek = "Saturday", date = "21.03", temperature = 19, label = "No sunglasses needed", color = cloudy),
    Weather(dayOfWeek = "Sunday", date = "22.03", temperature = 12, label = "Hold your hat", color = cloudy)
)