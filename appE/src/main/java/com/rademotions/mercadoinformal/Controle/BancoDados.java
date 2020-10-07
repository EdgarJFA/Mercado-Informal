package com.rademotions.mercadoinformal.Controle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.Modelo.Desejo;
import com.rademotions.mercadoinformal.Modelo.Mercado;
import com.rademotions.mercadoinformal.Modelo.ModalidadePagamento;
import com.rademotions.mercadoinformal.Modelo.Produto;
import com.rademotions.mercadoinformal.Modelo.Servico;
import com.rademotions.mercadoinformal.Modelo.Vendedor;
import com.rademotions.mercadoinformal.visao.LoginActivity;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Valet on 11/23/2018.
 */

public class BancoDados extends SQLiteOpenHelper {

    public static final String BASEDADOS_NOME = "MercadoBanco";
    private static final int BASEDADOS_VERSAO = 3;
    public static final String DBLOCATION = "/data/data/com.rademotions.mercadoinformal/databases/";
    private SQLiteDatabase mDataBase;
    private Context context;

    public BancoDados(Context context) {
        super(context, BASEDADOS_NOME, null, BASEDADOS_VERSAO);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE cliente (\n" +
                "    _id          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    nome         TEXT,\n" +
                "    apelido      TEXT,\n" +
                "    email        TEXT,\n" +
                "    telefone     INTEGER,\n" +
                "    cidade       TEXT,\n" +
                "    senha        TEXT,\n" +
                "    tipo_usuario TEXT\n" +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE perfil (\n" +
                "    id         INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    foto       BLOB,\n" +
                "    id_usuario INTEGER REFERENCES cliente (_id) ON DELETE CASCADE\n" +
                "                                                ON UPDATE CASCADE\n" +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE vendedor (\n" +
                "    _id               INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    licensa_comercial TEXT,\n" +
                "    documento_tipo    TEXT,\n" +
                "    nr_documento      TEXT,\n" +
                "    sexo              TEXT,\n" +
                "    atividade         TEXT,\n" +
                "    idade             INTEGER,\n" +
                "    id_cliente        INTEGER REFERENCES cliente (_id) ON DELETE CASCADE\n" +
                "                                                       ON UPDATE CASCADE,\n" +
                "    id_mercado        INTEGER REFERENCES mercado (_id) ON DELETE CASCADE\n" +
                "                                                       ON UPDATE CASCADE\n" +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE mercado (\n" +
                "    _id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    nome      TEXT,\n" +
                "    cidade    TEXT,\n" +
                "    provincia TEXT,\n" +
                "    latitude  TEXT,\n" +
                "    longitude TEXT\n" +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE modalidade_pagamento (\n" +
                "    _id         INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    data        TEXT    NOT NULL,\n" +
                "    nome        TEXT    NOT NULL,\n" +
                "    telefone    INTEGER UNIQUE\n" +
                "                        NOT NULL,\n" +
                "    id_vendedor INTEGER REFERENCES vendedor (_id) ON DELETE CASCADE\n" +
                "                                                  ON UPDATE CASCADE\n" +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE transacao (\n" +
                "    _id             INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    data_transacao  TEXT    NOT NULL,\n" +
                "    valor_transacao DOUBLE  NOT NULL,\n" +
                "    id_modalidade   INTEGER REFERENCES modalidade_pagamento (_id) ON DELETE CASCADE\n" +
                "                                                                  ON UPDATE CASCADE\n" +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE lista_desejo (\n" +
                "    _id        INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    id_cliente INTEGER REFERENCES cliente (_id) ON DELETE CASCADE\n" +
                "                                                ON UPDATE CASCADE,\n" +
                "    id_produto INTEGER\n" +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE servico (\n" +
                "    _id             INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    nome            TEXT,\n" +
                "    preco           DOUBLE,\n" +
                "    descricao       TEXT,\n" +
                "    imagem          BLOB,\n" +
                "    data_publicacao TEXT,\n" +
                "    disponibilidade TEXT,\n" +
                "    categoria       TEXT,\n" +
                "    id_vendedor     INTEGER REFERENCES vendedor (_id) ON DELETE CASCADE\n" +
                "                                                      ON UPDATE CASCADE\n" +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE produto (\n" +
                "    _id             INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    nome            TEXT,\n" +
                "    preco           DOUBLE,\n" +
                "    descricao       TEXT,\n" +
                "    imagem          BLOB,\n" +
                "    data_publicacao TEXT,\n" +
                "    disponibilidade TEXT,\n" +
                "    categoria       TEXT,\n" +
                "    quantidade      INTEGER,\n" +
                "    id_vendedor     INTEGER REFERENCES vendedor (_id) ON DELETE CASCADE\n" +
                "                                                      ON UPDATE CASCADE\n" +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE compra_produto (\n" +
                "    _id          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    data_compra  TEXT,\n" +
                "    id_produto   INTEGER REFERENCES produto (_id) ON DELETE CASCADE\n" +
                "                                                  ON UPDATE CASCADE,\n" +
                "    id_cliente   INTEGER REFERENCES cliente (_id) ON DELETE CASCADE\n" +
                "                                                  ON UPDATE CASCADE,\n" +
                "    id_transacao INTEGER REFERENCES transacao (_id) ON DELETE CASCADE\n" +
                "                                                    ON UPDATE CASCADE\n" +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE compra_servico (\n" +
                "    _id          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    data_compra  TEXT,\n" +
                "    id_servico   INTEGER REFERENCES servico (_id) ON DELETE CASCADE\n" +
                "                                                  ON UPDATE CASCADE,\n" +
                "    id_cliente   INTEGER REFERENCES cliente (_id) ON DELETE CASCADE\n" +
                "                                                  ON UPDATE CASCADE,\n" +
                "    id_transacao INTEGER REFERENCES transacao (_id) ON DELETE CASCADE\n" +
                "                                                    ON UPDATE CASCADE\n" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void openDataBase(){
        String caminho = context.getDatabasePath(BASEDADOS_NOME).getPath();

        if(mDataBase != null && mDataBase.isOpen()){
            return;
        }
        mDataBase = SQLiteDatabase.openDatabase(caminho,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDataBase(){
        if(mDataBase != null){
            mDataBase.close();
        }
    }

    public long registrarCliente(Cliente cliente){

        /*String senha;

        try{
            senha = encriptar(String.valueOf(cliente.getTelefone()), cliente.getSenha());
            cliente.setSenha(senha);
        }catch (Exception e){
            e.printStackTrace();
        }*/

            ContentValues values = new ContentValues();
            values.put("_id ",cliente.getId());
            values.put("nome",cliente.getNome());
            values.put("apelido",cliente.getApelido());
            values.put("email",cliente.getEmail());
            values.put("telefone",cliente.getTelefone());
            values.put("cidade",cliente.getCidade());
            values.put("senha",cliente.getSenha());
            values.put("tipo_usuario",cliente.getTipoUsuario());

        openDataBase();
        long returnValue = mDataBase.insert("cliente", null, values);
        closeDataBase();
        return returnValue;
    }

    public long registrarServico(Servico servico, Integer idVendedor, byte[] imagem){

        ContentValues values = new ContentValues();
        values.put("_id ",servico.getId());
        values.put("nome",servico.getNome());
        values.put("preco",servico.getPreco());
        values.put("descricao",servico.getDescricao());
        values.put("imagem",imagem);
        values.put("data_publicacao", servico.getData());
        values.put("disponibilidade",servico.getDisponibilidade());
        values.put("categoria",servico.getCategoria());
        values.put("id_vendedor",idVendedor);

        openDataBase();
        long returnValue = mDataBase.insert("servico", null, values);
        closeDataBase();
        return returnValue;
    }

    public long registrarProduto(Produto produto, Integer idVendedor, byte[] imagem){

        ContentValues values = new ContentValues();
        values.put("_id ",produto.getId());
        values.put("nome",produto.getNome());
        values.put("preco",produto.getPreco());
        values.put("descricao",produto.getDescricao());
        values.put("imagem",imagem);
        values.put("data_publicacao", produto.getDataPublicacao());
        values.put("disponibilidade",produto.getDiasponibilidade());
        values.put("categoria",produto.getCategoria());
        values.put("quantidade",produto.getQuantidade());
        values.put("id_vendedor",idVendedor);

        openDataBase();
        long returnValue = mDataBase.insert("produto", null, values);
        closeDataBase();
        return returnValue;
    }

    public long listarfavoritos(Desejo desejo){

        ContentValues values = new ContentValues();
        values.put("_id ", desejo.getId());
        values.put("id_cliente",desejo.getIdCliente());
        values.put("id_produto",desejo.getIdProduto());

        openDataBase();
        long returnValue = mDataBase.insert("lista_desejo", null, values);
        closeDataBase();
        return returnValue;
    }

    public long registrarModalidade(ModalidadePagamento modalidadePagamento){

        ContentValues values = new ContentValues();
        values.put("_id ",modalidadePagamento.getId());
        values.put("data",modalidadePagamento.getData());
        values.put("nome",modalidadePagamento.getNome());
        values.put("telefone",modalidadePagamento.getTelefone());
        values.put("id_vendedor",modalidadePagamento.getIdVendedor());

        openDataBase();
        long returnValue = mDataBase.insert("modalidade_pagamento", null, values);
        closeDataBase();;
        return returnValue;
    }

    public long registrarMercado(Mercado mercado){

        ContentValues values = new ContentValues();
        values.put("_id ",mercado.getId());
        values.put("nome",mercado.getNome());
        values.put("cidade",mercado.getCidade());
        values.put("provincia",mercado.getProvincia());
        values.put("latitude",mercado.getLatitude());
        values.put("longitude",mercado.getLongitude());

        openDataBase();
        long returnValue = mDataBase.insert("mercado", null, values);
        closeDataBase();
        return returnValue;
    }

    public long registrarVendedor(int idUsuario, Vendedor vendedor){

        ContentValues values = new ContentValues();
        values.put("_id ",vendedor.getId());
        values.put("licensa_comercial",vendedor.getLicensa());
        values.put("documento_tipo",vendedor.getTipoDocumento());
        values.put("nr_documento",vendedor.getN_documento());
        values.put("sexo",vendedor.getSexo());
        values.put("atividade",vendedor.getAtividade());
        values.put("idade",vendedor.getIdade());
        values.put("id_cliente",idUsuario);
        values.put("id_mercado",vendedor.getId_mercado());

        openDataBase();
        long returnValue = mDataBase.insert("vendedor", null, values);
        closeDataBase();
        return returnValue;
    }

    public Integer validaLogin(String email, String senha){
        SQLiteDatabase sql = this.getReadableDatabase();
        Cliente cli = null;

        /*String senhaEnc;

        try{
            senhaEnc = encriptar(email, senha);

            Cursor c = sql.rawQuery("select * FROM cliente where telefone = ? and senha = ? ", new String[]{email,senhaEnc});
            c.moveToFirst();
            if(c.getCount() > 0 ){
                cli = new Cliente(c.getInt(0));
                return cli.getId();
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/

        Cursor c = sql.rawQuery("select * FROM cliente where (telefone = ? and senha = ?) or (email = ? and senha = ?)", new String[]{email,senha,email,senha});
        c.moveToFirst();
        if(c.getCount() > 0 ){
            cli = new Cliente(c.getInt(0));
            return cli.getId();
        }

        return 0;
    }


    public void removerCliente(int id){

       SQLiteDatabase bd = null;

        try{

            bd = this.getReadableDatabase();

            bd.delete("cliente", "_id", new String[]{String.valueOf(id)});
        }catch (Exception e){

        }finally {
            if(bd != null){
                bd.close();
            }
        }
    }


    public Cliente retornarDadosCliente(int id){
        SQLiteDatabase sql = this.getReadableDatabase();
        Cliente cli = null;

        Cursor c = sql.rawQuery("select * FROM cliente where _id = ? ", new String[]{String.valueOf(id)});
        c.moveToFirst();
        if(c.getCount() > 0 ){
            cli = new Cliente(c.getString(1), c.getString(2), c.getString(3), c.getInt(4), c.getString(5),  c.getString(6));
            return cli;
        }
        return cli;
    }

    public Vendedor retornarDadosVendedor(int id){
        SQLiteDatabase sql = this.getReadableDatabase();
        Vendedor vend = null;

        Cursor c = sql.rawQuery("select * FROM vendedor where id_cliente = ? ", new String[]{String.valueOf(id)});
        c.moveToFirst();
        if(c.getCount() > 0 ){
            vend = new Vendedor( c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getInt(6), c.getInt(7), c.getInt(8));
            return vend;
        }
        return vend;
    }

    public Vendedor retornarDadosVendedorCompra(int id){
        SQLiteDatabase sql = this.getReadableDatabase();
        Vendedor vend = null;

        Cursor c = sql.rawQuery("select * FROM vendedor where _id = ? ", new String[]{String.valueOf(id)});
        c.moveToFirst();
        if(c.getCount() > 0 ){
            vend = new Vendedor( c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getInt(6), c.getInt(7), c.getInt(8));
            return vend;
        }
        return vend;
    }

    public Mercado retornarDadosMercado(int id){
        SQLiteDatabase sql = this.getReadableDatabase();
        Mercado merc = null;

        Cursor c = sql.rawQuery("select * FROM mercado where _id = ? ", new String[]{String.valueOf(id)});
        c.moveToFirst();
        if(c.getCount() > 0 ){
            merc = new Mercado(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5));
            return merc;
        }
        return merc;
    }

    public Integer retornarIdVendedor(int id_cliente){
        SQLiteDatabase sql = this.getReadableDatabase();
        Vendedor vend = null;

        Cursor c = sql.rawQuery("select * FROM vendedor where id_cliente = ? ", new String[]{String.valueOf(id_cliente)});
        c.moveToFirst();
        if(c.getCount() > 0 ){
            vend = new Vendedor(c.getInt(0));
            return vend.getId();
        }
        return 0;
    }

    public Integer retornarIdUsuario(Cliente cliente){
        SQLiteDatabase sql = this.getReadableDatabase();
        Cliente cli = null;

        Cursor c = sql.rawQuery("select * FROM cliente ", null);
        c.moveToFirst();
        if(c.getCount() > 0 ){
            cli = new Cliente(c.getInt(0));
            return cli.getId();
        }
        return 0;
    }

    public Integer retornarIdVendedor(Vendedor vendedor){
        SQLiteDatabase sql = this.getReadableDatabase();
        Vendedor vend = null;

        Cursor c = sql.rawQuery("select * FROM vendedor ", null);
        c.moveToFirst();
        if(c.getCount() > 0 ){
            vend = new Vendedor(c.getInt(0));
            return vend.getId();
        }
        return 0;
    }

    public String verificarUsuario(String telefone, String senha){
        SQLiteDatabase sql = this.getReadableDatabase();
        Cliente cli = null;

        /*String senhaEnc;

        try{
            senhaEnc = encriptar(telefone, senha);

            Cursor c = sql.rawQuery("select * FROM cliente where telefone = ? and senha = ? ", new String[]{telefone,senhaEnc});
            c.moveToFirst();
            if(c.getCount() > 0 ){
                cli = new Cliente(c.getString(7));
                return cli.getTipoUsuario();
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/

        Cursor c = sql.rawQuery("select * FROM cliente where (telefone = ? and senha = ?) or (email = ? and senha = ?)", new String[]{telefone,senha,telefone,senha});
        c.moveToFirst();
        if(c.getCount() > 0 ){
            cli = new Cliente(c.getString(7));
            return cli.getTipoUsuario();
        }

        return "usuario nao tem tipo";
    }

    public List<Mercado> retornarTodosMercados(){
        List<Mercado> listaMercado = new ArrayList<>();
        SQLiteDatabase sql = this.getReadableDatabase();

        Cursor c = sql.rawQuery("SELECT * FROM mercado", null);

        if(c.moveToFirst()){
            do{
                Mercado mercado = new Mercado();
                mercado.setId(c.getInt(0));
                mercado.setNome(c.getString(1));
                listaMercado.add(mercado);
            } while (c.moveToNext());
        }

        c.close();
        return listaMercado;
    }

    public Cursor retornarListaDados(String sql){
        SQLiteDatabase baseDados = getReadableDatabase();
        return baseDados.rawQuery(sql,null);
    }

    private SecretKeySpec gerarChave(String senha) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = senha.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES");
        return secretKeySpec;
    }

    private String encriptar(String dado, String senha) throws Exception{
        SecretKeySpec keySpec = gerarChave(senha);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(cipher.ENCRYPT_MODE, keySpec);
        byte[] encVal = cipher.doFinal(dado.getBytes());
        String encrypteddadoValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encrypteddadoValue;
    }

}
