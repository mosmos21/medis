package jp.co.unirita.medis.logic.util;

import java.lang.invoke.MethodHandles;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalLong;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.tag.TagRepository;
import jp.co.unirita.medis.util.exception.DBException;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;

@Service
@Transactional(rollbackFor = Exception.class)
public class TagLogic {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static final String COMMENT_NOTIFICATION_TAG = "g0000000000";

	@Autowired
	TagRepository tagRepository;
	@Autowired
	IdUtilLogic idUtilLogic;

	public List<Tag> getTagList() {
		try {
			List<Tag> tagList = tagRepository.findAll().stream()
					.filter(tag -> !tag.getTagId().equals(COMMENT_NOTIFICATION_TAG))
					.collect(Collectors.toList());
			tagList.sort(Comparator.naturalOrder());
			return tagList;
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: TagLogic, method: getTagList]");
			throw new DBException("DB Runtime Error[class: TagLogic, method: getTagList]");
		}
	}

	public synchronized String getNewTagId() throws IdIssuanceUpperException {
		try {
			OptionalLong maxId = tagRepository.findAll().stream()
					.filter(tag -> tag.getTagId().charAt(0) == 'n')
					.map(tag -> tag.getTagId().substring(1))
					.mapToLong(Long::parseLong)
					.max();
			if (!maxId.isPresent()) {
				return "n0000000000";
			}
			if (maxId.getAsLong() == 9999999999L) {
				throw new IdIssuanceUpperException("IDの発行限界");
			}
			return String.format("n%010d", maxId.getAsLong() + 1);
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: TagLogic, method: getNewTagId]");
			throw new DBException("DB Runtime Error[class: TagLogic, method: getNewTagId]");
		}
	}

	public synchronized String getNewSystemTagId() throws IdIssuanceUpperException {
		try {
			OptionalLong maxId = tagRepository.findAll().stream()
					.filter(tag -> tag.getTagId().charAt(0) == 's')
					.map(tag -> tag.getTagId().substring(1))
					.mapToLong(Long::parseLong)
					.max();
			if (!maxId.isPresent()) {
				return "s0000000000";
			}
			if (maxId.getAsLong() == 9999999999L) {
				throw new IdIssuanceUpperException("IDの発行限界");
			}
			return String.format("s%010d", maxId.getAsLong() + 1);
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: TagLogic, method: getNewSystemTagId]");
			throw new DBException("DB Runtime Error[class: TagLogic, method: getNewSystemTagId]");
		}
	}

	private Tag createTag(String value) throws IdIssuanceUpperException {
		try {
			String id = getNewTagId();
		    Tag tag = new Tag(id, value);
		    tagRepository.saveAndFlush(tag);
		    return tag;
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: TagLogic, method: createTag]");
			throw new DBException("DB Runtime Error[class: TagLogic, method: createTag]");
		}
	}

	public Tag createSystemTag(String value) throws IdIssuanceUpperException {
		try {
			String id = idUtilLogic.getNewSystemTagId();
			Tag tag = new Tag(id, value);
			tagRepository.saveAndFlush(tag);
			return tag;
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: TagLogic, method: createSystemTag]");
			throw new DBException("DB Runtime Error[class: TagLogic, method: createSystemTag]");
		}
	}

	public List<Tag> applyTags(List<Tag> tags) throws IdIssuanceUpperException {
		try {
			List<String> newTags = tags.stream()
	                .filter(tag -> tag.getTagId().equals(""))
	                .filter(tag -> tagRepository.findByTagName(tag.getTagName()).size() == 0)
	                .map(Tag::getTagName)
	                .collect(Collectors.toList());
	        for (String value : newTags) {
	            createTag(value);
	        }
	        return tagRepository.findByTagNameIn(tags.stream().map(Tag::getTagName).collect(Collectors.toList()));
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: TagLogic, method: applyTags]");
			throw new DBException("DB Runtime Error[class: TagLogic, method: applyTags]");
		}
	}

	public List<Tag> applySystemTag(List<Tag> tags) throws IdIssuanceUpperException {
		try {
			List<String> newTags = tags.stream()
					.filter(tag -> tag.getTagId().equals(""))
					.filter(tag -> tagRepository.findByTagName(tag.getTagName()).size() == 0)
					.map(Tag::getTagName)
					.collect(Collectors.toList());
			for (String value : newTags) {
				createSystemTag(value);
			}
			return tagRepository.findByTagNameIn(tags.stream().map(Tag::getTagName).collect(Collectors.toList()));
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: TagLogic, method: applySystemTag]");
			throw new DBException("DB Runtime Error[class: TagLogic, method: applySystemTag]");
		}
	}
}
