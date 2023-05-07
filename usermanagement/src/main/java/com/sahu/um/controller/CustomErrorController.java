package com.sahu.um.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sahu.um.constants.ErrorConstants;

@Controller
public class CustomErrorController implements ErrorController {

	@GetMapping("/error")
	public String handleError(HttpServletRequest httpServletRequest) {
		Object status = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				// handle HTTP 404 Not Found error
               return ErrorConstants.ERROR_404;
			}
			else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				 // handle HTTP 403 Forbidden error
				return ErrorConstants.ERROR_403;
			}
			else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				// handle HTTP 500 Internal Server er
				return ErrorConstants.ERROR_500;
			}
		}
		
		return ErrorConstants.MAIN_ERROR;
	}

}
