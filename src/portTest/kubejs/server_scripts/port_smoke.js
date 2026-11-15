ServerEvents.recipes(event => {
    event.recipes.expandeddelight.juicing(
        ['minecraft:apple', 'minecraft:carrot'],
        'expandeddelight:apple_juice'
    ).juicingtime(40).id('expandeddelight:port_smoke_kubejs');
});
