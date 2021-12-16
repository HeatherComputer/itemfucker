package com.github.raveninthedark.itemfucker.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.GameRules;

@Mixin(GameRules.class)
public class GameRulesMixin {
    @Inject(method = "<init>*", at = @At("RETURN"))
    public void afterInit(CallbackInfo ci) {
        this.addGameRule("IF.killAllItems", "false");
        this.addGameRule("IF.itemOverflowThreshold", "15");
        this.addGameRule("IF.itemOverflowRange", "3");
        this.addGameRule("IF.itemMinimumLifetime", "100");
    }

    @Shadow
    public void addGameRule(String key, String value)
    {
    }
}
