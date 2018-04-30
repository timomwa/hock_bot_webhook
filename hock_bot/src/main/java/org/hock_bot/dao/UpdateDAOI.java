package org.hock_bot.dao;

import java.util.List;

import org.hock_bot.model.Status;
import org.hock_bot.model.Update;

public interface UpdateDAOI  extends GenericDAOI<Update, Long> {

	public List<org.hock_bot.model.Update> findByType(List<Status> statuses, int size);

	public void updateStatus(Status status, Long updateId);

	public void increaseRetryCount(Long updateId);

}
