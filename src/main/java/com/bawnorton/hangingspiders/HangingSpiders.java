package com.bawnorton.hangingspiders;

import com.bawnorton.hangingspiders.common.registry.EntityRegister;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

public class HangingSpiders implements ModInitializer {
	public static final String MODID = "hangingspiders";

	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		GeckoLib.initialize();
		EntityRegister.init();
		LOGGER.info("Hanging Spiders Initialised");
	}
}
