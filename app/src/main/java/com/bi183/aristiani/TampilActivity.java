package com.bi183.aristiani;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

public class TampilActivity extends AppCompatActivity {

    private ImageView imgFilm;
    private TextView tvJudul, tvGenre, tvEpisode, tvTanggalRilis, tvPenulis, tvSinopsis;
    private String linkFilm;
    // private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil);

        imgFilm = findViewById(R.id.iv_film);
        tvJudul = findViewById(R.id.tv_judul);
        tvGenre = findViewById(R.id.tv_genre);
        tvEpisode = findViewById(R.id.tv_episode);
        tvTanggalRilis = findViewById(R.id.tv_tanggal);
        tvPenulis = findViewById(R.id.tv_penulis_skenario);
        tvSinopsis = findViewById(R.id.tv_sinopsis);

        Intent terimaData = getIntent();
        tvJudul.setText(terimaData.getStringExtra("JUDUL"));
        tvGenre.setText("Genre: " + terimaData.getStringExtra("GENRE"));
        tvEpisode.setText("Jumlah episode: " + terimaData.getStringExtra("EPISODE"));
        tvTanggalRilis.setText("Tanggal rilis: " + terimaData.getStringExtra("TANGGAL"));
        tvPenulis.setText("Penulis skenario: " + terimaData.getStringExtra("PENULIS_SKENARIO"));
        tvSinopsis.setText("Sinopsis: " + terimaData.getStringExtra("SINOPSIS"));
        String imgLocation = terimaData.getStringExtra("GAMBAR");

        try {
            File file = new File(imgLocation);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            imgFilm.setImageBitmap(bitmap);
            imgFilm.setContentDescription(imgLocation);
        } catch (FileNotFoundException er) {
            er.printStackTrace();
            Toast.makeText(this, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }

        linkFilm = terimaData.getStringExtra("LINK");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tampil_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.item_bagikan) {
            Intent bagikanBerita = new Intent(Intent.ACTION_SEND);
            bagikanBerita.putExtra(Intent.EXTRA_SUBJECT, tvJudul.getText().toString());
            bagikanBerita.putExtra(Intent.EXTRA_TEXT, linkFilm);
            bagikanBerita.setType("text/plain");
            startActivity(Intent.createChooser(bagikanBerita, "Bagikan berita"));
        }

        return super.onOptionsItemSelected(item);
    }
}
