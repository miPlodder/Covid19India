package covid19.example.covid19.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Covid19Client {

    public static final String BASE_URL = "https://api.covid19india.org";
    public static Retrofit retrofit = null;

    public static Retrofit getCovid19Client() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
