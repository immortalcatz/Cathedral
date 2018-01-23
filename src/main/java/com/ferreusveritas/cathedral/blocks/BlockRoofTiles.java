package com.ferreusveritas.cathedral.blocks;

import java.util.List;

import com.ferreusveritas.cathedral.Cathedral;
import com.ferreusveritas.cathedral.renderers.RendererRoofTiles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/*
M	8421	F	Desc
0	0000	E	Stairs
1	0001	W	Stairs
2	0010	S	Stairs
3	0011	N	Stairs
4	0100	E	Inverted Stairs
5	0101	W	Inverted Stairs
6	0110	S	Inverted Stairs
7	0111	N	Inverted Stairs
8	1000	E	Wall Slab
9	1001	W	Wall Slab
10	1010	S	Wall Slab
11	1011	N	Wall Slab
12	1100		Fence
13	1101		Bottom Slab
14	1110		Top Slab
15	1111		Solid Block
 */

public class BlockRoofTiles extends BlockStairs {

	int color;
	private IIcon sideIcon;

	public BlockRoofTiles(int color) {
		super(Blocks.stone, 0);
		useNeighborBrightness = true;
		this.color = color;
	}

	@Override
	public int getRenderType() {
		return RendererRoofTiles.id;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return sideIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
		String name = "terra";
		sideIcon = register.registerIcon(Cathedral.MODID + ":" + name + "-" + Integer.toHexString(this.color));
	}	

	public static boolean isStairs(Block block) {
		return block instanceof BlockStairs;
	}

	private boolean isStairsWithMetadata(IBlockAccess blockAccess, int x, int y, int z, int metadata){
		Block block = blockAccess.getBlock(x, y, z);
		return isStairs(block) && blockAccess.getBlockMetadata(x, y, z) == metadata;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		int metadata = world.getBlockMetadata(x, y, z);
		if((metadata & 8) == 8){
			if(metadata == 15){//Solid Block
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			} else
				if(metadata == 14){//Top Slab
					this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
				} else
					if(metadata == 13){//Bottom Slab
						this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
					}
		}
		else{
			super.setBlockBoundsBasedOnState(world, x, y, z);
		}
	}


