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

import org.apache.aries.jpa.container.context.PersistenceContextProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {

    private ServiceTracker tracker;

    @Override
    public void start(final BundleContext context) throws Exception {
        PersistenceProviderTracker persistenceProviderTracker = new PersistenceProviderTracker(context);
        tracker = new ServiceTracker(context, PersistenceContextProvider.class.getName(), persistenceProviderTracker);
        tracker.open();
    }

    @Override
    public void stop(final BundleContext context) throws Exception {
        tracker.close();
    }

}
