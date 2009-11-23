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

import org.gatein.mop.api.content.ContentType;
import org.gatein.mop.spi.content.ContentProvider;

import java.util.Map;
import java.util.HashMap;

/**
 * A global registry.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class ContentManagerRegistry
{

   /** The various providers. */
   public final transient Map<String, ContentRegistration> providers;

   public ContentManagerRegistry()
   {
      providers = new HashMap<String, ContentRegistration>();
   }

   public synchronized <S> void register(ContentType<S> contentType, ContentProvider<S> contentProvider)
   {
      if (contentType == null)
      {
         throw new NullPointerException();
      }
      if (contentProvider == null)
      {
         throw new NullPointerException();
      }
      providers.put(contentType.getMimeType(), new ContentRegistration(contentType, contentProvider));
   }

   public synchronized void unregister(ContentType contentType)
   {
      if (contentType == null)
      {
         throw new NullPointerException();
      }
      providers.remove(contentType.getMimeType());
   }
}