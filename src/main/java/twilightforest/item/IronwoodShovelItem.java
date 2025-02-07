package twilightforest.item;

import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.item.*;
import net.minecraft.core.NonNullList;

import net.minecraft.world.item.Item.Properties;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;

public class IronwoodShovelItem extends ShovelItem {

	public IronwoodShovelItem(Tier toolMaterial, Properties props) {
		super(toolMaterial, 1.5F, -3.0F, props);
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
		if (allowdedIn(tab)) {
			ItemStack istack = new ItemStack(this);
			istack.enchant(Enchantments.UNBREAKING, 1);
			list.add(istack);
		}
	}
}
