/**
 * Copyright (C) 2009 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.gatein.mop.core.api;

import org.gatein.mop.core.util.Tools;
import org.gatein.mop.spi.AdapterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
class AdapterFactoryRegistration<T, A>
{

   /** . */
   private static final Map<Class<?>, AdapterFactoryRegistration<?, ?>> instances;

   static
   {
      ServiceLoader<AdapterFactory> loader = ServiceLoader.load(AdapterFactory.class);
      Map<Class<?>, AdapterFactoryRegistration<?, ?>> map = new HashMap<Class<?>, AdapterFactoryRegistration<?, ?>>();
      for (AdapterFactory factory : loader)
      {
         AdapterFactoryRegistration registration = new AdapterFactoryRegistration(factory);
         map.put(registration.adapterType, registration);
      }

      //
      instances = map;
   }

   static <A> AdapterFactoryRegistration<Object, A> getInstance(Class<A> type)
   {
      return (AdapterFactoryRegistration<Object, A>)instances.get(type);
   }

   /** . */
   final AdapterFactory<T, A> factory;

   /** . */
   final Class<T> adapteeType;

   /** . */
   final Class<A> adapterType;

   AdapterFactoryRegistration(AdapterFactory<T, A> factory)
   {
      this.factory = factory;
      this.adapteeType = (Class<T>)Tools.resolve(factory.getClass(), AdapterFactory.class, 0);
      this.adapterType = (Class<A>)Tools.resolve(factory.getClass(), AdapterFactory.class, 1);
   }
}
