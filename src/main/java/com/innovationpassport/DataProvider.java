package com.innovationpassport;

/**
 * User: trojnaradam@gmail.com
 * Date: 14.03.16
 * Time: 08:23
 */

import com.innovationpassport.model.Ticket;

import java.util.Collection;

/**
 * backend API.
 */
public interface DataProvider {

  /**
   * @param count
   *            Number of transactions to fetch.
   * @return A Collection of most recent transactions.
   */
  Collection<Ticket> getRecentTicket(int count);
}
