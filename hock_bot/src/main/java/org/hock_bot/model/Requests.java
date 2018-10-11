package org.hock_bot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hock_bot.core.ConfigurationI;

import com.fasterxml.jackson.annotation.JsonProperty;


@XmlRootElement(name="raw_requests")
@Entity(name="raw_requests")
@Table(name = "raw_requests", catalog = ConfigurationI.CATALOG )
public class Requests extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 104932492304340L;

}
