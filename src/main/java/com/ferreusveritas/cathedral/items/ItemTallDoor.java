package com.ferreusveritas.cathedral.items;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemTallDoor extends Item {

	    public ItemTallDoor(Material material) {
	        this.maxStackSize = 64;
	        this.setCreativeTab(CreativeTabs.REDSTONE);
	    }
    
	    /*
	    // Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	    // True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	    @Override
		public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float px, float py, float pz) {
	        if (side != 1) { return false; }
	        
            ++y;
            Block block = Dwemer.tallDoorBlock;

            if (player.canPlayerEdit(x, y, z, side, itemStack) && player.canPlayerEdit(x, y + 1, z, side, itemStack) && player.canPlayerEdit(x, y + 2, z, side, itemStack)) {
                if (!block.canPlaceBlockAt(world, x, y, z)) { return false; }

                int dir = MathHelper.floor_double((player.rotationYaw + 180.0F) * 4.0F / 360.0F - 0.5D) & 3;
                placeDoorBlock(world, x, y, z, dir, block);
                --itemStack.stackSize;
                
                return true;
            }
            
            return false;
	    }
	    
	    @Override
	    @SideOnly(Side.CLIENT)
	    public void registerIcons(IIconRegister register){}
	    
	    public static void placeDoorBlock(World world, int x, int y, int z, int dir, Block block) {
	        byte xdelta = 0;
	        byte zdelta = 0;

	        if (dir == 0){ zdelta = 1; }
	        if (dir == 1){ xdelta = -1; }
	        if (dir == 2){ zdelta = -1; }
	        if (dir == 3){ xdelta = 1; }

	        int rightBlocks = (world.getBlock(x - xdelta, y, z - zdelta).isNormalCube() ? 1 : 0) + (world.getBlock(x - xdelta, y + 1, z - zdelta).isNormalCube() ? 1 : 0);
	        int leftBlocks = (world.getBlock(x + xdelta, y, z + zdelta).isNormalCube() ? 1 : 0) + (world.getBlock(x + xdelta, y + 1, z + zdelta).isNormalCube() ? 1 : 0);
	        boolean rightDoor = world.getBlock(x - xdelta, y, z - zdelta) == block || world.getBlock(x - xdelta, y + 1, z - zdelta) == block;
	        boolean leftDoor = world.getBlock(x + xdelta, y, z + zdelta) == block || world.getBlock(x + xdelta, y + 1, z + zdelta) == block;
	        boolean leftHinge = false;

	        if (rightDoor && !leftDoor){//There's a door on the right side but not the left side.
	            leftHinge = true;
	        }
	        else if (leftBlocks > rightBlocks){//There's more whole blocks on the left than on the right
	            leftHinge = true;
	        }

	        //X, Y, Z, new block ID, new metadata, flags
	        world.setBlock(x, y, z, block, dir, 2);//Bottom Block
	        world.setBlock(x, y + 1, z, block, 8 | (leftHinge ? 1 : 0), 2);//Middle Block
	        world.setBlock(x, y + 2, z, block, 12, 2);//Top Block
	        world.notifyBlocksOfNeighborChange(x, y, z, block);
	        world.notifyBlocksOfNeighborChange(x, y + 1, z, block);
	        world.notifyBlocksOfNeighborChange(x, y + 2, z, block);
	    }
	    */
}
