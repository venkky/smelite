/**
 * $Id: AbstractRESTProvider.java 17 2009-02-17 12:33:06Z azeckoski $
 * $URL: http://entitybus.googlecode.com/svn/tags/entitybus-1.0.2/webapp/src/main/java/org/sakaiproject/rest/providers/AbstractRESTProvider.java $
 * AbstractEntityProvider.java - entity-broker - Apr 30, 2008 7:26:11 PM - azeckoski
 **************************************************************************
 * Copyright (c) 2008 Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.above.smlite.entity.providers;

import org.sakaiproject.entitybus.entityprovider.EntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;

/**
 * Makes it easier to write {@link EntityProvider}s in webapps <br/>
 * A class to extend that gets rid of some of the redundant code that has
 * to be written over and over, causes this provider to be registered when it
 * is created and unregistered when it is destroyed
 * 
 * @author Aaron Zeckoski (aaron@caret.cam.ac.uk)
 */
public abstract class AbstractRESTProvider implements EntityProvider {

    public AbstractRESTProvider(EntityProviderManager entityProviderManager) {
        this.entityProviderManager = entityProviderManager;
        try {
            init();
        } catch (Exception e) {
            throw new RuntimeException("Unable to register the provider ("+this+"): " + e, e);
        }
    }

    private EntityProviderManager entityProviderManager;
    public void setEntityProviderManager(EntityProviderManager entityProviderManager) {
        this.entityProviderManager = entityProviderManager;
    }

    public void init() throws Exception {
        entityProviderManager.registerEntityProvider(this);
    }

    public void destroy() throws Exception {
        entityProviderManager.unregisterEntityProvider(this);
    }

}
