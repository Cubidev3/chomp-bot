import dev.kord.common.ratelimit.BucketRateLimiter
import dev.kord.common.ratelimit.consume
import dev.kord.core.Kord
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message
import dev.kord.core.event.gateway.ReadyEvent
import dev.kord.core.event.guild.GuildCreateEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.event.message.MessageUpdateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.Intents
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.count
import kotlin.time.Duration

const val APP_ID = "948383829738000424"

val whitelist = listOf(
    "penis",
    " cock ",
    " cocks ",
    " dick ",
    " dicks ",
    " balls ",
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

suspend fun main() {
    println("Starting...")

    val kord = Kord(System.getenv("BOT_TOKEN"))
    val botListRateLimiter = BucketRateLimiter(1, Duration.parse("6s"))

    kord.on<GuildCreateEvent> {
        val httpClient = HttpClient(CIO) {
            install(JsonFeature)
        }

        botListRateLimiter.consume {
            val guilds = kord.guilds.count()

            httpClient.post<HttpResponse>("https://discord.bots.gg/api/v1/bots/$APP_ID/stats") {
                headers {
                    append(HttpHeaders.Authorization, System.getenv("BOT_LIST_API_TOKEN"))
                }

                contentType(ContentType.Application.Json)

                body = object {
                    val guildCount = guilds
                }
            }
        }

        httpClient.close()
    }

    kord.on<MessageCreateEvent> {
        checkForTriggerWord(message)
    }

    kord.on<MessageUpdateEvent> {
        checkForTriggerWord(this.getMessage())
    }

    kord.on<ReadyEvent> {
        println("Ready")
    }

    kord.login {
        intents = Intents(Intent.GuildMessages, Intent.Guilds)

        presence {
            watching("for \"food\"")
        }
    }
}

suspend fun checkForTriggerWord(message: Message) {
    for (word in whitelist) {
        if (word in " ${message.content.lowercase().filter { it.isLetter() || it.isWhitespace() }} ") {
            message.reply {
                content = "**chomp**"
            }

            break
        }
    }
}