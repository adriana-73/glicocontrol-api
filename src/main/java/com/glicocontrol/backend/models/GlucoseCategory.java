package com.glicocontrol.backend.models;

public enum GlucoseCategory {
    HIPOGLICEMIA("Hipoglicemia (Baixa)", "#ff9800"), // Laranja/Amarelo
    NORMAL("Normal", "#4caf50"),                   // Verde
    HIPERGLICEMIA("Hiperglicemia (Alta)", "#f44336"); // Vermelho

    private final String descricao;
    private final String corHex;

    GlucoseCategory(String descricao, String corHex) {
        this.descricao = descricao;
        this.corHex = corHex;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCorHex() {
        return corHex;
    }
}
