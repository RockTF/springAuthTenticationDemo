package com.altimetrik.ee.demo.service;

import com.altimetrik.ee.demo.bean.PairedComponentDetailsBean;
import org.springframework.stereotype.Service;

@Service
public interface ComponentDetailsService {

	boolean createComponentDetails(final String applicationName);

	PairedComponentDetailsBean findAll(final String applicationName);

}
