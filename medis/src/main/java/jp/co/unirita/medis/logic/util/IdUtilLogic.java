package jp.co.unirita.medis.logic.util;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import jp.co.unirita.medis.domain.tag.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.tag.TagRepository;
import jp.co.unirita.medis.util.exception.DBException;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;
import lombok.Data;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

@Service
public class IdUtilLogic {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private Map<Character, Long> maxIdMap = new HashMap<>();

	@Autowired
	TagRepository tagRepository;

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
			String tagId = issueNewId('s', tagRepository,
					(Predicate<Tag>) tag -> tag.getTagId().charAt(0) == 's',
					(Function<Tag, String>) tag -> tag.getTagId().substring(1));
			tagRepository.saveAndFlush(new Tag(tagId, null));
			return tagId;
		} catch (DBException e) {
			logger.error("error in getNewSystemTagId()", e);
			throw e;
		}
	}

	private synchronized String issueNewId(
			char key, JpaRepository repository, Predicate filterFunc, Function mapFunc
	) throws DBException, IdIssuanceUpperException {
		if(maxIdMap.containsKey(key)) {
			long id = maxIdMap.get(key) + 1;
			maxIdMap.put(key, id);
			return String.format(key + "%010d", id);
		}
		long IdNum =0L;
		Stream<String> idStream = repository.findAll().stream().filter(filterFunc).map(mapFunc);
		OptionalLong maxId = idStream.mapToLong(Long::parseLong).max();
		if (maxId.isPresent()) {
			if (maxId.getAsLong() == 9999999999L) {
				throw new IdIssuanceUpperException("IDの発行限界");
			}
			IdNum = maxId.getAsLong() + 1;
		}
		maxIdMap.put(key, IdNum);
		return String.format(key + "%010d", IdNum);
	}
}
