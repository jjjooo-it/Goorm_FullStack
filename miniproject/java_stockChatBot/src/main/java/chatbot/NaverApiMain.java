package chatbot;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import chatbot.biz.NaverApiService;
import chatbot.model.SearchResult;
import chatbot.model.SearchResult.Item;

public class NaverApiMain {

    //전역 변수로 배열을 두어 Main 클래스에서 가져갈 수 있도록
    public static String[] newsTitle;
    public static String[] newsLink;

    public static String[] cafeTitle;
    public static String[] cafeLink;

    public static void main(String[] args) {
        String baseUrl = "https://openapi.naver.com/";
        String clientID = "G1dEFFuFyUNPIiBT57O8";
        String clientSecret = "39F7CU0dyH";

        // Retrofit 인스턴스 생성
        // GsonConverterFactory를 사용하여 JSON 데이터를 객체로 변환
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();

        // NaverApiService 인터페이스의 구현체를 생성
        NaverApiService service = retrofit.create(NaverApiService.class);
        SearchResult searchResult_news = null;
        SearchResult searchResult_cafe = null;

        // 검색 키워드 설정
        String kwd = "주식";
        try {
            // Naver API를 호출하여 검색 결과를 가져옴
            // execute() 메서드를 사용하여 동기적으로 API 호출
            searchResult_news = service.search(clientID, clientSecret, "news", kwd, 3, 1).execute().body();
            searchResult_cafe = service.search(clientID, clientSecret, "cafearticle", kwd, 3, 1).execute().body();
        } catch (IOException e) {
            // 예외 발생 시 스택 트레이스를 출력
            e.printStackTrace();
        }
        if (searchResult_news != null ||searchResult_cafe!=null) {
            result_news(searchResult_news);
            result_news(searchResult_news);
            result_cafe(searchResult_cafe);
        } else {
            System.out.println("찾기 실패");
        }
    }

    //뉴스 결과 값을 배열에 저장
    public static void result_news(SearchResult searchResult_news){
        newsTitle = new String[3];
        newsLink = new String[3];

        int index= 0;
        // 검색 결과의 각 아이템에 대해
        for (Item item : searchResult_news.items) {
            // 제목에서 HTML 태그(<b>, </b>) 제거
            newsTitle[index] = item.getTitle()
                    .replace("<b>", "")
                    .replace("</b>", "");
            newsLink[index]= item.link;

            index++;
        }
    }

    //카페 결과 값을 배열에 저장
    public static void result_cafe(SearchResult searchResult_cafe){
        cafeTitle = new String[3];
        cafeLink = new String[3];

        int index= 0;
        // 검색 결과의 각 아이템에 대해
        for (Item item : searchResult_cafe.items) {
            // 제목에서 HTML 태그(<b>, </b>) 제거
            cafeTitle[index] = item.getTitle()
                    .replace("<b>", "")
                    .replace("</b>", "");
            cafeLink[index]= item.link;

            index++;
        }
    }
}
