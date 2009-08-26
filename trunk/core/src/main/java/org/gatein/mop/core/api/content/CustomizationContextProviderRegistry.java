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
package org.gatein.mop.core.api.content;

import org.gatein.mop.spi.customization.CustomizationContextProvider;
import org.gatein.mop.api.content.CustomizationContext;

import java.util.Map;
import java.util.HashMap;

/**
 * A global registry.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class CustomizationContextProviderRegistry implements CustomizationContextResolver {

  /** The various providers. */
  final transient Map<String, CustomizationContextProvider> resolvers;

  public CustomizationContextProviderRegistry() {
    resolvers = new HashMap<String, CustomizationContextProvider>();
  }

  public synchronized void register(String contextType, CustomizationContextProvider contentProvider) {
    if (contextType == null) {
      throw new NullPointerException();
    }
    if (contentProvider == null) {
      throw new NullPointerException();
    }
    resolvers.put(contextType, contentProvider);
  }

  public synchronized void unregister(String contextType) {
    if (contextType == null) {
      throw new NullPointerException();
    }
    resolvers.remove(contextType);
  }

  public CustomizationContext resolve(String contextType, String contextId) {
    if (contextType == null) {
      throw new NullPointerException();
    }
    if (contextId == null) {
      throw new NullPointerException();
    }

    //
    CustomizationContextProvider provider = resolvers.get(contextType);

    //
    if (provider != null) {
      return provider.resolveContext(contextId);
    }

    //
    return null;
  }
}