package ikzm_jhm_utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ikzm_jhm_dto.Post;
import ikzm_jhm_dto.PostDetail;
import ikzm_jhm_dto.PostExamSelection;
import ikzm_jhm_model.ReportForm;
import ikzm_jhm_viewmodel.PostReportViewModel;
import ikzm_jhm_viewmodel.SearchResultViewModel;

/**
 * モデルオブジェクト間のデータ変換を行うユーティリティクラス。
 */
public class ModelConverter {

	private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	// =================================================================
	// 1. 表示系 (Output: DTO -> ViewModel)
	// =================================================================

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

				// Post.recruitmentNo は int なのでそのまま使用
				int recruitmentNo = post.getRecruitmentNo();

				summaries.add(new SearchResultViewModel.Summary(
						post.getPostId(),
						post.getUserId(),
						post.getDepartmentId(),
						post.getMethodId(),
						recruitmentNo,
						post.getCompanyName(),
						post.getExamDate(),
						// adviceText は削除したので引数から除去
						selectedExamIds,
						detailTexts));
			}
		}
		return new SearchResultViewModel(summaries);
	}

	public static PostReportViewModel toPostReportViewModel(
			Post post,
			PostDetail detail,
			String departmentName,
			String methodName,
			String posterName,
			Map<Integer, String> examCategoryMap,
			Map<Integer, String> examNameMap) {
		List<PostReportViewModel.SelectedExamItem> examItems = new ArrayList<>();

		if (post.getExamSelection() != null) {
			for (PostExamSelection sel : post.getExamSelection()) {
				int contentId = sel.getContentId();
				String category = examCategoryMap.getOrDefault(contentId, "その他");
				String name = examNameMap.getOrDefault(contentId, "未定義項目");

				examItems.add(new PostReportViewModel.SelectedExamItem(
						category,
						name,
						sel.getDetailText()));
			}
		}

		// Post.recruitmentNo は int
		int recruitmentNo = post.getRecruitmentNo();

		LocalDate resultDate = (detail != null) ? detail.getResultDate() : null;
		int scheduledHires = (detail != null) ? detail.getScheduled_hires() : 0;
		int totalApplicants = (detail != null) ? detail.getTotalApplicants() : 0;
		String adviceText = (detail != null) ? detail.getAdviceText() : "";

		return new PostReportViewModel(
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

	// =================================================================
	// 2. 保存系 (Input: Form -> DTO)
	// =================================================================

	public static Post toPostDto(ReportForm form, int userId) {
		Post post = new Post();
		post.setPostId(form.getPostId());
		post.setUserId(userId);
		post.setCompanyName(form.getCompanyName());
		post.setVenueAddress(form.getVenueAddress());
		post.setDepartmentId(form.getDepartmentId());
		post.setMethodId(form.getMethodId());

		// Form(String) -> DTO(int) への変換
		post.setRecruitmentNo(parseIntSafe(form.getRecruitmentNoStr()));

		post.setGrade(form.getGrade());
		post.setAnonymous(form.isAnonymous());
		post.setExamDate(parseDate(form.getExamDateStr()));

		return post;
	}

	public static PostDetail toPostDetailDto(ReportForm form) {
		PostDetail detail = new PostDetail();
		detail.setPostId(form.getPostId());
		detail.setAdviceText(form.getAdviceText());
		detail.setResultDate(parseDate(form.getResultDateStr()));
		detail.setScheduled_hires(parseIntSafe(form.getScheduledHiresStr()));
		detail.setTotalApplicants(parseIntSafe(form.getTotalApplicantsStr()));

		return detail;
	}

	public static List<PostExamSelection> toSelectionList(ReportForm form) {
		List<PostExamSelection> list = new ArrayList<>();

		if (form.getSelectedExamIds() != null) {
			for (Integer contentId : form.getSelectedExamIds()) {
				String detailText = null;
				if (form.getExamDetails() != null) {
					detailText = form.getExamDetails().get(contentId);
				}
				list.add(new PostExamSelection(contentId, detailText));
			}
		}
		return list;
	}

	// =================================================================
	// 3. 編集初期表示系 (Load: DTO -> Form)
	// =================================================================

	public static ReportForm toReportForm(Post post, PostDetail detail) {
		ReportForm form = new ReportForm();
		form.setPostId(post.getPostId());
		form.setCompanyName(post.getCompanyName());
		form.setVenueAddress(post.getVenueAddress());
		form.setDepartmentId(post.getDepartmentId());
		form.setMethodId(post.getMethodId());

		// DTO(int) -> Form(String)
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

	// =================================================================
	// ヘルパーメソッド
	// =================================================================

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