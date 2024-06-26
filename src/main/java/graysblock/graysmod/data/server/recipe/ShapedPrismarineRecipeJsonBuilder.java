package graysblock.graysmod.data.server.recipe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import graysblock.graysmod.recipe.RawShapedPrismarineRecipe;
import graysblock.graysmod.recipe.ShapedPrismarineRecipe;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ShapedPrismarineRecipeJsonBuilder implements CraftingRecipeJsonBuilder {
    private final RecipeCategory category;
    private final Item output;
    private final int count;
    private final List<String> pattern = Lists.newArrayList();
    private final Map<Character, Ingredient> inputs = Maps.newLinkedHashMap();
    private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap<>();
    @Nullable private String group;
    private boolean showNotification = true;

    public ShapedPrismarineRecipeJsonBuilder(RecipeCategory category, ItemConvertible output, int count) {
        this.category = category;
        this.output = output.asItem();
        this.count = count;
    }

    public static ShapedPrismarineRecipeJsonBuilder create(RecipeCategory category, ItemConvertible output) {
        return create(category, output, 1);
    }

    public static ShapedPrismarineRecipeJsonBuilder create(RecipeCategory category, ItemConvertible output, int count) {
        return new ShapedPrismarineRecipeJsonBuilder(category, output, count);
    }

    public ShapedPrismarineRecipeJsonBuilder input(Character c, TagKey<Item> tag) {
        return this.input(c, Ingredient.fromTag(tag));
    }

    public ShapedPrismarineRecipeJsonBuilder input(Character c, ItemConvertible itemProvider) {
        return this.input(c, Ingredient.ofItems(itemProvider));
    }

    public ShapedPrismarineRecipeJsonBuilder input(Character c, Ingredient ingredient) {
        if(this.inputs.containsKey(c)) {
            throw new IllegalArgumentException("Symbol '" + c + "' is already defined!");
        } else if (c == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.inputs.put(c, ingredient);
            return this;
        }
    }

    public ShapedPrismarineRecipeJsonBuilder pattern(String patternStr) {
        if(!this.pattern.isEmpty() && patternStr.length() != (this.pattern.getFirst()).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.pattern.add(patternStr);
            return this;
        }
    }

    public ShapedPrismarineRecipeJsonBuilder criterion(String string, AdvancementCriterion<?> advancementCriterion) {
        this.criteria.put(string, advancementCriterion);
        return this;
    }

    public ShapedPrismarineRecipeJsonBuilder group(@Nullable String string) {
        this.group = string;
        return this;
    }

    public ShapedPrismarineRecipeJsonBuilder showNotification(boolean showNotification) {
        this.showNotification = showNotification;
        return this;
    }

    @Override
    public Item getOutputItem() {
        return this.output;
    }

    @Override
    public void offerTo(RecipeExporter exporter, Identifier recipeId) {
        RawShapedPrismarineRecipe rawShapedPrismarineRecipe = this.validate(recipeId);
        Advancement.Builder builder = exporter.getAdvancementBuilder()
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        this.criteria.forEach(builder::criterion);
        ShapedPrismarineRecipe shapedPrismarineRecipe = new ShapedPrismarineRecipe(
                Objects.requireNonNullElse(this.group, ""),
                CraftingRecipeJsonBuilder.toCraftingCategory(this.category),
                rawShapedPrismarineRecipe,
                new ItemStack(this.output, this.count),
                this.showNotification
        );
        exporter.accept(recipeId, shapedPrismarineRecipe, builder.build(recipeId.withPrefixedPath("recipes/" + this.category.getName() + "/")));
    }

    private RawShapedPrismarineRecipe validate(Identifier recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        } else {
            return RawShapedPrismarineRecipe.create(this.inputs, this.pattern);
        }
    }
}