package com.ratermate.rating.entities;

import com.ratermate.rating.entities.PMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@Api(name = "sessionendpoint", namespace = @ApiNamespace(ownerDomain = "ratermate.com", ownerName = "ratermate.com", packagePath = "rating.entities"))
public class SessionEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listSession")
	public CollectionResponse<Session> listSession(@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<Session> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(Session.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<Session>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Session obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Session>builder().setItems(execute).setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getSession")
	public Session getSession(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		Session session = null;
		try {
			session = mgr.getObjectById(Session.class, id);
		} finally {
			mgr.close();
		}
		return session;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param session the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertSession")
	public Session insertSession(Session session) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (session.getSessionId() != null) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(session);
		} finally {
			mgr.close();
		}
		return session;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param session the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateSession")
	public Session updateSession(Session session) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (!containsSession(session)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.makePersistent(session);
		} finally {
			mgr.close();
		}
		return session;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeSession")
	public void removeSession(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			Session session = mgr.getObjectById(Session.class, id);
			mgr.deletePersistent(session);
		} finally {
			mgr.close();
		}
	}

	private boolean containsSession(Session session) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(Session.class, session.getSessionId());
		} catch (javax.jdo.JDOObjectNotFoundException ex) {
			contains = false;
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}
