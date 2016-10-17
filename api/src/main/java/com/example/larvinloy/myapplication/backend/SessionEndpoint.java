package com.example.larvinloy.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "sessionApi",
        version = "v1",
        resource = "session",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.larvinloy.example.com",
                ownerName = "backend.myapplication.larvinloy.example.com",
                packagePath = ""
        )
)
public class SessionEndpoint {

    private static final Logger logger = Logger.getLogger(SessionEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Session.class);
    }

    /**
     * Returns the {@link Session} with the corresponding ID.
     *
     * @param sessionId the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Session} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "session/{sessionId}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Session get(@Named("sessionId") Long sessionId) throws NotFoundException {
        logger.info("Getting Session with ID: " + sessionId);
        Session session = ofy().load().type(Session.class).id(sessionId).now();
        if (session == null) {
            throw new NotFoundException("Could not find Session with ID: " + sessionId);
        }
        return session;
    }

    /**
     * Inserts a new {@code Session}.
     */
    @ApiMethod(
            name = "insert",
            path = "session",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Session insert(Session session) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that session.sessionId has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(session).now();
        logger.info("Created Session with ID: " + session.getSessionId());

        return ofy().load().entity(session).now();
    }

    /**
     * Returns the {@link Session} with the corresponding ID.
     *
     * @param sessionId the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Session} with the provided ID.
     */
    @ApiMethod(
            name = "getAverages",
            path = "session/getAverages/{sessionId}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public List<Vote> getAverages(@Named("sessionId") Long sessionId) throws NotFoundException {
        logger.info("Getting Session with ID: " + sessionId);
//        Session session = ofy().load().type(Session.class).id(sessionId).now();
        List<Vote> data = ofy().load().type(Vote.class).filter("sessionId ==", sessionId).list();
//        if (session == null) {
//            throw new NotFoundException("Could not find Session with ID: " + sessionId);
//        }
        return data;
    }

    /**
     * Updates an existing {@code Session}.
     *
     * @param sessionId the ID of the entity to be updated
     * @param session   the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code sessionId} does not correspond to an existing
     *                           {@code Session}
     */
    @ApiMethod(
            name = "update",
            path = "session/{sessionId}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Session update(@Named("sessionId") Long sessionId, Session session) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(sessionId);
        ofy().save().entity(session).now();
        logger.info("Updated Session: " + session);
        return ofy().load().entity(session).now();
    }

    /**
     * Deletes the specified {@code Session}.
     *
     * @param sessionId the ID of the entity to delete
     * @throws NotFoundException if the {@code sessionId} does not correspond to an existing
     *                           {@code Session}
     */
    @ApiMethod(
            name = "remove",
            path = "session/{sessionId}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("sessionId") Long sessionId) throws NotFoundException {
        checkExists(sessionId);
        ofy().delete().type(Session.class).id(sessionId).now();
        logger.info("Deleted Session with ID: " + sessionId);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "session",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Session> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Session> query = ofy().load().type(Session.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Session> queryIterator = query.iterator();
        List<Session> sessionList = new ArrayList<Session>(limit);
        while (queryIterator.hasNext()) {
            sessionList.add(queryIterator.next());
        }
        return CollectionResponse.<Session>builder().setItems(sessionList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long sessionId) throws NotFoundException {
        try {
            ofy().load().type(Session.class).id(sessionId).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Session with ID: " + sessionId);
        }
    }
}