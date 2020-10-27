package com.example.projeto_3;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projeto_3.database.ProdutoDAO;
import com.example.projeto_3.modelo.Produto;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_NOVO_PRODUTO = 12;
    private final int RESULT_CODE_NOVO_PRODUTO = 10;
    private final int REQUEST_CODE_EDITAR_PRODUTO = 2;
    private final int RESULT_CODE_PRODUTO_EDITADO = 11;

    private ListView listViewProdutos;
    private ArrayAdapter<Produto> adapterProdutos;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Produtos");
        listViewProdutos = findViewById(R.id.listView_produtos);

        definirOnClickListenerListView();
        deletaOnLongClickListener();

    }

    @Override
    protected void onResume () {
        super.onResume();
        ProdutoDAO produtoDAO = new ProdutoDAO(getBaseContext());
        adapterProdutos = new ArrayAdapter<Produto>( MainActivity.this,
                android.R.layout.simple_list_item_1,
                produtoDAO.listar());
        listViewProdutos.setAdapter(adapterProdutos);
    }

    private void definirOnClickListenerListView() {
        listViewProdutos.setOnItemClickListener((parent, view, position, id) ->  {
            Produto produtoclicado = adapterProdutos.getItem(position);
            Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
            intent.putExtra( "produtoEdicao", produtoclicado);
            startActivityForResult(intent, REQUEST_CODE_EDITAR_PRODUTO);
        });
    }

    private void deletaOnLongClickListener(){
        listViewProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produtoClicado = adapterProdutos.getItem(position);

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setMessage("Deseja excluir o produto ?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapterProdutos.remove(produtoClicado);
                                adapterProdutos.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "Deletado", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("No", null).show();
                return true;

            }
        });
    }

    public void onClickProdutonovo (View v) {
        Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_EDITAR_PRODUTO && resultCode == RESULT_CODE_PRODUTO_EDITADO) {
            Produto produtoEditado = (Produto) data.getExtras().getSerializable("produtoEditado");
            for (int i = 0; i < adapterProdutos.getCount(); i++) {
                Produto produto = adapterProdutos.getItem(i);
                if (produto.getId() == produtoEditado.getId()) {
                    adapterProdutos.remove(produto);
                    adapterProdutos.insert(produtoEditado, i);
                    break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}