package jp.co.unirita.medis.logic.util;

import java.lang.invoke.MethodHandles;
import java.util.OptionalLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.tag.TagRepository;
import jp.co.unirita.medis.util.exception.DBException;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;
import lombok.Data;

@Service
@Data
public class IdUtilLogic {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	TagRepository tagRepository;

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
}
