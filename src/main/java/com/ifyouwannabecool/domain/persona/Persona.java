package com.ifyouwannabecool.domain.persona;

import com.webcohesion.enunciate.metadata.DocumentationExample;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Ryan Heaton
 */
@XmlRootElement
@XmlAccessorType( XmlAccessType.FIELD)
public class Persona {

  @DocumentationExample("customfield_10400")
  @XmlElement (name = "customId")
  private String id;
  @DocumentationExample("customemail_20400")
  @XmlElement (name = "customEmail")
  private String email;
  @DocumentationExample("customalias_30400")
  @XmlElement (name = "customAlias")
  private String alias;
  private String[] groups;
  private Name name;
  private javax.activation.DataHandler picture;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public String[] getGroups() {
    return groups;
  }

  public void setGroups(String[] groups) {
    this.groups = groups;
  }

  public Name getName() {
    return name;
  }

  public void setName(Name name) {
    this.name = name;
  }

  public DataHandler getPicture() {
    return picture;
  }

  public void setPicture(DataHandler picture) {
    this.picture = picture;
  }
}
