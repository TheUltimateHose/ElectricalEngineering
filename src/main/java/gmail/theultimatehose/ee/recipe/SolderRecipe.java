package gmail.theultimatehose.ee.recipe;


import gmail.theultimatehose.ee.Main;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;

public class SolderRecipe extends ShapedOreRecipe {

    public static ArrayList<SolderRecipe> recipes;

    public SolderRecipe(ItemStack result, Object... recipe) {
        super(result, recipe);
    }

    public static void init() {
        recipes = new ArrayList<SolderRecipe>(2);
        recipes.add(new SolderRecipe(new ItemStack(Main.pcbControl, 1), "RCT", "TRC", "NPN", 'N', "nuggetTin", 'P', Main.pcbEtched, 'R', Main.resistor, 'T', Main.transistor, 'C', Main.capacitorLV));
        recipes.add(new SolderRecipe(new ItemStack(Main.pcbVoltReg, 1), "FHT", "TRC", "NPN", 'N', "nuggetTin", 'P', Main.pcbEtched, 'R', Main.resistor, 'T', Main.transistor, 'C', Main.capacitorLV, 'F', Main.transformer, 'H', Main.capacitorHV));
        recipes.add(new SolderRecipe(new ItemStack(Main.gaussGun, 1), "TRB", " LC", "  I", 'T', new ItemStack(Main.coil, 1, OreDictionary.WILDCARD_VALUE), 'R', Main.pcbVoltReg, 'B', Blocks.iron_block, 'L', Blocks.lever, 'C', Main.pcbControl, 'I', Items.iron_ingot));
    }

    public static SolderRecipe checkMatch(ItemStack[] matrix) {

        recipes:
        for (SolderRecipe sr : recipes) {
            Object[] obj = sr.getInput();
            ItemStack[][] res = new ItemStack[obj.length][];

            int index = 0;
            for (Object o : obj) {
                if (o != null) {
                    if (o instanceof ItemStack) {
                        res[index] = new ItemStack[]{(ItemStack) o};
                    } else if (o instanceof ArrayList) {
                        ArrayList<ItemStack> items = (ArrayList<ItemStack>) o;
                        res[index] = new ItemStack[items.size()];
                        for (int i = 0; i < items.size(); i++) {
                            res[index][i] = items.get(i);
                        }
                    }
                }
                index++;
            }

            index = 0;
            slot:
            for (ItemStack[] is : res) {
                if (is != null) {
                    if (matrix[index] != null) {
                        if (is.length == 1) {
                            if (is[0].getItem() != matrix[index].getItem()) {
                                continue recipes;
                            }
                        } else {
                            for (ItemStack i : is) {
                                if (i.getItem() == matrix[index].getItem()) continue slot;
                            }
                            continue recipes;
                        }
                    } else {
                        continue recipes;
                    }
                }
                index++;
            }

            return sr;

        }

        return null;
    }

}
