package jp.co.unirita.medis.logic.system;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.comment.CommentRepository;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.documenttag.DocumentTag;
import jp.co.unirita.medis.domain.documenttag.DocumentTagRepository;
import jp.co.unirita.medis.domain.notificationconfig.NotificationConfig;
import jp.co.unirita.medis.domain.notificationconfig.NotificationConfigRepository;
import jp.co.unirita.medis.domain.templatetag.TemplateTagRepository;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfo;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.setting.SnackbarNotificationsForm;

@Service
public class CheckUpdateLogic {

	@Autowired
	MailLogic mailLogic;

	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	TemplateTagRepository templateTagRepository;
	@Autowired
	DocumentTagRepository documentTagRepository;
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	NotificationConfigRepository notificationConfigRepository;
	@Autowired
	UserDetailRepository userDetailRepository;
	@Autowired
	UpdateInfoRepository updateInfoRepository;

	public List<SnackbarNotificationsForm> updateSelect(String employeeNumber, String updateId) {
		List<UpdateInfo> newUpdateId = updateInfoRepository.findByUpdateIdAfter(updateId);
		List<SnackbarNotificationsForm> result = new ArrayList<>();

		for (UpdateInfo add : newUpdateId) {
			List<SnackbarNotificationsForm> snackbarNotificationsForm = new ArrayList<>();
			if (add.getUpdateType().equals("v0000000000")) {
				snackbarNotificationsForm = documentContributionNotificationSnackbar(employeeNumber, add.getUpdateId());

			}
			result.addAll(snackbarNotificationsForm);
		}
		// result.removeAll(Collections.singleton(null));
		result = result.stream().distinct().collect(Collectors.toList());

		return result;

	}

	/**
	 * 新規文書が投稿されたときに、文書についたタグを監視しているユーザにSnackbarを通知するためのロジック
	 *
	 * @param employeeNumber
	 *            文書を投稿したユーザの社員番号
	 * @param documentId
	 *            投稿した文書のID
	 */

	public List<SnackbarNotificationsForm> documentContributionNotificationSnackbar(String employeeNumber,
			String updateId) {
		List<SnackbarNotificationsForm> result = new ArrayList<>();
		// 新規かつユーザがもつUpdateId以降のDocumentId取得
		String documentId = updateInfoRepository.findOne(updateId).getDocumentId();

		// 公開されたDocumentId取得
		DocumentInfo privateDocumentTag = documentInfoRepository.findByDocumentPublishAndDocumentId(true, documentId);

		if (!(privateDocumentTag == null)) {

			// 新規かつユーザがもつUpdateId以降についたタグ一覧
			List<DocumentTag> documentTagList = documentTagRepository
					.findByDocumentId(privateDocumentTag.getDocumentId());

			// ユーザの監視タグ一覧
			List<String> userNotificationTagList = notificationConfigRepository.findByEmployeeNumber(employeeNumber)
					.stream().map(NotificationConfig::getTagId).collect(Collectors.toList());

			for (DocumentTag add : documentTagList) {
				if (userNotificationTagList.contains(add.getTagId())) {
					SnackbarNotificationsForm snackbarNotificationsForm = new SnackbarNotificationsForm();
					snackbarNotificationsForm.setDocumentId(add.getDocumentId());
					snackbarNotificationsForm.setUpdateType("v0000000000");
					snackbarNotificationsForm
							.setDocumentName(documentInfoRepository.findOne(add.getDocumentId()).getDocumentName());
					result.add(snackbarNotificationsForm);

				}
			}
		}
		return result;

	}
}
