package com.example.rafael.appandroidlistadeconvidados;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.rafael.appandroidlistadeconvidados.dominio.entidades.Cliente;
import com.example.rafael.appandroidlistadeconvidados.dominio.repositorio.ClienteDao;

import java.util.List;

/**
 * Created by Rafael on 29/10/2017.
 */

public class AdapterRecyclerView extends RecyclerView.Adapter {

    private List<Cliente> clientes;
    private Context context;
    private int c;


    public AdapterRecyclerView(List<Cliente> clientes, Context context, int c){
        this.clientes = clientes;
        this.context  = context;
        this.c = c;
    }

    public void updateList(){
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.listapersonalizada, parent, false);

        ViewHolderV holder = new ViewHolderV(view);

        return holder;
    }


    ClienteDao clienteDao;
    ViewHolderV holder;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        holder = (ViewHolderV) viewHolder;
        final Cliente cliente = clientes.get(position);
        clienteDao = new ClienteDao(context);

        if(clientes.get(position).compareceuFesta.equals("1")){
            holder.icon.setImageResource(R.drawable.icons_selecionado);
        }
        else {
            holder.icon.setImageResource(R.drawable.icons_excluir);
        }

        holder.nome.setText(cliente.nomeSobrenome);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClickListener(View view, int position, boolean inLongClick) {
                if(inLongClick){

                        clienteDao = new ClienteDao(context);

                        String m = clientes.get(position).mesa;

                        Intent s = new Intent(context,Main2Activity.class);
                        s.putExtra("mesa",m);
                        context.startActivity(s);

                    updateList();



                }
                else {

                    teste(position);
                }
            }
        });
    }

    TextView txNome;
    TextView txMesa;
    TextView txConvidado;
    TextView txCompareceu;


    private void teste(final int position){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.caixadialogo,null);

        txMesa       = view.findViewById(R.id.txtMesa);
        txConvidado  = view.findViewById(R.id.texConvidado);
        txCompareceu = view.findViewById(R.id.txtCompareceu);

        txMesa.setText(clientes.get(position).mesa);
        txConvidado.setText(clientes.get(position).convidado);
        txCompareceu.setText(clientes.get(position).compareceuFesta);

        builder.setView(view)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        clienteDao.update(clientes.get(position).condigo);

                        TestClass testClass = TestClass.getInstance();
                        testClass.muda(clientes.get(position).condigo);

                        //limpa o search
                        MainActivity.editTex.getText().clear();

                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }
}
