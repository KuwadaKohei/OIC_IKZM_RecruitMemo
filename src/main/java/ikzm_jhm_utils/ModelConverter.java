package ikzm_jhm_utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import ikzm_jhm_dto.Post;
import ikzm_jhm_dto.PostDetail;
import ikzm_jhm_dto.PostExamSelection;
import ikzm_jhm_model.PostForm;
import ikzm_jhm_viewmodel.PostViewModel;
import ikzm_jhm_viewmodel.SearchResultViewModel;

/**
 * モデルオブジェクト間のデータ変換を行うユーティリティクラス。
 */
public class ModelConverter {

	private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	// 表示系 (Output: DTO → ViewModel)
	/**
	 * 投稿DTOリストを簡易検索結果ビューモデルに変換する。
	 *
	 * @param posts 投稿DTOリスト
	 * @return ビューモデル
	 */
	public static SearchResultViewModel toSearchResultViewModel(List<Post> posts) {
		return toSearchResultViewModel(posts, Collections.emptyMap(), Collections.emptyMap());
	}

	/**
	 * 投稿DTOリストを検索結果ビューモデルに変換する。学科名と詳細情報を同時に埋め込む。
	 *
	 * @param posts               投稿DTOリスト
	 * @param departmentCourseMap departmentId -> 学科名
	 * @param postDetailMap       postId -> 投稿詳細
	 * @return ビューモデル
	 */
	public static SearchResultViewModel toSearchResultViewModel(
			List<Post> posts,
			Map<Integer, String> departmentCourseMap,
			Map<Integer, PostDetail> postDetailMap) {

		List<SearchResultViewModel.Summary> summaries = new ArrayList<>();

		if (posts != null) {
			Map<Integer, String> safeDepartmentMap = (departmentCourseMap != null)
					? departmentCourseMap
					: Collections.emptyMap();
			Map<Integer, PostDetail> safeDetailMap = (postDetailMap != null)
					? postDetailMap
					: Collections.emptyMap();

			for (Post post : posts) {
				String departmentCourseName = safeDepartmentMap.getOrDefault(post.getDepartmentId(), "未設定");
				PostDetail detail = safeDetailMap.get(post.getPostId());

				summaries.add(new SearchResultViewModel.Summary(
						post.getPostId(),
						post.getUserId(),
						post.getDepartmentId(),
						departmentCourseName,
						post.getRecruitmentNo(),
						post.getCompanyName(),
						post.getExamDate(),
						post.getCreateAt(),
						post.getUpdatedAt(),
						detail));
			}
		}
		return new SearchResultViewModel(summaries);
	}

	/**
	 * 投稿情報と関連マスタを集約し、詳細表示用ビューモデルを生成する。
	 */
	public static PostViewModel toPostViewModel(
			Post post,
			PostDetail detail,
			String departmentName,
			String methodName,
			String posterName,
			Map<Integer, String> examCategoryMap,
			Map<Integer, String> examNameMap) {

		if (examCategoryMap == null) {
			examCategoryMap = new HashMap<>();
		}

		List<PostViewModel.SelectedExamItem> examItems = new ArrayList<>();

		if (post.getExamSelection() != null) {
			for (PostExamSelection sel : post.getExamSelection()) {
				int contentId = sel.getContentId();
				String category = examCategoryMap.getOrDefault(contentId, "その他");
				String name = examNameMap.getOrDefault(contentId, "未定義項目");

				examItems.add(new PostViewModel.SelectedExamItem(
						category,
						name,
						sel.getDetailText()));
			}
		}

		int recruitmentNo = post.getRecruitmentNo();

		LocalDate resultDate = (detail != null) ? detail.getResultDate() : null;
		int scheduledHires = (detail != null) ? detail.getScheduled_hires() : 0;
		int totalApplicants = (detail != null) ? detail.getTotalApplicants() : 0;
		String adviceText = (detail != null) ? detail.getAdviceText() : "";

		return new PostViewModel(
				post.getPostId(),
				post.getCompanyName(),
				post.getExamDate(),
				post.getVenueAddress(),
				departmentName,
				methodName,
				recruitmentNo,
				post.getGrade(),
				posterName,
				resultDate,
				scheduledHires,
				totalApplicants,
				adviceText,
				examItems);
	}

	// 保存系 (Input: Form → DTO)

	/**
	 * フォーム入力を投稿DTOへ変換する。
	 */
	public static Post toPostDto(PostForm form, int userId) {
		Post post = new Post();

		post.setPostId(form.getPostId());
		post.setUserId(userId);
		post.setCompanyName(form.getCompanyName());
		post.setVenueAddress(form.getVenueAddress());
		post.setDepartmentId(form.getDepartmentId());
		post.setMethodId(form.getMethodId());
		post.setRecruitmentNo(parseIntSafe(form.getRecruitmentNoStr()));
		post.setGrade(form.getGrade());
		post.setAnonymous(form.isAnonymous());
		post.setExamDate(parseDate(form.getExamDateStr()));

		return post;
	}

	/**
	 * フォーム入力を投稿詳細DTOへ変換する。
	 */
	public static PostDetail toPostDetailDto(PostForm form) {
		PostDetail detail = new PostDetail();

		detail.setPostId(form.getPostId());
		detail.setAdviceText(form.getAdviceText());
		detail.setResultDate(parseDate(form.getResultDateStr()));
		detail.setScheduled_hires(parseIntSafe(form.getScheduledHiresStr()));
		detail.setTotalApplicants(parseIntSafe(form.getTotalApplicantsStr()));

		return detail;
	}

