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

import javax.persistence.EntityManagerFactory;

import org.apache.aries.jpa.container.context.PersistenceContextProvider;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class PersistenceProviderTracker implements ServiceTrackerCustomizer {

    private BundleContext context;

    private ServiceTracker serviceTracker;

    public PersistenceProviderTracker(final BundleContext context) {
        this.context = context;
    }

    @Override
    public Object addingService(final ServiceReference reference) {
        EntityManagerFactoryTracker entityManagerFactoryTracker = new EntityManagerFactoryTracker(context);
        try {
            Filter filter = context.createFilter("(&(" + Constants.OBJECTCLASS + "="
                    + EntityManagerFactory.class.getName() + ")" +
                    "(!(" + PersistenceContextProvider.PROXY_FACTORY_EMF_ATTRIBUTE + "=*)))");
            serviceTracker = new ServiceTracker(context, filter, entityManagerFactoryTracker);
            serviceTracker.open();
        } catch (InvalidSyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void modifiedService(final ServiceReference reference, final Object service) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removedService(final ServiceReference reference, final Object service) {
        serviceTracker.close();
    }

}
