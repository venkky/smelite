package com.above.smlite.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sakaiproject.entitybus.EntityBrokerManager;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.impl.EntityBrokerCoreServiceManager;
import org.sakaiproject.entitybus.providers.EntityRequestHandler;
import org.sakaiproject.entitybus.rest.EntityBrokerRESTServiceManager;
import org.sakaiproject.entitybus.util.servlet.DirectServlet;

import com.above.smlite.entity.providers.AbstractRESTProvider;
import com.above.smlite.entity.providers.SpendProjectionProvider;
import com.above.smlite.entity.providers.VendorProvider;

/**
 * Servlet implementation class SmliteController
 */
public class SmliteController extends DirectServlet {

    private static final long serialVersionUID = 2L;

    private transient EntityBrokerCoreServiceManager entityBrokerCoreServiceManager;
    private transient EntityBrokerRESTServiceManager entityRESTServiceManager;

    private transient List<AbstractRESTProvider> entityProviders;

    protected void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
    	res.setHeader( "Pragma", "no-cache" );
    	res.setHeader( "Cache-Control", "no-cache" );  
    	res.setDateHeader( "Expires", 0 );
    	super.service(req, res);
    }
    
    /**
     * Starts up all the entity providers and places them into the list
     * @param entityProviderManager the provider manager
     */
    protected void startProviders(EntityProviderManager entityProviderManager) {
        this.entityProviders = new Vector<AbstractRESTProvider>();
        this.entityProviders.add( new VendorProvider(entityProviderManager) );
        this.entityProviders.add( new SpendProjectionProvider(entityProviderManager) );
    }

    @Override
    public EntityRequestHandler initializeEntityRequestHandler() {
        EntityRequestHandler erh;
        try {
            // fire up the EB services
            this.entityBrokerCoreServiceManager = new EntityBrokerCoreServiceManager();
            EntityBrokerManager ebm = this.entityBrokerCoreServiceManager.getEntityBrokerManager();
            // create the EB REST services
            this.entityRESTServiceManager = new EntityBrokerRESTServiceManager(ebm);
            erh = this.entityRESTServiceManager.getEntityRequestHandler();
            if (erh == null) {
                throw new RuntimeException("FAILED to load EB EntityRequestHandler");
            }

            EntityProviderManager epm = this.entityBrokerCoreServiceManager.getEntityProviderManager();
            // fire up the providers
            startProviders(epm);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("FAILURE during init of direct servlet: " + e, e);
        }
        return erh;
    }

    @Override
    public void destroy() {
        super.destroy();
        if (this.entityProviders != null) {
            for (AbstractRESTProvider provider : entityProviders) {
                if (provider != null) {
                    try {
                        provider.destroy();
                    } catch (Exception e) {
                        System.err.println("WARN Could not clean up provider ("+provider+") on destroy: " + e);
                    }
                }
            }
            this.entityProviders.clear();
            this.entityProviders = null;
        }
        if (this.entityRESTServiceManager != null) {
            this.entityRESTServiceManager.destroy();
            this.entityRESTServiceManager = null;
        }
        if (this.entityBrokerCoreServiceManager != null) {
            this.entityBrokerCoreServiceManager.destroy();
            this.entityBrokerCoreServiceManager = null;
        }
    }

    @Override
    public String getCurrentLoggedInUserId() {
        return "tester";
    }

    @Override
    public void handleUserLogin(HttpServletRequest req, HttpServletResponse res, String path) {
        // attempt basic auth first?
        throw new SecurityException("Not able to handle login redirects yet");
    }

}
