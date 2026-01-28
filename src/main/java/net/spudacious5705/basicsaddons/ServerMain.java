package net.spudacious5705.basicsaddons;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


@Mod(ServerMain.MOD_ID)
public class ServerMain {
	public static final String MOD_ID = "basicsaddons";
	public static final Logger LOGGER = LogUtils.getLogger();

	public ServerMain(FMLJavaModLoadingContext context) {

        //Class<?> mixinLoader = MixinLoader.class;

        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);




    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID,path);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("SETTING UP BASICS ADDONS...");

    }

    @SubscribeEvent
    public void onEntitySpawn(MobSpawnEvent.FinalizeSpawn event) {
        if (event.getEntity() instanceof Monster mob) {
            mob.addEffect(new MobEffectInstance(
                    MobEffects.DIG_SLOWDOWN,
                    Integer.MAX_VALUE,
                    0,
                    false, // Ambient
                    false // Show particles

            ));

        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        ServerMain.LOGGER.info("HELLO from basics modifications");
    }

}