package com.mini.mini_2.exception;

import static com.mini.mini_2.exception.ErrorCode.*;

public class FoodNotFoundException extends NotFoundException {
    public FoodNotFoundException(Integer foodId) {
        
        super(FOOD_NOT_FOUND_EXCEPTION.getMessage().formatted(foodId));
    }

    @Override
    public String getErrorCode() {
        
        return FOOD_NOT_FOUND_EXCEPTION.name();
    }
}
