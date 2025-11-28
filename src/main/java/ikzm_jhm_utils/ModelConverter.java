package ikzm_jhm_utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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

	//表示系 (Output: DTO → ViewModel)
	public static SearchResultViewModel toSearchResultViewModel(List<Post> posts) {
		List<SearchResultViewModel.Summary> summaries = new ArrayList<>();

		if (posts != null) {
			for (Post post : posts) {
				// 試験選択情報の分解
				List<Integer> selectedExamIds = new ArrayList<>();
				Map<Integer, String> detailTexts = new HashMap<>();

				if (post.getExamSelection() != null) {
					for (PostExamSelection sel : post.getExamSelection()) {
						selectedExamIds.add(sel.getContentId());
						if (sel.getDetailText() != null) {
							detailTexts.put(sel.getContentId(), sel.getDetailText());
						}
					}
				}

				int recruitmentNo = post.getRecruitmentNo();

				summaries.add(new SearchResultViewModel.Summary(
						post.getPostId(),
						post.getUserId(),
						post.getDepartmentId(),
						post.getMethodId(),
						recruitmentNo,
						post.getCompanyName(),
						post.getExamDate(),
						selectedExamIds,
						detailTexts));
			}
		}
		return new SearchResultViewModel(summaries);
	}

	public static PostViewModel toPostViewModel(
			Post post,
			PostDetail detail,
			String departmentName,
			String methodName,
			String posterName,
			Map<Integer, String> examCategoryMap,
			Map<Integer, String> examNameMap) {
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

	//保存系 (Input: Form → DTO)

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

	public static PostDetail toPostDetailDto(PostForm form) {
		PostDetail detail = new PostDetail();
		
		detail.setPostId(form.getPostId());
		detail.setAdviceText(form.getAdviceText());
		detail.setResultDate(parseDate(form.getResultDateStr()));
		detail.setScheduled_hires(parseIntSafe(form.getScheduledHiresStr()));
		detail.setTotalApplicants(parseIntSafe(form.getTotalApplicantsStr()));

		return detail;
	}

	public static List<PostExamSelection> toSelectionList(PostForm form) {
		List<PostExamSelection> list = new ArrayList<>();

		if (form.getSelectedExamIds() != null) {
			for (Integer contentId : form.getSelectedExamIds()) {
				String detailText = null;
				if (form.getExamDetails() != null) {
					detailText = form.getExamDetails().get(contentId);
				}
				//第一引数であるpostIdは、投稿未登録時点では存在しないため0
				list.add(new PostExamSelection(0,contentId, detailText));
			}
		}
		return list;
	}

	//編集初期表示系 (Load: DTO → Form)

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

	//リクエスト解析系 (Request → Form)

	//HTTPリクエストのパラメータを解析し、PostFormオブジェクトを作成する

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
	private static LocalDate parseDate(String dateStr) {
		if (dateStr == null || dateStr.isEmpty())
			return null;
		try {
			return LocalDate.parse(dateStr, DATE_FMT);
		} catch (DateTimeParseException e) {
			return null;
		}
	}

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