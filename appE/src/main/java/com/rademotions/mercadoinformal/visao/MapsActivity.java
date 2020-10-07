package com.rademotions.mercadoinformal.visao;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.Modelo.Mercado;
import com.rademotions.mercadoinformal.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker; //acrescentado

    private Cliente cliente;
    private Mercado[] mercados; //acrescentado
    private ArrayList<Mercado> listaMercado;
    private BancoDados mDataBase;
    private Bundle bundle1;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mDataBase = new BancoDados(getApplicationContext());
        listaMercado = new ArrayList<>();


        Cursor cursor = mDataBase.retornarListaDados("select * FROM mercado"); //acrescentado
        mercados = new Mercado[cursor.getCount()];
        listaMercado.clear();

        int i=0;

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            String nome = cursor.getString(1);
            String cidade = cursor.getString(2);
            String provincia = cursor.getString(3);
            String latitude = cursor.getString(4);
            String longitude = cursor.getString(5);
            //listaMercado.add(new Mercado(id,nome,cidade, provincia,latitude,longitude));
            mercados[i] = new Mercado(id,nome,cidade, provincia,latitude,longitude);
            cursor.moveToNext();
            i++;
        } //acrescentado

        cursor.close();

        cliente = new Cliente();
        Bundle bundle = getIntent().getExtras();

        cliente.setId(bundle.getInt("id_cliente"));
        bundle1 = new Bundle();
        bundle1.putInt("identificador", cliente.getId());
        cliente = mDataBase.retornarDadosCliente(cliente.getId());

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //verifica se o provedor de internet está activo
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    if(marker!=null)
                        marker.remove(); //acrescentado

                    //retorna a latitude
                    double latitude = location.getLatitude();
                    //retorna a longitude
                    double logitude = location.getLongitude();
                    //Instanciando a classe LatLong
                    LatLng latLng = new LatLng(latitude, logitude);
                    //Instanciando a classe Geocoder
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> endereco = geocoder.getFromLocation(latitude,logitude, 1);
                        String info = cliente.getNome() + "\n"  ; //acrescentado
                        info += endereco.get(0).getLocality() + " , ";
                        info += endereco.get(0).getCountryName();
                        //mMap.addMarker(new MarkerOptions().position(latLng).title(info));
                        marker =  mMap.addMarker(new MarkerOptions().position(latLng).title(info)); //acrescentado do codigo acima
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(14.5f)); //acrescentado
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        } else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    if(marker!=null)
                        marker.remove(); //acrescentado

                    //retorna a latitude
                    double latitude = location.getLatitude();
                    //retorna a longitude
                    double logitude = location.getLongitude();
                    //Instanciando a classe LatLong
                    LatLng latLng = new LatLng(latitude, logitude);
                    //Instanciando a classe Geocoder
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> endereco = geocoder.getFromLocation(latitude,logitude, 1);
                        //String info = cliente.getNome() + " , Sua Posiçao"  ; //acrescentado
                        //info += endereco.get(0).getLocality() + " , ";
                        //info += endereco.get(0).getCountryName();
                        //mMap.addMarker(new MarkerOptions().position(latLng).title(info));
                        marker =  mMap.addMarker(new MarkerOptions().position(latLng).title("Sua Posiçao")); //acrescentado do codigo acima
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(14.5f)); //acrescentado
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

       /* for(int i = 0; i < listaMercado.size(); i++){
            mercados[i] = listaMercado.get(i);
        } //acrescentado*/

        for(int m = 0; m < mercados.length; m++){
            LatLng posicaoMercado = new LatLng(Double.parseDouble(mercados[m].getLatitude()), Double.parseDouble(mercados[m].getLongitude()));
            mMap.addMarker(new MarkerOptions().position(posicaoMercado).title("Mercado " +mercados[m].getNome()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        } //acrescentado

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onBackPressed() {

        //cliente = mDataBase.retornarDadosCliente(cliente.getId());
        String nivelUsuario = mDataBase.verificarUsuario(String.valueOf(cliente.getTelefone()),cliente.getSenha());

        if(nivelUsuario.equals("A3")){

            Intent intent = new Intent(MapsActivity.this, PainelAdminActivity.class);
            intent.putExtras(bundle1);
            startActivity(intent);

        } else if(nivelUsuario.equals("A1")){

            Intent intent = new Intent(MapsActivity.this, PainelClienteActivity.class);
            intent.putExtras(bundle1);
            startActivity(intent);

        } else{
            Toast.makeText(MapsActivity.this, "Nivel de usuário não encontrado", Toast.LENGTH_SHORT).show();
        }
    }
}
