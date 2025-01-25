package com.bloodybank.bloodybank.dto.converter;

import com.bloodybank.bloodybank.entity.User;

public class UserConverter {

    public static String convert(User user) {
        String s = String.format("%s %s %s %s %d %d",
                user.getName(), user.getSurname(), user.getEmail(), user.getBlood_type().getName(),
                user.getAge(), user.getCount());
        return s;
    }
}
