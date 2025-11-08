package evertasty.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.TridentEntityRenderer;

public class EvertastyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(
                evertasty.entity.ModEntities.CUSTOM_TRIDENT,
                TridentEntityRenderer::new
        );
    }
}