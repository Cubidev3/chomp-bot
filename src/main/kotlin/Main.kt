import dev.kord.core.Kord
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message
import dev.kord.core.event.gateway.ReadyEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.event.message.MessageUpdateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.Intents

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
        intents = Intents(Intent.GuildMessages)

        presence {
            watching("for \"food\"")
        }
    }
}

suspend fun checkForTriggerWord(message: Message) {
    for (word in whitelist) {
        if (word in " ${message.content.lowercase().filter { it.isLetter() }} ") {
            message.reply {
                content = "**chomp**"
            }

            break
        }
    }
}