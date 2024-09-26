package chatbot;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class StockMain{
    public static final Map<String, String> stockNameToCode = new HashMap<>();
    static {
        stockNameToCode.put("삼성전자", "005930");
        stockNameToCode.put("SK하이닉스", "000660");
        stockNameToCode.put("LG에너지솔루션", "373220");
        stockNameToCode.put("현대차", "005380");
        stockNameToCode.put("삼성바이오로직스", "207940");

        stockNameToCode.put("삼성전자우", "005935");
        stockNameToCode.put("기아", "000270");
        stockNameToCode.put("셀트리온", "068270");
        stockNameToCode.put("KB금융", "105560");
        stockNameToCode.put("POSCO홀딩스", "005490");

        stockNameToCode.put("NAVER", "035420");
        stockNameToCode.put("LG화학", "051910");
        stockNameToCode.put("신한지주", "055550");
        stockNameToCode.put("삼성물산", "028260");
        stockNameToCode.put("삼성SDI", "006400");

//        stockNameToCode.put("현대모비스", "012330");
//        stockNameToCode.put("포스코퓨처엠", "003670");
//        stockNameToCode.put("하나금융지주", "086790");
//        stockNameToCode.put("카카오", "035720");
//        stockNameToCode.put("삼성생명", "032830");
//
//        stockNameToCode.put("삼성화재", "000810");
//        stockNameToCode.put("LG전자", "066570");
//        stockNameToCode.put("한미반도체", "042700");
//        stockNameToCode.put("메리츠금융지주", "138040");
//        stockNameToCode.put("HMM", "011200");
//
//        stockNameToCode.put("HD현대중공업", "329180");
//        stockNameToCode.put("크래프톤", "259960");
//        stockNameToCode.put("SK스퀘어", "402340");
//        stockNameToCode.put("LG", "003550");
//        stockNameToCode.put("두산에너빌리티", "034020");
    }
    public static void main(String[] args) {
        stockList = new String[15];
        // entrySet을 이용한 일반적인 반복문

        int index =0;
        for (Map.Entry<String, String> entry : stockNameToCode.entrySet()) {
            stockList[index] = entry.getKey() ;
            index++;
        }

        for(int i=0;i<15;i++){
            System.out.println(stockList[i]);
        }
    }

    static String result;
    public static String[] stockList;
    public static void findStockName(String stockName){
        // Naver 금융 API의 기본 URL 설정
        String baseUrl = "https://finance.naver.com/";

        // OkHttpClient를 사용하여 쿠키를 추가
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder().header("Cookie",
                                "NNB=GLQYO4ZO7ZSGM; NAC=wpd1BMAOuEfaA; ASID=afd35ed20000018ffaba0e5500000059; NFS=2; _ga=GA1.2.1399269910.1719536390; m_loc=15c3281ca1405d9c88c9927414293966c1f48bfe12e80878fa8f3d9a4cd195fe; NV_WETR_LAST_ACCESS_RGN_M=\"MDk2ODA2NTU=\"; NV_WETR_LOCATION_RGN_M=\"MDk2ODA2NTU=\"; nid_tct=xORzb5IqWXaeN9l2; NACT=1; summary_item_type=recent; field_list=8|04000000:25|04000018; page_uid=iofhKdqo1SossiV8lDNssssstuo-321172; nid_inf=681954406; NID_AUT=eAmDO75h9T75waQnabNmzL6p0DRsq2UKXAO7d3uR7Y/axS5rQLCX5ym8l8jGd/OM; NID_JKL=IQ+F7oy7mA9RzpGTv9fbBvZj1V8suI4uVOfk/raqBp4=; NID_SES=AAABsBdh3+2RXH9uYn0A1GIPpVO8oXITAzmjeJU3IMLFvvsAelRD282viql55vWH1FaBu8y4NWAHSFh1ik78raP6U9NJrnH+1HSpIhT9mhu9JHJCFW3ZLZAuXhC7jIS4WqqZOl2wdOJnnQxWgZCFFLjqgIGTQz8cc7uO/Cfa/7lTvMrtZQTQ8TOdqlBhYATj0hVQf2tmj+pzgJPcFqf0rTnMFInjgAdgCfZp1i4gk3lC5b5Faw/uYfCmyoAuN+NkBC8A2yq6F3eEYi1mc7HUXxcwITYr5ff5Qro4NPmjQShAJiu/rm8AQ4b2fDExYU46InQ/YMU035FYVNfzJEZzG83lFrd9O+eausAwfsSfqmMerYPyqLMyeUWlhWd9/9XS7O5m9ku176pOsttF3K/DbY/UZ5bAjwd4f/gnjEwVTYxhtRePyX/3pEhT71Q0dYSqmFNad8VpaEd1CBQTKoIssE2/BMzfPNAV4wn3uAaVrJBpj1H4uAD2qh29EwnBN+5nwST2rJJHtsrkHTedtsp+8ZbV5oqMOnclVzOyQvyU5Rv08dRwxW4RvAWa5lXhq3cn3pbJMg==; BUC=24r0I9XN77KrvNC70O7o7_GXDwjsmtp_nBrpSGXiMUk=; JSESSIONID=0C1760267F32D5021A1DA07FFAB08222; naver_stock_codeList=005930%7C006400%7C051910%7C028260%7C035420%7C055550%7C005490%7C105560%7C068270%7C000270%7C005935%7C207940%7C005380%7C373220%7C000660%7C")
                        .method(original.method(), original.body()).build();
                return chain.proceed(request);
            }
        }).build();

        // Retrofit 인스턴스 생성
        Retrofit retrofit = new Retrofit.Builder().client(client).addConverterFactory(ScalarsConverterFactory.create()) // ScalarsConverterFactory
                // 사용
                .baseUrl(baseUrl).build();

        // NaverFinanceApiService 인터페이스의 구현체를 생성
        NaverFinanceApiService service = retrofit.create(NaverFinanceApiService.class);

        try {
            // 코드를 하드코딩하지 않고 사용자로부터 입력받음
            System.out.println("시가총액 TOP15 리스트중에 궁금하신 종목 이름을 입력해주세요.");

            // 종목 이름을 코드로 변환
            String stockCode = stockNameToCode.get(stockName);
            if (stockCode == null) {
                System.out.println("해당하는 종목 이름이 없습니다.");
                return;
            }

            // Naver API를 호출하여 검색 결과를 가져옴
            String response = service.getStockData("recent", stockCode, 1).execute().body();

            // 최종 호출한 URL 출력
//            String apiUrl = baseUrl + "item/item_right_ajax.naver?type=recent&code=" + stockCode + "&page=1";
//            System.out.println("API 호출 URL: " + apiUrl);

            // Gson을 사용하여 JSON 데이터를 파싱
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
//            System.out.println("JSON 데이터: " + jsonObject);

            // item_list에서 입력한 itemcode의 change_val 찾기
            if (jsonObject.has("item_list")) {
                JsonArray itemList = jsonObject.getAsJsonArray("item_list");
                boolean found = false;
                for (JsonElement item : itemList) {
                    JsonObject itemObj = item.getAsJsonObject();
                    if (itemObj.get("itemcode").getAsString().equals(stockCode)) {
                        String nowPrice = itemObj.get("now_val").getAsString();
                        String itemName = itemObj.get("itemname").getAsString();
                        result= "종목이름은 " + itemName + ", 해당 주식의 가격은 " + nowPrice + "원이고, 코드번호는 " + stockCode + "입니다.";
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("itemcode " + stockCode + " not found in item_list.");
                }
            } else {
                System.out.println("item_list not found in response.");
            }

        } catch (IOException e) {
            // 예외 발생 시 스택 트레이스를 출력
            e.printStackTrace();
        }
    }


    public interface NaverFinanceApiService {
        @GET("item/item_right_ajax.naver")
        Call<String> getStockData(@Query("type") String type, @Query("code") String code, @Query("page") int page);
    }
}
