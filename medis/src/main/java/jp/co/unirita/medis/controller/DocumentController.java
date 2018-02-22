package jp.co.unirita.medis.controller;

import java.util.List;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.form.document.DocumentForm;
import jp.co.unirita.medis.logic.DocumentLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.CommentInfoForm;
import jp.co.unirita.medis.logic.CommentLogic;
import jp.co.unirita.medis.util.exception.InvalidArgumentException;

import javax.swing.text.Document;

@RestController
@RequestMapping("/v1/documents")
public class DocumentController {

	@Autowired
	CommentLogic commentlogic;
	@Autowired
    DocumentLogic documentLogic;

	@RequestMapping(value = { "{documentId}/comments" }, method = RequestMethod.GET)
	public List<CommentInfoForm> getfind(@AuthenticationPrincipal User user,
			@PathVariable(value = "documentId") String documentId) throws InvalidArgumentException {
		List<CommentInfoForm> list = commentlogic.getCommentInfo(documentId);

		return list;
	}

    @GetMapping(value = "{documentId:^d[0-9]{10}+$}")
    @ResponseStatus(HttpStatus.OK)
    public DocumentForm getDocument(@PathVariable(value ="documentId") String documentId) {
        System.out.println("get document [id = " + documentId + "]");

        // TODO 存在チェック

        DocumentForm document = documentLogic.getDocument(documentId);
        System.out.println(document);
        return document;
    }

    @GetMapping(value = "{documentId:^d[0-9]{10}+$}/tags")
    @ResponseStatus(HttpStatus.OK)
    public List<Tag> getDocumentTagList(@PathVariable(value ="documentId") String documentId) {
        return documentLogic.getDocumentTags(documentId);
    }

    @PostMapping(value = "{documentId:^d[0-9]{10}+$}")
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentForm updateDocument(@RequestBody DocumentForm document) throws Exception {
        // TODO 社員番号を確認
        documentLogic.update(document, "99999");
        return document;
    }

    @PostMapping(value = "{documentId:^d[0-9]{10}+$}/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateDocumentTagList(@PathVariable(value = "documentId") String documentId, @RequestBody List<Tag> tags) throws Exception {
        documentLogic.updateTags(documentId, tags);
    }

    @PutMapping(value = "new")
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentForm saveDocument(@RequestBody DocumentForm document) throws Exception{
        // TODO 社員番号を取得するようにする
        documentLogic.save(document, "99999");
        return document;
    }

    @PutMapping(value = "{documentId:^d[0-9]{10}+$}/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveDocumentTagList(@PathVariable(value = "documentId") String documentId, @RequestBody List<Tag> tags) throws Exception {
        documentLogic.saveTags(documentId, tags);
    }
}



