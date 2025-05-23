package dev.lopyluna.dndecor.register;

import com.simibubi.create.*;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.content.decoration.palettes.AllPaletteBlocks;
import com.simibubi.create.content.decoration.palettes.ConnectedGlassPaneBlock;
import com.simibubi.create.content.decoration.palettes.WindowBlock;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltModel;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlock;
import com.simibubi.create.content.kinetics.flywheel.FlywheelBlock;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlock;
import com.simibubi.create.content.logistics.vault.ItemVaultBlock;
import com.simibubi.create.content.trains.display.FlapDisplayBlock;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.block.connected.*;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.lopyluna.dndecor.DnDecor;
import dev.lopyluna.dndecor.content.blocks.*;
import dev.lopyluna.dndecor.content.blocks.beam.BeamBlock;
import dev.lopyluna.dndecor.content.blocks.beam.BeamCTBehaviour;
import dev.lopyluna.dndecor.content.blocks.diagonal_girder.DiagonalGirderBlock;
import dev.lopyluna.dndecor.content.blocks.diagonal_girder.DiagonalGirderGenerator;
import dev.lopyluna.dndecor.content.blocks.flywheel.FlywheelTypeBlock;
import dev.lopyluna.dndecor.content.blocks.full_belt.FullBeltBlock;
import dev.lopyluna.dndecor.content.blocks.full_belt.FullBeltGenerator;
import dev.lopyluna.dndecor.content.blocks.storage_container.ColoredStorageContainerBlock;
import dev.lopyluna.dndecor.content.blocks.storage_container.ColoredStorageContainerCTBehaviour;
import dev.lopyluna.dndecor.content.configs.server.kinetics.DStress;
import dev.lopyluna.dndecor.content.entries.BoltEntry;
import dev.lopyluna.dndecor.register.helpers.list_providers.MaterialTypeProvider;
import dev.lopyluna.dndecor.register.helpers.list_providers.MetalTypeBlockList;
import dev.lopyluna.dndecor.register.helpers.list_providers.MetalTypeBoltBlockList;
import dev.lopyluna.dndecor.register.helpers.list_providers.StoneTypeBlockList;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.Tags;

import java.util.function.Function;

import static com.simibubi.create.api.behaviour.display.DisplaySource.displaySource;
import static com.simibubi.create.api.behaviour.display.DisplayTarget.displayTarget;
import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static com.tterrag.registrate.providers.RegistrateRecipeProvider.has;
import static dev.lopyluna.dndecor.DnDecor.REG;
import static dev.lopyluna.dndecor.register.DnDecorTags.optionalTag;
import static dev.lopyluna.dndecor.register.helpers.BlockTransgender.*;

@SuppressWarnings({"removal", "deprecation", "SameParameterValue", "unused"})
public class DnDecorBlocks {

