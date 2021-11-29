package com.awards.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import static com.awards.config.AwardsConstants.ERROR_PATH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.awards.dto.error.ErrorDetailDto;

@RestController
public class ApiErrorController implements ErrorController {
	@Autowired
	private ErrorAttributes errorAttributes;

	@GetMapping("/error")
	public ResponseEntity<ErrorDetailDto> handleError(WebRequest argRequest, HttpServletResponse argResponse) {
		Map<String, Object> tempErrorAttributes = this.errorAttributes.getErrorAttributes(argRequest,
				ErrorAttributeOptions.defaults());
		return ErrorDetailDto.builder().status(HttpStatus.BAD_REQUEST).path((String) tempErrorAttributes.get("path"))
				.message((String) tempErrorAttributes.get("message")).entity();

	}

	/**
	 * 
	 * @param errorAttributes the error attributes. see details
	 *                        {@link ErrorAttributes}
	 */
	public void setErrorAttributes(ErrorAttributes errorAttributes) {
		this.errorAttributes = errorAttributes;
	}

	/**
	 * 
	 * @return
	 */
	public String getErrorPath() {
		return ERROR_PATH;
	}
}
