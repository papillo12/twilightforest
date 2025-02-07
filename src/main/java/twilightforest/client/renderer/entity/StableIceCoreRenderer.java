package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import twilightforest.client.model.entity.StableIceCoreModel;
import twilightforest.entity.StableIceCoreEntity;

public class StableIceCoreRenderer extends TFBipedRenderer<StableIceCoreEntity, StableIceCoreModel> {

	public StableIceCoreRenderer(EntityRenderDispatcher manager, StableIceCoreModel model) {
		super(manager, model, 0.4F, "iceshooter.png");
	}

	@Override
	protected void scale(StableIceCoreEntity entity, PoseStack stack, float partialTicks) {
		float bounce = entity.tickCount + partialTicks;
		stack.translate(0F, Mth.sin((bounce) * 0.2F) * 0.15F, 0F);
	}
}
