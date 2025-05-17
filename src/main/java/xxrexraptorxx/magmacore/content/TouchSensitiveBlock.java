package xxrexraptorxx.magmacore.content;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TouchSensitiveBlock extends Block {

    protected static final VoxelShape CUSTOM_COLLISION_AABB = Block.box(0.0625D, 0.0625D, 0.0625D, 15.9375D, 15.9375D, 15.9375D);

    public TouchSensitiveBlock(Properties properties) {
        super(properties);
    }


    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return CUSTOM_COLLISION_AABB;
    }


    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
        if(!level.isClientSide) {
            if(entity instanceof LivingEntity livingEntity) {
                setInsideEffects(livingEntity, entity, state, level, pos, effectApplier);
            }
        }
    }


    private static void setInsideEffects(LivingEntity livingEntity, Entity entity, BlockState state, Level level, BlockPos pos, InsideBlockEffectApplier effectApplier) {
        //do stuff
    }
}
