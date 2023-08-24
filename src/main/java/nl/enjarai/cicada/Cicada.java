package nl.enjarai.cicada;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.util.Identifier;
import nl.enjarai.cicada.api.conversation.ConversationManager;
import nl.enjarai.cicada.api.util.CicadaEntrypoint;
import nl.enjarai.cicada.api.util.JsonSource;
import nl.enjarai.cicada.api.util.ProperLogger;
import nl.enjarai.cicada.util.CapeHandler;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

public final class Cicada implements ModInitializer, ClientModInitializer, CicadaEntrypoint {
	public static final String MOD_ID = "cicada";
	public static final Logger LOGGER = ProperLogger.getLogger(MOD_ID);

	public static final ConversationManager CONVERSATION_MANAGER = new ConversationManager();

	@Override
	public void onInitialize() {
		// Load and run the conversations in a separate thread to avoid increased load times.
		CompletableFuture.runAsync(() -> {
			CONVERSATION_MANAGER.init();
			CONVERSATION_MANAGER.run();
		}, ConversationManager.getThreadPool());
	}

	@Override
	public void onInitializeClient() {
		ClientLifecycleEvents.CLIENT_STOPPING.register(client -> CapeHandler.shutdown());
	}

	@Override
	public void registerConversations(ConversationManager conversationManager) {
		conversationManager.registerSource(
				JsonSource.fromUrl("https://raw.githubusercontent.com/enjarai/cicada-lib/master/src/main/resources/cicada/cicada/conversations.json")
						.or(JsonSource.fromResource("cicada/cicada/conversations.json")),
				LOGGER::info
		);
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}
