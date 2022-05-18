package com.gabriel.martins.vote.enums;

import java.util.stream.Stream;

public enum VoteType {
    SIM("SIM"),
    NAO("NAO");

    private final String value;

    private VoteType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static VoteType fromString(final String value) {
        return Stream.of(VoteType.values())
                .filter(e -> e.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new EnumConstantNotPresentException(VoteType.class, value));
    }
}
