package com.bi183.aristiani;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.bi183.aristiani.Film;
import com.bi183.aristiani.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "db_film2";
    private final static String TABLE_FILM = "tb_film";
    private final static String KEY_ID_FILM = "ID_Film";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_GENRE = "Genre";
    private final static String KEY_EPISODE = "Episode";
    private final static String KEY_TGL = "Tanggal_Rilis";
    private final static String KEY_PENULIS = "Penulis_Skenario";
    private final static String KEY_SINOPSIS = "Sinopsis";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_LINK = "Link";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());
    private Context context;

    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_BERITA = "CREATE TABLE " + TABLE_FILM
                + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_GENRE + " TEXT, "
                + KEY_EPISODE + " INTEGER, " + KEY_TGL + " DATE, "
                + KEY_PENULIS + " TEXT, " + KEY_SINOPSIS + " TEXT, "
                + KEY_GAMBAR + " TEXT, " + KEY_LINK + " TEXT)";

        db.execSQL(CREATE_TABLE_BERITA);
        inisialisasiFilmAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahFilm (Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_EPISODE, dataFilm.getEpisode());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggalRilis()));
        cv.put(KEY_PENULIS, dataFilm.getPenulisSkenario());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_LINK, dataFilm.getLink());

        db.insert(TABLE_FILM, null, cv);
        db.close();
    }

    public void tambahFilm (Film dataFilm, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_EPISODE, dataFilm.getEpisode());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggalRilis()));
        cv.put(KEY_PENULIS, dataFilm.getPenulisSkenario());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_LINK, dataFilm.getLink());

        db.insert(TABLE_FILM, null, cv);
    }

    public void editFilm (Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_EPISODE, dataFilm.getEpisode());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggalRilis()));
        cv.put(KEY_PENULIS, dataFilm.getPenulisSkenario());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_LINK, dataFilm.getLink());

        db.update(TABLE_FILM, cv, KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdFilm())});
        db.close();
    }

    public void hapusFilm(int idFilm) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idFilm)});
        db.close();
    }

    public ArrayList<Film> getAllFilm() {
        ArrayList<Film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr.moveToFirst()){
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(4));
                } catch (ParseException er){
                    er.printStackTrace();
                }

                Film tempFilm = new Film (
                        csr.getInt(0),
                        csr.getString(1),
                        csr.getString(2),
                        csr.getString(3),
                        tempDate,
                        csr.getString(5),
                        csr.getString(6),
                        csr.getString(7),
                        csr.getString(8)
                );

                dataFilm.add(tempFilm);
            } while(csr.moveToNext());
        }

        return dataFilm;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiFilmAwal(SQLiteDatabase db) {
        int idFilm = 0;
        Date tempDate = new Date();

        // Menambah data berita ke-1
        try {
            tempDate = sdFormat.parse("12/08/2016 00:00");
        } catch (Exception er) {
            er.printStackTrace();
        }

        Film film1 = new Film(
                idFilm,
                "Cinderella and Four Knights",
                "Drama, Family",
                "16",
                tempDate,
                "Baek Myo, Min Ji-Eun, Won Young-Sil",
                "Serial drama “Cinderella and Four Knights” bercerita tentang seorang pelajar SMA memiliki nama yaitu Eun Ha-Won (Park So-Dam).\n" +
                        "Dia berharap untuk menjadi guru yang mendambakan mendiang ibunya (ibunya meninggal dalam kecelakaan mobil).\n" +
                        "Ha-Won memiliki kepribadian yang cerah dan moral yang kuat, tetapi dia tidak bahagia di rumah.\n" +
                        "Dia tidak terikat dari ayahnya, ibu tiri dan saudara tiri perempuan dan juga dianiaya.\n" +
                        "Ha-Won bekerja berbagai pekerjaan paruh waktu untuk membayar biaya kuliahnya yang akan datang.\n" +
                        "Dia tahu dia perlu menghasilkan lebih banyak uang untuk membayar penyimpanan abu ibunya di kuburan. Abu ibunya baru-baru ini dihapus karena tagihan yang belum dibayar.\n" +
                        "Sementara itu, Ji-Woon (Jung Il-Woo), Hyun-Min (Ahn Jae-Hyeon) dan Seo-Woo (Lee Jung-Shin) adalah sepupu manja dan tinggal bersama di rumah besar Sky House.\n" +
                        "Dengan kepribadian mereka yang berbeda – Ji-Woon adalah pria yang tangguh, Hyun-Min adalah playboy dan Seo-Woo adalah musisi – mereka tidak saling menyukai sama sekali.\n" +
                        "Selain itu, Ji-Woon suka Hye-Ji (Son Na-Eun) yang suka Hyun-Min.\n" +
                        "Suatu hari, kakek mereka (Kim Yong-Geon) memerintahkan 3 cucunya untuk menghadiri upacara pernikahan ke-5.\n" +
                        "Hyun-Min tidak ingin pergi, tapi dia mempekerjakan Ha-Won selama 3 jam dan membawanya ke upacara pernikahan kakeknya.\n" +
                        "Rencananya adalah untuk mengacaukan upacara pernikahan kakeknya dengan penampilan mengejutkan Ha-Won.\n" +
                        "Di sana, sang kakek menyaksikan sikap tanpa ampun Ha-Won terhadap Hyun-Min dan memutuskan untuk mempekerjakannya sebagai kepala pelayan di Sky House. Dia berharap dia bisa mengubah cara cucunya.",
                storeImageFile(R.drawable.film3),
                "https://dramaqu.live/nonton-the-king-eternal-monarch-2020-subtitle-indonesia/"
        );

        tambahFilm(film1, db);
        idFilm++;

        // Menambah data berita ke-3
        try {
            tempDate = sdFormat.parse("24/02/2017 00:00");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film2 = new Film(
                idFilm,
                "Strong Woman Do Bong-Soon",
                "Romance, Supernatural, Investigation, Comedy",
                "16",
                tempDate,
                "Baek Mi Kyeong",
                "Do Bong Soon adalah wanita mungil, pengangguran yang jujur dan baik. Dia tampak kecil dan manis di luar, tetapi dia sebenarnya sangat, sangat kuat. Di keluarganya, selama beberapa generasi para wanita telah diberikan kekuatan Hercules untuk digunakan demi kebaikan yang lebih besar. Namun jika disalahgunakan, kekuatan mereka akan diambil.\n" +
                        "\n" +
                        "Sementara berdiri untuk dirinya sendiri setelah anggota geng menggertaknya, dia menemukan dirinya didekati oleh Ahn Min Hyuk, CEO Ainsoft yang tampan dan agak kekanak-kanakan, sebuah perusahaan game. Ahn Min Hyuk, yang sangat tidak percaya kepada polisi, menyaksikan seluruh pertukaran kekuatan manusia super Bong Soon, dan sekarang ingin mempekerjakannya sebagai pengawal setelah diancam oleh musuh yang tidak dikenal. Min Hyuk jatuh cinta dengan Bong Soon yang sangat kuat pada pandangan pertama, tapi ada tangkapan.\n" +
                        "\n" +
                        "Bong Soon memiliki mata untuk orang lain; petugas polisi dan teman masa kecilnya, In Guk Doo, yang dikenalnya sejak sekolah menengah. Ketika kekacauan terjadi setelah serangkaian penculikan di kampung halaman Do Bong Soon di Dobong-dong, Dobong-gu, Bong Soon harus memutuskan apakah akan menggunakan kekuatannya dan melawan kejahatan, atau memainkannya dengan aman dan menjaga kekuatannya tersembunyi dari dunia. Dikombinasikan dengan cinta segitiga yang dia hadapi antara In Guk Doo dan Ahn Min Hyuk, serta harus menjaga Min Hyuk aman, kehidupan Bong Soon dilemparkan ke dalam kekacauan. Bisakah dia menggunakan kekuatannya untuk kebaikan yang lebih besar, atau akankah pada akhirnya terbukti terlalu banyak?",
                storeImageFile(R.drawable.film1),
                "https://dramaqu.live/nonton-cinderella-and-four-knights/"
        );

        tambahFilm(film2, db);
        idFilm++;

        // Menambah data berita ke-3
        try {
            tempDate = sdFormat.parse("17/04/2020 00:00");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film3 = new Film(
                idFilm,
                "The King: Eternal Monarch",
                "Romance, History, Fantasy",
                "24",
                tempDate,
                "Kim Eun Sook",
                "Cerita The King: Eternal Monarch dibuka dengan adegan pembunuhan Raja Korea, Lee Ho (Kwon Yool). Sang putra mahkota, Lee Gon (Jeong Hyun Jun) yang berusia 8 tahun menyaksikan kejadian tersebut. \n" +
                        "Lee Lim berusaha membunuh putra mahkota namun digagalkan oleh kedatangan seseorang yang misterius. Sebelum diselamatkan, putra mahkota memegang sebuah tanda pengenal bertuliskan ‘Jong Tae Eul’. \n" +
                        "Lee Lim berhasil kabur ke dunia paralel. Ia menemukan Republik Korea, sebuah negara yang menerapkan sistem berbeda dengan tempat asalnya. \n" +
                        "Di sana ia menemukan orang-orang yang ia kenali namun dengan kehidupan yang berbanding terbalik. Lalu penonton dapat menyimpulkan bahwa setiap karakter masing-masing memiliki ‘duplikat’. \n" +
                        "Sedari awal, Lee Lim berkeinginan untuk ‘menggenggam dunia’ di tangannya. Keinginan tersebut hampir sama seperti menjadi dewa atau penguasa atas unsur-unsur kehidupan manusia. \n" +
                        "Pada sinopsis The King: Eternal Monarch episode-episode berikutnya, Lee Lim melakukan percobaan dengan menukar kehidupan manusia yang sengsara dengan cara membunuh duplikatnya yang memiliki kehidupan lebih baik di dunia paralel.",
                storeImageFile(R.drawable.film2),
                "https://dramaqu.live/nonton-the-ki" +
                        "ng-eternal-monarch-2020-subtitle-indonesia/"
        );

        tambahFilm(film3, db);
        idFilm++;
    }
}
