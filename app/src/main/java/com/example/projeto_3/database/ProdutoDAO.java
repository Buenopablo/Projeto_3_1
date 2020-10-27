package com.example.projeto_3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.projeto_3.database.entity.ProdutoEntity;
import com.example.projeto_3.modelo.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private String SQL_LISTAR_TODOS = " SELECT * FROM " + ProdutoEntity.TABLE_NAME;
    private DBGateway dbGateway;

    public ProdutoDAO(Context context) {
        dbGateway = DBGateway.getInstance(context);
    }

    public boolean salvar(Produto produto) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProdutoEntity.COLUMNS_NAME_NOME, produto.getNome());
        contentValues.put(ProdutoEntity.COLUMNS_NAME_VALOR, produto.getValor());
        long id = dbGateway.getDatabase().insert(ProdutoEntity.TABLE_NAME, null, contentValues);
        return id > 0;
    }

    public List<Produto> listar () {
        List<Produto> produtos = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_LISTAR_TODOS, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ProdutoEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(ProdutoEntity.COLUMNS_NAME_NOME));
            Float valor = cursor.getFloat(cursor.getColumnIndex(ProdutoEntity.COLUMNS_NAME_VALOR));
            produtos.add(new Produto(id, nome, valor));
        }
        cursor.close();
        return produtos;
    }

}
