package fi.zcode.surveillance.resource;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * REST Web Service
 *
 * @author mlyly
 */
@Path("/translation")
@Component
public class TranslationResource {

    private static final Logger LOG = LoggerFactory.getLogger(TranslationResource.class);

    public TranslationResource() {
        LOG.info("*** TranslationResource() ***");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<TranslationRDTO> find() {
        LOG.info("find()");
        return new ArrayList<TranslationRDTO>();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public TranslationRDTO findById(@PathParam("id") String id) {
        LOG.info("findById({})", id);
        TranslationRDTO result = new TranslationRDTO();
        result.setId(id);
        return result;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public void update(TranslationRDTO translation) {
        LOG.info("update({})", translation);
    }

    @DELETE
    public void delete() {
        LOG.info("delete()");
    }
}
