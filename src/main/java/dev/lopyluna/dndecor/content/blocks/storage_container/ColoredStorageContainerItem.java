package dev.lopyluna.dndecor.content.blocks.storage_container;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.foundation.block.IBE;
import dev.lopyluna.dndecor.register.DnDecorBETypes;
import dev.lopyluna.dndecor.register.DnDecorBlocks;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static dev.lopyluna.dndecor.content.blocks.storage_container.ColoredStorageContainerBlock.COLOR;

public class ColoredStorageContainerItem extends BlockItem {

    private final DyeColor color;

    public ColoredStorageContainerItem(Properties properties, DyeColor color) {
        super(DnDecorBlocks.DYED_STORAGE_CONTAINER.get(), properties);
        this.color = color;
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(@NotNull BlockPlaceContext pContext) {
        if (super.getPlacementState(pContext) == null)
            return super.getPlacementState(pContext);
        return Objects.requireNonNull(super.getPlacementState(pContext)).setValue(COLOR, color);
    }

    @Override
    public @NotNull String getDescriptionId() {
        return "item.design_decor." + RegisteredObjectsHelper.getKeyOrThrow(this).getPath();
    }

    @Override
    public @NotNull InteractionResult place(@NotNull BlockPlaceContext ctx) {
        InteractionResult initialResult = super.place(ctx);
        if (!initialResult.consumesAction())
            return initialResult;
        tryMultiPlace(ctx);
        return initialResult;
    }

    @Override
    protected boolean updateCustomBlockEntityTag(@NotNull BlockPos pos, Level level, Player player,
                                                 @NotNull ItemStack itemStack, @NotNull BlockState blockState) {
        MinecraftServer minecraftserver = level.getServer();
        if (minecraftserver == null) return false;
        CustomData blockEntityData = itemStack.get(DataComponents.BLOCK_ENTITY_DATA);
        if (blockEntityData != null) {
            CompoundTag nbt = blockEntityData.copyTag();
            nbt.remove("Length");
            nbt.remove("Size");
            nbt.remove("Controller");
            nbt.remove("LastKnownPos");
            BlockEntity.addEntityType(nbt, ((IBE<?>) this.getBlock()).getBlockEntityType());
            itemStack.set(DataComponents.BLOCK_ENTITY_DATA, CustomData.of(nbt));
        }
        return super.updateCustomBlockEntityTag(pos, level, player, itemStack, blockState);
    }

    private void tryMultiPlace(BlockPlaceContext ctx) {
        Player player = ctx.getPlayer();
        if (player == null) return;
        if (player.isShiftKeyDown()) return;
        Direction face = ctx.getClickedFace();
        ItemStack stack = ctx.getItemInHand();
        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        BlockPos placedOnPos = pos.relative(face.getOpposite());
        BlockState placedOnState = world.getBlockState(placedOnPos);

        ColoredStorageContainerBlockEntity tankAt = ConnectivityHandler.partAt(DnDecorBETypes.COLORED_STORAGE_CONTAINER.get(), world, placedOnPos);
        if (tankAt == null) return;
        ColoredStorageContainerBlockEntity controllerBE = tankAt.getControllerBE();
        if (controllerBE == null) return;
        if (!ColoredStorageContainerBlock.isVault(placedOnState)) return;

        int width = controllerBE.radius;
        if (width == 1) return;

        int tanksToPlace = 0;
        Direction.Axis vaultBlockAxis = ColoredStorageContainerBlock.getVaultBlockAxis(placedOnState);
        if (vaultBlockAxis == null) return;
        if (face.getAxis() != vaultBlockAxis) return;

        Direction vaultFacing = Direction.fromAxisAndDirection(vaultBlockAxis, Direction.AxisDirection.POSITIVE);
        BlockPos startPos = face == vaultFacing.getOpposite() ? controllerBE.getBlockPos().relative(vaultFacing.getOpposite()) : controllerBE.getBlockPos().relative(vaultFacing, controllerBE.length);

        if (VecHelper.getCoordinate(startPos, vaultBlockAxis) != VecHelper.getCoordinate(pos, vaultBlockAxis)) return;

        for (int xOffset = 0; xOffset < width; xOffset++) for (int zOffset = 0; zOffset < width; zOffset++) {
            BlockPos offsetPos = vaultBlockAxis == Direction.Axis.X ? startPos.offset(0, xOffset, zOffset) : startPos.offset(xOffset, zOffset, 0);
            BlockState blockState = world.getBlockState(offsetPos);
            if (ColoredStorageContainerBlock.isVault(blockState)) continue;
            if (!blockState.canBeReplaced()) return;
            tanksToPlace++;
        }

        if (!player.isCreative() && stack.getCount() < tanksToPlace) return;

        for (int xOffset = 0; xOffset < width; xOffset++) for (int zOffset = 0; zOffset < width; zOffset++) {
            BlockPos offsetPos = vaultBlockAxis == Direction.Axis.X ? startPos.offset(0, xOffset, zOffset) : startPos.offset(xOffset, zOffset, 0);
            BlockState blockState = world.getBlockState(offsetPos);
            if (ColoredStorageContainerBlock.isVault(blockState)) continue;
            BlockPlaceContext context = BlockPlaceContext.at(ctx, offsetPos, face);
            player.getPersistentData().putBoolean("SilenceVaultSound", true);
            super.place(context);
            player.getPersistentData().remove("SilenceVaultSound");
        }
    }
}