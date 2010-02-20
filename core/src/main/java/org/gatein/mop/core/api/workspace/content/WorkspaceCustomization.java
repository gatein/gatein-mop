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
package org.gatein.mop.core.api.workspace.content;

import org.chromattic.api.annotations.ManyToOne;
import org.chromattic.api.annotations.Destroy;
import org.chromattic.api.annotations.PrimaryType;
import org.chromattic.api.annotations.Property;
import org.gatein.mop.api.content.CustomizationContext;
import org.gatein.mop.api.content.ContentType;
import org.gatein.mop.core.api.workspace.UIWindowImpl;
import org.gatein.mop.core.api.content.ContentRegistration;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@PrimaryType(name = "mop:workspacecustomization")
public abstract class WorkspaceCustomization extends AbstractCustomization
{

   @Property(name = "mop:mimetype")
   public abstract String getMimeType();

   public abstract void setMimeType(String mimeType);

   @Property(name = "mop:contentid")
   public abstract String getContentId();

   public abstract void setContentId(String contentId);

   @ManyToOne
   public abstract Object getOwner();

   @Destroy
   public abstract void doDestroy();

   public final CustomizationContext getContext()
   {
      Object owner = getOwner();
      if (owner instanceof CustomizationContainer)
      {
         return ((CustomizationContainer)owner).getOwner();
      }
      else if (owner instanceof UIWindowImpl)
      {
         return (UIWindowImpl)owner;
      }
      else
      {
         throw new AssertionError();
      }
   }

   public final ContentType<Object> getType()
   {
      ContentRegistration registration = registry.providers.get(getMimeType());
      if (registration != null)
      {
         return (ContentType<Object>)registration.getContentType();
      }
      else
      {
         return null;
      }
   }
}
