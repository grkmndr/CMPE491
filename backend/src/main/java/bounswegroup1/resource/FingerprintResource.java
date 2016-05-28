package bounswegroup1.resource;


import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import bounswegroup1.db.FingerprintDAO;
import bounswegroup1.model.Fingerprint;

import io.dropwizard.auth.Auth;


@Path("/fingerprint")
@Produces(MediaType.APPLICATION_JSON)

public class FingerprintResource{
    private final FingerprintDAO fingerprintDAO;

    public FingerprintResource(FingerprintDAO fingerprintDAO) {
        super();
        this.fingerprintDAO = fingerprintDAO;
        
    }


    @POST
    public Fingerprint addFingerprint(Fingerprint fingerprint){
    	
    	fingerprintDAO.addFingerprint(fingerprint);
        return fingerprint;
    }

    @GET
    @Path("/{fingerprint}")
    public Fingerprint getSongForFingerprint(@PathParam("fingerprint") String fingerprint){
        return fingerprintDAO.getSongForFingerprint(fingerprint);
    }

}