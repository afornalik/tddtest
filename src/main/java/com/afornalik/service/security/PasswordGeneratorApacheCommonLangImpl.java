package com.afornalik.service.security;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordGeneratorApacheCommonLangImpl implements PasswordGenerator{

    @Override
    public String generatePassword() {
        return RandomStringUtils.randomAlphabetic(10);
    }
}
