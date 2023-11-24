package model;

import dto.ItemDto;

import java.util.List;

public interface ItemModel {
    public boolean saveItem(ItemDto dto);
    public boolean updateItem(ItemDto dto);
    public boolean deleteItem(String id);
    public List<ItemDto> allItem();
}
