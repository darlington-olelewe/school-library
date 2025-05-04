package edu.schoollibrary.service;

public interface InventoryDifference {
  int getBookDifference(int assignedToLibrary, Integer leftInventory);
}
