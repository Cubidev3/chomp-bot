import dev.kord.core.Kord
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message
import dev.kord.core.event.gateway.ReadyEvent
import dev.kord.core.event.guild.GuildCreateEvent
import dev.kord.core.event.guild.GuildDeleteEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.event.message.MessageUpdateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.Intents
import kotlinx.coroutines.flow.count
import org.discordbots.api.client.DiscordBotListAPI

const val RESPONSE_MESSAGE = "**chomp**"

val triggerWords = listOf(
    "penis",
    " cock ",
    " cocks ",
    " dick ",
    " dicks ",
    "testicle",
    "male genital",
    " dong ",
    " dongs ",
    "meat stick",
    "love shaft",
    "third leg",
    " wiener ",
    "baby maker",
    "peepee",
    "meat shaft",
    "male reproductive genital",
    "sperm shooter",
    " pp ",
)
val triggerWordsAlone = listOf( // I will add to this... later... probably
    "balls"
)

suspend fun main() {
    println("Starting...")

    val kord = Kord(System.getenv("BOT_TOKEN"))
    val botListApi = DiscordBotListAPI.Builder()
        .token(System.getenv("BOT_LIST_TOKEN"))
        .botId("948383829738000424")
        .build()

    kord.on<GuildCreateEvent> { botListApi.setStats(kord.guilds.count()) }
    kord.on<GuildDeleteEvent> { botListApi.setStats(kord.guilds.count()) }

    kord.on<MessageCreateEvent> { checkForTriggerWord(message) }
    kord.on<MessageUpdateEvent> { checkForTriggerWord(getMessage()) }

    kord.on<ReadyEvent> { println("Ready") }

    kord.login {
        intents = Intents(Intent.GuildMessages, Intent.Guilds)

        presence {
            watching("for \"food\"")
        }
    }
}

suspend fun checkForTriggerWord(message: Message) {
    val filteredMessage = message.content.lowercase().filter { it.isLetter() || it.isWhitespace() }

    for (word in triggerWords) {
        if (word in " $filteredMessage ") {
            message.reply {
                content = RESPONSE_MESSAGE
            }

            break
        }
    }

    for (word in triggerWordsAlone) {
        if (word == filteredMessage) {
            message.reply {
                content = RESPONSE_MESSAGE
            }

            break
        }
    }
}