package com.gabriel.martins.vote.enums;

import java.util.stream.Stream;

public enum CpfStatus {
    ABLE_TO_VOTE("ABLE_TO_VOTE"),
    UNABLE_TO_VOTE("UNABLE_TO_VOTE");

    private final String value;

    private CpfStatus(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CpfStatus fromString(final String value) {
        return Stream.of(CpfStatus.values())
                .filter(e -> e.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new EnumConstantNotPresentException(CpfStatus.class, value));
    }
}
