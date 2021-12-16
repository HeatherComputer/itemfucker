package com.github.raveninthedark.itemfucker.mixins;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

@Mixin(EntityItem.class)
@SuppressWarnings("unchecked")
public class EntityItemMixin extends Entity{
    public int boundingBox;
    public AxisAlignedBB bb;

    public EntityItemMixin(World worldIn) throws Exception {
        super(worldIn);
        throw new Exception("Mixin constructor was called. this should never happen!");
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void onItemInit(CallbackInfo ci) {
        this.boundingBox = getGameRuleAsInt("IF.itemOverflowRange");
        this.bb = AxisAlignedBB.getBoundingBox(this.posX - this.boundingBox, this.posY - this.boundingBox, this.posZ - this.boundingBox, this.posX + this.boundingBox, this.posY + this.boundingBox, this.posZ + this.boundingBox);
    }


    @Inject(method = "onUpdate()V", at = @At("HEAD"), cancellable = true)
    public void beforeOnUpdate(CallbackInfo ci) {
        //System.out.println("FUCK");
        //this.setDead();
        if (this.worldObj.isRemote) return;
        if (this.worldObj.getGameRules().getGameRuleBooleanValue("IF.killAllItems")) {
            worldObj.removeEntity(this);
            ci.cancel();
            return;
        }
        if (this.age < getGameRuleAsInt("IF.itemMinimumLifetime")) return;
        //List<Entity> list = this.worldObj.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox(), EntitySelectors.getTeamCollisionPredicate(this));
        if (this.boundingBox != getGameRuleAsInt("IF.itemOverflowRange")) {
            //this.boundingBox = this.worldObj.getGameRules().getInt("IF.itemOverflowRange");
            this.boundingBox = getGameRuleAsInt("IF.itemOverflowRange");
            this.bb = AxisAlignedBB.getBoundingBox(this.posX - this.boundingBox, this.posY - this.boundingBox, this.posZ - this.boundingBox, this.posX + this.boundingBox, this.posY + this.boundingBox, this.posZ + this.boundingBox);
        }
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.bb);
        //System.out.println(list.size());
        if (!list.isEmpty())
        {
            //int i = this.worldObj.getGameRules().getInt("IF.itemOverflowThreshold");
            int i = getGameRuleAsInt("IF.itemOverflowThreshold");

            if (i > 0 && list.size() > i - 1)
            {
                worldObj.removeEntity(this);
                ci.cancel();
            }
        }
    }

    @Shadow
    protected void entityInit() {
        
    }

    @Shadow
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
        
    }

    @Shadow
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
        
    }

    public int getGameRuleAsInt(String rule) {
        String t = this.worldObj.getGameRules().getGameRuleStringValue(rule);
        return Integer.parseInt(t);

    }

    @Shadow
    public int age;
}
