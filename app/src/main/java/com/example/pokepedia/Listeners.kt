package com.example.pokepedia


interface OnItemClickListener<Item> {
    fun onItemClick(item: Item)
}

interface OnRetryClickListener {
    fun onButtonClick()
}