package task.swenson.eventplanner.domain.use_case.util

import task.swenson.eventplanner.data.pojos.Item

fun getItemList(): List<Item> = listOf(
    Item(
        id = 1,
        selectedFromCategoryId = 1,
        title = "Item 1",
        imageUrl = "www.google.com",
        minBudget = 10,
        maxBudget = 100,
        avgBudget = 50,
        isSelected = false
    ),
    Item(
        id = 2,
        selectedFromCategoryId = 1,
        title = "Item 2",
        imageUrl = "www.google.com",
        minBudget = 10,
        maxBudget = 100,
        avgBudget = 50,
        isSelected = true
    ),
    Item(
        id = 3,
        selectedFromCategoryId = 1,
        title = "Item 3",
        imageUrl = "www.google.com",
        minBudget = 10,
        maxBudget = 100,
        avgBudget = 50,
        isSelected = false
    ),
    Item(
        id = 4,
        selectedFromCategoryId = 2,
        title = "Item 4",
        imageUrl = "www.google.com",
        minBudget = 10,
        maxBudget = 100,
        avgBudget = 50,
        isSelected = true
    ),
    Item(
        id = 5,
        selectedFromCategoryId = 2,
        title = "Item 5",
        imageUrl = "www.google.com",
        minBudget = 10,
        maxBudget = 100,
        avgBudget = 50,
        isSelected = false
    ),
    Item(
        id = 6,
        selectedFromCategoryId = 3,
        title = "Item 6",
        imageUrl = "www.google.com",
        minBudget = 10,
        maxBudget = 100,
        avgBudget = 50,
        isSelected = true
    ),
    Item(
        id = 7,
        selectedFromCategoryId = 3,
        title = "Item 7",
        imageUrl = "www.google.com",
        minBudget = 10,
        maxBudget = 100,
        avgBudget = 50,
        isSelected = true
    ),
    Item(
        id = 8,
        selectedFromCategoryId = 3,
        title = "Item 8",
        imageUrl = "www.google.com",
        minBudget = 10,
        maxBudget = 100,
        avgBudget = 50,
        isSelected = true
    )
)

fun notSelectedItem(): Item = Item(
    id = 1,
    title = "Item",
    imageUrl = "www.google.com",
    minBudget = 10,
    maxBudget = 100,
    avgBudget = 50,
    isSelected = false,
    selectedFromCategoryId = null
)

fun selectedItem(): Item = Item(
    id = 1,
    title = "Item",
    imageUrl = "www.google.com",
    minBudget = 10,
    maxBudget = 100,
    avgBudget = 50,
    isSelected = true,
    selectedFromCategoryId = 2
)

fun itemNoId(): Item =
    Item(
        selectedFromCategoryId = 1,
        title = "Item 1",
        imageUrl = "www.google.com",
        minBudget = 10,
        maxBudget = 100,
        avgBudget = 50,
        isSelected = false
    )

fun itemNoTitle(): Item =
    Item(
        id = 1,
        selectedFromCategoryId = 1,
        imageUrl = "www.google.com",
        minBudget = 10,
        maxBudget = 100,
        avgBudget = 50,
        isSelected = false
    )

fun itemNoMinBudget(): Item =
    Item(
        id = 1,
        selectedFromCategoryId = 1,
        title = "Item 1",
        imageUrl = "www.google.com",
        maxBudget = 100,
        avgBudget = 50,
        isSelected = false
    )

fun itemNoMaxBudget(): Item =
    Item(
        id = 1,
        selectedFromCategoryId = 1,
        title = "Item 1",
        imageUrl = "www.google.com",
        minBudget = 10,
        avgBudget = 50,
        isSelected = false
    )

fun itemNoAvgBudget(): Item =
    Item(
        id = 1,
        selectedFromCategoryId = 1,
        title = "Item 1",
        imageUrl = "www.google.com",
        minBudget = 10,
        maxBudget = 100,
        isSelected = false
    )