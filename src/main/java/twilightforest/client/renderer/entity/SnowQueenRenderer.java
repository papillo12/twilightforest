package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.SnowQueenModel;
import twilightforest.entity.boss.SnowQueenEntity;

public class SnowQueenRenderer extends HumanoidMobRenderer<SnowQueenEntity, SnowQueenModel> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("snowqueen.png");

	public SnowQueenRenderer(EntityRenderDispatcher manager, SnowQueenModel model) {
		super(manager, model, 0.625F);
	}

	@Override
	public ResourceLocation getTextureLocation(SnowQueenEntity entity) {
		return textureLoc;
	}

	@Override
	protected void scale(SnowQueenEntity queen, PoseStack stack, float partialTicks) {
		float scale = 1.2F;
		stack.scale(scale, scale, scale);
	}

	@Override
	public void render(SnowQueenEntity queen, float yaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
		super.render(queen, yaw, partialTicks, stack, buffer, light);

		//FIXME
    }
}
