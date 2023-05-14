package com.aquariux.tradingservice.enums;

public enum CoinPairEnum {
    ETHUSDT("ETHUSDT"), BTCUSDT("BTCUSDT");

    private String value;

    CoinPairEnum(String value) {
        this.value = value;
    }

    public static CoinPairEnum fromString(String value) {
        for (CoinPairEnum coinPairEnum : CoinPairEnum.values()) {
            if (coinPairEnum.value.equalsIgnoreCase(value)) {
                return coinPairEnum;
            }
        }
        throw new IllegalArgumentException(String.format("Not supported coin pair %s", value));
    }
}
