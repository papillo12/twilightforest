package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import twilightforest.entity.GiantMinerEntity;

public class TFGiantRenderer<T extends GiantMinerEntity> extends HumanoidMobRenderer<T, PlayerModel<T>> {
	private final PlayerModel<T> normalModel;
	private final PlayerModel<T> slimModel;

	public TFGiantRenderer(EntityRenderDispatcher manager) {
		super(manager, new PlayerModel<>(0, false), 1.8F);
		normalModel = getModel();
		slimModel = new PlayerModel<>(0, true);

		this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(0.5F), new HumanoidModel<>(0.5F)));
	}

	@Override
	public void render(T entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		if(this.model.riding) {
			matrixStackIn.translate(0, -2.5F, 0);
		}
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(GiantMinerEntity entity) {
		Minecraft mc = Minecraft.getInstance();
		boolean slim = false;
		ResourceLocation texture = DefaultPlayerSkin.getDefaultSkin();

		if (mc.getCameraEntity() instanceof AbstractClientPlayer) {
			AbstractClientPlayer client = ((AbstractClientPlayer) mc.getCameraEntity());
			texture = client.getSkinTextureLocation();
			slim = client.getModelName().equals("slim");
		}

		model = slim ? slimModel : normalModel;
		return texture;
	}

	@Override
	public void scale(T entitylivingbaseIn, PoseStack stack, float partialTickTime) {
		float scale = 4.0F;
		stack.scale(scale, scale, scale);
	}
}
