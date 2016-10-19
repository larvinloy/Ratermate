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

import java.math.BigInteger;
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
    public Session getAverages(@Named("sessionId") Long sessionId) throws NotFoundException {
        logger.info("Getting Session with ID: " + sessionId);
        Session session = ofy().load().type(Session.class).id(sessionId).now();
        List<Vote> data = ofy().load().type(Vote.class).filter("sessionId ==", sessionId).list();

        int catCount = session.getCategories().size();
        BigInteger n = new BigInteger(session.getN());
        BigInteger nsquare = n.multiply(n);

        ArrayList<BigInteger> sum = new ArrayList<BigInteger>();
        ArrayList<String> sumString = new ArrayList<String>();
        ArrayList<String> averageString = new ArrayList<String>();

        String info;
        info = catCount + " vote list size:" + data.size() + " every vote array size: ";
        for (Vote vote :data)
        {
            ArrayList<String> ratings = vote.getVotes();

            info += ratings.size() + " ";

            for(int i = 0 ;i < 2 ;i++)
            {
                if(sum.size() < 2)
                {
                    sum.add(i,new BigInteger(ratings.get(i)));
                }
                else
                {
                    sum.set(i,sum.get(i).multiply(new BigInteger(ratings.get(i))).mod(nsquare));
                }
            }
        }

        //average done here
        double fdivisor = (double) (1.0/(double)data.size());

        while(fdivisor < 1000000)
            fdivisor*=10;
        BigInteger divisor = new BigInteger(String.valueOf((int)fdivisor));

        for(int i = 0 ;i < 2 ;i++)
        {
            BigInteger average = sum.get(i).modPow(divisor,nsquare);
            averageString.add(average.toString());
        }

        session.setAverages(averageString);


        if (session == null)
        {
            throw new NotFoundException("Could not find Session with ID: " + sessionId);
        }
//        session.setG(info);
        return session;
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