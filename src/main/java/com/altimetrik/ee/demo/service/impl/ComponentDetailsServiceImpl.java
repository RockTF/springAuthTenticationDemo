package com.altimetrik.ee.demo.service.impl;

import com.altimetrik.ee.demo.bean.PairedComponentDetailsBean;
import com.altimetrik.ee.demo.entity.ComponentDetailsEntity;
import com.altimetrik.ee.demo.repository.ComponentDetailsRepository;
import com.altimetrik.ee.demo.service.ComponentDetailsService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ComponentDetailsServiceImpl implements ComponentDetailsService {

  @Value("${spring.application.name}")
  private String applicationName;

  @Autowired private ComponentDetailsRepository componentDetailsRepository;

  @Override
  public PairedComponentDetailsBean findAll(final String applicationName) {
    final PairedComponentDetailsBean pairedComponentDetails =
        new PairedComponentDetailsBean(
            this.componentDetailsRepository.getByComponentName(applicationName),
            this.componentDetailsRepository.getByComponentNameNotIn(applicationName));
    return pairedComponentDetails;
  }

  @Override
  public void createComponentDetails(final String applicationName) {
    if (this.componentDetailsRepository.findByComponentName(applicationName) == null) {
      this.componentDetailsRepository.save(
          new ComponentDetailsEntity(applicationName, UUID.randomUUID().toString()));
    }
  }

  @Scheduled(cron = "${cron.component.identifier.reg-ex}")
  public void regenerateComponentIdentifier() {
    final ComponentDetailsEntity componentDetails =
        this.componentDetailsRepository.findByComponentName(this.applicationName);
    componentDetails.setComponentIdentifier(UUID.randomUUID().toString());
    this.componentDetailsRepository.save(componentDetails);
  }
}
