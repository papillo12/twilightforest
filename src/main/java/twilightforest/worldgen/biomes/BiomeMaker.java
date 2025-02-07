package twilightforest.worldgen.biomes;

import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import twilightforest.TFStructures;

import java.util.Map;

public final class BiomeMaker extends BiomeHelper {
	public static final Map<ResourceKey<Biome>, Biome> BIOMES = generateBiomes();

	private static Map<ResourceKey<Biome>, Biome> generateBiomes() {
		final ImmutableMap.Builder<ResourceKey<Biome>, Biome> biomes = ImmutableMap.builder();

		commonBiomes(biomes);
		mushroomBiomes(biomes);
		rareBiomes(biomes);
		swampBiomes(biomes);
		darkForestBiomes(biomes);
		snowRegionBiomes(biomes);
		highlandsBiomes(biomes);

		return biomes.build();
	}

	private static void commonBiomes(ImmutableMap.Builder<ResourceKey<Biome>, Biome> biomes) {
		biomes.put(BiomeKeys.FOREST,
				biomeWithDefaults(defaultAmbientBuilder(), defaultMobSpawning().setPlayerCanSpawn(), twilightForestGen(defaultGenSettingBuilder()))
						.build()
		);

		biomes.put(BiomeKeys.DENSE_FOREST,
				biomeWithDefaults(defaultAmbientBuilder().waterColor(0x005522), defaultMobSpawning().setPlayerCanSpawn(), denseForestGen(defaultGenSettingBuilder()))
						.temperature(0.7F)
						.downfall(0.8F)
						.depth(0.2F)
						.scale(0.2F)
						.build()
		);

		biomes.put(BiomeKeys.FIREFLY_FOREST,
				biomeWithDefaults(defaultAmbientBuilder(), defaultMobSpawning().setPlayerCanSpawn(), fireflyForestGen(defaultGenSettingBuilder()))
						.temperature(0.5F)
						.downfall(1)
						.depth(0.125F)
						.scale(0.05F)
						.build()
		);

		biomes.put(BiomeKeys.CLEARING,
				biomeWithDefaults(defaultAmbientBuilder(), defaultMobSpawning().setPlayerCanSpawn(), addDefaultStructures(defaultGenSettingBuilder()))
						.biomeCategory(Biome.BiomeCategory.PLAINS)
						.temperature(0.8F)
						.downfall(0.4F)
						.depth(0.125F)
						.scale(0.05F)
						.build()
		);

		biomes.put(BiomeKeys.OAK_SAVANNAH,
				biomeWithDefaults(defaultAmbientBuilder(), defaultMobSpawning().setPlayerCanSpawn(), oakSavannaGen(defaultGenSettingBuilder()))
						.biomeCategory(Biome.BiomeCategory.SAVANNA)
						.temperature(0.9F)
						.downfall(0)
						.depth(0.2F)
						.scale(0.2F)
						.build()
		);
	}

	private static void mushroomBiomes(ImmutableMap.Builder<ResourceKey<Biome>, Biome> biomes) {
		biomes.put(BiomeKeys.MUSHROOM_FOREST,
				biomeWithDefaults(defaultAmbientBuilder(), defaultMobSpawning().setPlayerCanSpawn(), mushroomForestGen(defaultGenSettingBuilder()))
						.temperature(0.8F)
						.downfall(0.8F)
						.build()
		);

		biomes.put(BiomeKeys.DENSE_MUSHROOM_FOREST,
				biomeWithDefaults(defaultAmbientBuilder(), defaultMobSpawning().setPlayerCanSpawn(), denseMushroomForestGen(defaultGenSettingBuilder().addStructureStart(TFStructures.CONFIGURED_MUSHROOM_TOWER)))
						.temperature(0.8F)
						.downfall(1)
						.depth(0.125F)
						.scale(0.05F)
						.build()
		);
	}

	private static void rareBiomes(ImmutableMap.Builder<ResourceKey<Biome>, Biome> biomes) {
		biomes.put(BiomeKeys.SPOOKY_FOREST,
				biomeWithDefaults(defaultAmbientBuilder().grassColorOverride(0xC45123).foliageColorOverride(0xFF8501).waterColor(0xFA9111),  spookSpawning(), spookyForestGen(defaultGenSettingBuilder()))
						.temperature(0.5F)
						.downfall(1)
						.depth(0.125F)
						.scale(0.05F)
						.build()
		);

		biomes.put(BiomeKeys.ENCHANTED_FOREST,
				biomeWithDefaults(defaultAmbientBuilder().foliageColorOverride(0x00FFFF).grassColorOverride(0x00FFFF).grassColorModifier(BiomeGrassColors.ENCHANTED_FOREST), defaultMobSpawning(), enchantedForestGen(defaultGenSettingBuilder().addStructureStart(TFStructures.CONFIGURED_QUEST_GROVE)))
						.build()
		);

		biomes.put(BiomeKeys.STREAM,
				biomeWithDefaults(defaultAmbientBuilder(), defaultMobSpawning(), defaultGenSettingBuilder())
						.biomeCategory(Biome.BiomeCategory.RIVER)
						.temperature(0.5F)
						.downfall(0.1F)
						.depth(-0.8F)
						.scale(0)
						.build()
		);

		biomes.put(BiomeKeys.LAKE,
				biomeWithDefaults(defaultAmbientBuilder(), defaultMobSpawning(), defaultGenSettingBuilder())
						.biomeCategory(Biome.BiomeCategory.OCEAN)
						.temperature(0.66F)
						.downfall(1)
						.depth(-1.8F)
						.scale(0.1F)
						.build()
		);
	}

