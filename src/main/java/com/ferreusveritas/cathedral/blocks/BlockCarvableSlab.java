package com.ferreusveritas.cathedral.blocks;

import net.minecraft.block.Block;

public class BlockCarvableSlab extends BlockCarvable {

	public Block master;
	public BlockCarvableSlab bottom;
	public BlockCarvableSlab top;
	public boolean isBottom;

	public BlockCarvableSlab(Block master) {
		
	}
	
	/*
	public BlockCarvableSlab(BlockCarvable master) {
		opaque = true;

		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);

		this.master = master;
		bottom = this;
		top = new BlockCarvableSlab(this);

		isBottom = true;
	}

	public BlockCarvableSlab(BlockCarvableSlab bottomBlock) {
		super();
		setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
		master = bottomBlock.master;
		bottom = bottomBlock;
		top = this;

		carverHelper = bottomBlock.carverHelper;
		isBottom = false;
	}

	@Override
	public Block setBlockName(String p_149663_1_) {
		if (this != top) {
			top.setBlockName(p_149663_1_);
		}
		return super.setBlockName(p_149663_1_);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		if (isBottom) {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		} else {
			setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {
		setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return Item.getItemFromBlock(bottom);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getPickBlock(MovingObjectPosition mop, World world, int x, int y, int z) {
		return new ItemStack(bottom, 1, world.getBlockMetadata(x, y, z));
	}

	@Override
	public IIcon getIcon(int side, int metadata) {
		return carverHelper.getIcon(side, metadata);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void getSubBlocks(Item block, CreativeTabs tabs, List list) {
		//if (isBottom){
			super.getSubBlocks(block, tabs, list);
		//} else {
		//	list.add(new ItemStack(block, 1, 1));
		//}
	}

	//
	// * Checks if the block is a solid face on the given side, used by placement logic.
	// *
	// * @param world
	// *            The current world
	// * @param x
	// *            X Position
	// * @param y
	// *            Y position
	// * @param z
	// *            Z position
	// * @param side
	// *            The side to check
	// * @return True if the block is solid on the specified side.
	// 
	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return !isBottom && (side == UP);
	}

	public void registerSlabTop() {
		String name = Block.blockRegistry.getNameForObject(this.bottom);
		name = name.substring(name.indexOf(':') + 1) + "_top";
		GameRegistry.registerBlock(this.top, ItemCarvableSlab.class, name);
	}
	*/
}
