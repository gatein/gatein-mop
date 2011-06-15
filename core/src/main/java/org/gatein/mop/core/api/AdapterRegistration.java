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
import org.gatein.mop.spi.AdapterLifeCycle;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
class AdapterRegistration<T, A>
{

   /** . */
   private static final Map<Class<?>, AdapterRegistration<?, ?>> instances;

   static
   {
      ServiceLoader<AdapterLifeCycle> loader = ServiceLoader.load(AdapterLifeCycle.class);
      Map<Class<?>, AdapterRegistration<?, ?>> map = new HashMap<Class<?>, AdapterRegistration<?, ?>>();
      for (AdapterLifeCycle factory : loader)
      {
         AdapterRegistration registration = new AdapterRegistration(factory);
         map.put(registration.adapterType, registration);
      }

      //
      instances = map;
   }

   static <A> AdapterRegistration<Object, A> getInstance(Class<A> type)
   {
      return (AdapterRegistration<Object, A>)instances.get(type);
   }

   /** . */
   final AdapterLifeCycle<T, A> factory;

   /** . */
   final Class<T> adapteeType;

   /** . */
   final Class<A> adapterType;

   AdapterRegistration(AdapterLifeCycle<T, A> factory)
   {
      this.factory = factory;
      this.adapteeType = (Class<T>)Tools.resolve(factory.getClass(), AdapterLifeCycle.class, 0);
      this.adapterType = (Class<A>)Tools.resolve(factory.getClass(), AdapterLifeCycle.class, 1);
   }
}
