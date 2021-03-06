package theultimatehose.electricalengineering.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.RecipeInfo;
import codechicken.nei.recipe.TemplateRecipeHandler;
import theultimatehose.electricalengineering.Util;
import theultimatehose.electricalengineering.gui.GUIMAD;
import theultimatehose.electricalengineering.item.ItemManager;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SortingRecipeHandler extends TemplateRecipeHandler {

    public SortingRecipeHandler() {
        RecipeInfo.setGuiOffset(this.getGuiClass(), 0, 0);
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(10, 25, 20, 30), this.getName()));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(this.getName()) && getClass() == SortingRecipeHandler.class) {
            ArrayList<ItemStack> list = new ArrayList<ItemStack>();
            list.add(new ItemStack(ItemManager.capacitorLV));
            list.add(new ItemStack(ItemManager.transistor));
            list.add(new ItemStack(ItemManager.resistor));
            arecipes.add(new CachedSorting(new ItemStack(ItemManager.pcbScrapLV), list, false));
            list.add(new ItemStack(ItemManager.capacitorHV));
            list.add(new ItemStack(ItemManager.transformer));
            arecipes.add(new CachedSorting(new ItemStack(ItemManager.pcbScrapHV), list, true));
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        list.add(new ItemStack(ItemManager.capacitorLV));
        list.add(new ItemStack(ItemManager.transistor));
        list.add(new ItemStack(ItemManager.resistor));
        if (result.getItem() == ItemManager.capacitorLV || result.getItem() == ItemManager.transistor || result.getItem() == ItemManager.resistor) {
            arecipes.add(new CachedSorting(new ItemStack(ItemManager.pcbScrapLV), list, false));
            list.add(new ItemStack(ItemManager.capacitorHV));
            list.add(new ItemStack(ItemManager.transformer));
            arecipes.add(new CachedSorting(new ItemStack(ItemManager.pcbScrapHV), list, true));
        } else if (result.getItem() == ItemManager.capacitorHV || result.getItem() == ItemManager.transformer) {
            list.add(new ItemStack(ItemManager.capacitorHV));
            list.add(new ItemStack(ItemManager.transformer));
            arecipes.add(new CachedSorting(new ItemStack(ItemManager.pcbScrapHV), list, true));
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        Item in = ingredient.getItem();
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        list.add(new ItemStack(ItemManager.capacitorLV));
        list.add(new ItemStack(ItemManager.transistor));
        list.add(new ItemStack(ItemManager.resistor));
        if (in == ItemManager.pcbScrapLV) {
            arecipes.add(new CachedSorting(new ItemStack(ItemManager.pcbScrapLV), list, false));
        } else if (in == ItemManager.pcbScrapHV) {
            list.add(new ItemStack(ItemManager.capacitorHV));
            list.add(new ItemStack(ItemManager.transformer));
            arecipes.add(new CachedSorting(new ItemStack(ItemManager.pcbScrapHV), list, true));
        }
    }

    @Override
    public String getGuiTexture() {
        return Util.MOD_ID_LOWER + ":textures/gui/guiMAD.png";
    }

    @Override
    public void drawExtras(int recipe) {
        drawProgressBar(3, 21, 176, 0, 20, 30, 100, 1);
        CachedSorting sort = (CachedSorting) this.arecipes.get(recipe);
        drawChanceString(sort.getIngredient().item.getItem() == ItemManager.pcbScrapHV);
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GUIMAD.class;
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GuiDraw.changeTexture(this.getGuiTexture());
        GuiDraw.drawTexturedModalRect(-1, 0, 6, 4, 166, 77);
    }

    @Override
    public int recipiesPerPage() {
        return 1;
    }

    public void drawChanceString(boolean hv) {
        if (!hv) {
            GuiDraw.drawString("33,3% chance to get any shown", 0, 75, 0x404040, false);
        } else {
            GuiDraw.drawString("20% chance to get any shown", 0, 75, 0x404040, false);
        }
    }

    public String getName() {
        return Util.MOD_ID_LOWER + ".sorting";
    }

    @Override
    public String getRecipeName() {
        return "Sorting";
    }

    public class CachedSorting extends CachedRecipe {

        public PositionedStack input;
        public ArrayList<PositionedStack> output;

        public CachedSorting(ItemStack in, ArrayList<ItemStack> out, boolean hv) {
            input = new PositionedStack(in, 3, 2);
            output = new ArrayList<PositionedStack>();
            output.add(new PositionedStack(out.get(0), 29, 2));
            output.add(new PositionedStack(out.get(1), 29, 20));
            output.add(new PositionedStack(out.get(2), 29, 38));
            if (hv) {
                output.add(new PositionedStack(out.get(3), 47, 2));
                output.add(new PositionedStack(out.get(4), 47, 20));
            }
        }

        @Override
        public PositionedStack getResult() {
            return null;
        }

        @Override
        public PositionedStack getIngredient() {
            return input;
        }

        @Override
        public List<PositionedStack> getOtherStacks() {
            return output;
        }
    }

}
