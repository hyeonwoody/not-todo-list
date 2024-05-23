package com.toyproject.notTodoList.domain.member.domain.entity.authprovider;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AuthProviderConverter implements AttributeConverter<AuthProvider, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AuthProvider authProvider) {
        if (authProvider == null){
            return null;
        }
        return authProvider.ordinal();
    }

    @Override
    public AuthProvider convertToEntityAttribute(Integer code) {
        if (code == null){
            return null;
        }
        return AuthProvider.values()[code];
    }
}
