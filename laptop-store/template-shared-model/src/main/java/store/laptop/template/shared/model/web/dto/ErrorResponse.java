

package store.laptop.template.shared.model.web.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorResponse {
	Long stubId;
	String message;
}