	/**
	 * フォームの試験選択情報をDTOリストに変換する。
	 */
	public static List<PostExamSelection> toSelectionList(PostForm form) {
		List<PostExamSelection> list = new ArrayList<>();

		if (form.getSelectedExamIds() != null) {
			for (Integer contentId : form.getSelectedExamIds()) {
				String detailText = null;
				if (form.getExamDetails() != null) {
					detailText = form.getExamDetails().get(contentId);
				}
				// 第一引数であるpostIdは、投稿未登録時点では存在しないため0
				list.add(new PostExamSelection(0, contentId, detailText));
			}
		}
		return list;
	}

	// 編集初期表示系 (Load: DTO → Form)

	/**
	 * 投稿DTOと詳細DTOからフォーム初期値を構築する。
	 */
	public static PostForm toPostForm(Post post, PostDetail detail) {
		PostForm form = new PostForm();
		form.setPostId(post.getPostId());
		form.setCompanyName(post.getCompanyName());
		form.setVenueAddress(post.getVenueAddress());
		form.setDepartmentId(post.getDepartmentId());
		form.setMethodId(post.getMethodId());

		// DTO(int) → Form(String)
		form.setRecruitmentNoStr(String.valueOf(post.getRecruitmentNo()));

		form.setGrade(post.getGrade());
		form.setAnonymous(post.isAnonymous());

		if (post.getExamDate() != null) {
			form.setExamDateStr(post.getExamDate().format(DATE_FMT));
		}

		if (detail != null) {
			form.setAdviceText(detail.getAdviceText());
			form.setScheduledHiresStr(String.valueOf(detail.getScheduled_hires()));
			form.setTotalApplicantsStr(String.valueOf(detail.getTotalApplicants()));
			if (detail.getResultDate() != null) {
				form.setResultDateStr(detail.getResultDate().format(DATE_FMT));
			}
		}

		List<Integer> ids = new ArrayList<>();
		Map<Integer, String> details = new HashMap<>();

		if (post.getExamSelection() != null) {
			for (PostExamSelection sel : post.getExamSelection()) {
				ids.add(sel.getContentId());
				if (sel.getDetailText() != null) {
					details.put(sel.getContentId(), sel.getDetailText());
				}
			}
		}
		form.setSelectedExamIds(ids);
		form.setExamDetails(details);

		return form;
	}

	// リクエスト解析系 (Request → Form)

	// HTTPリクエストのパラメータを解析し、PostFormオブジェクトを作成する

	/**
	 * HTTPリクエストから投稿フォームを組み立てる。
	 */
	public static PostForm toPostForm(HttpServletRequest request) {
		PostForm form = new PostForm();

		// 基本的な文字列項目
		form.setCompanyName(request.getParameter("companyName"));
		form.setExamDateStr(request.getParameter("examDateStr"));
		form.setVenueAddress(request.getParameter("venueAddress"));
		form.setRecruitmentNoStr(request.getParameter("recruitmentNoStr"));
		form.setAdviceText(request.getParameter("adviceText"));

		// 数値型 (パースエラー対策込みの変換)
		form.setDepartmentId(parseIntSafe(request.getParameter("departmentId")));
		form.setMethodId(parseIntSafe(request.getParameter("methodId")));
		form.setGrade(parseIntSafe(request.getParameter("grade")));

		// チェックボックス (boolean)
		form.setAnonymous("true".equals(request.getParameter("isAnonymous")));

		// 詳細情報
		form.setResultDateStr(request.getParameter("resultDateStr"));
		form.setScheduledHiresStr(request.getParameter("scheduledHiresStr"));
		form.setTotalApplicantsStr(request.getParameter("totalApplicantsStr"));

		// --- 試験選択情報の複雑なマッピング ---

		// 1. チェックされたIDリスト (selectedExamIds)
		String[] selectedIds = request.getParameterValues("selectedExamIds");
		List<Integer> idList = new ArrayList<>();
		if (selectedIds != null) {
			for (String s : selectedIds) {
				int id = parseIntSafe(s);
				if (id > 0)
					idList.add(id);
			}
		}
		form.setSelectedExamIds(idList);

		// 2. 詳細記述マップ (examDetails)
		// name="detail_101" のようなパラメータを取得
		Map<Integer, String> detailMap = new HashMap<>();
		for (Integer id : idList) {
			String detail = request.getParameter("detail_" + id);
			if (detail != null && !detail.isBlank()) {
				detailMap.put(id, detail);
			}
		}
		form.setExamDetails(detailMap);

		return form;
	}

	// ヘルパーメソッド
	/**
	 * yyyy-MM-dd 形式の文字列を LocalDate に変換する。
	 *
	 * @param dateStr 日付文字列
	 * @return 変換結果。失敗時はnull
	 */
	private static LocalDate parseDate(String dateStr) {
		if (dateStr == null || dateStr.isEmpty())
			return null;
		try {
			return LocalDate.parse(dateStr, DATE_FMT);
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	/**
	 * 数値文字列を安全に int へ変換する。
	 *
	 * @param numStr 数値文字列
	 * @return 変換結果。失敗時は0
	 */
	private static int parseIntSafe(String numStr) {
		if (numStr == null || numStr.isEmpty())
			return 0;
		try {
			return Integer.parseInt(numStr);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}