package ikzm_jhm_action;

import java.util.List;

import ikzm_jhm_viewmodel.PostReportViewModel;
import ikzm_jhm_viewmodel.SearchResultViewModel;

public class ReportAction {
	
	//一覧/検索リスト取得、ViewModel変換
	public SearchResultViewModel getReportList() {
		
	}
	
	//投稿の詳細データ取得、ViewModel変換、匿名性処理
	public PostReportViewModel getReportDetails(int postId) {
		
	}
	
	//特定のユーザーの投稿を全て取得
	public List<PostReportViewModel> getMyReportList (int userId){
		
	}
	
	//ユーザーから受け取った検索情報をもとに必要なDAOを呼び出す
	public SearchResultViewModel searchReports(String word){
		
	}
}
