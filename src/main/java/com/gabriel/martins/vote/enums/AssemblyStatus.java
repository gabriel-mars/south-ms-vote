package com.gabriel.martins.vote.enums;

import java.util.stream.Stream;

public enum AssemblyStatus {
    APPROVED("APROVADO"),
    WAITING("AGUARDANDO"),
    VOTING("VOTANDO"),
    CLOSED("FECHADO"),
    TIED("EMPATE"),
    REPROVED("REPROVADO");

    private final String value;

    private AssemblyStatus(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AssemblyStatus fromString(final String value) {
        return Stream.of(AssemblyStatus.values())
                .filter(e -> e.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new EnumConstantNotPresentException(AssemblyStatus.class, value));
    }
}
