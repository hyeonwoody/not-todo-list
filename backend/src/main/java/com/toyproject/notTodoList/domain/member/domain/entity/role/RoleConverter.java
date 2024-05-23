package com.toyproject.notTodoList.domain.member.domain.entity.role;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter (autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Role role) {
        if (role == null){
            return null;
        }
        return role.getValue();
    }

    @Override
    public Role convertToEntityAttribute(Integer code) {
        if (code == null){
            return null;
        }
        return Stream.of(Role.values())
                .filter(c-> c.getValue().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
