package com.github.raveninthedark.itemfucker.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.GameRules;

@Mixin(GameRules.class)
public class GameRuleMixin {
    @Inject(method = "<init>*", at = @At("RETURN"))
    public void afterInit(CallbackInfo ci) {
        this.addGameRule("IF.killAllItems", "false", GameRules.ValueType.BOOLEAN_VALUE);
        this.addGameRule("IF.itemOverflowThreshold", "15", GameRules.ValueType.NUMERICAL_VALUE);
        this.addGameRule("IF.itemOverflowRange", "2", GameRules.ValueType.NUMERICAL_VALUE);
        this.addGameRule("IF.itemMinimumLifetime", "100", GameRules.ValueType.NUMERICAL_VALUE);
    }

    @Shadow
    public void addGameRule(String key, String value, GameRules.ValueType type)
    {
    }
}