package com.example.projeto_3.database.contract;

import com.example.projeto_3.database.entity.ProdutoEntity;

public final class ProdutoContract {

    private ProdutoContract() {}

    public static final String criarTabela() {
        return " CREATE TABLE " + ProdutoEntity.TABLE_NAME + "(" + ProdutoEntity._ID + " INTEGER PRIMARY KEY," +
                ProdutoEntity.COLUMNS_NAME_NOME +"TEXT," +ProdutoEntity.COLUMNS_NAME_VALOR + "REAL)";

    }

    public static final String removerTabela() {
        return "DROP TABLE IF EXISTS" + ProdutoEntity.TABLE_NAME;
    }
}