    public static final BlockEntry<FullBeltBlock> BELT = REG.block("belt", FullBeltBlock::new)
            .properties(p -> p.sound(SoundType.WOOL).strength(0.8f).mapColor(MapColor.COLOR_GRAY))
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> {
                p.models().withExistingParent("block/belt/diagonal_end", Create.asResource("block/belt/diagonal_end"))
                        .texture("0", DnDecor.loc("block/belt_diagonal")).texture("particle", DnDecor.loc("block/belt_diagonal"));
                p.models().withExistingParent("block/belt/diagonal_middle", Create.asResource("block/belt/diagonal_middle"))
                        .texture("0", DnDecor.loc("block/belt_diagonal")).texture("particle", DnDecor.loc("block/belt_diagonal"));
                p.models().withExistingParent("block/belt/diagonal_start", Create.asResource("block/belt/diagonal_start"))
                        .texture("0", DnDecor.loc("block/belt_diagonal")).texture("particle", DnDecor.loc("block/belt_diagonal"));

                p.models().withExistingParent("block/belt/end", Create.asResource("block/belt/end")).texture("0", DnDecor.loc("block/belt"));
                p.models().withExistingParent("block/belt/middle", Create.asResource("block/belt/middle")).texture("0", DnDecor.loc("block/belt"));
                p.models().withExistingParent("block/belt/start", Create.asResource("block/belt/start")).texture("0", DnDecor.loc("block/belt"));

                p.models().withExistingParent("block/belt/end_bottom", Create.asResource("block/belt/end_bottom")).texture("1", DnDecor.loc("block/belt_offset"));
                p.models().withExistingParent("block/belt/middle_bottom", Create.asResource("block/belt/middle_bottom")).texture("1", DnDecor.loc("block/belt_offset"));
                p.models().withExistingParent("block/belt/start_bottom", Create.asResource("block/belt/start_bottom")).texture("1", DnDecor.loc("block/belt_offset"));


                p.models().withExistingParent("block/belt/particle", Create.asResource("block/belt/particle")).texture("particle", DnDecor.loc("block/belt"));


                new FullBeltGenerator().generate(c, p);
            })
            .transform(DStress.setNoImpact())
            .transform(displaySource(AllDisplaySources.ITEM_NAMES))
            .onRegister(CreateRegistrate.blockModel(() -> BeltModel::new))
            .clientExtension(() -> BeltBlock.RenderProperties::new)
            .register();

    public static final DyedBlockList<FlapDisplayBlock> DYED_DISPLAY_BOARDS = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return REG.block(colorName + "_display_board", FlapDisplayTypeBlock::new)
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.mapColor(color.getMapColor()))
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(pickaxeOnly())
                .transform(DStress.setNoImpact())
                .recipe((c, p) ->
                        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 8)
                                .pattern("DDD")
                                .pattern("DCD")
                                .pattern("DDD")
                                .define('C', color.getTag())
                                .define('D', DnDecorTags.commonItemTag("create/display_boards"))
                                .unlockedBy("has_" + c.getName(), has(c.get()))
                                .save(p, DnDecor.loc("crafting/" + c.getName()))
                )
                .blockstate((c, p) -> {
                    p.models().withExistingParent("block/" + c.getName() + "/block", DnDecor.loc("block/display_board_base/block"))
                            .texture("7", DnDecor.loc("block/display_boards/" + colorName))
                            .texture("particle", DnDecor.loc("block/display_boards/" + colorName));
                    p.models().withExistingParent("block/" + c.getName() + "/item", DnDecor.loc("block/display_board_base/item"))
                            .texture("7", DnDecor.loc("block/display_boards/" + colorName))
                            .texture("particle", DnDecor.loc("block/display_boards/" + colorName));

                    p.horizontalBlock(c.get(), AssetLookup.partialBaseModel(c, p));
                })
                .transform(displayTarget(AllDisplayTargets.DISPLAY_BOARD))
                .item()
                .tag(DnDecorTags.commonItemTag("create/display_boards"))
                .transform(customItemModel())
                .register();
    });

    public static final DyedBlockList<FlywheelBlock> DYED_FLYWHEELS = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        return REG.block(colorName + "_flywheel", p -> new FlywheelTypeBlock(color, p))
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.noOcclusion().mapColor(color.getMapColor()))
                .transform(axeOrPickaxe())
                .transform(DStress.setNoImpact())
                .recipe((c, p) ->
                        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 8)
                                .pattern("DDD")
                                .pattern("DCD")
                                .pattern("DDD")
                                .define('C', color.getTag())
                                .define('D', DnDecorTags.commonItemTag("create/flywheels"))
                                .unlockedBy("has_" + c.getName(), has(c.get()))
                                .save(p, DnDecor.loc("crafting/" + c.getName()))
                )
                .blockstate((c, p) -> {
                    p.models().withExistingParent("block/" + c.getName() + "/block", Create.asResource("block/flywheel/block"))
                            .texture("0", DnDecor.loc("block/flywheel/" + colorName))
                            .texture("particle", DnDecor.loc("block/flywheel/" + colorName));
                    p.models().withExistingParent("block/" + c.getName() + "/item", Create.asResource("block/flywheel/item"))
                            .texture("0", DnDecor.loc("block/flywheel/" + colorName))
                            .texture("particle", DnDecor.loc("block/flywheel/" + colorName));

                    BlockStateGen.axisBlock(c, p, getBlockModel(true, c, p));
                })
                .item()
                .tag(DnDecorTags.commonItemTag("create/flywheels"))
                .transform(customItemModel())
                .register();
    });

    public static final BlockEntry<WindowBlock> ORNATE_IRON_GLASS =
            customWindowBlock("ornate_iron_glass", () -> omni("palettes/ornate_iron_glass"), () -> omni("palettes/ornate_iron_glass_end"), () -> RenderType::cutout, false, () -> MapColor.TERRACOTTA_LIGHT_GRAY)
                    .recipe((c, p) -> {
                        p.stonecutting(DataIngredient.items(AllPaletteBlocks.ORNATE_IRON_WINDOW.get()), RecipeCategory.BUILDING_BLOCKS, c, 1);
                        p.stonecutting(DataIngredient.items(c), RecipeCategory.BUILDING_BLOCKS, AllPaletteBlocks.ORNATE_IRON_WINDOW, 1);
                    }).register();

    public static final BlockEntry<ConnectedGlassPaneBlock> ORNATE_IRON_GLASS_PANE =
            customWindowPane("ornate_iron_glass", ORNATE_IRON_GLASS, () -> omni("palettes/ornate_iron_glass"), () -> RenderType::cutoutMipped).register();

    public static final StoneTypeBlockList<CrushingWheelBlock> STONE_TYPE_CRUSHING_WHEELS = new StoneTypeBlockList<>((block, id) -> {
        if (id.equals("andesite")) return AllBlocks.CRUSHING_WHEEL;
        return REG.block(id + "_crushing_wheel", p -> new CrushingWheelTypeBlock(block, p))
                .properties(p -> p.mapColor(block.get().defaultMapColor()).sound(block.get().defaultBlockState().getSoundType()))
                .initialProperties(SharedProperties::stone)
                .properties(BlockBehaviour.Properties::noOcclusion)
                .transform(pickaxeOnly())
                .recipe((c, p) ->
                        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 1)
                                .requires(block.get())
                                .requires(DnDecorTags.commonItemTag("create/crushing_wheels"))
                                .unlockedBy("has_" + c.getName(), has(c.get()))
                                .save(p, DnDecor.loc("crafting/" + c.getName()))
                )
                .blockstate((c, p) -> {
                    p.models().withExistingParent("block/" + c.getName() + "/block", Create.asResource("block/crushing_wheel/block"))
                            .texture("insert", DnDecor.loc("block/crushing_wheels/" + id + "/insert"))
                            .texture("plates", DnDecor.loc("block/crushing_wheels/" + id + "/plates"));
                    p.models().withExistingParent("block/" + c.getName() + "/item", Create.asResource("block/crushing_wheel/item"))
                            .texture("insert", DnDecor.loc("block/crushing_wheels/" + id + "/insert"))
                            .texture("plates", DnDecor.loc("block/crushing_wheels/" + id + "/plates"));
                    BlockStateGen.axisBlock(c, p, s -> AssetLookup.partialBaseModel(c, p));
                })
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(DStress.setImpact(8.0))
                .item()
                .tag(DnDecorTags.commonItemTag("create/crushing_wheels"))
                .transform(customItemModel())
                .register();
    });

    public static final StoneTypeBlockList<MillstoneBlock> STONE_TYPE_MILLSTONE = new StoneTypeBlockList<>((block, id) -> {
        if (id.equals("andesite")) return AllBlocks.MILLSTONE;
        return REG.block(id + "_millstone", p -> new MillstoneTypeBlock(block, p))
                .properties(p -> p.mapColor(block.get().defaultMapColor()).sound(block.get().defaultBlockState().getSoundType()))
                .initialProperties(SharedProperties::stone)
                .transform(pickaxeOnly())
                .recipe((c, p) ->
                        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 1)
                                .requires(block.get())
                                .requires(DnDecorTags.commonItemTag("create/millstones"))
                                .unlockedBy("has_" + c.getName(), has(c.get()))
                                .save(p, DnDecor.loc("crafting/" + c.getName()))
                )
                .blockstate((c, p) -> {
                    p.models().withExistingParent("block/" + c.getName() + "/block", Create.asResource("block/millstone/block"))
                            .texture("5", DnDecor.loc("block/millstones/" + id));
                    p.models().withExistingParent("block/" + c.getName() + "/inner", Create.asResource("block/millstone/inner"))
                            .texture("5", DnDecor.loc("block/millstones/" + id));
                    p.models().withExistingParent("block/" + c.getName() + "/item", Create.asResource("block/millstone/item"))
                            .texture("5", DnDecor.loc("block/millstones/" + id))
                            .texture("4", DnDecor.loc("block/crushing_wheels/" + id + "/plates"));
                    p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p));
                })
                .transform(DStress.setImpact(4.0))
                .item()
                .tag(DnDecorTags.commonItemTag("create/millstones"))
                .transform(customItemModel())
                .register();
    });

    public static final BlockEntry<Block> INDUSTRIAL_PLATING_BLOCK = REG.block("industrial_plating_block", Block::new)
            .transform(layeredConnected(() -> omni("industrial_plating_block_side"), () -> omni("industrial_plating_block")))
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.COLOR_GRAY))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 1);
                p.stonecutting(DataIngredient.items(c), RecipeCategory.BUILDING_BLOCKS, AllBlocks.INDUSTRIAL_IRON_BLOCK, 1);
                p.stonecutting(DataIngredient.tag(Tags.Items.INGOTS_IRON), RecipeCategory.BUILDING_BLOCKS, c, 2);
            })
            .transform(pickaxeOnly())
            .tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .simpleItem()
            .lang("Block of Industrial Plating")
            .register();

    public static final BlockEntry<LargeGirderBlock> LARGE_METAL_GIRDER = REG.block("large_metal_girder", LargeGirderBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.COLOR_GRAY).sound(SoundType.NETHERITE_BLOCK))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 1)
                    .pattern("CC").pattern("CC")
                    .define('C', AllBlocks.METAL_GIRDER)
                    .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/" + c.getName()))
            ).transform(pickaxeOnly())
            .onRegister(CreateRegistrate.connectedTextures(() -> new RotatedPillarCTBehaviour(rectangle("large_girder"), omni("large_girder_top"))))
            .blockstate((c, p) -> p.axisBlock(c.get(), DnDecor.loc("block/large_girder"), DnDecor.loc("block/large_girder_top")))
            .simpleItem()
            .register();

    public static final BlockEntry<BeamBlock> BEAM = REG.block("beam", BeamBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.noOcclusion().sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.COLOR_GRAY))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .blockstate((c, p) -> p.getVariantBuilder(c.get()).forAllStates(s -> {
                var beam = s.getValue(BeamBlock.BEAM);
                var axis = s.getValue(BeamBlock.AXIS) == Direction.Axis.X ? "_x" : "_z";
                var modelBoth = p.models().getExistingFile(DnDecor.loc("block/beam/block" + axis));
                var modelTop = p.models().getExistingFile(DnDecor.loc("block/beam/top" + axis));
                var modelBottom = p.models().getExistingFile(DnDecor.loc("block/beam/bottom" + axis));
                var model = switch (beam) {
                    case TOP -> modelTop;
                    case BOTTOM -> modelBottom;
                    case BOTH -> modelBoth;
                };
                return ConfiguredModel.builder().modelFile(model).build();
            }))
            .onRegister(connectedTextures(() -> new BeamCTBehaviour(horizontalKryppers("beam/beam"), vertical("beam/beam_top_z"), horizontalKryppers("beam/beam_top_x"))))
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.tag(Tags.Items.INGOTS_IRON), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.items(LARGE_METAL_GIRDER.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get(), INDUSTRIAL_PLATING_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 1);
            })
            .transform(pickaxeOnly())
            .item()
            .model((c, p) -> p.withExistingParent("item/" + c.getName(), DnDecor.loc("block/beam/item")))
            .build()
            .lang("Beam")
            .register();

    public static final BlockEntry<DiagonalGirderBlock> DIAGONAL_GIRDER = REG.block("diagonal_girder", DiagonalGirderBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.COLOR_GRAY))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .properties(BlockBehaviour.Properties::noOcclusion)
            .addLayer(() -> RenderType::cutout)
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 2)
                        .pattern(" C")
                        .pattern("C ")
                        .define('C', AllBlocks.METAL_GIRDER)
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/" + c.getName()));
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 2)
                        .pattern("C ")
                        .pattern(" C")
                        .define('C', AllBlocks.METAL_GIRDER)
                        .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/mirrored_" + c.getName()));
            }).transform(axeOrPickaxe())
            .tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .blockstate(new DiagonalGirderGenerator()::generate)
            .lang("Diagonal Girder")
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<OrnateGrateBlock> ORNATE_GRATE = REG.block("ornate_grate", OrnateGrateBlock::new)
            .transform(ornateConnected(() -> omni("ornate_grate")))
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.sound(SoundType.WOOD).mapColor(MapColor.TERRACOTTA_GRAY))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 8)
                    .pattern("ISI")
                    .pattern("S S")
                    .pattern("ISI")
                    .define('S', Tags.Items.RODS_WOODEN)
                    .define('I', Tags.Items.INGOTS_IRON)
                    .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/" + c.getName()))
            ).transform(axeOrPickaxe())
            .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
            .lang("Ornate Grate")
            .addLayer(() -> RenderType::cutoutMipped)
            .item()
            .transform(b -> b.model((c, p) -> p.blockItem(() -> c.getEntry().getBlock())).build())
            .register();

    public static final BlockEntry<Block> ZINC_BRICKS = REG.block("zinc_bricks", Block::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.sound(SoundType.METAL).mapColor(MapColor.GLOW_LICHEN))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 8)
                    .pattern("ZZ")
                    .pattern("ZZ")
                    .define('Z', AllBlocks.ZINC_BLOCK)
                    .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/" + c.getName()))
            ).transform(pickaxeOnly())
            .lang("Zinc Bricks")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> ZINC_CHECKER_TILES = REG.block("zinc_checker_tiles", Block::new)
            .transform(connected(() -> omni("zinc_checker_tiles")))
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.sound(SoundType.METAL).mapColor(MapColor.GLOW_LICHEN))
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 8)
                    .pattern("III")
                    .pattern("IZI")
                    .pattern("III")
                    .define('I', AllItems.ZINC_INGOT)
                    .define('Z', AllBlocks.ZINC_BLOCK)
                    .unlockedBy("has_" + c.getName(), has(c.get())).save(p, DnDecor.loc("crafting/" + c.getName()))
            ).transform(pickaxeOnly())
            .lang("Zinc Checker Tiles")
            .simpleItem()
            .register();

    public static final DyedBlockList<VelvetBlock> DYED_VELVET_BLOCKS = new DyedBlockList<>(color -> velvetBlock(color.getSerializedName(), color.getMapColor(), color));

    public static final BlockEntry<ColoredStorageContainerBlock> DYED_STORAGE_CONTAINER = regColoredStorageContainer();
    
    public static TagKey<Item> darkMetalDecorTag = optionalTag(BuiltInRegistries.ITEM, DnDecor.loc("dark_metal_decor"));

    public static TagKey<Block> stairsBlockTag = optionalTag(BuiltInRegistries.BLOCK, ResourceLocation.withDefaultNamespace("stairs"));
    public static TagKey<Item> stairsItemTag = optionalTag(BuiltInRegistries.ITEM, ResourceLocation.withDefaultNamespace("stairs"));
    public static TagKey<Block> slabsBlockTag = optionalTag(BuiltInRegistries.BLOCK, ResourceLocation.withDefaultNamespace("slabs"));
    public static TagKey<Item> slabsItemTag = optionalTag(BuiltInRegistries.ITEM, ResourceLocation.withDefaultNamespace("slabs"));

    public static final BlockEntry<Block> DARK_METAL_BLOCK = REG.block("dark_metal_block", Block::new)
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK).sound(SoundType.NETHERITE_BLOCK).strength(0.5f,1.5f))
            .blockstate((c, p) -> p.simpleBlock(c.get()))
            .transform(pickaxeOnly())
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.tag(darkMetalDecorTag), RecipeCategory.BUILDING_BLOCKS, c, 1);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("CC")
                        .pattern("CC")
                        .define('C', AllBlocks.INDUSTRIAL_IRON_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DnDecor.loc("crafting/" + c.getName() + "_from_" + c.getName()));
            })
            .tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .item()
            .tag(darkMetalDecorTag)
            .build()
            .register();

    public static final BlockEntry<Block> DARK_METAL_PLATING = REG.block("dark_metal_plating", Block::new)
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK).sound(SoundType.NETHERITE_BLOCK).strength(0.5f,1.5f))
            .blockstate((c, p) -> p.simpleBlock(c.get()))
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(omni("dark_metal_plating"))))
            .onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, omni("dark_metal_plating"))))
            .transform(pickaxeOnly())
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.items(DARK_METAL_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 1);
                p.stonecutting(DataIngredient.items(c.get()), RecipeCategory.BUILDING_BLOCKS, DARK_METAL_BLOCK, 1);
                p.stonecutting(DataIngredient.tag(darkMetalDecorTag), RecipeCategory.BUILDING_BLOCKS, c, 1);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 9)
                        .pattern("CCC")
                        .pattern("CCC")
                        .pattern("CCC")
                        .define('C', DARK_METAL_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DnDecor.loc("crafting/" + c.getName() + "_from_" + c.getName()));
            })
            .tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .item()
            .tag(darkMetalDecorTag)
            .build()
            .register();

    public static final BlockEntry<SlabBlock> DARK_METAL_SLAB = REG.block("dark_metal_block_slab", SlabBlock::new)
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK).sound(SoundType.NETHERITE_BLOCK).strength(0.5f,1.5f))
            .blockstate((c, p) -> p.slabBlock(c.get(), DnDecor.loc("block/dark_metal_block"),
                    DnDecor.loc("block/dark_metal_block_slab"), DnDecor.loc("block/dark_metal_block"), DnDecor.loc("block/dark_metal_block")))
            .transform(pickaxeOnly())
            .tag(stairsBlockTag)
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 4);
                p.stonecutting(DataIngredient.tag(darkMetalDecorTag), RecipeCategory.BUILDING_BLOCKS, c, 2);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 6)
                        .pattern("CCC")
                        .define('C', DARK_METAL_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DnDecor.loc("crafting/" + c.getName() + "_from_" + c.getName()));
            })
            .tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .item()
            .tag(slabsItemTag)
            .build()
            .register();

    public static final BlockEntry<StairBlock> DARK_METAL_STAIRS = REG.block("dark_metal_block_stairs", p -> new StairBlock(DARK_METAL_BLOCK.getDefaultState(), p))
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK).sound(SoundType.NETHERITE_BLOCK).strength(0.5f,1.5f))
            .blockstate((c, p) -> p.stairsBlock(c.get(), DnDecor.loc("block/dark_metal_block")))
            .transform(pickaxeOnly())
            .tag(stairsBlockTag)
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.tag(darkMetalDecorTag), RecipeCategory.BUILDING_BLOCKS, c, 1);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("X  ").pattern("XX ").pattern("XXX")
                        .define('X', DARK_METAL_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DnDecor.loc("crafting/" + c.getName() + "_from_" + c.getName()));
            })
            .item()
            .tag(darkMetalDecorTag, stairsItemTag)
            .build()
            .register();

    public static final BlockEntry<Block> DARK_METAL_BRICKS = REG.block("dark_metal_bricks", Block::new)
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK).sound(SoundType.NETHERITE_BLOCK).strength(0.5f,1.5f))
            .blockstate((c, p) -> p.simpleBlock(c.get()))
            .transform(pickaxeOnly())
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.tag(darkMetalDecorTag), RecipeCategory.BUILDING_BLOCKS, c, 1);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("CC")
                        .pattern("CC")
                        .define('C', DARK_METAL_BLOCK.get())
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DnDecor.loc("crafting/" + c.getName() + "_from_" + c.getName()));
            })
            .tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .item()
            .tag(darkMetalDecorTag)
            .build()
            .register();

    public static final BlockEntry<SlabBlock> DARK_METAL_BRICK_SLAB = REG.block("dark_metal_brick_slab", SlabBlock::new)
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK).sound(SoundType.NETHERITE_BLOCK).strength(0.5f,1.5f))
            .blockstate((c, p) -> p.slabBlock(c.get(), DnDecor.loc("block/dark_metal_bricks"),
                    DnDecor.loc("block/dark_metal_bricks"), DnDecor.loc("block/dark_metal_bricks"), DnDecor.loc("block/dark_metal_bricks")))
            .transform(pickaxeOnly())
            .tag(stairsBlockTag)
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 4);
                p.stonecutting(DataIngredient.tag(darkMetalDecorTag), RecipeCategory.BUILDING_BLOCKS, c, 2);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 6)
                        .pattern("CCC")
                        .define('C', DARK_METAL_BRICKS.get())
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DnDecor.loc("crafting/" + c.getName() + "_from_" + c.getName()));
            })
            .tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .item()
            .tag(slabsItemTag)
            .build()
            .register();

    public static final BlockEntry<StairBlock> DARK_METAL_BRICK_STAIRS = REG.block("dark_metal_brick_stairs", p -> new StairBlock(DARK_METAL_BLOCK.getDefaultState(), p))
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK).sound(SoundType.NETHERITE_BLOCK).strength(0.5f,1.5f))
            .blockstate((c, p) -> p.stairsBlock(c.get(), DnDecor.loc("block/dark_metal_bricks")))
            .transform(pickaxeOnly())
            .tag(stairsBlockTag)
            .recipe((c, p) -> {
                p.stonecutting(DataIngredient.items(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, c, 2);
                p.stonecutting(DataIngredient.tag(darkMetalDecorTag), RecipeCategory.BUILDING_BLOCKS, c, 1);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
                        .pattern("X  ").pattern("XX ").pattern("XXX")
                        .define('X', DARK_METAL_BRICKS.get())
                        .unlockedBy("has_" + c.getName(), has(c.get()))
                        .save(p, DnDecor.loc("crafting/" + c.getName() + "_from_" + c.getName()));
            })
            .item()
            .tag(darkMetalDecorTag, stairsItemTag)
            .build()
            .register();


    private static BlockEntry<ColoredStorageContainerBlock> regColoredStorageContainer() {
        return REG.block("colored_storage_container", ColoredStorageContainerBlock::new)
                .lang("Storage Container")
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.mapColor(MapColor.COLOR_GRAY)
                        .sound(SoundType.NETHERITE_BLOCK)
                        .explosionResistance(1200))
                .transform(pickaxeOnly())
                .blockstate((c, p) -> p.getVariantBuilder(c.get()).forAllStates(s -> {
                    var refModel = Create.asResource("block/item_vault");
                    var refModelItem = Create.asResource("item/item_vault");
                    var color = s.getValue(ColoredStorageContainerBlock.COLOR);
                    var id = color.getSerializedName();
                    var path0 = DnDecor.loc("block/storage_container/" + id + "_storage_container_bottom_small");
                    var path1 = DnDecor.loc("block/storage_container/" + id + "_storage_container_front_small");
                    var path2 = DnDecor.loc("block/storage_container/" + id + "_storage_container_side_small");
                    var path3 = DnDecor.loc("block/storage_container/" + id + "_storage_container_top_small"); //particle

                    ModelFile model = p.models().withExistingParent("block/storage_containers/" + id, refModel)
                            .texture("0", path0).texture("1", path1).texture("2", path2).texture("3", path3).texture("particle", path3);

                    p.models().withExistingParent("item/" + id + "_storage_container", refModelItem).parent(model);
                    return ConfiguredModel.builder()
                            .modelFile(model)
                            .rotationY(s.getValue(ItemVaultBlock.HORIZONTAL_AXIS) == Direction.Axis.X ? 90 : 0)
                            .build();
                }))
                .onRegister(CreateRegistrate.connectedTextures(ColoredStorageContainerCTBehaviour::new))
                .register();
    }

    @SuppressWarnings("all")
    public static final MetalTypeBlockList<Block> METAL_TYPE_FLOORS = new MetalTypeBlockList<Block>(type -> {
        var metal = type.get();
        if (metal.requireMods()) return null;
        if (!DnDecor.LOAD_ALL_METALS) {
            if (metal.modIDs.equals(MaterialTypeProvider.NA)) return null;
            boolean anyModLoaded = metal.modIDs.isEmpty();
            if (metal.requireMods()) for (String mod : metal.modIDs)
                if (ModList.get().isLoaded(mod)) {
                    anyModLoaded = true;
                    break;
                }
            if (!anyModLoaded) return null;
        }
        var builder = REG.block(metal.id + "_floor", Block::new)
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.sound(metal.sound).mapColor(metal.color))
                .transform(pickaxeOnly());

        builder = builder.recipe((c, p) -> {
            var ingredient = metal.getIngredient();
            if (ingredient != null) p.stonecutting(ingredient, RecipeCategory.BUILDING_BLOCKS, c, 2);
        });
        return builder.simpleItem().register();
    });

    @SuppressWarnings("all")
    public static final MetalTypeBlockList<LargeChain> METAL_TYPE_LARGE_CHAINS = new MetalTypeBlockList<LargeChain>(type -> {
        var metal = type.get();
        if (metal.requireMods()) return null;
        if (!DnDecor.LOAD_ALL_METALS) {
            if (metal.modIDs.equals(MaterialTypeProvider.NA)) return null;
            boolean anyModLoaded = metal.modIDs.isEmpty();
            if (metal.requireMods()) for (String mod : metal.modIDs)
                if (ModList.get().isLoaded(mod)) {
                    anyModLoaded = true;
                    break;
                }
            if (!anyModLoaded) return null;
        }

        var builder = REG.block("large_" + metal.id + "_chain", LargeChain::new)
                .initialProperties(SharedProperties::softMetal)
                .properties(p -> p.sound(DnDecorSoundTypes.CHAIN_HEAVY).mapColor(metal.color))
                .addLayer(() -> RenderType::cutout)
                .transform(pickaxeOnly())
                .blockstate((c, p) -> {
                    p.models().withExistingParent("block/" + c.getName(), DnDecor.loc("block/large_chain")).texture("0", DnDecor.loc("block/" + metal.id + "_large_chain"));
                    p.models().withExistingParent("block/" + c.getName() + "/block", DnDecor.loc("block/large_chain")).texture("0", DnDecor.loc("block/" + metal.id + "_large_chain"));
                    p.models().withExistingParent("block/" + c.getName() + "/item", DnDecor.loc("block/large_chain")).texture("0", DnDecor.loc("block/" + metal.id + "_large_chain"));
                    BlockStateGen.axisBlock(c, p, getBlockModel(true, c, p));
                }).tag(AllTags.AllBlockTags.BRITTLE.tag, BlockTags.CLIMBABLE);

        builder = builder.recipe((c, p) -> {
            var ingredient = metal.getIngredient();
            if (ingredient != null) p.stonecutting(ingredient, RecipeCategory.BUILDING_BLOCKS, c, 4);
        });
        return builder.simpleItem().register();
    });

    public static final MetalTypeBoltBlockList<?> METAL_TYPE_BOLTS = new MetalTypeBoltBlockList<>(type -> {
        var metal = type.get();
        if (!DnDecor.LOAD_ALL_METALS) {
            if (metal.modIDs.equals(MaterialTypeProvider.NA)) return null;
            boolean anyModLoaded = metal.modIDs.isEmpty();
            if (metal.requireMods()) for (String mod : metal.modIDs) if (ModList.get().isLoaded(mod)) { anyModLoaded = true; break; }
            if (!anyModLoaded) return null;
        }
        return new BoltEntry<>(metal);
    });

    private static CTSpriteShiftEntry horizontal(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.HORIZONTAL, blockTextureName, connectedTextureName);
    }
    private static CTSpriteShiftEntry horizontalKryppers(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.HORIZONTAL_KRYPPERS, blockTextureName, connectedTextureName);
    }
    private static CTSpriteShiftEntry vertical(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.VERTICAL, blockTextureName, connectedTextureName);
    }
    private static CTSpriteShiftEntry rectangle(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.RECTANGLE, blockTextureName, connectedTextureName);
    }
    private static CTSpriteShiftEntry cross(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.CROSS, blockTextureName, connectedTextureName);
    }
    private static CTSpriteShiftEntry roof(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.ROOF, blockTextureName, connectedTextureName);
    }
    private static CTSpriteShiftEntry roofStair(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.ROOF_STAIR, blockTextureName, connectedTextureName);
    }
    private static CTSpriteShiftEntry omni(String blockTextureName, String connectedTextureName) {
        return getCT(AllCTTypes.OMNIDIRECTIONAL, blockTextureName, connectedTextureName);
    }

    private static CTSpriteShiftEntry horizontal(String texture) {
        return getCT(AllCTTypes.HORIZONTAL, texture, texture);
    }
    private static CTSpriteShiftEntry horizontalKryppers(String texture) {
        return getCT(AllCTTypes.HORIZONTAL_KRYPPERS, texture, texture);
    }
    private static CTSpriteShiftEntry vertical(String texture) {
        return getCT(AllCTTypes.VERTICAL, texture, texture);
    }
    private static CTSpriteShiftEntry rectangle(String texture) {
        return getCT(AllCTTypes.RECTANGLE, texture, texture);
    }
    private static CTSpriteShiftEntry cross(String texture) {
        return getCT(AllCTTypes.CROSS, texture, texture);
    }
    private static CTSpriteShiftEntry roof(String texture) {
        return getCT(AllCTTypes.ROOF, texture, texture);
    }
    private static CTSpriteShiftEntry roofStair(String texture) {
        return getCT(AllCTTypes.ROOF_STAIR, texture, texture);
    }
    private static CTSpriteShiftEntry omni(String texture) {
        return getCT(AllCTTypes.OMNIDIRECTIONAL, texture, texture);
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName, String connectedTextureName) {
        return CTSpriteShifter.getCT(type, DnDecor.loc("block/" + blockTextureName),
                DnDecor.loc("block/" + connectedTextureName + "_connected"));
    }

    protected static String getItemName(ItemLike pItemLike) {
        return BuiltInRegistries.ITEM.getKey(pItemLike.asItem()).getPath();
    }

    private static <T extends Block> Function<BlockState, ModelFile> getBlockModel(boolean customItem, DataGenContext<Block, T> c, RegistrateBlockstateProvider p) {
        return $ -> customItem ? AssetLookup.partialBaseModel(c, p) : AssetLookup.standardModel(c, p);
    }

    public static void register() {
    }
}