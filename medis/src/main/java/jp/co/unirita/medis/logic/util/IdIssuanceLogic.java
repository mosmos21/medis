package jp.co.unirita.medis.logic.util;

import jp.co.unirita.medis.domain.bookmark.Bookmark;
import jp.co.unirita.medis.domain.bookmark.BookmarkRepository;
import jp.co.unirita.medis.domain.comment.Comment;
import jp.co.unirita.medis.domain.comment.CommentRepository;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.tag.TagRepository;
import jp.co.unirita.medis.domain.templateinfo.TemplateInfo;
import jp.co.unirita.medis.domain.templateinfo.TemplateInfoRepository;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfo;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;
import jp.co.unirita.medis.util.exception.DBException;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalLong;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Service
@Slf4j
public class IdIssuanceLogic {

	private Map<Character, Long> maxIdMap = new HashMap<>();

	@Autowired
	TemplateInfoRepository templateInfoRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	TagRepository tagRepository;
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	UpdateInfoRepository updateInfoRepository;
	@Autowired
	BookmarkRepository bookmarkRepository;

	public synchronized String createTemplateId() throws IdIssuanceUpperException{
		try {
			return issueNewId('t', templateInfoRepository,
					(Function<TemplateInfo, String>) info -> info.getTemplateId().substring(1));
		} catch (DBException e) {
			log.error("error in createTemplateId()", e);
			throw e;
		}
	}

	public synchronized String createDocumentId() throws IdIssuanceUpperException {
		try {
			return issueNewId('d', documentInfoRepository,
					(Function<DocumentInfo, String>) info -> info.getDocumentId().substring(1));
		} catch (DBException e) {
			log.error("error in createDocumentId()", e);
			throw e;
		}
	}

	public synchronized String createTagId() throws IdIssuanceUpperException {
		try {
			return issueNewId('n', tagRepository,
					(Predicate<Tag>) tag -> tag.getTagId().charAt(0) == 'n',
					(Function<Tag, String>) tag -> tag.getTagId().substring(1));
		} catch (DBException e) {
			log.error("error in createSystemTagId()", e);
			throw e;
		}
	}

	public synchronized String createSystemTagId() throws IdIssuanceUpperException {
		try {
			return issueNewId('s', tagRepository,
					(Predicate<Tag>) tag -> tag.getTagId().charAt(0) == 's',
					(Function<Tag, String>) tag -> tag.getTagId().substring(1));
		} catch (DBException e) {
			log.error("error in createSystemTagId()", e);
			throw e;
		}
	}

	public synchronized String createCommentId() throws IdIssuanceUpperException {
		try {
			return issueNewId('o', commentRepository,
					(Function<Comment, String>) tag -> tag.getCommentId().substring(1));
		} catch (DBException e) {
			log.error("error in createCommentId()", e);
			throw e;
		}
	}

	public synchronized String createUpdateId() throws IdIssuanceUpperException {
		try {
			return issueNewId('u', updateInfoRepository,
					(Function<UpdateInfo, String>) info -> info.getUpdateId().substring(1));
		} catch (DBException e) {
			log.error("error in createUpdateId()", e);
			throw e;
		}
	}

	public synchronized String createBookmarkId() throws IdIssuanceUpperException {
		try {
			return issueNewId('m', bookmarkRepository,
					(Function<Bookmark, String>) book -> book.getBookmarkId().substring(1));
		} catch (DBException e) {
			log.error("error in createBookmarkId()", e);
			throw e;
		}
	}

	private synchronized String issueNewId(char key, JpaRepository repository, Function mapFunc)
			throws DBException, IdIssuanceUpperException {
		return issueNewId(key, repository, o -> true, mapFunc);
	}

	private String issueNewId(
			char key, JpaRepository repository, Predicate filterFunc, Function mapFunc
	) throws DBException, IdIssuanceUpperException {
	    long idNum;
	    if (maxIdMap.containsKey(key)) {
	        idNum = maxIdMap.get(key);
        } else {
            Stream<String> idStream = repository.findAll().stream().filter(filterFunc).map(mapFunc);
            OptionalLong maxId = idStream.mapToLong(Long::parseLong).max();
            idNum = maxId.orElse(0L);
        }
        if (idNum == 9999999999L) {
            throw new IdIssuanceUpperException("IDの発行限界");
        }
        long newId = idNum + 1;
        maxIdMap.put(key, newId);
        return String.format(key + "%010d", newId);
	}
}
