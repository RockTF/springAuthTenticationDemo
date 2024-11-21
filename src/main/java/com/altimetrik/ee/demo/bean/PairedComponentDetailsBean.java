package com.altimetrik.ee.demo.bean;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class PairedComponentDetailsBean extends ComponentDetailsBean {

  private List<ComponentDetailsBean> pairedComponentDetails;

  public PairedComponentDetailsBean() {
    super();
  }

  public PairedComponentDetailsBean(final String componentName, final String componentIdentifier) {
    super(componentName, componentIdentifier);
  }

  public PairedComponentDetailsBean(
      final String componentName,
      final String componentIdentifier,
      final List<ComponentDetailsBean> pairedComponentDetails) {
    super(componentName, componentIdentifier);
    // Defensive copy
    this.pairedComponentDetails = copyList(pairedComponentDetails);
  }

  public PairedComponentDetailsBean(
      final ComponentDetailsBean componentDetails,
      final List<ComponentDetailsBean> pairedComponentDetails) {
    super(componentDetails.getComponentName(), componentDetails.getComponentIdentifier());
    // Defensive copy
    this.pairedComponentDetails = copyList(pairedComponentDetails);
  }

  // Getter with defensive copy
  public List<ComponentDetailsBean> getPairedComponentDetails() {
    return pairedComponentDetails == null ? null : new ArrayList<>(pairedComponentDetails);
  }

  // Setter with defensive copy
  public void setPairedComponentDetails(List<ComponentDetailsBean> pairedComponentDetails) {
    this.pairedComponentDetails = copyList(pairedComponentDetails);
  }

  // Utility method to safely copy a list
  private List<ComponentDetailsBean> copyList(List<ComponentDetailsBean> list) {
    return list == null ? null : new ArrayList<>(list);
  }
}
