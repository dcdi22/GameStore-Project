package com.company.GameStore.dao;

import com.company.GameStore.dto.TShirt;

import java.util.List;

public interface TShirtDao {

    TShirt getShirt(int tshirtId);

    List<TShirt> getAllShirts();

    List<TShirt> getShirtByColor(String color);

    List<TShirt> getShirtBySize(String size);

    TShirt addShirt(TShirt tShirt);

    void updateShirt(TShirt tShirt);

    void deleteShirt(int tshirtId);

}
