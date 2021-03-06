package com.supermartijn642.itemcollectors;

import com.supermartijn642.itemcollectors.screen.ContainerProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Created 7/15/2020 by SuperMartijn642
 */
public class CollectorBlock extends Block {

    private static final VoxelShape SHAPE = VoxelShapes.or(
        VoxelShapes.create(3 / 16d, 0 / 16d, 3 / 16d, 13 / 16d, 1 / 16d, 13 / 16d),
        VoxelShapes.create(5 / 16d, 1 / 16d, 5 / 16d, 11 / 16d, 2 / 16d, 11 / 16d),
        VoxelShapes.create(6 / 16d, 2 / 16d, 6 / 16d, 10 / 16d, 5 / 16d, 10 / 16d),
        VoxelShapes.create(5 / 16d, 5 / 16d, 5 / 16d, 11 / 16d, 6 / 16d, 11 / 16d),
        VoxelShapes.create(5 / 16d, 6 / 16d, 5 / 16d, 6 / 16d, 11 / 16d, 6 / 16d),
        VoxelShapes.create(5 / 16d, 6 / 16d, 10 / 16d, 6 / 16d, 11 / 16d, 11 / 16d),
        VoxelShapes.create(10 / 16d, 6 / 16d, 5 / 16d, 11 / 16d, 11 / 16d, 6 / 16d),
        VoxelShapes.create(10 / 16d, 6 / 16d, 10 / 16d, 11 / 16d, 11 / 16d, 11 / 16d),
        VoxelShapes.create(6 / 16d, 10 / 16d, 5 / 16d, 11 / 16d, 11 / 16d, 6 / 16d),
        VoxelShapes.create(6 / 16d, 10 / 16d, 10 / 16d, 10 / 16d, 11 / 16d, 11 / 16d),
        VoxelShapes.create(5 / 16d, 10 / 16d, 6 / 16d, 6 / 16d, 11 / 16d, 10 / 16d),
        VoxelShapes.create(10 / 16d, 10 / 16d, 6 / 16d, 11 / 16d, 11 / 16d, 10 / 16d),
        VoxelShapes.create(6 / 16d, 6 / 16d, 6 / 16d, 10 / 16d, 10 / 16d, 10 / 16d));

    private final Supplier<CollectorTile> tileSupplier;
    private final ContainerProvider containerProvider;

    public CollectorBlock(String registryName, Supplier<CollectorTile> tileSupplier, ContainerProvider containerProvider){
        super(Properties.create(Material.ROCK, MaterialColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(5, 1200));
        this.setRegistryName(registryName);
        this.tileSupplier = tileSupplier;
        this.containerProvider = containerProvider;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_){
        if(!worldIn.isRemote)
            NetworkHooks.openGui((ServerPlayerEntity)player, new INamedContainerProvider() {
                @Override
                public ITextComponent getDisplayName(){
                    return new StringTextComponent("");
                }

                @Nullable
                @Override
                public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player){
                    return CollectorBlock.this.containerProvider.createContainer(id, player, pos);
                }
            }, pos);
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return this.tileSupplier.get();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
        return SHAPE;
    }
}
