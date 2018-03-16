package jp.co.unirita.medis.logic.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.bookmark.BookmarkRepository;
import jp.co.unirita.medis.domain.comment.CommentRepository;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.documentitem.DocumentItemRepository;
import jp.co.unirita.medis.domain.documenttag.DocumentTagRepository;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;

@Service
public class DeleteLogic {

	@Autowired
	BookmarkRepository bookmarkRepository;
	@Autowired
	DocumentTagRepository documentTagRepository;
	@Autowired
	DocumentItemRepository documentItemRepository;
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	UpdateInfoRepository updateInfoRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;

	public void deleteDocument(String documentId) {
		System.out.println(1);
		bookmarkRepository.delete(bookmarkRepository.findByDocumentId(documentId));
		System.out.println(2);
		documentTagRepository.deleteByDocumentId(documentId);
		System.out.println(3);
		documentItemRepository.deleteByDocumentId(documentId);
		System.out.println(4);
		commentRepository.delete(commentRepository.findByDocumentId(documentId));
		System.out.println(5);
		updateInfoRepository.delete(updateInfoRepository.findByDocumentId(documentId));
		System.out.println(6);
		documentInfoRepository.delete(documentId);
		System.out.println(7);
	}
}
