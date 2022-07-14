package shadowmaster435.funkyfarming.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class VoraceBulb extends Item {

    public VoraceBulb(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity player = (PlayerEntity) user;
       // System.out.println(((PlayerEntity) user).getHungerManager().getFoodLevel());
        int foodlevel = ((PlayerEntity) user).getHungerManager().getFoodLevel();
        ItemStack newstack = new ItemStack(this);




        if (stack.getDamage() - (20 - foodlevel) <= 0) {
            stack.decrement(1);

        }
        stack.setDamage(stack.getDamage() - foodlevel);

        player.getHungerManager().setFoodLevel(foodlevel + ((20 - stack.getDamage())));
        player.getHungerManager().setSaturationLevel(stack.getDamage() - player.getHungerManager().getFoodLevel() * 0.75f);



        player.setStackInHand(player.getActiveHand(), stack);

        return super.finishUsing(stack, world, user);

    }
}
