package com.example.projeto_3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projeto_3.database.ProdutoDAO;
import com.example.projeto_3.modelo.Produto;

public class MainActivity extends AppCompatActivity {

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
        ProdutoDAO produtoDao = new ProdutoDAO(getBaseContext());
        adapterProdutos = new ArrayAdapter<Produto>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                produtoDao.listar());
        listViewProdutos.setAdapter(adapterProdutos);
    }

    private void definirOnClickListenerListView() {
        listViewProdutos.setOnItemClickListener((parent, view, position, id) -> {
            Produto produtoClicado = adapterProdutos.getItem(position);
            Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
            intent.putExtra( "produtoEdicao", produtoClicado);
            startActivity(intent);
        });
    }

    public void onClickNovoProduto (View v) {
        Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
        startActivity(intent);
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
                                ProdutoDAO produtoDAO = new ProdutoDAO(getBaseContext());
                                produtoDAO.ExcluirProduto(produtoClicado);
                                recreate();
                            }
                        })
                        .setNegativeButton("No", null).show();
                return true;

            }
        });
    }

}