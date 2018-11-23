package com.welson.reader.retrofit;

import com.google.gson.GsonBuilder;
import com.welson.reader.constant.Constants;
import com.welson.reader.entity.BookMixAToc;
import com.welson.reader.entity.BookSource;
import com.welson.reader.entity.ChapterRead;
import com.welson.reader.entity.RankingList;
import com.welson.reader.entity.Rankings;
import com.welson.reader.entity.Recommend;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private OkHttpClient client = new OkHttpClient();
    private GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    private Retrofit retrofit = null;
    private ApiService apiService;
    private static RetrofitHelper instance = null;

    private RetrofitHelper(){
        init();
    }

    public static RetrofitHelper getInstance() {
        if (instance == null){
            instance = new RetrofitHelper();
        }
        return instance;
    }

    private void init(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public Observable<Recommend> getRecommend(String gender){
        return apiService.getRecommend(gender);
    }

    public Observable<List<BookSource>> getABookSource(String view,String book){
        return apiService.getABookSource(view,book);
    }

    public Observable<List<BookSource>> getBBookSource(String view,String book){
        return apiService.getBBookSource(view, book);
    }

    public Observable<BookMixAToc> getBookMixAToc(String bookId,String view){
        return apiService.getBookMixAToc(bookId, view);
    }

    public Observable<BookMixAToc> getBookRead(String bookId){
        return apiService.getBookRead(bookId);
    }

    public Observable<BookMixAToc> getBookBToc(String bookId,String view){
        return apiService.getBookBToc(bookId, view);
    }

    public Observable<ChapterRead> getChapterRead(String url){
        return apiService.getChapterRead(url);
    }

    public Observable<RankingList> getRankings(){
        return apiService.getRankings();
    }

    public Observable<Rankings> getRanking(String id){
        return apiService.getRanking(id);
    }
}
