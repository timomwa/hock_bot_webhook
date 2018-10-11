package org.hock_bot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hock_bot.core.ConfigurationI;

@Entity
@Table(name = "vote", catalog = ConfigurationI.CATALOG, indexes={@Index(name="voterPostIdx", columnList="voterUserId, position")})
public class Vote extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1390932341L;
	
	@Column(name="voterUserId")
	private Integer voterUserId;
	
	@Column(name="nomineeUsername")
	private String nomineeUsername;
	
	@Column(name="selfNomination")
	private Boolean selfNomination;
	
	@Column(name="nomineeNames")
	private String nomineeNames;
	
	@Column(name="position")
	private String position;

	public String getNomineeUsername() {
		return nomineeUsername;
	}

	public void setNomineeUsername(String nomineeUsername) {
		this.nomineeUsername = nomineeUsername;
	}

	public Boolean getSelfNomination() {
		return selfNomination;
	}

	public void setSelfNomination(Boolean selfNomination) {
		this.selfNomination = selfNomination;
	}

	public String getNomineeNames() {
		return nomineeNames;
	}

	public void setNomineeNames(String nomineeNames) {
		this.nomineeNames = nomineeNames;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getVoterUserId() {
		return voterUserId;
	}

	public void setVoterUserId(Integer voterUserId) {
		this.voterUserId = voterUserId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nVote [\n\tvoterUserId=");
		builder.append(voterUserId);
		builder.append(", \n\tnomineeUsername=");
		builder.append(nomineeUsername);
		builder.append(", \n\tselfNomination=");
		builder.append(selfNomination);
		builder.append(", \n\tnomineeNames=");
		builder.append(nomineeNames);
		builder.append(", \n\tposition=");
		builder.append(position);
		builder.append("\n]\n");
		return builder.toString();
	}

	

}