	@SuppressWarnings("rawtypes")
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity) {
		int metadata = world.getBlockMetadata(x, y, z);

		setBlockBoundsBasedOnState(world, x, y, z);

		if((metadata & 8) == 8){
			AxisAlignedBB axisalignedbb1 = this.getCollisionBoundingBoxFromPool(world, x, y, z);

			if (axisalignedbb1 != null && axisAlignedBB.intersectsWith(axisalignedbb1)) {
				list.add(axisalignedbb1);
			}
		}
		else {
			super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
		}
	}


	public void setBoundsForSlab(IBlockAccess blockAccess, int x, int y, int z)
	{
		int metadata = blockAccess.getBlockMetadata(x, y, z);

		if ((metadata & 4) != 0) {//Stairs are upside-down
			this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
		else {//Stairs are right-side-up
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		}
	}

	//returns true if the remaining piece of the inside step needs to be rendered
	public boolean setBoundsForRemainingCrossStep(IBlockAccess blockAccess, int x, int y, int z)
	{
		int metadata = blockAccess.getBlockMetadata(x, y, z);
		int dir = metadata & 3;

		//Top half of Block
		float minY = 0.5F;
		float maxY = 1.0F;

		if ((metadata & 4) != 0) {//Stairs are inverted
			//Bottom half of Block
			minY = 0.0F;
			maxY = 0.5F;
		}

		float minX = 0.0F;
		float maxX = 0.5F;
		float minZ = 0.5F;
		float maxZ = 1.0F;
		//LL
		//HL

		boolean inside = false;
		Block adjBlock;
		int adjaMetadata;
		int adjaDir;

		if (dir == 0) {//Full block side is facing east
			adjBlock = blockAccess.getBlock(x - 1, y, z);//get block to west
			adjaMetadata = blockAccess.getBlockMetadata(x - 1, y, z);

			if (isStairs(adjBlock) && (metadata & 4) == (adjaMetadata & 4)) {
				//LL
				//HL
				adjaDir = adjaMetadata & 3;

				//Western block is facing north and northern block is either not facing the same direction as this block or not stairs at all
				if (adjaDir == 3 && !this.isStairsWithMetadata(blockAccess, x, y, z - 1, metadata)) {
					minZ = 0.0F;
					maxZ = 0.5F;
					//HL
					//LL
					inside = true;
				}
				//Eastern block is facing south and southern block is either not facing the same direction as this block or not stairs at all
				else if (adjaDir == 2 && !this.isStairsWithMetadata(blockAccess, x, y, z + 1, metadata)) {
					minZ = 0.5F;
					maxZ = 1.0F;
					//LL
					//HL
					inside = true;
				}
			}
		}
		else if (dir == 1) {//Full block side is facing west
			adjBlock = blockAccess.getBlock(x + 1, y, z);//get block to east
			adjaMetadata = blockAccess.getBlockMetadata(x + 1, y, z);

			if (isStairs(adjBlock) && (metadata & 4) == (adjaMetadata & 4)) {
				minX = 0.5F;
				maxX = 1.0F;
				//LL
				//LH
				adjaDir = adjaMetadata & 3;
				//Eastern block is facing north and northern block is either not facing the same direction as this block or not stairs at all
				if (adjaDir == 3 && !this.isStairsWithMetadata(blockAccess, x, y, z - 1, metadata)) {
					minZ = 0.0F;
					maxZ = 0.5F;
					//LH
					//LL
					inside = true;
				}
				//Eastern block is facing north and southern block is either not facing the same direction as this block or not stairs at all
				else if (adjaDir == 2 && !this.isStairsWithMetadata(blockAccess, x, y, z + 1, metadata)) {
					minZ = 0.5F;//Redundant?
					maxZ = 1.0F;//Redundant?
					//LL
					//LH
					inside = true;
				}
			}
		}
		else if (dir == 2) {//Full block side is facing south
			adjBlock = blockAccess.getBlock(x, y, z - 1);//get block to north
			adjaMetadata = blockAccess.getBlockMetadata(x, y, z - 1);

			if (isStairs(adjBlock) && (metadata & 4) == (adjaMetadata & 4)) {
				minZ = 0.0F;
				maxZ = 0.5F;
				//HL
				//LL
				adjaDir = adjaMetadata & 3;

				//Northern block is facing west and western block is either not facing the same direction as this block or not stairs at all
				if (adjaDir == 1 && !this.isStairsWithMetadata(blockAccess, x - 1, y, z, metadata)) {
					//HL
					//LL
					inside = true;
				}
				//Northern block is facing east and eastern block is either not facing the same direction as this block or not stairs at all
				else if (adjaDir == 0 && !this.isStairsWithMetadata(blockAccess, x + 1, y, z, metadata)) {
					minX = 0.5F;
					maxX = 1.0F;
					//LH
					//LL
					inside = true;
				}
			}
		}
		else if (dir == 3) {//Full block side is facing north
			adjBlock = blockAccess.getBlock(x, y, z + 1);//get block to south
			adjaMetadata = blockAccess.getBlockMetadata(x, y, z + 1);

			if (isStairs(adjBlock) && (metadata & 4) == (adjaMetadata & 4)) {
				//LL
				//HL
				adjaDir = adjaMetadata & 3;

				//Southern block is facing west and western block is either not facing the same direction as this block or not stairs at all
				if (adjaDir == 1 && !this.isStairsWithMetadata(blockAccess, x - 1, y, z, metadata)) {
					//LL
					//HL
					inside = true;
				}
				//Southern block is facing east and eastern block is either not facing the same direction as this block or not stairs at all
				else if (adjaDir == 0 && !this.isStairsWithMetadata(blockAccess, x + 1, y, z, metadata)) {
					minX = 0.5F;
					maxX = 1.0F;
					//LL
					//LH
					inside = true;
				}
			}
		}

		if (inside) {
			this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		}

		return inside;
	}

	//returns true if the stairs are "straight" after processing
	public boolean setBoundsForRemainingStep(IBlockAccess blockAccess, int x, int y, int z)
	{
		int metadata = blockAccess.getBlockMetadata(x, y, z);
		int dir = metadata & 3;

		//Top half of block
		float minY = 0.5F;
		float maxY = 1.0F;

		if ((metadata & 4) != 0) {//Stairs are inverted
			//Bottom half of block
			minY = 0.0F;
			maxY = 0.5F;
		}

		float minX = 0.0F;
		float maxX = 1.0F;
		float minZ = 0.0F;
		float maxZ = 0.5F;
		//HH
		//LL

		boolean straight = true;//"straight" is the default stairs shape.
		Block adjaBlock;
		int adjaMetadata;
		int adjaDir;

		if (dir == 0) {//Full block side is facing east
			minX = 0.5F;
			maxZ = 1.0F;
			//LH
			//LH
			adjaBlock = blockAccess.getBlock(x + 1, y, z);//get block to east
			adjaMetadata = blockAccess.getBlockMetadata(x + 1, y, z);

			if (isStairs(adjaBlock) && (metadata & 4) == (adjaMetadata & 4)) {//Ensure adjacent block is stairs and is flipped the same way
				adjaDir = adjaMetadata & 3;

				//Eastern block is facing north and southern block is either not facing the same direction as this block or not stairs at all
				if (adjaDir == 3 && !this.isStairsWithMetadata(blockAccess, x, y, z + 1, metadata)) {
					maxZ = 0.5F;//Make quarter block in NE corner
					//LH
					//LL
					straight = false;
				}
				//Eastern block is facing south and northern block is either not facing the same direction as this block or not stairs at all
				else if (adjaDir == 2 && !this.isStairsWithMetadata(blockAccess, x, y, z - 1, metadata)) {
					minZ = 0.5F;//Make quarter block in SE corner
					//LL
					//LH
					straight = false;
				}
			}
		}
		else if (dir == 1) {//Full block side is facing west
			maxX = 0.5F;
			maxZ = 1.0F;
			//HL
			//HL
			adjaBlock = blockAccess.getBlock(x - 1, y, z);//get block to west
			adjaMetadata = blockAccess.getBlockMetadata(x - 1, y, z);

			if (isStairs(adjaBlock) && (metadata & 4) == (adjaMetadata & 4))
			{
				adjaDir = adjaMetadata & 3;

				//Western block is facing north and southern block is either not facing the same direction as this block or not stairs at all
				if (adjaDir == 3 && !this.isStairsWithMetadata(blockAccess, x, y, z + 1, metadata)) {
					maxZ = 0.5F;//Make quarter block in NW corner
					//HL
					//LL
					straight = false;
				}
				//Western block is facing south and northern block is either not facing the same direction as this block or not stairs at all
				else if (adjaDir == 2 && !this.isStairsWithMetadata(blockAccess, x, y, z - 1, metadata)){
					minZ = 0.5F;//Make quarter block in SW corner
					//LL
					//HL
					straight = false;
				}
			}
		}
		else if (dir == 2) {//Full block side is facing south
			minZ = 0.5F;
			maxZ = 1.0F;
			//LL
			//HH
			adjaBlock = blockAccess.getBlock(x, y, z + 1);//get block to south
			adjaMetadata = blockAccess.getBlockMetadata(x, y, z + 1);

			if (isStairs(adjaBlock) && (metadata & 4) == (adjaMetadata & 4)){
				adjaDir = adjaMetadata & 3;

				//Southern block is facing west and eastern block is either not facing the same direction as this block or not stairs at all
				if (adjaDir == 1 && !this.isStairsWithMetadata(blockAccess, x + 1, y, z, metadata)) {
					maxX = 0.5F;//Make quarter block in SW corner
					//LL
					//HL
					straight = false;
				}
				//Southern block is facing east and western block is either not facing the same direction as this block or not stairs at all
				else if (adjaDir == 0 && !this.isStairsWithMetadata(blockAccess, x - 1, y, z, metadata)) {
					minX = 0.5F;//Make quarter block in SE corner
					//LL
					//LH
					straight = false;
				}
			}
		}
		else if (dir == 3) {//Full block side is facing north
			//HH
			//LL
			adjaBlock = blockAccess.getBlock(x, y, z - 1);//get block to north
			adjaMetadata = blockAccess.getBlockMetadata(x, y, z - 1);

			if (isStairs(adjaBlock) && (metadata & 4) == (adjaMetadata & 4))
			{
				adjaDir = adjaMetadata & 3;

				//Northern block is facing west and eastern block is either not facing the same direction as this block or not stairs at all
				if (adjaDir == 1 && !this.isStairsWithMetadata(blockAccess, x + 1, y, z, metadata)) {
					maxX = 0.5F;//Make quarter block in NW corner
					//HL
					//LL
					straight = false;
				}
				//Northern block is facing east and western block is either not facing the same direction as this block or not stairs at all
				else if (adjaDir == 0 && !this.isStairsWithMetadata(blockAccess, x - 1, y, z, metadata)) {
					minX = 0.5F;//Make quarter block in NE corner
					//LH
					//LL
					straight = false;
				}
			}
		}

		this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		return straight;
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
		return false;
	}

}