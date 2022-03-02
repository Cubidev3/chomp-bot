import dev.kord.core.Kord
import dev.kord.core.behavior.reply
import dev.kord.core.event.gateway.ReadyEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.Intents

suspend fun main() {
    println("Starting...")

    val whitelist = listOf(
        "penis",
        "cock",
        "dick",
        "balls",
        "testicles",
        "male genitalia",
        "dong",
        "meat stick",
        "richard",
        "love shaft",
        "third leg",
        "wiener",
        "baby makers",
        "peepee",
    )

    val kord = Kord("OTQ4MzgzODI5NzM4MDAwNDI0.Yh7BRQ.aQlqZ1MX-scVZrciM0S5UhzYYbM")

    kord.on<MessageCreateEvent> {
        var triggersWhitelist = false

        for (word in whitelist) {
            if (triggersWhitelist) break
            if (word in message.content.lowercase()) triggersWhitelist = true
        }

        if (triggersWhitelist) this.message.reply {
            content = "**chomp**"
        }
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