package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import twilightforest.client.model.entity.AdherentModel;
import twilightforest.entity.AdherentEntity;

public class AdherentRenderer extends TFBipedRenderer<AdherentEntity, AdherentModel> {
	public AdherentRenderer(EntityRenderDispatcher manager) {
		super(manager, new AdherentModel(), new AdherentModel(), new AdherentModel(), 0.625F, "adherent.png");
	}

	@Override
	protected void scale(AdherentEntity e, PoseStack ms, float partialTicks) {
		float bounce = e.tickCount + partialTicks;
		ms.translate(0F, -0.125F - Mth.sin((bounce) * 0.133F) * 0.1F, 0F);
	}
}
