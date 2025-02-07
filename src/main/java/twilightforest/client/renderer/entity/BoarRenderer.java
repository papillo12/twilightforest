package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.BoarModel;
import twilightforest.entity.passive.BoarEntity;

//old renderer used to use PigModel, had to change it because of the texture size change
public class BoarRenderer extends MobRenderer<BoarEntity, BoarModel<BoarEntity>> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("wildboar.png");

	public BoarRenderer(EntityRenderDispatcher manager, BoarModel<BoarEntity> model) {
		super(manager, new BoarModel<>(), 0.7F);
	}

	@Override
	public ResourceLocation getTextureLocation(BoarEntity entity) {
		return textureLoc;
	}
}
