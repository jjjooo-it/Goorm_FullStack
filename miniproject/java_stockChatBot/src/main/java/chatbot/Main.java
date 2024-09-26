package chatbot;

import chatbot.biz.ChatbotApiService;
import chatbot.model.UpdateResponse;
import chatbot.model.UpdateResponse.Update;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Main {
	public static <bool> void main(String[] args) {
		final String TOKEN = "7494347572:AAFY3cII9ouuaANP7pZFttTqRJ0hZ34D7bs";

		// Retrofit 인스턴스를 생성하여 텔레그램 API와 통신 설정
		Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.telegram.org/bot" + TOKEN + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

		// ChatbotApiService 인터페이스의 구현체를 생성
        ChatbotApiService service = retrofit.create(ChatbotApiService.class);
		
    	try { 
    		// 처음으로 업데이트를 가져와서 마지막 업데이트 ID를 설정
    		UpdateResponse response = service.getUpdates(0).execute().body();
    		long lastId = 0;
    		if (response.result.size() != 0) {    			
    			lastId = response.result.get(response.result.size() - 1).updateId;
    		}

			boolean flag = false;
			while (true) {
				response = service.getUpdates(lastId + 1).execute().body();
				for (Update update : response.result) {
					long id = update.message.from.id;
					String text = update.message.text;

					if(text.equals("1")){
						StringBuilder messageBuilder = new StringBuilder();
						service.sendMessage(id + "", "오늘의 시세에 대해 알고 싶으시군요! \n시가총액 TOP15 리스트중에 궁금하신 종목 이름을 입력해주세요😁").execute().body();//데이터 가져오기
						StockMain.main(null);
						if (StockMain.stockList!= null) {
							for (int i = 0; i <15; i++) {
								messageBuilder.append("- ").append(StockMain.stockList[i]).append("\n");
							}
							service.sendMessage(id + "",  "리스트는 다음과 같아요.\n"+messageBuilder).execute().body();
						} else {
							System.out.println("리스트를 불러오지 못했어요ㅎ");
						}
						flag = true;
						TimeUnit.SECONDS.sleep(5);
					} else if(text.equals("2")){
						service.sendMessage(id + "", "오늘의 주식 뉴스를 알려드릴게요😁").execute().body();
						NaverApiMain.main(null); //데이터 가져오기
						if (NaverApiMain.newsTitle != null && NaverApiMain.newsLink != null) {
							for (int i = 0; i <3; i++) {
								service.sendMessage(id + "",  NaverApiMain.newsTitle[i]+"\n"+ NaverApiMain.newsLink[i]).execute().body();
							}
						} else {
							System.out.println("뉴스가 없네요 ㅎ");
						}
					} else if(text.equals("3")){
						service.sendMessage(id + "", "오늘의 주식 커뮤니티 소식을 알려드릴게요😁").execute().body();
						NaverApiMain.main(null); //데이터 가져오기
						if (NaverApiMain.cafeTitle != null && NaverApiMain.cafeLink != null) {
							for (int i = 0; i <3; i++) {
								service.sendMessage(id + "",  NaverApiMain.cafeTitle[i]+"\n"+ NaverApiMain.cafeLink[i]).execute().body();
							}
						} else {
							System.out.println("카페 소식이 없네요 ㅎ");
						}
					} else {

						if(Arrays.asList(StockMain.stockList).contains(text) && flag==true){
							StockMain.findStockName(text);
							if(StockMain.result==null){
								service.sendMessage(id + "", "종목을 찾지 못했어요").execute().body();
							} else{
								service.sendMessage(id + "",  StockMain.result).execute().body();
							}
							flag=false;
						} else {
							if (flag == true) {
								service.sendMessage(id + "", "종목을 찾지 못했어요").execute().body();
							} else {
								service.sendMessage(id + "", "지원하지 않는 번호입니다😢 \n1~3의 숫자 중에 입력해주세요.").execute().body();
							}

						}
					}
					if(flag ==false){
						service.sendMessage(id + "", "🌳당신의 주식 도우미, Today's Stock Bot 입니다🌳" +
								"\n" +
								"궁금하신 내용의 번호를 입력해주세요.\n" +
								"------------------------------------------\n" +
								"<1> 오늘의 시세를 알고 싶어!\n" +
								"<2> 주식 뉴스를 보고 싶어!\n" +
								"<3> 주식 커뮤니티 소식을 보고 싶어!\n" +
								"------------------------------------------\n" +
								"\n" +
								"편리한 주식 거래를 위해 도와드릴게요💪").execute().body();
					}
					// 마지막 업데이트 ID를 갱신
					lastId = update.updateId;

				}
    		}
		} catch (Exception e) {
			// 예외 발생 시 스택 트레이스를 출력
			e.printStackTrace();
		}
	}
}
