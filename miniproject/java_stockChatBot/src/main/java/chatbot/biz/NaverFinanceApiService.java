package chatbot.biz;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NaverFinanceApiService {
    @GET("item/item_right_ajax.naver?type=recent&page=1")
    Call<String> getStockData(
            @Query("code") String code
    );
}