	private static void swampBiomes(ImmutableMap.Builder<ResourceKey<Biome>, Biome> biomes) {
		biomes.put(BiomeKeys.SWAMP,
				biomeWithDefaults(defaultAmbientBuilder().grassColorOverride(0x5C694E).foliageColorOverride(0x496137).waterColor(0xE0FFAE).grassColorModifier(BiomeGrassColors.SWAMP), swampSpawning(), swampGen(defaultGenSettingBuilder()).addStructureStart(TFStructures.CONFIGURED_LABYRINTH))
						.biomeCategory(Biome.BiomeCategory.SWAMP)
						.temperature(0.8F)
						.downfall(0.9F)
						.depth(-0.25F)
						.scale(0.25F)
						.build()
		);

		biomes.put(BiomeKeys.FIRE_SWAMP,
				biomeWithDefaults(whiteAshParticles(defaultAmbientBuilder().grassColorOverride(0x572E23).foliageColorOverride(0x64260F).waterColor(0x6C2C2C)), new MobSpawnSettings.Builder(), fireSwampGen(defaultGenSettingBuilder()).addStructureStart(TFStructures.CONFIGURED_HYDRA_LAIR))
						.biomeCategory(Biome.BiomeCategory.SWAMP)
						.temperature(1)
						.downfall(0.4F)
						.depth(0.1F)
						.scale(0.2F)
						.build()
		);
	}

	private static void darkForestBiomes(ImmutableMap.Builder<ResourceKey<Biome>, Biome> biomes) {
		biomes.put(BiomeKeys.DARK_FOREST,
				biomeWithDefaults(defaultAmbientBuilder().grassColorOverride(0x4B6754).foliageColorOverride(0x3B5E3F).grassColorModifier(BiomeGrassColors.DARK_FOREST), darkForestSpawning(), darkForestGen().addStructureStart(TFStructures.CONFIGURED_KNIGHT_STRONGHOLD))
						.temperature(0.7F)
						.downfall(0.8F)
						.depth(0.125F)
						.scale(0.05F)
						.build()
		);

		biomes.put(BiomeKeys.DARK_FOREST_CENTER, // FIXME: colors
				biomeWithDefaults(defaultAmbientBuilder().grassColorOverride(0x667540).foliageColorOverride(0xF9821E).grassColorModifier(BiomeGrassColors.DARK_FOREST_CENTER), new MobSpawnSettings.Builder(), darkForestCenterGen().addStructureStart(TFStructures.CONFIGURED_DARK_TOWER))
						.depth(0.125F)
						.scale(0.05F)
						.build()
		);
	}

	private static void snowRegionBiomes(ImmutableMap.Builder<ResourceKey<Biome>, Biome> biomes) {
		biomes.put(BiomeKeys.SNOWY_FOREST,
				biomeWithDefaults(defaultAmbientBuilder(), snowForestSpawning(), snowyForestGen().addStructureStart(TFStructures.CONFIGURED_YETI_CAVE))
						.precipitation(Biome.Precipitation.SNOW)
						.temperature(0.09F)
						.downfall(0.9F)
						.depth(0.2F)
						.scale(0.2F)
						.build()
		);

		biomes.put(BiomeKeys.GLACIER,
				biomeWithDefaults(defaultAmbientBuilder(), penguinSpawning(), glacierGen().addStructureStart(TFStructures.CONFIGURED_AURORA_PALACE))
						.biomeCategory(Biome.BiomeCategory.ICY)
						.temperature(0.8F)
						.downfall(0.1F)
						.precipitation(Biome.Precipitation.SNOW)
						.build()
		);
	}

	private static void highlandsBiomes(ImmutableMap.Builder<ResourceKey<Biome>, Biome> biomes) {
		biomes.put(BiomeKeys.HIGHLANDS,
				biomeWithDefaults(defaultAmbientBuilder(), defaultMobSpawning(), highlandsGen().addStructureStart(TFStructures.CONFIGURED_TROLL_CAVE))
						.biomeCategory(Biome.BiomeCategory.MESA)
						.temperature(0.4F)
						.downfall(0.7F)
						.depth(3.5F)
						.scale(0.05F)
						.build()
		);

		biomes.put(BiomeKeys.THORNLANDS,
				biomeWithDefaults(defaultAmbientBuilder(), new MobSpawnSettings.Builder(), thornlandsGen())
						.biomeCategory(Biome.BiomeCategory.NONE)
						.temperature(0.3F)
						.downfall(0.2F)
						.depth(6F)
						.scale(0.1F)
						.build()
		);

		biomes.put(BiomeKeys.FINAL_PLATEAU,
				biomeWithDefaults(defaultAmbientBuilder(), ravenSpawning(), defaultGenSettingBuilder().surfaceBuilder(twilightforest.worldgen.ConfiguredSurfaceBuilders.CONFIGURED_PLATEAU).addStructureStart(TFStructures.CONFIGURED_FINAL_CASTLE))
						.biomeCategory(Biome.BiomeCategory.MESA)
						.temperature(0.3F)
						.downfall(0.2F)
						.depth(10.5F)
						.scale(0.025F)
						.build()
		);
	}
}
