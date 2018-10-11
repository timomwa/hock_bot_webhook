package org.hock_bot.cron;

import java.io.Serializable;

public class VoteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -395456312001L;
	private String nominee;
	private String position;
	private Long count;
	public VoteDTO(String nominee, String position, Long count){
		this.nominee = nominee;
		this.count = count;
	}
	public String getNominee() {
		return nominee;
	}
	public void setNominee(String nominee) {
		this.nominee = nominee;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nVoteDTO [\n\tnominee=");
		builder.append(nominee);
		builder.append(", \n\tposition=");
		builder.append(position);
		builder.append(", \n\tcount=");
		builder.append(count);
		builder.append("\n]\n");
		return builder.toString();
	}
	
	
	

}
