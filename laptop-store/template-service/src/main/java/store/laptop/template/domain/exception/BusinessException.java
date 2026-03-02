

package store.laptop.template.domain.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {
	private Long StubId;

	public BusinessException(String message, Long stubId) {
		super(message);
		this.StubId = stubId;
	}

	public BusinessException(String message, Long stubId, Throwable cause) {
		super(message, cause);
		this.StubId = stubId;
	}

	public BusinessException(Long stubId, Throwable cause) {
		super(cause);
		this.StubId = stubId;
	}
}
