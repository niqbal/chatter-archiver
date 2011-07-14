package com.gro.DataAccess;

import com.force.sdk.oauth.context.ForceSecurityContextHolder;
import com.force.sdk.oauth.context.SecurityContext;
import com.gro.utils.ChatterResponse;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


@Service
public class ChatterService {
    public static final String API_VERSION = "v22.0";
    private static final Logger logger = LoggerFactory.getLogger(ChatterService.class);

    @Autowired
    DB db;

    public List<BasicDBObject> getMentions() throws IOException {
        URLConnection urlConn = buildConn("/services/data/" + API_VERSION + "/chatter/feeds/news/me", false);
        String json = readResult(urlConn);
        ObjectMapper mapper = new ObjectMapper();
        ChatterResponse r = mapper.readValue(json.getBytes(), ChatterResponse.class);
        for(DBObject o:r.getFeedItems().getItems()) {
            o.put("_id", o.get("id"));
            db.getCollection("myposts").save(o);
        }

        return r.getFeedItems().getItems();
    }

    public List<DBObject> getArchived() throws IOException {
        DBObject condition = (DBObject) JSON.parse("{\"user.id\":\"" + getUserId() + "\"}");
        DBObject sort = (DBObject) JSON.parse("{\"modifiedDate\":-1}");
        return db.getCollection("myposts").find(condition).sort(sort).toArray();
    }


    /**
     * Builds the connection to the specified API destination based on data from the logged 
     * in user. Optionally the userid can be added to the end of the URL.
     */
    private URLConnection buildConn(String destination, boolean includeUserId) throws IOException {
        SecurityContext sc = ForceSecurityContextHolder.get(false);
        
        String endpoint = getEndpoint();
        String url = endpoint + destination + (includeUserId ? sc.getUserId() : "");
        
        URL restUrl = new URL(url);
        URLConnection urlConn = restUrl.openConnection();
        urlConn.addRequestProperty("Authorization", "OAuth " + sc.getSessionId());
        
        return urlConn;
    }

    /**
     * Get the user id of the logged in user.
     */
    private String getUserId() {
        SecurityContext sc = ForceSecurityContextHolder.get(false);
        return sc.getUserId();
    }
    
    /**
     * Get the API endpoint for the logged in user.
     */
    private String getEndpoint() {
        SecurityContext sc = ForceSecurityContextHolder.get(false);
        return sc.getEndPoint().substring(0, sc.getEndPoint().indexOf("/services"));
    }


    private String readResult(URLConnection urlConn) throws IOException {
        BufferedReader in = null;
        StringBuilder jsonReturn = new StringBuilder();
        
        try {
            in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            
            String inputLine;
            
            while((inputLine = in.readLine()) != null) {
                jsonReturn.append(inputLine);
            }            
        } catch(IOException e) {
            BufferedReader errorIn = null;
            try {                
                errorIn = new BufferedReader(new InputStreamReader(((HttpURLConnection)urlConn).getErrorStream()));
                String apiErrorMessage = errorIn.readLine();
                logger.error("Error while connecting to the REST API: " + apiErrorMessage);
                throw new IOException(apiErrorMessage, e);
            } finally {
                if(errorIn != null) {
                    try {
                        errorIn.close();
                    } catch (IOException e1) {
                        logger.error("Error closing error input stream from chatter api call.");
                    }
                }                
            }
        }
        finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    logger.error("Error closing input stream from chatter api call.");
                }
            }
        }
        
       return jsonReturn.toString();
    }

}
