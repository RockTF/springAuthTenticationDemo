package com.altimetrik.ee.demo.controller;

import com.altimetrik.ee.demo.bean.PairedComponentDetailsBean;
import com.altimetrik.ee.demo.service.ComponentDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/service")
public class ServiceController {

	protected static Logger logger = LoggerFactory.getLogger(ServiceController.class.getName());

	@Value("${spring.application.name}")
	private String applicationName;

	@Autowired
	private ComponentDetailsService componentDetailsService;

	@GetMapping(value = "/")
	@Operation(
			summary = "Get service name and identifier",
			description = "Retrieve service details and corresponding values for all paired services",
			responses = {
					@ApiResponse(responseCode = "200", description = "Service details retrieved successfully",
							content = @Content(schema = @Schema(implementation = PairedComponentDetailsBean.class))),
					@ApiResponse(responseCode = "500", description = "Internal server error")
			}
	)	public PairedComponentDetailsBean findAll() {
		return componentDetailsService.findAll(this.applicationName);
	}

}
