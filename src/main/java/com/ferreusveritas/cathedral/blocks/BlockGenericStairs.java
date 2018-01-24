package com.ferreusveritas.cathedral.blocks;

import com.ferreusveritas.cathedral.Cathedral;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class BlockGenericStairs extends BlockStairs {

	public BlockGenericStairs(String name, IBlockState blockDef) {
		super(blockDef);
		name += "-stairs";
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Cathedral.tabCathedral);
		this.useNeighborBrightness = true;
	}

}
