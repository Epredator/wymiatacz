package com.innovationpassport.backend;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends EntityRepository<Message, Long> {
    QueryResult<Message> findByRead(boolean read);

    QueryResult<Message> findByFlagIsNotNullAndRead(boolean read);

    List<Message> findByFlagIsNotNullAndTrashed(boolean trashed);
}
