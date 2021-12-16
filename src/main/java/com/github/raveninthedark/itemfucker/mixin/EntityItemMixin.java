package com.github.raveninthedark.itemfucker.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

@Mixin(EntityItem.class)
public class EntityItemMixin extends Entity {
    public int boundingBox;
    public AxisAlignedBB bb;

    public EntityItemMixin(World worldIn) throws Exception {
        super(worldIn);
        throw new Exception("Mixin constructor was called. this should never happen!");
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void onItemInit(CallbackInfo ci) {
        this.boundingBox = this.world.getGameRules().getInt("IF.itemOverflowRange") - 1;
        this.bb = new AxisAlignedBB(this.posX + this.boundingBox, this.posY + this.boundingBox, this.posZ + this.boundingBox, this.posX - this.boundingBox, this.posY - this.boundingBox, this.posZ - this.boundingBox);
    }

    @Inject(method = "onUpdate()V", at = @At("HEAD"), cancellable = true)
    public void beforeOnUpdate(CallbackInfo ci) {
        //System.out.println("FUCK");
        //this.setDead();
        if (this.world.isRemote) return;
        if (this.world.getGameRules().getBoolean("IF.killAllItems")) {
            world.removeEntity(this);
            ci.cancel();
            return;
        }
        if (this.age < this.world.getGameRules().getInt("IF.itemMinimumLifetime") * 20) {
            return;
        }
        //List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox(), EntitySelectors.getTeamCollisionPredicate(this));
        if ((this.boundingBox + 1) != this.world.getGameRules().getInt("IF.itemOverflowRange")) {
            this.boundingBox = this.world.getGameRules().getInt("IF.itemOverflowRange");
            this.bb = new AxisAlignedBB(this.posX + this.boundingBox, this.posY + this.boundingBox, this.posZ + this.boundingBox, this.posX - this.boundingBox, this.posY - this.boundingBox, this.posZ - this.boundingBox);
        }
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb);
        if (!list.isEmpty())
        {
            int i = this.world.getGameRules().getInt("IF.itemOverflowThreshold");

            if (i > 0 && list.size() > i - 1)
            {
                world.removeEntity(this);
                ci.cancel();
            }
        }
    }

    @Shadow
    public void entityInit() {
    }

    @Shadow
    public void readEntityFromNBT(NBTTagCompound compound) {
    }

    @Shadow
    public void writeEntityToNBT(NBTTagCompound compound) {
    }

    @Shadow
    public int age;
}