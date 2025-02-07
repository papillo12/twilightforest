package twilightforest.client.model.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;

public class QuestRamTrophyModel extends GenericTrophyModel {

	public ModelPart horns;
	public ModelPart head;
	
	public QuestRamTrophyModel() {
		texWidth = 128;
		texHeight = 128;

		this.head = new ModelPart(this, 0, 0);
		this.head.setPos(0.0F, -4.0F, 0.0F);
		this.head.texOffs(74, 70).addBox(-6.0F, -4.0F, -10.0F, 12.0F, 8.0F, 15.0F, 0.0F, 0.0F, 0.0F);
		this.head.texOffs(42, 71).addBox(-6.0F, -7.0F, -6.0F, 12.0F, 3.0F, 11.0F, 0.0F, 0.0F, 0.0F);
		this.horns = new ModelPart(this, 0, 0);
		this.horns.setPos(0.0F, -4.0F, 0.0F);
		this.horns.texOffs(64, 0).addBox(-9.0F, -6.0F, -1.0F, 4.0F, 10.0F, 10.0F, 0.0F, 0.0F, 0.0F);
		this.horns.texOffs(48, 0).addBox(-13.0F, -6.0F, 5.0F, 4.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.horns.texOffs(92, 0).addBox(5.0F, -6.0F, -1.0F, 4.0F, 10.0F, 10.0F, 0.0F, 0.0F, 0.0F);
		this.horns.texOffs(110, 0).addBox(9.0F, -6.0F, 5.0F, 4.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(horns, -0.4363323129985824F, 0.0F, 0.0F);
		this.head.addChild(this.horns);
	}

	private void setRotateAngle(ModelPart model, float x, float y, float z) {
		model.xRot = x;
		model.yRot = y;
		model.zRot = z;
	}
	
	@Override
	public void setRotations(float x, float y, float z) {
		super.setRotations(x, y, z);
		this.head.yRot = y * ((float) Math.PI / 180F);
		this.head.xRot = x * ((float) Math.PI / 180F);
	}
	
	@Override
	public void renderToBuffer(PoseStack matrix, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.head.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
