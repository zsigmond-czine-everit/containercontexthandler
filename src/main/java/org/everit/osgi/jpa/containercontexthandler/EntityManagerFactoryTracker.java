package org.everit.osgi.jpa.containercontexthandler;

/*
 * Copyright (c) 2011, Everit Kft.
 *
 * All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */

import java.util.HashMap;

import javax.persistence.PersistenceContextType;

import org.apache.aries.jpa.container.PersistenceUnitConstants;
import org.apache.aries.jpa.container.context.PersistenceContextProvider;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class EntityManagerFactoryTracker implements ServiceTrackerCustomizer {

    private BundleContext context;

    private PersistenceContextProvider manager;

    public EntityManagerFactoryTracker(final BundleContext context) {
        this.context = context;
        ServiceReference managerServiceReference = context.getServiceReference(PersistenceContextProvider.class
                .getName());
        manager = (PersistenceContextProvider) context.getService(managerServiceReference);
    }

    @Override
    public Object addingService(final ServiceReference reference) {
        // EntityManagerFactory service = (EntityManagerFactory) context.getService(reference);
        // Map<String, Object> properties = service.getProperties();
        // HashMap<String, Object> hashMap = (properties instanceof HashMap) ? (HashMap) properties
        // : new HashMap<String, Object>(properties);
        // if (hashMap.get(PersistenceContextProvider.PERSISTENCE_CONTEXT_TYPE) != null) {
        // hashMap.put(PersistenceContextProvider.PERSISTENCE_CONTEXT_TYPE, PersistenceContextType.TRANSACTION);
        // }
        // String unitName = (String) properties.get(PersistenceUnitConstants.OSGI_UNIT_NAME);
        String[] propertyKeys = reference.getPropertyKeys();
        HashMap<String, Object> properties = new HashMap<String, Object>();
        for (String key : propertyKeys) {
            properties.put(key, reference.getProperty(key));
        }

        if (properties.get(PersistenceContextProvider.PERSISTENCE_CONTEXT_TYPE) == null) {
            properties.put(PersistenceContextProvider.PERSISTENCE_CONTEXT_TYPE, PersistenceContextType.TRANSACTION);
        }
        manager.registerContext((String) reference.getProperty(PersistenceUnitConstants.OSGI_UNIT_NAME),
                context.getBundle(),
                properties);

        return null;
    }

    @Override
    public void modifiedService(final ServiceReference reference, final Object service) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removedService(final ServiceReference reference, final Object service) {
        // TODO Auto-generated method stub

    }

}
